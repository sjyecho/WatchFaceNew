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
package com.android.mi.wearable.albumwatchface.data.watchface

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.wear.watchface.style.UserStyleSetting
import androidx.wear.watchface.style.UserStyleSetting.ListUserStyleSetting
import com.android.mi.wearable.albumwatchface.R

// Defaults for all styles.
// X_COLOR_STYLE_ID - id in watch face database for each style id.
// X_COLOR_STYLE_NAME_RESOURCE_ID - String name to display in the user settings UI for the style.
// X_COLOR_STYLE_ICON_ID - Icon to display in the user settings UI for the style.
const val COLOR_STYLE1_ID = "color_style1_id"
private const val COLOR_STYLE1_NAME_RESOURCE_ID = R.string.color_style_1
//private const val RED_COLOR_STYLE_ICON_ID = R.drawable.complication_left_style1

const val COLOR_STYLE2_ID = "color_style2_id"
private const val COLOR_STYLE2_NAME_RESOURCE_ID = R.string.color_style_2
//private const val GREEN_COLOR_STYLE_ICON_ID = R.drawable.complication_left_style1
const val COLOR_STYLE3_ID = "color_style3_id"
private const val COLOR_STYLE3_NAME_RESOURCE_ID = R.string.color_style_3

const val COLOR_STYLE4_ID = "color_style4_id"
private const val COLOR_STYLE4_NAME_RESOURCE_ID = R.string.color_style_4

const val COLOR_STYLE5_ID = "color_style5_id"
private const val COLOR_STYLE5_NAME_RESOURCE_ID = R.string.color_style_5

const val COLOR_STYLE6_ID = "color_style6_id"
private const val COLOR_STYLE6_NAME_RESOURCE_ID = R.string.color_style_6

const val COLOR_STYLE7_ID = "color_style7_id"
private const val COLOR_STYLE7_NAME_RESOURCE_ID = R.string.color_style_7

const val COLOR_STYLE8_ID = "color_style8_id"
private const val COLOR_STYLE8_NAME_RESOURCE_ID = R.string.color_style_8

const val COLOR_STYLE9_ID = "color_style9_id"
private const val COLOR_STYLE9_NAME_RESOURCE_ID = R.string.color_style_9

const val COLOR_STYLE10_ID = "color_style10_id"
private const val COLOR_STYLE10_NAME_RESOURCE_ID = R.string.color_style_10

const val COLOR_STYLE11_ID = "color_style11_id"
private const val COLOR_STYLE11_NAME_RESOURCE_ID = R.string.color_style_11

const val COLOR_STYLE12_ID = "color_style12_id"
private const val COLOR_STYLE12_NAME_RESOURCE_ID = R.string.color_style_12

const val COLOR_STYLE13_ID = "color_style13_id"
private const val COLOR_STYLE13_NAME_RESOURCE_ID = R.string.color_style_13

const val COLOR_STYLE14_ID = "color_style14_id"
private const val COLOR_STYLE14_NAME_RESOURCE_ID = R.string.color_style_14

const val COLOR_STYLE15_ID = "color_style15_id"
private const val COLOR_STYLE15_NAME_RESOURCE_ID = R.string.color_style_15

const val COLOR_STYLE16_ID = "color_style16_id"
private const val COLOR_STYLE16_NAME_RESOURCE_ID = R.string.color_style_16

const val COLOR_STYLE17_ID = "color_style17_id"
private const val COLOR_STYLE17_NAME_RESOURCE_ID = R.string.color_style_17

const val COLOR_STYLE18_ID = "color_style18_id"
private const val COLOR_STYLE18_NAME_RESOURCE_ID = R.string.color_style_18

const val COLOR_STYLE19_ID = "color_style19_id"
private const val COLOR_STYLE19_NAME_RESOURCE_ID = R.string.color_style_19

const val COLOR_STYLE20_ID = "color_style20_id"
private const val COLOR_STYLE20_NAME_RESOURCE_ID = R.string.color_style_20

const val COLOR_STYLE21_ID = "color_style21_id"
private const val COLOR_STYLE21_NAME_RESOURCE_ID = R.string.color_style_21

const val COLOR_STYLE22_ID = "color_style22_id"
private const val COLOR_STYLE22_NAME_RESOURCE_ID = R.string.color_style_22

const val COLOR_STYLE23_ID = "color_style23_id"
private const val COLOR_STYLE23_NAME_RESOURCE_ID = R.string.color_style_23

const val COLOR_STYLE24_ID = "color_style24_id"
private const val COLOR_STYLE24_NAME_RESOURCE_ID = R.string.color_style_24

const val COLOR_STYLE25_ID = "color_style25_id"
private const val COLOR_STYLE25_NAME_RESOURCE_ID = R.string.color_style_25

const val COLOR_STYLE26_ID = "color_style26_id"
private const val COLOR_STYLE26_NAME_RESOURCE_ID = R.string.color_style_26

const val COLOR_STYLE27_ID = "color_style27_id"
private const val COLOR_STYLE27_NAME_RESOURCE_ID = R.string.color_style_27

const val COLOR_STYLE28_ID = "color_style28_id"
private const val COLOR_STYLE28_NAME_RESOURCE_ID = R.string.color_style_28

const val COLOR_STYLE29_ID = "color_style29_id"
private const val COLOR_STYLE29_NAME_RESOURCE_ID = R.string.color_style_29

const val COLOR_STYLE30_ID = "color_style30_id"
private const val COLOR_STYLE30_NAME_RESOURCE_ID = R.string.color_style_30

const val COLOR_STYLE31_ID = "color_style31_id"
private const val COLOR_STYLE31_NAME_RESOURCE_ID = R.string.color_style_31

const val COLOR_STYLE32_ID = "color_style32_id"
private const val COLOR_STYLE32_NAME_RESOURCE_ID = R.string.color_style_32

const val COLOR_STYLE33_ID = "color_style33_id"
private const val COLOR_STYLE33_NAME_RESOURCE_ID = R.string.color_style_33

const val COLOR_STYLE34_ID = "color_style34_id"
private const val COLOR_STYLE34_NAME_RESOURCE_ID = R.string.color_style_34

const val COLOR_STYLE35_ID = "color_style35_id"
private const val COLOR_STYLE35_NAME_RESOURCE_ID = R.string.color_style_35

const val COLOR_STYLE36_ID = "color_style36_id"
private const val COLOR_STYLE36_NAME_RESOURCE_ID = R.string.color_style_36


/**
 * Represents watch face color style options the user can select (includes the unique id, the
 * complication style resource id, and general watch face color style resource ids).
 *
 * The companion object offers helper functions to translate a unique string id to the correct enum
 * and convert all the resource ids to their correct resources (with the Context passed in). The
 * renderer will use these resources to render the actual colors and ComplicationDrawables of the
 * watch face.
 */
enum class ColorStyleIdAndResourceIds(
    val id: String,
    @StringRes val nameResourceId: Int,
    @DrawableRes val watchFaceStyle: Int,
     val colorInt: Int,
    @DrawableRes val colorIcon: Int
) {
    COLOR1(
        id = COLOR_STYLE1_ID,
        nameResourceId = COLOR_STYLE1_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_1,
        colorInt = R.color.color1,
        colorIcon = R.drawable.c1
    ),

    COLOR2(
        id = COLOR_STYLE2_ID,
        nameResourceId = COLOR_STYLE2_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_2,
        colorInt = R.color.color2,
        colorIcon = R.drawable.c2
    ),

    COLOR3(
        id = COLOR_STYLE3_ID,
        nameResourceId = COLOR_STYLE3_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_3,
        colorInt = R.color.color3,
        colorIcon = R.drawable.c3
    ),

    COLOR4(
        id = COLOR_STYLE4_ID,
        nameResourceId = COLOR_STYLE4_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_4,
        colorInt = R.color.color4,
        colorIcon = R.drawable.c4
    ),

    COLOR5(
        id = COLOR_STYLE5_ID,
        nameResourceId = COLOR_STYLE5_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_5,
        colorInt = R.color.color5,
        colorIcon = R.drawable.c5
    ),

    COLOR6(
        id = COLOR_STYLE6_ID,
        nameResourceId = COLOR_STYLE6_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_6,
        colorInt = R.color.color6,
        colorIcon = R.drawable.c6
    ),

    COLOR7(
        id = COLOR_STYLE7_ID,
        nameResourceId = COLOR_STYLE7_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_7,
        colorInt = R.color.color7,
        colorIcon = R.drawable.c7
    ),

    COLOR8(
        id = COLOR_STYLE8_ID,
        nameResourceId = COLOR_STYLE8_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_8,
        colorInt = R.color.color8,
        colorIcon = R.drawable.c8
    ),

    COLOR9(
        id = COLOR_STYLE9_ID,
        nameResourceId = COLOR_STYLE9_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_9,
        colorInt = R.color.color9,
        colorIcon = R.drawable.c9
    ),

    COLOR10(
        id = COLOR_STYLE10_ID,
        nameResourceId = COLOR_STYLE10_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_10,
        colorInt = R.color.color10,
        colorIcon = R.drawable.c10
    ),

    COLOR11(
        id = COLOR_STYLE11_ID,
        nameResourceId = COLOR_STYLE11_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_11,
        colorInt = R.color.color11,
        colorIcon = R.drawable.c11
    ),

    COLOR12(
        id = COLOR_STYLE12_ID,
        nameResourceId = COLOR_STYLE12_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_12,
        colorInt = R.color.color12,
        colorIcon = R.drawable.c12
    ),

    COLOR13(
        id = COLOR_STYLE13_ID,
        nameResourceId = COLOR_STYLE13_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_13,
        colorInt = R.color.color13,
        colorIcon = R.drawable.c13
    ),

    COLOR14(
        id = COLOR_STYLE14_ID,
        nameResourceId = COLOR_STYLE14_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_14,
        colorInt = R.color.color14,
        colorIcon = R.drawable.c14
    ),

    COLOR15(
        id = COLOR_STYLE15_ID,
        nameResourceId = COLOR_STYLE15_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_15,
        colorInt = R.color.color15,
        colorIcon = R.drawable.c15
    ),

    COLOR16(
        id = COLOR_STYLE16_ID,
        nameResourceId = COLOR_STYLE16_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_16,
        colorInt = R.color.color16,
        colorIcon = R.drawable.c16
    ),

    COLOR17(
        id = COLOR_STYLE17_ID,
        nameResourceId = COLOR_STYLE17_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_17,
        colorInt = R.color.color17,
        colorIcon = R.drawable.c17
    ),

    COLOR18(
        id = COLOR_STYLE18_ID,
        nameResourceId = COLOR_STYLE18_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_18,
        colorInt = R.color.color18,
        colorIcon = R.drawable.c18
    ),

    COLOR19(
        id = COLOR_STYLE19_ID,
        nameResourceId = COLOR_STYLE19_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_19,
        colorInt = R.color.color19,
        colorIcon = R.drawable.c19
    ),

    COLOR20(
        id = COLOR_STYLE20_ID,
        nameResourceId = COLOR_STYLE20_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_20,
        colorInt = R.color.color20,
        colorIcon = R.drawable.c20
    ),

    COLOR21(
        id = COLOR_STYLE21_ID,
        nameResourceId = COLOR_STYLE21_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_21,
        colorInt = R.color.color21,
        colorIcon = R.drawable.c21
    ),

    COLOR22(
        id = COLOR_STYLE22_ID,
        nameResourceId = COLOR_STYLE22_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_22,
        colorInt = R.color.color22,
        colorIcon = R.drawable.c22
    ),

    COLOR23(
        id = COLOR_STYLE23_ID,
        nameResourceId = COLOR_STYLE23_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_23,
        colorInt = R.color.color23,
        colorIcon = R.drawable.c23
    ),

    COLOR24(
        id = COLOR_STYLE24_ID,
        nameResourceId = COLOR_STYLE24_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_24,
        colorInt = R.color.color24,
        colorIcon = R.drawable.c24
    ),

    COLOR25(
        id = COLOR_STYLE25_ID,
        nameResourceId = COLOR_STYLE25_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_25,
        colorInt = R.color.color25,
        colorIcon = R.drawable.c25
    ),

    COLOR26(
        id = COLOR_STYLE26_ID,
        nameResourceId = COLOR_STYLE26_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_26,
        colorInt = R.color.color26,
        colorIcon = R.drawable.c26
    ),

    COLOR27(
        id = COLOR_STYLE27_ID,
        nameResourceId = COLOR_STYLE27_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_27,
        colorInt = R.color.color27,
        colorIcon = R.drawable.c27
    ),

    COLOR28(
        id = COLOR_STYLE28_ID,
        nameResourceId = COLOR_STYLE28_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_28,
        colorInt = R.color.color28,
        colorIcon = R.drawable.c28
    ),

    COLOR29(
        id = COLOR_STYLE29_ID,
        nameResourceId = COLOR_STYLE29_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_29,
        colorInt = R.color.color29,
        colorIcon = R.drawable.c29
    ),

    COLOR30(
        id = COLOR_STYLE30_ID,
        nameResourceId = COLOR_STYLE30_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_30,
        colorInt = R.color.color30,
        colorIcon = R.drawable.c30
    ),

    COLOR31(
        id = COLOR_STYLE31_ID,
        nameResourceId = COLOR_STYLE31_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_31,
        colorInt = R.color.color31,
        colorIcon = R.drawable.c31
    ),

    COLOR32(
        id = COLOR_STYLE32_ID,
        nameResourceId = COLOR_STYLE32_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_32,
        colorInt = R.color.color32,
        colorIcon = R.drawable.c32
    ),

    COLOR33(
        id = COLOR_STYLE33_ID,
        nameResourceId = COLOR_STYLE33_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_33,
        colorInt = R.color.color33,
        colorIcon = R.drawable.c33
    ),

    COLOR34(
        id = COLOR_STYLE34_ID,
        nameResourceId = COLOR_STYLE34_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_34,
        colorInt = R.color.color34,
        colorIcon = R.drawable.c34

    ),

    COLOR35(
        id = COLOR_STYLE35_ID,
        nameResourceId = COLOR_STYLE35_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_35,
        colorInt = R.color.color35,
        colorIcon = R.drawable.c35
    ),

    COLOR36(
        id = COLOR_STYLE36_ID,
        nameResourceId = COLOR_STYLE36_NAME_RESOURCE_ID,
        watchFaceStyle = TYPE_36,
        colorInt = R.color.color36,
        colorIcon = R.drawable.c36
    );



    companion object {
        /**
         * Translates the string id to the correct ColorStyleIdAndResourceIds object.
         */
        fun getColorStyleConfig(id: String): ColorStyleIdAndResourceIds {
            return when (id) {
                COLOR1.id -> COLOR1
                COLOR2.id -> COLOR2
                COLOR3.id -> COLOR3
                COLOR4.id -> COLOR4
                COLOR5.id -> COLOR5
                COLOR6.id -> COLOR6
                COLOR7.id -> COLOR7
                COLOR8.id -> COLOR8
                COLOR9.id -> COLOR9
                COLOR10.id -> COLOR10
                COLOR11.id -> COLOR11
                COLOR12.id -> COLOR12
                COLOR13.id -> COLOR13
                COLOR14.id -> COLOR14
                COLOR15.id -> COLOR15
                COLOR16.id -> COLOR16
                COLOR17.id -> COLOR17
                COLOR18.id -> COLOR18
                COLOR19.id -> COLOR19
                COLOR20.id -> COLOR20
                COLOR21.id -> COLOR21
                COLOR22.id -> COLOR22
                COLOR23.id -> COLOR23
                COLOR24.id -> COLOR24
                COLOR25.id -> COLOR25
                COLOR26.id -> COLOR26
                COLOR27.id -> COLOR27
                COLOR28.id -> COLOR28
                COLOR29.id -> COLOR29
                COLOR30.id -> COLOR30
                COLOR31.id -> COLOR31
                COLOR32.id -> COLOR32
                COLOR33.id -> COLOR33
                COLOR34.id -> COLOR34
                COLOR35.id -> COLOR35
                else -> COLOR36
            }
        }

        /**
         * Returns a list of [UserStyleSetting.ListUserStyleSetting.ListOption] for all
         * ColorStyleIdAndResourceIds enums. The watch face settings APIs use this to set up
         * options for the user to select a style.
         */
        fun toOptionList(context: Context): List<ListUserStyleSetting.ListOption> {
            val colorStyleIdAndResourceIdsList = enumValues<ColorStyleIdAndResourceIds>()
            return colorStyleIdAndResourceIdsList.map { shapeStyleIdAndResourceIds ->
                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                BitmapFactory.decodeResource(context.resources,shapeStyleIdAndResourceIds.colorIcon,options)
                options.inSampleSize = 2
                options.inJustDecodeBounds = false
                val iconBitmap = BitmapFactory.decodeResource(context.resources,shapeStyleIdAndResourceIds.colorIcon,options)
                ListUserStyleSetting.ListOption(
                    UserStyleSetting.Option.Id(shapeStyleIdAndResourceIds.id),
                    context.resources,
                    shapeStyleIdAndResourceIds.nameResourceId,
                    Icon.createWithBitmap(iconBitmap)
                )
            }
        }
    }
}


