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

package io.getstream.avatarview.glide

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import io.getstream.avatarview.AvatarBitmapCombiner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext

/**
 * A Bitmap loader to emit loaded avatar bitmaps.
 */
@PublishedApi
internal object AvatarBitmapLoader {

    /**
     * Load Bitmaps and emit them on callback flow.
     *
     * @param requestManager A class for managing and starting requests for Glide.
     * @param data A list of model to load images.
     * @param errorPlaceholder An error placeholder that should be shown when request failed.
     *
     * @return A flow of [AvatarResult].
     */
    @PublishedApi
    internal fun loadBitmaps(
        requestManager: RequestManager,
        data: List<Any?>,
        errorPlaceholder: Drawable? = null
    ): Flow<AvatarResult> = callbackFlow {

        var avatarViewTarget: CustomTarget<Bitmap>? = null

        withContext(Dispatchers.IO) {
            data.forEach { endPoint ->
                avatarViewTarget = object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        trySend(AvatarResult.Success(resource))
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)

                        val error = errorPlaceholder ?: errorDrawable ?: let {
                            trySend(AvatarResult.Failure(null))
                            return
                        }

                        AvatarBitmapCombiner.drawableToBitmap(error, error.intrinsicWidth)?.let {
                            trySend(AvatarResult.Success(it))
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) = Unit
                }.also { target ->
                    requestManager
                        .asBitmap()
                        .load(endPoint)
                        .into(target)
                }
            }
        }

        awaitClose {
            requestManager.clear(avatarViewTarget)
        }
    }
}
