

<h1 align="center">AvatarView</h1></br>

<p align="center">
AvatarView supports segmented style images, borders, indicators, and initials.
</p><br>


<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/></a>
</p><br>

<p align="center">
<img src="/preview/preview0.png" width="32%"/>
<img src="/preview/preview1.png" width="32%"/>
</p>

## Download

### Gradle
Add the below codes to your **root** `build.gradle` file (not your module build.gradle file).
```gradle
allprojects {
    repositories {
        mavenCentral()
    }
}
```
And add a dependency code to your **module**'s `build.gradle` file.
```gradle
dependencies {
    implementation "io.getstream.avatarview-glide:$version_avatarview"
}
```

The `io.getstream.avatarview-glide` dependency includes [Glide](https://github.com/bumptech/glide) to load images internally. So if you're using Coil in your project, please make sure you project is using the same Glide version or exclude Glide dependencies to adapt yours.

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
    crossFadeEnabled = true,
    requestListener = myRequestListener,
    requestOptions = myRequestOptions
)
```

<img src="/preview/preview2.png" width="32%" align="right"/>

### Loading Segmented Style Image

`AvatarView` supports loading up to four images with the segmented style as in the example below:

```kotlin
avatarView.loadImage(
  data = listof(url1, url2, url3, url4) 
)
```

We can set the maximum section size of the avatar when we load multiple images by using the `avatarViewMaxSectionSize` attribute  as in the exmample below:

```xml
app:avatarViewMaxSectionSize="4"
```

The default value is 4, and you can set the segmentation styles on your taste.

<img src="/preview/preview5.png" width="32%" align="right"/>

### PlaceHolder

We can set a placeholder to show a placeholder when loading an image as in the example below:

```xml
app:avatarViewPlaceholder="@drawable/stream"
```
Or we can set a drawable manually on the `AvatarView`.

```kotlin
avatarView.placeholder = drawable
```

<img src="/preview/preview4.png" width="32%" align="right"/>

### ErrorPlaceHolder

We can set an error placeholder to show a placeholder when the request failed as in the example below:

```xml
app:avatarViewErrorPlaceholder="@drawable/stream"
```
Or we can set a drawable manually on the `AvatarView`.

```kotlin
avatarView.errorPlaceholder = drawable
```

### Custom RequestBuilder

You can customize the [RequestBuilder](https://bumptech.github.io/glide/doc/options.html#requestbuilder/) and provide additional information to load an image as in the example below:

```kotlin
avatarView.loadImage(
    data = cats,
    requestBuilder = Glide.with(avatarView4)
        .asDrawable()
        .override(120, 120)
        .centerCrop()
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
              ): Boolean {
                 // do something //
                 return true
              }

              override fun onResourceReady(
                  resource: Drawable?,
                  model: Any?,
                  target: Target<Drawable>?,
                  dataSource: DataSource?,
                  isFirstResource: Boolean
              ): Boolean {
                 // do something //
                 return false
              }
          })
)
```



```xml
Copyright 2021 Stream.IO, Inc. All Rights Reserved.

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
