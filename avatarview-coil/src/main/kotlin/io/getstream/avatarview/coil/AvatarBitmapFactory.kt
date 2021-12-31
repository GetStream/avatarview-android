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

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.Px
import coil.ImageLoader
import io.getstream.avatarview.AvatarBitmapCombiner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * A Bitmap factory to create avatar bitmaps.
 */
public open class AvatarBitmapFactory(private val context: Context) {

    /**
     * Creates a Bitmap with the image request [data] to represent the avatar in a suspending operation.
     *
     * @param data An image request model.
     * @param avatar An [Avatar] data model which includes request model and avatar styles.
     * @param avatarSize A specified avatar size.
     *
     * @return The loaded bitmap or null if the loading failed (e.g. network issues).
     */
    @JvmSynthetic
    internal suspend fun createAvatarBitmapInternal(
        data: Any?,
        avatar: Avatar,
        @Px avatarSize: Int
    ): Bitmap? {
        val customBitmap = withContext(Dispatchers.IO) {
            loadAvatarBitmapBlocking(data, avatar, avatarSize)
        }

        val bitmap = customBitmap ?: loadAvatarBitmap(data, avatar, avatarSize)
        if (bitmap != null) {
            return bitmap
        }

        val customPlaceholderBitmap = withContext(Dispatchers.IO) {
            loadAvatarPlaceholderBitmapBlocking(data, avatar, avatarSize)
        }

        return customPlaceholderBitmap ?: loadAvatarPlaceholderBitmap(data, avatar, avatarSize)
    }

    /**
     * Creates a Bitmap to represent an avatar image.
     *
     * This method takes precedence over [loadAvatarBitmap] if both are implemented.
     *
     * Override this method only if you can't provide a suspending implementation, otherwise
     * override [loadAvatarBitmap] instead.
     *
     * @param data An image request model.
     * @param avatar An [Avatar] data model which includes request model and avatar styles.
     * @param avatarSize A specified avatar size.
     *
     * @return The loaded bitmap or null if the loading failed (e.g. network issues).
     */
    public open fun loadAvatarBitmapBlocking(
        data: Any?,
        avatar: Avatar,
        @Px avatarSize: Int
    ): Bitmap? {
        return null
    }

    /**
     * Loads a Bitmap with the image request [data] to represent the avatar in a suspending operation.
     *
     * This method requests images by using the [ImageLoader] on an IO coroutines scope.
     * The [loadAvatarBitmapBlocking] method takes precedence over this one if both are implemented.
     * Prefer implementing this method if possible.
     *
     * @param data An image request model.
     * @param avatar An [Avatar] data model which includes request model and avatar styles.
     * @param avatarSize A specified avatar size.
     *
     * @return The loaded bitmap or null if the loading failed (e.g. network issues).
     */
    public open suspend fun loadAvatarBitmap(
        data: Any?,
        avatar: Avatar,
        @Px avatarSize: Int
    ): Bitmap? {
        return AvatarImageLoaderInternal.loadAsBitmap(
            context = context,
            data = data,
            onSuccess = avatar.onSuccess,
            onError = avatar.onError
        )
    }

    /**
     * Loads a Bitmap with the [avatar] to represent the placeholder of the avatar
     * in a suspending operation. This method will be executed if the previous image request failed.
     *
     * This method takes precedence over [loadAvatarPlaceholderBitmap] if both are implemented.
     *
     * Override this method only if you can't provide a suspending implementation, otherwise
     * override [loadAvatarPlaceholderBitmap] instead.
     *
     * @param data An image request model.
     * @param avatar An [Avatar] data model which includes request model and avatar styles.
     * @param avatarSize A specified avatar size.
     */
    public open fun loadAvatarPlaceholderBitmapBlocking(
        data: Any?,
        avatar: Avatar,
        @Px avatarSize: Int
    ): Bitmap? {
        return null
    }

    /**
     * Loads a Bitmap with the [avatar] to represent the placeholder of the avatar
     * in a suspending operation. This method will be executed if the previous image request failed.
     *
     * The [loadAvatarPlaceholderBitmapBlocking] method takes precedence over this one if both are implemented.
     * Prefer implementing this method if possible.
     *
     * @param data An image request model.
     * @param avatar An [Avatar] data model which includes request model and avatar styles.
     * @param avatarSize A specified avatar size.
     */
    public open suspend fun loadAvatarPlaceholderBitmap(
        data: Any?,
        avatar: Avatar,
        @Px avatarSize: Int
    ): Bitmap? {
        return null
    }

    /**
     * Creates a combined avatar Bitmap with the data model [avatar] and the specified [avatarSize]
     * to represent the avatar image, in a suspending operation.
     *
     * @param avatar An [Avatar] data model which includes request model and avatar styles.
     * @param avatarSize A specified avatar size.
     *
     * @return The combined bitmap or null if the loading failed (e.g. network issues).
     */
    public open suspend fun createAvatarBitmaps(
        avatar: Avatar,
        @Px avatarSize: Int,
    ): Bitmap? {
        return avatar.data.take(avatar.maxSectionSize)
            .map { createAvatarBitmapInternal(it, avatar, avatarSize) }
            .takeIf { it.isNotEmpty() }
            ?.let {
                AvatarBitmapCombiner.combine(
                    bitmaps = it,
                    size = avatarSize,
                    maxSectionSize = avatar.maxSectionSize,
                    errorPlaceholder = avatar.errorPlaceholder
                )
            }
    }

    /** Returns cache key for caching the avatar Bitmap image on memory. */
    public open fun avatarBitmapKey(avatar: Avatar): String? = "${avatar.data}"
}
