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
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.decode.DataSource
import coil.fetch.DrawableResult
import coil.fetch.Fetcher
import coil.request.Options
import coil.size.pxOrElse

/**
 * An image request fetcher of [Avatar] data type.
 *
 * This fetcher will create a Bitmap using the [Avatar] data in the coroutines scope.
 */
internal class AvatarFetcher constructor(
    private val context: Context
) : Fetcher.Factory<Avatar> {

    override fun create(data: Avatar, options: Options, imageLoader: ImageLoader): Fetcher {
        val targetSize = options.size.width.pxOrElse { 0 }
        val resources = options.context.resources
        return Fetcher {
            DrawableResult(
                BitmapDrawable(
                    resources,
                    AvatarCoil.getAvatarBitmapFactory(context).createAvatarBitmaps(
                        data,
                        targetSize - data.avatarBorderWidth * 2
                    )
                ),
                false,
                DataSource.MEMORY
            )
        }
    }
}
