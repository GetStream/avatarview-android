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

package io.getstream.avatarviewdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.transform.BlurTransformation
import coil.transform.RoundedCornersTransformation
import io.getstream.avatarview.coil.loadImage
import io.getstream.avatarviewdemo.Samples.cats
import io.getstream.avatarviewdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            avatarView1.loadImage(cats.take(1))

            avatarView2.loadImage(
                cats.take(2)
            ) {
                crossfade(true)
                crossfade(300)
                lifecycle(this@MainActivity)
            }

            avatarView3.loadImage(
                cats.take(3)
            ) {
                crossfade(true)
                crossfade(400)
                lifecycle(this@MainActivity)
            }

            avatarView4.loadImage(
                cats.take(4)
            ) {
                crossfade(true)
                crossfade(400)
                lifecycle(this@MainActivity)
            }

            avatarView5.loadImage(
                cats.take(1)
            ) {
                crossfade(true)
                crossfade(400)
                lifecycle(this@MainActivity)
            }

            avatarView6.loadImage(
                cats.take(2)
            ) {
                crossfade(true)
                crossfade(400)
                lifecycle(this@MainActivity)
            }

            avatarView7.loadImage(
                cats.take(3)
            ) {
                crossfade(true)
                crossfade(400)
                lifecycle(this@MainActivity)
                transformations(
                    BlurTransformation(this@MainActivity),
                    RoundedCornersTransformation(36f)
                )
            }

            avatarView8.loadImage(
                cats.take(4)
            ) {
                crossfade(true)
                crossfade(400)
                lifecycle(this@MainActivity)
            }
        }
    }
}
