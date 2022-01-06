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

package io.getstream.avatarview.internal

import android.content.res.Resources
import android.content.res.TypedArray
import android.util.TypedValue
import android.view.View
import androidx.core.view.ViewCompat
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.math.roundToInt

/** Returns integer dimensional value from the integer px value. */
internal val Int.dp: Int
    @JvmSynthetic inline get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
        Resources.getSystem().displayMetrics
    ).roundToInt()

/** Returns enum object the corresponding index. */
@JvmSynthetic
internal inline fun <reified T : Enum<T>> TypedArray.getEnum(index: Int, default: T): T {
    return getInt(index, -1).let {
        if (it >= 0) enumValues<T>()[it] else default
    }
}

/** Returns an array of Int the corresponding resource id. */
@JvmSynthetic
internal fun TypedArray.getIntArray(resourceId: Int, default: IntArray): IntArray {
    return getResourceId(resourceId, -1).let {
        if (it >= 0) resources.getIntArray(it) else default
    }
}

/** Extension scope function for [TypedArray]. */
@JvmSynthetic
internal inline fun TypedArray.use(block: (TypedArray) -> Unit) {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    block(this)
    recycle()
}

/** Returns parsed initials from a String. */
internal val String.parseInitials: String
    @JvmSynthetic inline get() {
        val textList = trim().split(" ")
        return when {
            textList.size > 1 -> "${textList[0][0]}${textList[1][0]}"
            textList[0].length > 1 -> "${textList[0][0]}${textList[0][1]}"
            textList[0].isNotEmpty() -> "${textList[0][0]}"
            else -> ""
        }.uppercase()
    }

/** Returns a view is RTL layout or not. */
internal val View.isRtlLayout: Boolean
    @JvmSynthetic get() = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL

/** Returns an array of Shader positions from the int array. */
internal val IntArray.arrayPositions: FloatArray
    @JvmSynthetic inline get() {
        val positions = arrayListOf(0f)
        val interval = 1.0f / this.size
        for (i in 1 until size) {
            positions.add(positions[i - 1] + interval)
        }
        return positions.toFloatArray()
    }
