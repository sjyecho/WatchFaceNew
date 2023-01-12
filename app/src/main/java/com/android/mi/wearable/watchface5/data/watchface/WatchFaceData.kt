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
const val TYPE_10 = 10
const val TYPE_11 = 11
const val TYPE_12 = 12
const val TYPE_13 = 13
const val TYPE_14 = 14
const val TYPE_15 = 15
const val TYPE_16 = 16
const val TYPE_17 = 17
const val TYPE_18 = 18
const val TYPE_19 = 19
const val TYPE_20 = 20
const val TYPE_21 = 21
const val TYPE_22 = 22
const val TYPE_23 = 23
const val TYPE_24 = 24
const val TYPE_25 = 25
const val TYPE_26 = 26
const val TYPE_27 = 27
const val TYPE_28 = 28
const val TYPE_29 = 29
const val TYPE_30 = 30
const val TYPE_31 = 31
const val TYPE_32 = 32
const val TYPE_33 = 33
const val TYPE_34 = 34
const val TYPE_35 = 35
const val TYPE_36 = 36
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
    val activeColorStyle: ColorStyleIdAndResourceIds = ColorStyleIdAndResourceIds.COLOR1,
//    val ambientColorStyle: ColorStyleIdAndResourceIds = ColorStyleIdAndResourceIds.AMBIENT_YELLOW,
    val shapeStyle: ShapeStyleIdAndResourceIds = ShapeStyleIdAndResourceIds.STY1,
    val positionStyle: PositionStyle = PositionStyle.TOP
)
