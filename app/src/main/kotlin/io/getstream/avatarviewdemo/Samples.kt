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
package io.getstream.avatarviewdemo

object Samples {

    val cats: List<String>
        get() = listOf(
            "https://swiftype-ss.imgix.net/https%3A%2F%2Fcdn.petcarerx.com%2FLPPE%2Fimages%2Farticlethumbs%2FFluffy-Cats-Small.jpg?ixlib=rb-1.1.0&h=320&fit=clip&dpr=2.0&s=c81a75f749ea4ed736b7607100cb52cc.png",
            "https://images.ctfassets.net/cnu0m8re1exe/1GxSYi0mQSp9xJ5svaWkVO/d151a93af61918c234c3049e0d6393e1/93347270_cat-1151519_1280.jpg?fm=jpg&fl=progressive&w=660&h=433&fit=fill",
            "https://img.webmd.com/dtmcms/live/webmd/consumer_assets/site_images/article_thumbnails/other/cat_relaxing_on_patio_other/1800x1200_cat_relaxing_on_patio_other.jpg",
            "https://post.healthline.com/wp-content/uploads/2020/08/cat-thumb2-732x415.jpg",
        ).shuffled()
}
