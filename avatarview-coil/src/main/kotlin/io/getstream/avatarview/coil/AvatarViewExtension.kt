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

@file:JvmName("AvatarViewExtension")
@file:JvmMultifileClass

package io.getstream.avatarview.coil

import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import coil.transform.Transformation
import io.getstream.avatarview.AvatarShape
import io.getstream.avatarview.AvatarView

/**
 * Loads an image request [data] to the [AvatarView].
 *
 * @param data An image data to be loaded.
 * @param onStart A lambda function will be executed when start requesting.
 * @param onComplete A lambda function will be executed when loading finished.
 * @param onSuccess A lambda function will be executed when loading succeeds.
 * @param onError A lambda function will be executed when loading failed.
 * @param builder A receiver to be applied with the [ImageRequest.Builder].
 */
@JvmSynthetic
public fun AvatarView.loadImage(
    data: Any?,
    onStart: () -> Unit = {},
    onComplete: () -> Unit = {},
    onSuccess: (request: ImageRequest, result: SuccessResult) -> Unit = { _, _ -> },
    onError: (request: ImageRequest, result: ErrorResult) -> Unit = { _, _ -> },
    builder: ImageRequest.Builder.() -> Unit = {}
) {
    loadPlaceholder()
    AvatarImageLoaderInternal.load(
        target = this,
        data = Avatar(
            data = listOf(data),
            maxSectionSize = maxSectionSize,
            avatarBorderWidth = avatarBorderWidth,
            errorPlaceholder = errorPlaceholder,
            onSuccess = onSuccess,
            onError = onError
        ),
        transformation = transformation,
        onStart = onStart,
        onComplete = onComplete,
        builder = builder,
    )
}

/**
 * Loads a list of image request [data] to the [AvatarView].
 * Up to 4 images will be combined and loaded.
 *
 * @param data A list of image data to be loaded.
 * @param onStart A lambda function will be executed when start requesting.
 * @param onComplete A lambda function will be executed when finish loading.
 * @param onSuccess A lambda function will be executed when loading succeeds.
 * @param onError A lambda function will be executed when loading failed.
 * @param builder A receiver to be applied with the [ImageRequest.Builder].
 */
@JvmSynthetic
public fun AvatarView.loadImage(
    data: List<Any?>,
    onStart: () -> Unit = {},
    onComplete: () -> Unit = {},
    onSuccess: (request: ImageRequest, result: SuccessResult) -> Unit = { _, _ -> },
    onError: (request: ImageRequest, result: ErrorResult) -> Unit = { _, _ -> },
    builder: ImageRequest.Builder.() -> Unit = {}
) {
    loadPlaceholder()
    AvatarImageLoaderInternal.load(
        target = this,
        data = Avatar(
            data = data,
            maxSectionSize = maxSectionSize,
            avatarBorderWidth = avatarBorderWidth,
            errorPlaceholder = errorPlaceholder,
            onSuccess = onSuccess,
            onError = onError
        ),
        transformation = transformation,
        onStart = onStart,
        onComplete = onComplete,
        builder = builder,
    )
}

/**
 * Loads a vararg of image request [data] to the [AvatarView].
 * Up to 4 images will be combined and loaded.
 *
 * @param data A vararg of image data to be loaded.
 * @param onStart A lambda function will be executed when start requesting.
 * @param onComplete A lambda function will be executed when finish loading.
 * @param onSuccess A lambda function will be executed when loading succeeds.
 * @param onError A lambda function will be executed when loading failed.
 * @param builder A receiver to be applied with the [ImageRequest.Builder].
 */
@JvmSynthetic
public fun AvatarView.loadImage(
    vararg data: Any?,
    onStart: () -> Unit = {},
    onComplete: () -> Unit = {},
    onSuccess: (request: ImageRequest, result: SuccessResult) -> Unit = { _, _ -> },
    onError: (request: ImageRequest, result: ErrorResult) -> Unit = { _, _ -> },
    builder: ImageRequest.Builder.() -> Unit = {}
) {
    loadPlaceholder()
    loadImage(
        data = data.toList(),
        onStart = onStart,
        onComplete = onComplete,
        onSuccess = onSuccess,
        onError = onError,
        builder = builder
    )
}

/** Loads a placeholder with the [AvatarView.placeholder] property. */
@PublishedApi
internal fun AvatarView.loadPlaceholder() {
    placeholder?.let {
        AvatarImageLoaderInternal.load(
            target = this,
            data = it,
            transformation = transformation,
            onStart = {},
            onComplete = {},
            builder = {}
        )
    }
}

/** Returns a [Transformation] from a [AvatarView]. */
@PublishedApi
internal val AvatarView.transformation: Transformation
    @JvmSynthetic inline get() = when (avatarShape) {
        AvatarShape.CIRCLE -> CircleCropTransformation()
        AvatarShape.ROUNDED_RECT -> RoundedCornersTransformation(avatarBorderRadius)
    }
