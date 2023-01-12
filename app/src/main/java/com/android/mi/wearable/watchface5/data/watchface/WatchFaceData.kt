package com.android.mi.wearable.watchface5.data.watchface


/**
 * 相册表盘的三种style
 */
const val TYPE_1 = 0
const val TYPE_2 = 1
const val TYPE_3 = 2
const val TYPE_4 = 3
const val TYPE_5 = 4
const val TYPE_6 = 5
const val TYPE_7 = 6
const val TYPE_8 = 7
const val TYPE_9 = 8
/**
 * type
 */
const val STYLE1: Int = 0
const val STYLE2: Int = 1
const val STYLE3: Int = 2
const val STYLE4: Int = 3
const val STYLE5: Int = 4
const val STYLE6: Int = 5
const val STYLE7: Int = 6
const val STYLE8: Int = 7
const val STYLE9: Int = 8

/**
 * position
 */
const val PositionStyle1: Int = 0
const val PositionStyle2: Int = 1
const val PositionStyle3: Int = 2
const val PositionStyle4: Int = 3



data class WatchFaceData(
    val activeColorStyle: ColorStyleIdAndResourceIds = ColorStyleIdAndResourceIds.YELLOW,
//    val ambientColorStyle: ColorStyleIdAndResourceIds = ColorStyleIdAndResourceIds.AMBIENT_YELLOW,
    val shapeStyle: ShapeStyleIdAndResourceIds = ShapeStyleIdAndResourceIds.STY1,
    val positionStyle: PositionStyle = PositionStyle.TOP
)
