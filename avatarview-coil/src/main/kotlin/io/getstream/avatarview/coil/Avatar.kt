/*
 * Copyright 2021 Stream.IO, Inc. All Rights Reserved.
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

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.annotation.DrawableRes
import coil.request.ImageRequest
import coil.request.ImageResult
import okhttp3.HttpUrl
import java.io.File

/**
 * A data transfer model for transferring image data to [AvatarFetcher].
 * This model will be fetched by [AvatarFetcher] when we request image loading by using this type to Coil.
 *
 * The default supported data types are:
 * - [String] (mapped to a [Uri])
 * - [Uri] ("android.resource", "content", "file", "http", and "https" schemes only)
 * - [HttpUrl]
 * - [File]
 * - [DrawableRes]
 * - [Drawable]
 * - [Bitmap]
 *
 * @param data A list of image data.
 * @param maxSectionSize The maximum section size of the avatar when loading multiple images.
 * @param avatarBorderWidth The border width of AvatarView.
 * @param errorPlaceholder An error placeholder that should be shown when request failed.
 */
public data class Avatar(

    /** A list of data to be requested. */
    val data: List<Any?>,

    /** The maximum size of the sections. */
    val maxSectionSize: Int,

    /** The border width of AvatarView. */
    val avatarBorderWidth: Int,

    /** An error placeholder that should be shown when request failed. */
    val errorPlaceholder: Drawable?,

    /** A lambda function will be executed when loading succeeds. */
    val onSuccess: (request: ImageRequest, metadata: ImageResult.Metadata) -> Unit,

    /** A lambda function will be executed when loading failed. */
    val onError: (request: ImageRequest, throwable: Throwable) -> Unit,
) {
    private val bagOfTags: MutableMap<String, Any> = mutableMapOf()

    /**
     * Sets a tag associated with this avatar and a key.
     * If the given [newValue] was already set for the given key, this calls do nothing,
     * the given [newValue] would be ignored.
     *
     * @param key A new key to set a tag associated with this avatar.
     * @param newValue A new value to be set on the bag.
     */
    @Suppress("UNCHECKED_CAST")
    public fun <T : Any> setTagIfAbsent(key: String, newValue: T) {
        synchronized(bagOfTags) {
            val previous = bagOfTags[key] as? T
            if (previous == null) {
                bagOfTags[key] = newValue
            }
        }
    }

    /**
     * Returns the tag associated with this avatar using the given [key].
     *
     * @param key A new key to get a tag associated with this avatar.
     *
     * @return A tag in the bag.
     */
    @Suppress("UNCHECKED_CAST")
    public fun <T : Any> getTag(key: String): T? {
        synchronized(bagOfTags) {
            return bagOfTags[key] as? T
        }
    }
}
