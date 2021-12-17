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

package io.getstream.avatarview

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.media.ThumbnailUtils
import androidx.annotation.Px
import androidx.core.graphics.applyCanvas
import io.getstream.avatarview.internal.InternalAvatarViewApi

/**
 * A bitmap combiner to provide segmented style bitmap from a list of bitmaps.
 * This combiner supports a maximum of 4 combined bitmaps.
 */
public object AvatarBitmapCombiner {

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        isAntiAlias = true
        isFilterBitmap = true
        isDither = true
    }

    /**
     * Combines a list of bitmaps with a specific bitmap size.
     *
     * @param bitmaps A list of bitmaps to combine.
     * @param size A size of the bitmap.
     * @param maxSectionSize The maximum size of the sections.
     *
     * @return A combined bitmap.
     */
    public fun combine(
        bitmaps: List<Bitmap?>,
        @Px size: Int,
        maxSectionSize: Int,
        errorPlaceholder: Drawable? = null,
    ): Bitmap {
        return Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888).applyCanvas {
            val sourceRect = Rect(0, 0, size, size)
            val mappedBitmaps = bitmaps.mapNotNull { bitmap ->
                bitmap ?: drawableToBitmap(errorPlaceholder, size)
            }
            createAvatarItems(mappedBitmaps, size, maxSectionSize).forEach {
                drawBitmap(it.bitmap, sourceRect, it.position, paint)
            }
        }
    }

    /**
     * Combines a list of bitmaps with a specific bitmap size.
     *
     * @param bitmaps A list of bitmaps to combine.
     * @param size A size of the bitmap.
     * @param maxSectionSize The maximum size of the sections.
     *
     * @return A combined bitmap.
     */
    public fun combine(
        vararg bitmaps: Bitmap?,
        @Px size: Int,
        maxSectionSize: Int,
        errorPlaceholder: Drawable? = null,
    ): Bitmap {
        return combine(bitmaps.toList(), size, maxSectionSize, errorPlaceholder)
    }

    /**
     * Creates a list of [AvatarItem]s with a specific bitmap size.
     *
     * @param bitmaps A list of bitmaps to combine.
     * @param size A size of the bitmap.
     * @param maxSectionSize The maximum size of the sections.
     *
     * @return A list of [AvatarItem].
     */
    private fun createAvatarItems(
        bitmaps: List<Bitmap>,
        @Px size: Int,
        maxSectionSize: Int
    ): List<AvatarItem> {
        val avatarBitmaps = bitmaps.take(maxSectionSize)
        return when (avatarBitmaps.size) {
            0 -> emptyList()
            1 -> listOf(
                avatarBitmaps[0].toAvatarItem(SectionType.FULL_CIRCLE, size)
            )
            2 -> listOf(
                avatarBitmaps[0].toAvatarItem(SectionType.LEFT, size),
                avatarBitmaps[1].toAvatarItem(SectionType.RIGHT, size)
            )
            3 -> listOf(
                avatarBitmaps[0].toAvatarItem(SectionType.TOP_LEFT, size),
                avatarBitmaps[1].toAvatarItem(SectionType.TOP_RIGHT, size),
                avatarBitmaps[2].toAvatarItem(SectionType.BOTTOM, size)
            )
            else -> listOf(
                avatarBitmaps[0].toAvatarItem(SectionType.TOP_LEFT, size),
                avatarBitmaps[1].toAvatarItem(SectionType.TOP_RIGHT, size),
                avatarBitmaps[2].toAvatarItem(SectionType.BOTTOM_LEFT, size),
                avatarBitmaps[3].toAvatarItem(SectionType.BOTTOM_RIGHT, size)
            )
        }
    }

    /**
     * Convert a bitmap to an [AvatarItem] by the [SectionType].
     *
     * @param sectionType A type of section to determine [AvatarItem].
     * @param size A size of the bitmap.
     *
     * @return An instance of [AvatarItem].
     *
     */
    private fun Bitmap.toAvatarItem(sectionType: SectionType, @Px size: Int): AvatarItem {
        return when (sectionType) {
            SectionType.FULL_CIRCLE -> {
                AvatarItem(scaleCenterCrop(size, size), Rect(0, 0, size, size))
            }
            SectionType.LEFT -> {
                AvatarItem(scaleCenterCrop(size / 2, size), Rect(0, 0, size, size))
            }
            SectionType.RIGHT -> {
                AvatarItem(
                    scaleCenterCrop(size / 2, size),
                    Rect(size / 2, 0, size + size / 2, size)
                )
            }
            SectionType.BOTTOM -> {
                AvatarItem(
                    scaleCenterCrop(size, size / 2),
                    Rect(0, size / 2, size, size + size / 2)
                )
            }
            SectionType.TOP_LEFT -> {
                AvatarItem(
                    scaleCenterCrop(size, size),
                    Rect(0, 0, size / 2, size / 2)
                )
            }
            SectionType.TOP_RIGHT -> {
                AvatarItem(
                    scaleCenterCrop(size, size),
                    Rect(size / 2, 0, size, size / 2)
                )
            }
            SectionType.BOTTOM_LEFT -> {
                AvatarItem(
                    scaleCenterCrop(size, size),
                    Rect(0, size / 2, size / 2, size)
                )
            }
            SectionType.BOTTOM_RIGHT -> {
                AvatarItem(
                    scaleCenterCrop(size, size),
                    Rect(size / 2, size / 2, size, size)
                )
            }
        }
    }

    /**
     * Creates a centered bitmap of the desired size.
     *
     * @param newWidth A desired width size.
     * @param newHeight A desired width size.
     *
     * @return A new extracted bitmap.
     */
    private fun Bitmap.scaleCenterCrop(@Px newWidth: Int, @Px newHeight: Int): Bitmap {
        return ThumbnailUtils.extractThumbnail(this, newWidth, newHeight)
    }

    /**
     * Creates a Bitmap from a drawable with a specific size specs.
     *
     * @param drawable A drawable should be created as Bitmap.
     * @param size A desired of the new Bitmap.
     *
     * @return A new created Bitmap.
     */
    @InternalAvatarViewApi
    public fun drawableToBitmap(drawable: Drawable?, @Px size: Int): Bitmap? =
        if (drawable is VectorDrawable) {
            drawable.vectorDrawableToBitmap(size)
        } else {
            when (drawable) {
                is BitmapDrawable -> drawable.bitmapDrawableToBitmap(size)
                else -> drawable?.toBitmap(size)
            }
        }

    /**
     * Creates a Bitmap from a [VectorDrawable] with a specific size specs.
     *
     * @param size A desired of the new Bitmap.
     * @return A new created Bitmap.
     */
    private fun VectorDrawable.vectorDrawableToBitmap(@Px size: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        this.setBounds(0, 0, canvas.width, canvas.height)
        this.draw(canvas)
        return bitmap
    }

    /**
     * Creates a Bitmap from a [BitmapDrawable] with a specific size specs.
     *
     * @param size A desired of the new Bitmap.
     * @return A new created Bitmap.
     */
    private fun BitmapDrawable.bitmapDrawableToBitmap(@Px size: Int): Bitmap =
        Bitmap.createScaledBitmap(bitmap, size, size, false)

    /**
     * Creates a Bitmap from a [Bitmap] with a specific size specs.
     *
     * @param size A desired of the new Bitmap.
     * @return A new created Bitmap.
     */
    private fun Drawable.toBitmap(@Px size: Int): Bitmap? =
        try {
            val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            this.setBounds(0, 0, canvas.width, canvas.height)
            this.draw(canvas)
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    /**
     * A data holder of a bitmap and positions.
     */
    private data class AvatarItem(val bitmap: Bitmap, val position: Rect)

    /**
     * A type of sections.
     */
    private enum class SectionType {
        FULL_CIRCLE,
        LEFT,
        RIGHT,
        BOTTOM,
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }
}
