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

@file:JvmName("AvatarViewStreamIntegration")
@file:JvmMultifileClass

package io.getstream.avatarview.stream.integration

import android.graphics.drawable.Drawable
import io.getstream.avatarview.AvatarView
import io.getstream.avatarview.coil.Avatar
import io.getstream.avatarview.coil.AvatarImageLoaderInternal
import io.getstream.avatarview.coil.loadImage
import io.getstream.avatarview.stream.integration.StreamAvatarBitmapFactory.Companion.BAG_AVATAR_INITIALS
import io.getstream.avatarview.stream.integration.StreamAvatarBitmapFactory.Companion.BAG_AVATAR_INITIALS_FONT
import io.getstream.avatarview.stream.integration.StreamAvatarBitmapFactory.Companion.BAG_AVATAR_INITIALS_TEXT_COLOR
import io.getstream.avatarview.stream.integration.StreamAvatarBitmapFactory.Companion.BAG_AVATAR_INITIALS_TEXT_SIZE
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.extensions.isAnonymousChannel
import io.getstream.chat.android.client.models.Channel
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.client.models.initials
import kotlinx.coroutines.launch

/**
 * Sets [User] to the [AvatarView] for loading image and online status.
 *
 * @param user The [User] model of the Stream SDK.
 * @param errorPlaceholder An error placeholder that should be shown when request failed.
 */
public fun AvatarView.setUserData(
    user: User,
    errorPlaceholder: Drawable? = null
) {
    loadImage(
        data = Avatar(
            data = listOf(user.model),
            maxSectionSize = maxSectionSize,
            avatarBorderWidth = avatarBorderWidth,
            errorPlaceholder = errorPlaceholder,
            onSuccess = { _, _ -> },
            onError = { _, _ -> },
        ).also {
            it.putInitialStylesOnBag(this, user)
        }
    )

    indicatorEnabled = user.online
}

/**
 * Sets [Channel] to the [AvatarView] for loading images.
 *
 * @param channel A [Channel] model of the Stream SDK.
 * @param errorPlaceholder An error placeholder that should be shown when request failed.
 */
public fun AvatarView.setChannelData(channel: Channel, errorPlaceholder: Drawable? = null) {
    val otherUsers = channel.getUsersExcludingCurrent()
    if (channel.isAnonymousChannel() && otherUsers.size == 1) {
        setUserData(otherUsers.first())
    } else {
        launch {
            AvatarImageLoaderInternal.loadAsBitmap(context, channel.image)?.also {
                loadImage(it)
            } ?: loadImage(
                data = Avatar(
                    data = otherUsers.map { it.model },
                    maxSectionSize = maxSectionSize,
                    avatarBorderWidth = avatarBorderWidth,
                    errorPlaceholder = errorPlaceholder,
                    onSuccess = { _, _ -> },
                    onError = { _, _ -> },
                ).also {
                    it.putInitialStylesOnBag(this@setChannelData, channel)
                }
            )
        }
        indicatorEnabled = false
    }
}

/** Returns a request model data from a [User]. */
private val User.model: String
    get() = image.takeIf { it.isNotEmpty() } ?: name

/**
 * Puts initials style information associated with [AvatarView] on the bag with the [User].
 *
 * @param avatarView The target that will be loaded an avatar image.
 * @param user The [User] model of the Stream SDK.
 */
internal fun Avatar.putInitialStylesOnBag(avatarView: AvatarView, user: User) {
    avatarView.run {
        setTagIfAbsent(
            user.model,
            mutableMapOf<String, Any>(
                BAG_AVATAR_INITIALS to user.initials,
                BAG_AVATAR_INITIALS_FONT to avatarInitialsStyle,
                BAG_AVATAR_INITIALS_TEXT_COLOR to avatarInitialsTextColor,
                BAG_AVATAR_INITIALS_TEXT_SIZE to avatarInitialsTextSize.toFloat()
            )
        )
    }
}

/**
 * Puts initials style information associated with [AvatarView] on the bag with the [Channel].
 *
 * @param avatarView The target that will be loaded an avatar image.
 * @param channel The [Channel] model of the Stream SDK.
 */
internal fun Avatar.putInitialStylesOnBag(avatarView: AvatarView, channel: Channel) {
    channel.getUsersExcludingCurrent().forEach { user ->
        putInitialStylesOnBag(avatarView, user)
    }
}

/** Returns list of [User] excepts the current user. */
@PublishedApi
@JvmSynthetic
internal fun Channel.getUsersExcludingCurrent(): List<User> {
    val users = members.map { it.user }
    val currentUserId = ChatClient.instance().getCurrentUser()?.id
    return if (currentUserId != null) {
        users.filterNot { it.id == currentUserId }
    } else {
        users
    }
}
