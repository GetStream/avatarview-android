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
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import io.getstream.avatarview.AvatarBitmapCombiner
import io.getstream.avatarview.AvatarShape
import io.getstream.avatarview.AvatarView
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Loads an image request [data] to the [AvatarView].
 *
 * @param data An image data to be loaded.
 * @param crossFadeEnabled Enables crossFade animation when load an image.
 * @param requestListener A class for monitoring the status of a request while images load.
 * @param requestOptions A receiver to be applied with the [RequestOptions].
 */
@JvmSynthetic
public inline fun AvatarView.loadImage(
    data: Any?,
    crossFadeEnabled: Boolean = true,
    requestListener: RequestListener<Drawable>? = null,
    requestOptions: () -> RequestOptions = { RequestOptions() }
) {
    loadPlaceHolder()
    val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(crossFadeEnabled).build()
    Glide.with(this)
        .load(data)
        .error(errorPlaceholder)
        .apply(requestOptions())
        .listener(requestListener)
        .transition(withCrossFade(factory))
        .transform(transformation)
        .into(this)
}

/**
 * Loads an image request [data] to the [AvatarView].
 *
 * @param data An image data to be loaded.
 * @param requestBuilder A generic class that can handle setting options and staring loads for generic resource types.
 */
@JvmSynthetic
public fun AvatarView.loadImage(
    data: Any?,
    requestBuilder: RequestBuilder<*>
) {
    loadPlaceHolder()
    requestBuilder
        .load(data)
        .transform(transformation)
        .into(this)
}

/**
 * Loads a list of image request [data] to the [AvatarView].
 * Up to 4 images will be combined and loaded.
 *
 * @param data A list of image data to be loaded.
 * @param requestListener A class for monitoring the status of a request while images load.
 * @param requestOptions A receiver to be applied with the [RequestOptions].
 */
public fun AvatarView.loadImage(
    data: List<Any?>,
    crossFadeEnabled: Boolean = true,
    requestListener: RequestListener<Drawable>? = null,
    requestOptions: () -> RequestOptions = { RequestOptions() }
) {
    loadPlaceHolder()
    collectAndCombineBitmaps(data) { sectionedBitmap ->
        this@loadImage.loadImage(
            data = sectionedBitmap,
            crossFadeEnabled = crossFadeEnabled,
            requestListener = requestListener,
            requestOptions = { requestOptions().diskCacheStrategy(DiskCacheStrategy.NONE) }
        )
    }
}

/**
 * Loads a list of image request [data] to the [AvatarView].
 * Up to 4 images will be combined and loaded.
 *
 * @param data A list of image data to be loaded.
 * @param requestBuilder A generic class that can handle setting options and staring loads for generic resource types.
 */
public fun AvatarView.loadImage(
    data: List<Any?>,
    requestBuilder: RequestBuilder<*>
) {
    loadPlaceHolder()
    collectAndCombineBitmaps(data) { sectionedBitmap ->
        this@loadImage.loadImage(
            data = sectionedBitmap,
            requestBuilder = requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE)
        )
    }
}

/**
 * Collects multiple Bitmap and combines them as one sectioned image.
 *
 * @param data A list of image data to be loaded.
 * @param onLoadImage An executable lambda to load a sectioned image.
 */
@PublishedApi
@JvmSynthetic
internal inline fun AvatarView.collectAndCombineBitmaps(
    data: List<Any?>,
    crossinline onLoadImage: AvatarView.(Bitmap) -> Unit
) {
    launch {
        val avatarResults: ArrayList<AvatarResult> = arrayListOf()
        val avatarResultFlow = AvatarBitmapLoader.loadBitmaps(
            requestManager = Glide.with(this@collectAndCombineBitmaps),
            data = data,
            errorPlaceholder = errorPlaceholder
        )

        avatarResultFlow.collect {
            avatarResults.add(it)

            if (avatarResults.size == data.size.coerceAtMost(maxSectionSize)) {
                val bitmaps: List<Bitmap> =
                    avatarResults.filterIsInstance<AvatarResult.Success>()
                        .map { success -> success.bitmap }

                if (bitmaps.isNotEmpty()) {
                    val sectionedBitmap =
                        AvatarBitmapCombiner.combine(
                            bitmaps = bitmaps,
                            size = bitmaps.last().width - avatarBorderWidth * 2,
                            maxSectionSize = maxSectionSize,
                            errorPlaceholder = errorPlaceholder
                        )

                    onLoadImage(sectionedBitmap)
                }
                cancel()
            }
        }
    }
}

/** Loads a placeholder with the [AvatarView.placeholder] property. */
@PublishedApi
internal fun AvatarView.loadPlaceHolder() {
    placeholder?.let {
        Glide.with(context)
            .load(it)
            .transform(transformation)
            .into(this)
    }
}

/** Returns a [MultiTransformation] from a [AvatarView]. */
@PublishedApi
internal val AvatarView.transformation: MultiTransformation<Bitmap>
    @JvmSynthetic inline get() = when (avatarShape) {
        AvatarShape.CIRCLE -> MultiTransformation(CircleCrop())
        AvatarShape.ROUNDED_RECT -> MultiTransformation(
            CenterCrop(),
            RoundedCorners(avatarBorderRadius.toInt())
        )
    }
