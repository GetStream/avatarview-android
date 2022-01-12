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
import com.bumptech.glide.request.target.CustomTarget

/**
 * A result class that encapsulates outcome with a Bitmap from the target request by a [CustomTarget].
 */
@PublishedApi
internal sealed class AvatarResult {

    /** A result class that encapsulates the successful result using Bitmap data. */
    class Success(val bitmap: Bitmap) : AvatarResult()

    /** A result class that encapsulates the failure result using Drawable data. */
    class Failure(val placeholder: Drawable?) : AvatarResult()
}
