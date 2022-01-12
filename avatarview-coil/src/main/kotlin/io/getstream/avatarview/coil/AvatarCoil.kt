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

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.ImageLoaderFactory
import io.getstream.avatarview.coil.AvatarCoil.imageLoader
import io.getstream.avatarview.coil.AvatarCoil.setImageLoader

/**
 * AvatarCoil provides a [ImageLoader], [AvatarBitmapFactory], and [ImageHeadersProvider] that can be
 * fully customized for loading avatar image:
 *
 * - [imageLoader] be used to load [Avatar] payload internally. You can customize with your
 * own [ImageLoaderFactory] or [AvatarImageLoaderFactory] by using [setImageLoader] function.
 *
 * - [AvatarBitmapFactory] will creates avatar bitmaps when [AvatarFetcher] fetches
 * the [Avatar] payload successfully. The loaded bitmaps will be operated by the factory and they will be
 * loaded as [BitmapDrawable] to the [io.getstream.avatarview.AvatarView].
 *
 * - [ImageHeadersProvider] be used to provide image header. If you're using your own CDN,
 * you can set the [AvatarCoil.imageHeadersProvider] to load image data with your own header.
 */
public object AvatarCoil {

    private var imageLoader: ImageLoader? = null
    private var imageLoaderFactory: ImageLoaderFactory? = null

    /**
     * Sets a [ImageLoaderFactory] to provide your own [ImageLoader].
     *
     * @param factory An [ImageLoader] factory.
     */
    @Synchronized
    public fun setImageLoader(factory: ImageLoaderFactory) {
        imageLoaderFactory = factory
        imageLoader = null
    }

    /** Returns an [imageLoader] or [newImageLoader]. */
    @PublishedApi
    internal fun imageLoader(context: Context): ImageLoader = imageLoader ?: newImageLoader(context)

    /** Returns an [imageLoader] or a new [imageLoader] from the [imageLoaderFactory]. */
    @Synchronized
    private fun newImageLoader(context: Context): ImageLoader {
        imageLoader?.let { return it }

        val imageLoaderFactory = imageLoaderFactory ?: newImageLoaderFactory(context)
        return imageLoaderFactory.newImageLoader().apply {
            imageLoader = this
        }
    }

    /** Creates a new default instance of the [ImageLoaderFactory]. */
    private fun newImageLoaderFactory(context: Context): ImageLoaderFactory {
        return AvatarImageLoaderFactory(context).apply {
            imageLoaderFactory = this
        }
    }

    /** Returns an [imageLoader] to load avatar images. */
    @PublishedApi
    internal inline val Context.avatarImageLoader: ImageLoader
        get() = imageLoader(this)

    /** An avatar bitmap factory to create custom avatar bitmaps. */
    @SuppressLint("StaticFieldLeak")
    private var avatarBitmapFactory: AvatarBitmapFactory? = null

    /** Returns an [AvatarBitmapFactory]. */
    public fun getAvatarBitmapFactory(context: Context): AvatarBitmapFactory {
        return avatarBitmapFactory ?: synchronized(this) {
            avatarBitmapFactory ?: AvatarBitmapFactory(context.applicationContext).also {
                avatarBitmapFactory = it
            }
        }
    }

    /** Sets a custom [AvatarBitmapFactory]. */
    @Synchronized
    public fun setAvatarBitmapFactory(avatarBitmapFactory: AvatarBitmapFactory?) {
        this.avatarBitmapFactory = avatarBitmapFactory
    }

    /** Provides a custom image header. */
    public var imageHeadersProvider: ImageHeadersProvider = DefaultImageHeadersProvider
}
