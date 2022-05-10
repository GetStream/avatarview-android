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
import android.os.Build
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

/**
 * An [ImageLoader] factory to provide an instance of the [ImageLoader].
 *
 * AvatarImageLoaderFactory creates a default [ImageLoader] that has
 * caching strategy with OkHttp, image decoder (supports GIFs), and [AvatarFetcher].
 */
public class AvatarImageLoaderFactory(
    private val context: Context,
    private val builder: ImageLoader.Builder.() -> Unit = {}
) : ImageLoaderFactory {

    /** Creates a new [ImageLoader] to load [Avatar] image data. */
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(context)
            .allowHardware(false)
            .crossfade(true)
            .components {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
                add(AvatarFetcher(context))
            }
            .apply(builder)
            .build()
    }
}
