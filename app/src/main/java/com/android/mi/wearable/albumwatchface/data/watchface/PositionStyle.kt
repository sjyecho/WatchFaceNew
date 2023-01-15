package com.android.mi.wearable.albumwatchface.data.watchface

import android.content.Context
import androidx.wear.watchface.style.UserStyleSetting
import com.android.mi.wearable.albumwatchface.R


private const val TOP_STYLE_NAME_RESOURCE_ID = R.string.top_style_name
private const val BOTTOM_STYLE_NAME_RESOURCE_ID = R.string.top_style_name
private const val LEFT_STYLE_NAME_RESOURCE_ID = R.string.top_style_name
private const val RIGHT_STYLE_NAME_RESOURCE_ID = R.string.top_style_name

enum class PositionStyle(
    val id: String,
    val nameResourceId: Int,
    val positionStyle: Int,

) {
    //上下左右但是需要区分上下左右是都都显示
    TOP(
        id = "top_style_id",
        nameResourceId = TOP_STYLE_NAME_RESOURCE_ID,
        positionStyle = PositionStyle1,
    ),
    BOTTOM(
        id = "bottom_style_id",
        nameResourceId = BOTTOM_STYLE_NAME_RESOURCE_ID,
        positionStyle = PositionStyle2,
    ),
    LEFT(
        id = "left_style_id",
        nameResourceId = LEFT_STYLE_NAME_RESOURCE_ID,
        positionStyle = PositionStyle3,
    ),
    RIGHT(
        id = "right_style_id",
        nameResourceId = RIGHT_STYLE_NAME_RESOURCE_ID,
        positionStyle = PositionStyle4,
    );

    companion object {
        /**
         * Translates the string id to the correct ColorStyleIdAndResourceIds object.
         */
        fun getPositionStyleConfig(id: String): PositionStyle {
            return when (id) {
                TOP.id -> TOP
                BOTTOM.id -> BOTTOM
                LEFT.id -> LEFT
                RIGHT.id -> RIGHT
                else -> TOP
            }
        }

        /**
         * Returns a list of [UserStyleSetting.ListUserStyleSetting.ListOption] for all
         * ColorStyleIdAndResourceIds enums. The watch face settings APIs use this to set up
         * options for the user to select a style.
         */
        fun toOptionList(context: Context): List<UserStyleSetting.ListUserStyleSetting.ListOption> {

                val positionStyleIdAndResourceIdsList = arrayOf(TOP,BOTTOM,LEFT,RIGHT)
                return positionStyleIdAndResourceIdsList.map { shapeStyleIdAndResourceIds ->
                    UserStyleSetting.ListUserStyleSetting.ListOption(
                        UserStyleSetting.Option.Id(shapeStyleIdAndResourceIds.id),
                        context.resources,
                        shapeStyleIdAndResourceIds.nameResourceId,
                        null
                    )
                }


        }
    }
}