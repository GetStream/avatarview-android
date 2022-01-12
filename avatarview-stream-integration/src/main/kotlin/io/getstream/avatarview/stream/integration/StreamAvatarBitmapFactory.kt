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
package io.getstream.avatarview.stream.integration

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.Typeface
import android.util.TypedValue
import androidx.annotation.Px
import io.getstream.avatarview.coil.Avatar
import io.getstream.avatarview.coil.AvatarBitmapFactory
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * A custom bitmap factory that extends [AvatarBitmapFactory] to implement
 * Stream SDK's avatar bitmap style.
 */
public class StreamAvatarBitmapFactory(private val context: Context) :
    AvatarBitmapFactory(context) {

    private val gradientBaseColors =
        context.resources.getIntArray(R.array.avatarView_gradient_colors)

    override suspend fun loadAvatarPlaceholderBitmap(
        data: Any?,
        avatar: Avatar,
        avatarSize: Int
    ): Bitmap {
        return createInitialsBitmap(data, avatar, avatarSize)
    }

    private fun createInitialsBitmap(
        data: Any?,
        avatar: Avatar,
        @Px avatarSize: Int,
    ): Bitmap {
        val initials = avatar.getTagFromInitialsMap(data, BAG_AVATAR_INITIALS) ?: ""
        return Bitmap.createBitmap(avatarSize, avatarSize, Bitmap.Config.ARGB_8888).apply {
            val canvas = Canvas(this)
            canvas.drawGradient(initials, avatarSize)
            canvas.drawInitials(data, avatar, initials, avatarSize)
        }
    }

    private fun Canvas.drawGradient(initials: String, avatarSize: Int) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            shader = createLinearGradientShader(initials, avatarSize)
        }
        drawRect(
            0f,
            0f,
            avatarSize.toFloat(),
            avatarSize.toFloat(),
            paint
        )
    }

    private fun Canvas.drawInitials(
        data: Any?,
        avatar: Avatar,
        initials: String,
        @Px avatarSize: Int,
    ) {
        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            textAlign = Paint.Align.CENTER
            color =
                avatar.getTagFromInitialsMap(data, BAG_AVATAR_INITIALS_TEXT_COLOR) ?: Color.WHITE
            textSize = (avatar.getTagFromInitialsMap(data, BAG_AVATAR_INITIALS_TEXT_SIZE) ?: 13f).sp
            typeface = Typeface.defaultFromStyle(
                avatar.getTagFromInitialsMap(data, BAG_AVATAR_INITIALS_FONT) ?: Typeface.NORMAL
            )
        }
        drawText(
            initials,
            avatarSize / 2f,
            avatarSize / 2f - (textPaint.ascent() + textPaint.descent()) / 2f,
            textPaint
        )
    }

    private val Float.sp: Float
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            this, context.resources.displayMetrics
        )

    private fun createLinearGradientShader(initials: String, @Px avatarSize: Int): Shader {
        val baseColorIndex = abs(initials.hashCode()) % gradientBaseColors.size
        val baseColor = gradientBaseColors[baseColorIndex]
        return LinearGradient(
            0f,
            0f,
            0f,
            avatarSize.toFloat(),
            adjustColorLBrightness(baseColor, GRADIENT_DARKER_COLOR_FACTOR),
            adjustColorLBrightness(baseColor, GRADIENT_LIGHTER_COLOR_FACTOR),
            Shader.TileMode.CLAMP
        )
    }

    private fun adjustColorLBrightness(color: Int, factor: Float): Int {
        val a = Color.alpha(color)
        val r = (Color.red(color) * factor).roundToInt()
        val g = (Color.green(color) * factor).roundToInt()
        val b = (Color.blue(color) * factor).roundToInt()
        return Color.argb(
            a,
            r.coerceAtMost(255),
            g.coerceAtMost(255),
            b.coerceAtMost(255)
        )
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> Avatar.getTagFromInitialsMap(data: Any?, key: String): T? {
        val map: MutableMap<String, Any> = getTag(data?.toString() ?: "") ?: mutableMapOf()
        return map[key] as? T
    }

    internal companion object {
        private const val GRADIENT_DARKER_COLOR_FACTOR = 1.3f
        private const val GRADIENT_LIGHTER_COLOR_FACTOR = 0.7f

        internal const val BAG_AVATAR_INITIALS = "BAG_AVATAR_INITIALS"
        internal const val BAG_AVATAR_INITIALS_FONT = "BAG_AVATAR_INITIALS_FONT"
        internal const val BAG_AVATAR_INITIALS_TEXT_SIZE = "BAG_AVATAR_INITIALS_TEXT_SIZE"
        internal const val BAG_AVATAR_INITIALS_TEXT_COLOR = "BAG_AVATAR_INITIALS_TEXT_COLOR"
    }
}
