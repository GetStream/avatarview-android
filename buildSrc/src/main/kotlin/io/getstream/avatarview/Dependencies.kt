package io.getstream.avatarview

object Versions {
    internal const val ANDROID_GRADLE_PLUGIN = "7.1.0"
    internal const val ANDROID_GRADLE_SPOTLESS = "6.1.0"
    internal const val GRADLE_NEXUS_PUBLISH_PLUGIN = "1.1.0"
    internal const val KOTLIN = "1.6.0"
    public const val KOTLIN_GRADLE_KTLINT = "0.41.0"
    internal const val KOTLIN_GRADLE_DOKKA = "1.6.10"
    internal const val KOTLIN_BINARY_VALIDATOR = "0.8.0"
    internal const val KOTLIN_COROUTINE = "1.5.2"

    internal const val ANDROIDX_APPCOMPAT = "1.4.0"
    internal const val ANDROIDX_CORE = "1.7.0"

    internal const val OKHTTP = "4.9.2"
    internal const val COIL = "1.4.0"
    internal const val GLIDE = "4.13.0"

    internal const val STREAM_CHAT_SDK = "4.27.0"

    internal const val CONSTRAINT_LAYOUT = "2.1.2"
    internal const val MATERIAL = "1.5.0-beta01"
}

object Dependencies {
    const val androidGradlePlugin =
        "com.android.tools.build:gradle:${Versions.ANDROID_GRADLE_PLUGIN}"
    const val gradleNexusPublishPlugin =
        "io.github.gradle-nexus:publish-plugin:${Versions.GRADLE_NEXUS_PUBLISH_PLUGIN}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
    const val spotlessGradlePlugin =
        "com.diffplug.spotless:spotless-plugin-gradle:${Versions.ANDROID_GRADLE_SPOTLESS}"
    const val dokka = "org.jetbrains.dokka:dokka-gradle-plugin:${Versions.KOTLIN_GRADLE_DOKKA}"
    const val kotlinBinaryValidator =
        "org.jetbrains.kotlinx:binary-compatibility-validator:${Versions.KOTLIN_BINARY_VALIDATOR}"
    const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.KOTLIN_COROUTINE}"

    const val androidxAppcompat = "androidx.appcompat:appcompat:${Versions.ANDROIDX_APPCOMPAT}"
    const val androidxCore = "androidx.core:core-ktx:${Versions.ANDROIDX_CORE}"

    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    const val coil = "io.coil-kt:coil:${Versions.COIL}"
    const val coilGif = "io.coil-kt:coil-gif:${Versions.COIL}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.GLIDE}"

    const val streamChatSdk = "io.getstream:stream-chat-android-client:${Versions.STREAM_CHAT_SDK}"

    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    const val material = "com.google.android.material:material:${Versions.MATERIAL}"
}
