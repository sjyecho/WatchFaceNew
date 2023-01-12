/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.mi.wearable.watchface5.data.watchface

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.wear.watchface.style.UserStyleSetting
import androidx.wear.watchface.style.UserStyleSetting.ListUserStyleSetting
import com.android.mi.wearable.watchface5.R

//相册表盘的九种style
const val ONE_STYLE_ID = "one_style_id"
private const val ONE_STYLE_NAME_RESOURCE_ID = R.string.one_style_name

const val TWO_STYLE_ID = "two_style_id"
private const val TWO_STYLE_NAME_RESOURCE_ID = R.string.two_style_name

const val THREE_STYLE_ID = "three_style_id"
private const val THREE_STYLE_NAME_RESOURCE_ID = R.string.three_style_name

const val FOUR_STYLE_ID = "four_style_id"
private const val FOUR_STYLE_NAME_RESOURCE_ID = R.string.four_style_name

const val FIVE_STYLE_ID = "five_style_id"
private const val FIVE_STYLE_NAME_RESOURCE_ID = R.string.five_style_name

const val SIX_STYLE_ID = "six_style_id"
private const val SIX_STYLE_NAME_RESOURCE_ID = R.string.six_style_name

const val SEVEN_STYLE_ID = "seven_style_id"
private const val SEVEN_STYLE_NAME_RESOURCE_ID = R.string.seven_style_name

const val EIGHT_STYLE_ID = "eight_style_id"
private const val EIGHT_STYLE_NAME_RESOURCE_ID = R.string.eight_style_name

const val NINE_STYLE_ID = "nine_style_id"
private const val NINE_STYLE_NAME_RESOURCE_ID = R.string.nine_style_name

/**
 * Represents watch face color style options the user can select (includes the unique id, the
 * complication style resource id, and general watch face color style resource ids).
 *
 * The companion object offers helper functions to translate a unique string id to the correct enum
 * and convert all the resource ids to their correct resources (with the Context passed in). The
 * renderer will use these resources to render the actual colors and ComplicationDrawables of the
 * watch face.
 */
enum class ShapeStyleIdAndResourceIds(
    val id: String,
    @StringRes val nameResourceId: Int,
    @DrawableRes val shapeType: Int,
) {
    STY1(
        id = ONE_STYLE_ID,
        nameResourceId = ONE_STYLE_NAME_RESOURCE_ID,
        shapeType = STYLE1,
    ),

    STY2(
        id = TWO_STYLE_ID,
        nameResourceId = TWO_STYLE_NAME_RESOURCE_ID,
        shapeType = STYLE2,
    ),

    STY3(
        id = THREE_STYLE_ID,
        nameResourceId = THREE_STYLE_NAME_RESOURCE_ID,
        shapeType = STYLE3,
    ),
    STY4(
    id = FOUR_STYLE_ID,
    nameResourceId = FOUR_STYLE_NAME_RESOURCE_ID,
    shapeType = STYLE4,
    ),
    STY5(
        id = FIVE_STYLE_ID,
        nameResourceId = FIVE_STYLE_NAME_RESOURCE_ID,
        shapeType = STYLE5,
    ),
    STY6(
        id = SIX_STYLE_ID,
        nameResourceId = SIX_STYLE_NAME_RESOURCE_ID,
        shapeType = STYLE6,
    ),
    STY7(
        id = SEVEN_STYLE_ID,
        nameResourceId = SEVEN_STYLE_NAME_RESOURCE_ID,
        shapeType = STYLE7,
    ),
    STY8(
        id = EIGHT_STYLE_ID,
        nameResourceId = EIGHT_STYLE_NAME_RESOURCE_ID,
        shapeType = STYLE8,
    ),
    STY9(
        id = NINE_STYLE_ID,
        nameResourceId = NINE_STYLE_NAME_RESOURCE_ID,
        shapeType = STYLE9,
    );

    companion object {
        /**
         * Translates the string id to the correct ColorStyleIdAndResourceIds object.
         */
        fun getShapeStyleConfig(id: String): ShapeStyleIdAndResourceIds {
            return when (id) {
                STY1.id -> STY1
                STY2.id -> STY2
                STY3.id -> STY3
                STY4.id -> STY4
                STY5.id -> STY5
                STY6.id -> STY6
                STY7.id -> STY7
                STY8.id -> STY8
                STY9.id -> STY9
                else -> STY3
            }
        }

        /**
         * Returns a list of [UserStyleSetting.ListUserStyleSetting.ListOption] for all
         * ColorStyleIdAndResourceIds enums. The watch face settings APIs use this to set up
         * options for the user to select a style.
         */
        fun toOptionList(context: Context): List<ListUserStyleSetting.ListOption> {
            val shapeStyleIdAndResourceIdsList = enumValues<ShapeStyleIdAndResourceIds>()
            return shapeStyleIdAndResourceIdsList.map { shapeStyleIdAndResourceIds ->
                ListUserStyleSetting.ListOption(
                    UserStyleSetting.Option.Id(shapeStyleIdAndResourceIds.id),
                    context.resources,
                    shapeStyleIdAndResourceIds.nameResourceId,
                    null
                )
            }
        }
    }
}
