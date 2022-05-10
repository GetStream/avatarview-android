/*
 * Copyright 2022 Stream.IO, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.getstream.avatarview.coil

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.load
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.CircleCropTransformation
import coil.transform.Transformation
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.coil.AvatarCoil.avatarImageLoader
import io.getstream.avatarview.internal.InternalAvatarViewApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Headers.Companion.toHeaders

/** An internal image loader to request image data with Coil. */
@InternalAvatarViewApi
public object AvatarImageLoaderInternal {

    /**
     * Request an image [data] and loads it as a Bitmap in a suspending operation.
     *
     * If you're using your own CDN, you can set the [AvatarCoil.imageHeadersProvider] to load
     * image data.
     *
     * @param context A context to build [ImageRequest].
     * @param data An image [data].
     * @param onSuccess A lambda function will be executed when loading succeeds.
     * @param onError A lambda function will be executed when loading failed.
     *
     * @return The loaded bitmap or null if the loading failed (e.g. network issues).
     */
    @JvmSynthetic
    public suspend fun loadAsBitmap(
        context: Context,
        data: Any?,
        onSuccess: (request: ImageRequest, result: SuccessResult) -> Unit = { _, _ -> },
        onError: (request: ImageRequest, result: ErrorResult) -> Unit = { _, _ -> },
    ): Bitmap? = withContext(Dispatchers.IO) {
        val imageResult = context.avatarImageLoader.execute(
            ImageRequest.Builder(context)
                .headers(AvatarCoil.imageHeadersProvider.getImageRequestHeaders().toHeaders())
                .listener(onSuccess = onSuccess, onError = onError)
                .data(data)
                .build()
        )
        (imageResult.drawable as? BitmapDrawable)?.bitmap
    }

    /**
     * Loads an image [data] to the [target] AvatarView with [transformation].
     *
     * @param target A target [AvatarView] to load image data.
     * @param data An image data to be loaded.
     * @param transformation A [Transformation] to transform loaded images.
     * @param onStart A lambda function will be executed when start requesting.
     * @param onComplete A lambda function will be executed when finish loading.
     * @param builder A receiver to be applied with the [ImageRequest.Builder].
     */
    @JvmSynthetic
    @PublishedApi
    internal fun load(
        target: AvatarView,
        data: Any?,
        transformation: Transformation = CircleCropTransformation(),
        onStart: () -> Unit,
        onComplete: () -> Unit,
        builder: ImageRequest.Builder.() -> Unit
    ) {
        val context = target.context
        target.load(data, context.avatarImageLoader) {
            headers(AvatarCoil.imageHeadersProvider.getImageRequestHeaders().toHeaders())
            transformations(transformation)
            listener(
                onStart = { onStart() },
                onCancel = { onComplete() },
                onError = { _, _ -> onComplete() },
                onSuccess = { _, _ -> onComplete() },
            )
            apply(builder)
        }
    }
}
