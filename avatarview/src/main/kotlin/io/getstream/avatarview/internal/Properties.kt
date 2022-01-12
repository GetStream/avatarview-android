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
package io.getstream.avatarview.internal

import android.graphics.Color
import androidx.annotation.ColorInt

/** A definition of the internal blue color. */
internal val internalBlue: Int
    @JvmSynthetic @ColorInt get() = Color.parseColor("#005FFF")

/** A definition of the internal green color. */
internal val internalGreen: Int
    @JvmSynthetic @ColorInt get() = Color.parseColor("#20E070")
