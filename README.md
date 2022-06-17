<a href="https://getstream.io/tutorials/android-chat/?utm_source=Github&utm_campaign=Devrel_oss&utm_medium=avatarview-android"><img src="https://user-images.githubusercontent.com/24237865/148317308-8b39adb4-2c24-4094-abb7-8ad808fd1f96.png" /></a><br><br>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/GetStream/avatarview-android/actions/workflows/android.yml"><img alt="Build Status" src="https://github.com/GetStream/avatarview-android/actions/workflows/android.yml/badge.svg"/></a>
  <a href="https://androidweekly.net/issues/issue-498"><img alt="Android Weekly" src="https://skydoves.github.io/badges/android-weekly.svg"/></a>
  <a href="https://getstream.github.io/avatarview-android/"><img alt="Dokka" src="preview/dokka-avatarview.svg"/></a>
</p><br>

<p align="center">
✨ AvatarView supports loading profile images with fractional style, borders, indicators, and initials for Android.
</p><br>

## Preview
<p align="center">
<img src="/preview/preview7.gif" width="32%"/>
<img src="https://user-images.githubusercontent.com/24237865/146585515-a10a7446-fa47-4e34-9813-89b14177793d.png" width="32.3%"/>
<img src="https://user-images.githubusercontent.com/24237865/146585501-889b031c-55d1-4822-9d25-1d2c8ff8bd67.png" width="32.3%"/>
</p>

<a href="https://getstream.io/tutorials/android-chat/?utm_source=Github&utm_campaign=Devrel_oss&utm_medium=sketchbook"><img src="https://user-images.githubusercontent.com/24237865/138428440-b92e5fb7-89f8-41aa-96b1-71a5486c5849.png" align="right" width="13%"/></a>

## Contribution 💙

AvatarView is maintained by __[Stream](https://getstream.io/)__.
If you’re interested in adding powerful In-App Messaging to your app, check out the __[Stream Chat Tutorial for Android](https://getstream.io/tutorials/android-chat/?utm_source=Github&utm_campaign=Devrel_oss&utm_medium=avatarview-android)__!
Also, anyone can contribute to improving code, docs, or something following our [Contributing Guideline](/CONTRIBUTING.md).

## Blog Posts
Learn how to create stylish, highly-customized profile images using AvatarView for Android.
- __[AvatarView for Android: Take Your Profile Images to the Next Level](https://getstream.io/blog/avatarview-android/)__

## Download
[![Maven Central](https://img.shields.io/maven-central/v/io.getstream/avatarview.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.getstream%22%20AND%20a:%22avatarview%22)

### Gradle
Add the below codes to your **root** `build.gradle` file (not your module build.gradle file).
```gradle
allprojects {
    repositories {
        mavenCentral()
    }
}
```
Next, add the below dependency to your **module**'s `build.gradle` file.
```gradle
dependencies {
    implementation "io.getstream:avatarview-coil:1.0.6"
}
```

>  **Note:** The `io.getstream.avatarview-coil` dependency includes [Coil](https://github.com/coil-kt/coil) to load images internally. So if you're using Coil in your project, please make sure your project is using the same Coil version or exclude Coil dependencies to adapt yours.

We highly recommend using **AvatarView-Coil** to load images. However, if you'd more prefer to use [Glide](https://github.com/bumptech/glide), you can use [AvatarView-Glide](/avatarview-glide/README.md) instead.

## SNAPSHOT

<details>
 <summary>See how to import the snapshot</summary>

### Including the SNAPSHOT
Snapshots of the current development version of AvatarView are available, which track [the latest versions](https://oss.sonatype.org/content/repositories/snapshots/io/getstream/avatarview/).

To import snapshot versions on your project, add the code snippet below on your gradle file.
```Gradle
repositories {
   maven { url 'https://oss.sonatype.org/content/repositories/snapshots/' }
}
```

Next, add the below dependency to your **module**'s `build.gradle` file.
```gradle
dependencies {
    implementation "io.getstream:avatarview-coil:1.0.7-SNAPSHOT"
    implementation "io.getstream:avatarview-glide:1.0.7-SNAPSHOT"
}
```

</details>

## Usage

First, add the following XML namespace inside your XML layout file.

```gradle
xmlns:app="http://schemas.android.com/apk/res-auto"
```

### **AvatarView** in XML layout

You can customize `AvatarView` in your XML layout by setting attributes.

```gradle
<io.getstream.avatarview.AvatarView
    android:layout_width="110dp"
    android:layout_height="110dp"
    app:avatarViewBorderColor="@color/yellow"
    app:avatarViewBorderWidth="3dp"
    app:avatarViewIndicatorBorderColor="@color/white"
    app:avatarViewIndicatorBorderSizeCriteria="10"
    app:avatarViewIndicatorColor="@color/md_green_100"
    app:avatarViewIndicatorEnabled="true"
    app:avatarViewIndicatorPosition="bottomRight"
    app:avatarViewIndicatorSizeCriteria="9"
    app:avatarViewInitialsTextStyle="bold"
    app:avatarViewShape="circle" />
```
### Loading Single Image

You can load an image on your `AvatarView` by using the `loadImage` method as in the example below:

```kotlin
avatarView.loadImage(data)
```

The default supported **data** types are `String`, `Uri`, `HttpUrl` , `File`, `DrawableRes`, `Drawable`, and `Bitmap`. <br>
You can also set a place holder and request listeners as in the example below:

```kotlin
avatarView.loadImage(
    data = data,
    placeholder = drawable,
    onStart = {
        // started requesting an image
    },
    onComplete = {
        // completed requesting an image
    }
)
```

<img src="/preview/preview2.png" width="32%" align="right"/>

### Loading Images with Fractional Style

`AvatarView` supports loading up to four images with the fractional style as in the example below:

```kotlin
avatarView.loadImage(
  data = listof(url1, url2, url3, url4)
)
```

We can set the maximum section size of the avatar when we load multiple images by using the `avatarViewMaxSectionSize` attribute  as in the exmample below:

```xml
app:avatarViewMaxSectionSize="4"
```

The default value is 4, and you can set the fractional formats to your taste.

<img src="/preview/preview5.png" width="32%" align="right"/>

### Loading Placeholder

We can set a placeholder to show a placeholder during loading an image as in the example below:

```xml
app:avatarViewPlaceholder="@drawable/stream"
```
Or we can set a drawable manually on the `AvatarView`.

```kotlin
avatarView.placeholder = drawable
```

<img src="/preview/preview4.png" width="32%" align="right"/>

### Error Placeholder

We can set an error placeholder to show a placeholder when the request failed as in the example below:

```xml
app:avatarViewErrorPlaceholder="@drawable/stream"
```
Or we can set a drawable manually on the `AvatarView`.

```kotlin
avatarView.errorPlaceholder = drawable
```

### Custom ImageRequest

You can customize [ImageRequest](https://coil-kt.github.io/coil/image_requests/) and provide information to load an image as in the example below:

```kotlin
avatarView.loadImage(
  data = data
) {
    crossfade(true)
    crossfade(300)
    transformations(CircleCropTransformation())
    lifecycle(this@MainActivity)
}
```

<img src="/preview/preview9.png" width="32%" align="right"/>

## Border
You can customize border relevant attributes as in the example below:

```gradle
<io.getstream.avatarview.AvatarView
    android:layout_width="110dp"
    android:layout_height="110dp"
    app:avatarViewBorderColor="@color/white"
    app:avatarViewBorderWidth="3dp" />
```

Also, you can make a gradient for the border with an `avatarViewIndicatorBorderColorArray` attribute. First, declare an array of color in you **colors.xml** file as in the example below:

#### colors.xml
```gradle
<array name="rainbow">
    <item>@color/red</item>
    <item>@color/orange</item>
    <item>@color/yellow</item>
    <item>@color/chartreuse</item>
    <item>@color/green</item>
</array>
```

Next, apply the color array with the `avatarViewBorderColorArray` attribute instread of the `avatarViewBorderColor` as in the below example:

```gradle
<io.getstream.avatarview.AvatarView
    android:layout_width="110dp"
    android:layout_height="110dp"
    app:avatarViewBorderColorArray="@color/white"
    app:avatarViewBorderWidth="3dp" />
```

<img src="/preview/preview8.png" width="32%" align="right"/>

## Shape
AvatarView supports two shapes; circle and rounded rect. You can customize the shapes as in the example below:

### Circle

You can set the shape as a circle by setting the `avatarViewShape` attribute to `circle`.

```gradle
<io.getstream.avatarview.AvatarView
    android:layout_width="110dp"
    android:layout_height="110dp"
    app:avatarViewShape="circle" />
```

### Rounded Rect

You can set the shape as a rounded rect by setting the `avatarViewShape` attribute to `rounded_rect`. Also, you can customize a radius of the border with an `avatarViewBorderRadius` attribute.

```gradle
<io.getstream.avatarview.AvatarView
    android:layout_width="110dp"
    android:layout_height="110dp"
    app:avatarViewShape="rounded_rect"
    app:avatarViewBorderRadius="21dp"
    />
```

<img src="/preview/preview10.png" width="32%" align="right"/>

## Indicator
AvatarView supports drawing an indicator, which can be used for presenting a user online status or badges. You can enable it by giving **true** for an `avatarViewIndicatorEnabled` attribute as in the example below:

```gradle
<io.getstream.avatarview.AvatarView
    android:layout_width="110dp"
    android:layout_height="110dp"
    app:avatarViewIndicatorEnabled="true"
    app:avatarViewIndicatorColor="@color/green"
    app:avatarViewIndicatorBorderColor="@color/white"
    app:avatarViewIndicatorSizeCriteria="9"
    app:avatarViewIndicatorBorderSizeCriteria="10"
    app:avatarViewIndicatorPosition="bottomRight" />
```

As you can see above, you can customize the color of the indicator and border of the indicator, size criteria, and position. Also, you can customize the whole indicator with your custom drawable resource:

```gradle
<io.getstream.avatarview.AvatarView
    android:layout_width="110dp"
    android:layout_height="110dp"
    app:avatarViewIndicatorDrawable="@drawable/stream" />
```

<img src="/preview/preview3.png" width="32%" align="right"/>

## Initials

`AvatarView` supports drawing initials. You can draw and customize initials instead of loading an image over the `AvatarView` as in the example below:

```gradle
<io.getstream.avatarview.AvatarView
    android:layout_width="110dp"
    android:layout_height="110dp"
    app:avatarViewInitials="AB"
    app:avatarViewInitialsBackgroundColor="@color/skyBlue"
    app:avatarViewInitialsTextColor="@color/white"
    app:avatarViewInitialsTextSize="21sp"
    app:avatarViewInitialsTextSizeRatio="0.33"
    app:avatarViewInitialsTextStyle="bold" />
```

## AvatarCoil

The `io.getstream.avatarview-coil` dependency supports customizing the internal Coil that is called `AvatarCoil`.

### Custom ImageLoader

You can load images with your custom `ImageLoader` to load `AvatarView` by setting an `ImageLoaderFactory` on the `AvatarCoil`. Then all `AvatarView` will be loaded by the provided `ImageLoader` as in example the below:

```kotlin
AvatarCoil.setImageLoader(
    AvatarImageLoaderFactory(context) {
        crossfade(true)
        crossfade(400)
        okHttpClient {
            OkHttpClient.Builder()
                .cache(CoilUtils.createDefaultCache(context))
                .build()
        }
    }
)
```

### Custom AvatarBitmapFactory

#### Loading custom Avatar bitmaps

Avatar bitmaps are created by the internal bitmap factory called `AvatarBitmapFactory`. However, you can override the image loading methods and provide your own bitmap loader like the example below:

> Note: The `loadAvatarBitmapBlocking` method takes precedence over this one if both are implemented.

```kotlin
AvatarCoil.setAvatarBitmapFactory(
    object : AvatarBitmapFactory(context) {
        override suspend fun loadAvatarBitmap(data: Any?): Bitmap? {
            return withContext(Dispatchers.IO) {
                val imageResult = context.imageLoader.execute(
                    ImageRequest.Builder(context)
                       .headers(AvatarCoil.imageHeadersProvider.getImageRequestHeaders().toHeaders())
                       .data(data)
                       .build()
                )
                (imageResult.drawable as? BitmapDrawable)?.bitmap
            }
        }
    }
)
```

If you don't use coroutines, you can override `loadAvatarBitmapBlocking` method instead.

```kotlin
AvatarCoil.setAvatarBitmapFactory(
    object : AvatarBitmapFactory(context) {
        override fun loadAvatarBitmapBlocking(): Bitmap? {
            return // return your loaded Bitmap
        }
    }
)
```

#### Loading custom Avatar placeholder bitmaps

Basically, you can draw your placeholder drawable by setting the `placeholder` property on the `AvatarView`. However, you can provide your own bitmap loader by overriding the `loadAvatarPlaceholderBitmap` method like the example below:

> Note: The `loadAvatarPlaceholderBitmap` will be executed if the previous image request failed. And the `loadAvatarPlaceholderBitmapBlocking` method takes precedence over this one if both are implemented.

```kotlin
AvatarCoil.setAvatarBitmapFactory(
    object : AvatarBitmapFactory(context) {
        override fun loadAvatarPlaceholderBitmap(): Bitmap? {
            return // return your loaded placeholder Bitmap
        }
    }
)
```

If you don't use coroutines, you can override `loadAvatarPlaceholderBitmapBlocking` method instead like the example below:

```kotlin
AvatarCoil.setAvatarBitmapFactory(
    object : AvatarBitmapFactory(context) {
        override fun loadAvatarPlaceholderBitmapBlocking(): Bitmap? {
            return // return your loaded placeholder Bitmap
        }
    }
)
```

### Custom ImageHeadersProvider

If you're using your own CDN, you can set the `imageHeadersProvider` on `AvatarCoil` to load image data with your own header as in the example below:

```kotlin
AvatarCoil.imageHeadersProvider = yourImageHeadersProvider
```

## AvatarView with Glide
We highly recommend using **AvatarView-Coil** to load images if possible. However, you can also use [Glide](https://github.com/bumptech/glide) instead.

> 👉 Check out [AvatarView-Glide](/avatarview-glide/README.md).

 <a href="https://getstream.io/tutorials/android-chat/">
<img src="https://user-images.githubusercontent.com/24237865/138428440-b92e5fb7-89f8-41aa-96b1-71a5486c5849.png" align="right" width="12%"/></a>

## Stream Integration

[![Maven Central](https://img.shields.io/maven-central/v/io.getstream/avatarview-stream-integration.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.getstream%22%20AND%20a:%22avatarview-stream-integration%22)

AvatarView supports integrating features with [Stream Chat SDK for Android](https://getstream.io/chat/sdk/android/). First, You can simply integrate with **Stream Chat SDK** by adding the dependency below:

```gradle
dependencies {
    implementation "io.getstream:avatarview-stream-integration:$avatarview_version"
}
```

Next, you should set the `StreamAvatarBitmapFactory` on the `AvatarCoil` as in the below:

```kotlin
AvatarCoil.setAvatarBitmapFactory(StreamAvatarBitmapFactory(context))
```

Basically, it will load the `image` extra data of the `User`. But if there's no valid image data, the initials from the `name` will be loaded.

<img src="/preview/preview6.png" width="32%" align="right"/>

Then you can set your `User` model to the `AvatarView` as in the example below:

```kotlin
val currentUser = ChatClient.instance().getCurrentUser()
avatarView.setUserData(currentUser)
```

Also, you can set your `Channel` model to the `AvatarView` as in the example below:

```kotlin
avatarView.setChannel(channel)
```

The channel image will be loaded. But if there is no valid channel image, an image composed of members will be loaded.

## AvatarView Attributes

For more details, you can check out the [Dokka-AvatarView](https://getstream.github.io/avatarview-android/avatarview/io.getstream.avatarview/-avatar-view/index.html).

| Attributes                            | Type      | Description                                                  |
| ------------------------------------- | --------- | ------------------------------------------------------------ |
| avatarViewBorderColor                 | color     | AvatarView border color                                      |
| avatarViewBorderColorArray            | array     | AvatarView border color array                                |
| avatarViewBorderRadius                | dimension | AvatarView border radius                                     |
| avatarViewBorderWidth                 | dimension | AvatarView Border width                                      |
| avatarViewInitials                    | string    | AvatarView initials to be drawn instead of an image          |
| avatarViewInitialsTextSize            | integer   | AvatarView initials text size                                |
| avatarViewInitialsTextSizeRatio       | float     | AvatarView initials text size ratio following the width size |
| avatarViewInitialsTextColor           | color     | AvatarView initials text color                               |
| avatarViewInitialsBackgroundColor     | color     | AvatarView initials background color                         |
| avatarViewInitialsTextStyle           | enum      | AvatarView initials text style                               |
| avatarViewShape                       | enum      | AvatarView shapes                                            |
| avatarViewIndicatorEnabled            | boolean   | Sets the visibility of the indicator                         |
| avatarViewIndicatorPosition           | enum      | Sets the position of the indicator                           |
| avatarViewIndicatorColor              | color     | Color of the indicator                                       |
| avatarViewIndicatorBorderColor        | color     | Border color of the indicator                                |
| avatarViewIndicatorBorderColorArray   | array     | Border color array of the indicator                          |
| avatarViewIndicatorSizeCriteria       | float     | Size criteria of the indicator                               |
| avatarViewIndicatorBorderSizeCriteria | float     | Border Size criteria of the indicator                        |
| avatarViewSupportRtlEnabled           | boolean   | Supports RTL layout is enabled or not                        |
| avatarViewMaxSectionSize              | enum      | The maximum section size of the avatar when loading multiple images |
| avatarViewPlaceholder                 | drawable  | A placeholder that should be shown when loading an image     |
| avatarViewErrorPlaceholder            | drawable  | An error placeholder that should be shown when request failed |

 <a href="https://getstream.io/tutorials/android-chat/?utm_source=Github&utm_campaign=Devrel_oss&utm_medium=avatarview-android"><img src="https://user-images.githubusercontent.com/24237865/146505581-a79e8f7d-6eda-4611-b41a-d60f0189e7d4.jpeg" align="right" /></a>

## Find this library useful? :heart:

Support it by joining __[stargazers](https://github.com/getStream/avatarview-android/stargazers)__ for this repository. :star: <br>
Also, follow **[Stream](https://twitter.com/getstream_io)** on Twitter for our next creations!

# License
```xml
Copyright 2022 Stream.IO, Inc. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
