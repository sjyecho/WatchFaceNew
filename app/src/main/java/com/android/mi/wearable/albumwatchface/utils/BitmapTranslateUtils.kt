package com.android.mi.wearable.albumwatchface.utils
import com.android.mi.wearable.albumwatchface.R
import com.android.mi.wearable.albumwatchface.data.watchface.*
import java.util.*

object BitmapTranslateUtils {
    //当前position
    fun currentPositionItemPosition(id: String): Int{
        return when(id){
            PositionStyle.TOP.id -> 0
            PositionStyle.BOTTOM.id ->1
            PositionStyle.LEFT.id ->2
            PositionStyle.RIGHT.id ->3
            else ->-1
        }
    }


    //当前style的位置
    fun currentShapeItemPosition(id: String): Int {
        return when(id){
            ShapeStyleIdAndResourceIds.STY1.id -> 0
            ShapeStyleIdAndResourceIds.STY2.id -> 1
            ShapeStyleIdAndResourceIds.STY3.id -> 2
            ShapeStyleIdAndResourceIds.STY4.id -> 3
            ShapeStyleIdAndResourceIds.STY5.id -> 4
            ShapeStyleIdAndResourceIds.STY6.id -> 5
            ShapeStyleIdAndResourceIds.STY7.id -> 6
            ShapeStyleIdAndResourceIds.STY8.id -> 7
            ShapeStyleIdAndResourceIds.STY9.id -> 8
            else -> 0
        }
    }

    //下一个style的位置
    fun nextResId(currentId: String): String{
        return when (currentId){
            ShapeStyleIdAndResourceIds.STY1.id -> ShapeStyleIdAndResourceIds.STY2.id
            ShapeStyleIdAndResourceIds.STY2.id -> ShapeStyleIdAndResourceIds.STY3.id
            ShapeStyleIdAndResourceIds.STY3.id -> ShapeStyleIdAndResourceIds.STY4.id
            ShapeStyleIdAndResourceIds.STY4.id -> ShapeStyleIdAndResourceIds.STY5.id
            ShapeStyleIdAndResourceIds.STY5.id -> ShapeStyleIdAndResourceIds.STY6.id
            ShapeStyleIdAndResourceIds.STY6.id -> ShapeStyleIdAndResourceIds.STY7.id
            ShapeStyleIdAndResourceIds.STY7.id -> ShapeStyleIdAndResourceIds.STY8.id
            ShapeStyleIdAndResourceIds.STY8.id -> ShapeStyleIdAndResourceIds.STY9.id
            ShapeStyleIdAndResourceIds.STY6.id -> ShapeStyleIdAndResourceIds.STY1.id
            else -> ShapeStyleIdAndResourceIds.STY1.id
        }
    }

    //当前默认的相册表盘的背景
    fun currentBg(styleType: Int): Int{
        when (styleType) {
            STYLE1-> {
                return R.drawable.style1_bg
            }
            STYLE2 -> {
                return R.drawable.style_2_bg
            }
            STYLE3 -> {
                return R.drawable.style3_bg
            }
            STYLE4 -> {
                return R.drawable.style_4
            }
            STYLE5 -> {
                return R.drawable.style_5_bg
            }
            STYLE6 -> {
                return R.drawable.style_6_bg
            }
            STYLE7 ->{
                return R.drawable.style_bg_7
            }
            STYLE8 -> {
                return R.drawable.style_8_bg
            }
            STYLE9 -> {
                return R.drawable.style_9_bg
            }

        }
        return -1
    }


    //相册表盘当前的时间
    fun currentTime() : String{
        //获取当前的小时
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        //存放当前的时间
        var time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()
        //判断当前的小时是否是<10
        if (hour<10){
            val currentHour = "0$hour"
            time = currentHour
        }else{
            val currentHour = hour.toString()
            time = currentHour
        }
        time += ":"
        //获取当前的分钟
        val minute = Calendar.getInstance().get(Calendar.MINUTE)
        time += when {
            minute<10 -> {
                val currentMinute = "0$minute"
                currentMinute
            }
            else -> {
                val currentMinute = minute.toString()
                currentMinute
            }
        }
        return time
    }

    //样式九的星期
    fun currentWeek8(): Array<String>{
        val weekArray = arrayOf("0","0","0")
        when(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)){
            2 -> {
                weekArray[0] = "M"
                weekArray[1] = "O"
                weekArray[2] = "N"
                return  weekArray
            }
            3 -> {
                weekArray[0] = "T"
                weekArray[1] = "U"
                weekArray[2] = "E"
                return  weekArray
            }
            4 -> {
                weekArray[0] = "W"
                weekArray[1] = "E"
                weekArray[2] = "D"
                return  weekArray
            }
            5 -> {
                weekArray[0] = "T"
                weekArray[1] = "H"
                weekArray[2] = "U"
                return  weekArray
            }
            6 -> {
                weekArray[0] = "F"
                weekArray[1] = "R"
                weekArray[2] = "I"
                return  weekArray
            }
            7 -> {
                weekArray[0] = "S"
                weekArray[1] = "A"
                weekArray[2] = "T"
                return  weekArray
            }
            else -> {
                weekArray[0] = "S"
                weekArray[1] = "U"
                weekArray[2] = "N"
                return  weekArray
            }

        }
    }


    //获取当前的TUE
    fun currentWeekdayNotAll() : String{
        when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)){
            2 -> {
                return "MON"
            }
            3 -> {
                return "TUE"
            }
            4 -> {
                return "WED"
            }
            5 -> {
                return "THU"
            }
            6 -> {
                return "FRI"
            }
            7 -> {
                return "SAT"
            }
            else -> {
                return "SUN"
            }

        }

    }


    //获取当前的TUESDAY
    fun currentWeekdayAll() : String{
        when (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            2 -> {
                return "MONDAY"
            }
            3 -> {
                return "TUESDAY"
            }
            4 -> {
                return "WEDNESDAY"
            }
            5 -> {
                return "THURSDAY"
            }
            6 -> {
                return "FRIDAY"
            }
            7 -> {
                return "SATURDAY"
            }
            else -> {
                return "SUNDAY"
            }
        }
    }

    //获取当前的TUESDAY
    fun currentWeekday(weekday: Int) : Array<String>{
        when ((weekday)) {
            2 -> {
                return arrayOf("M","o","n","d","a","y")
            }
            3 -> {
                return arrayOf("T","u","e","s","d","a","y")
            }
            4 -> {
                return arrayOf("W","e","d","n","e","s","d","a","y")
            }
            5 -> {
                return arrayOf("T","h","u","r","s","d","a","y")
            }
            6 -> {
                return arrayOf("F","r","i","d","a","y")
            }
            7 -> {
                return arrayOf("S","a","t","u","r","d","a","y")
            }
            else -> {
                return arrayOf("S","u","n","d","a","y")
            }
        }
    }


//方案七的时间样式
//用数组存当前的每个数字
fun currentTime17(): Array<String>{
    //先定义一个数组
    val timeArray = arrayOf("0", "0", ":", "0", "0")
    //获取当前的小时
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    //获取当前的小时
    if (hour<10){
        timeArray[0] = "0"
        timeArray[1] = hour.toString()
    }else{
        //获取当前的小时十位
        val hourTen = hour/10%10
        val hourBit = hour%10
        timeArray[0] = hourTen.toString()
        timeArray[1] = hourBit.toString()
    }
    //获取当前的分钟
    val minute = Calendar.getInstance().get(Calendar.MINUTE)
    if (minute<10){
        timeArray[3] = "0"
        timeArray[4] = minute.toString()
    }else{
        val minuteTen = minute / 10 %10
        val minuteBit = minute % 10
        timeArray[3] = minuteTen.toString()
        timeArray[4] = minuteBit.toString()
    }
    return timeArray

}
    //获取当前样式4的月份和/
    //获取当前的日期08
    fun currentMonth4(): String{
        //先定义一个数组
        val dataArray = arrayOf("0", "0", "/")
        //获取当前的小时
        val month = Calendar.getInstance().get(Calendar.MONTH)
        //获取当前的小时
        if (month<10){
            dataArray[0] = "0"
            if (month == 0){
                dataArray[1] = "1"
            }else{
                dataArray[1] = month.toString()
            }
        }else{
            //获取当前的小时十位
            val monthTen = month/10%10
            val monthBit = month%10
            dataArray[0] = monthTen.toString()
            dataArray[1] = monthBit.toString()
        }
        return dataArray[0]+dataArray[1]+dataArray[2]
    }

    //获取当前样式4的月份和/
    //获取当前的日期16
    fun currentDay4(): String{
        //先定义一个数组
        val dataArray = arrayOf("0", "0")
        //获取当前的day
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        if (day<10){
            dataArray[0] = "0"
            dataArray[1] = day.toString()
        }else{
            val dayTen = day / 10 %10
            val dayBit = day % 10
            dataArray[0] = dayTen.toString()
            dataArray[1] = dayBit.toString()
        }
        return dataArray[0]+dataArray[1]
    }

    //获取当前的月份08
    fun currentMonth8(): String{
        //先定义一个数组
        val monthArray = arrayOf("0", "0")
        //获取当前的day
        val month = Calendar.getInstance().get(Calendar.MONTH)+1
        if (month<10){
            monthArray[0] = "0"
            monthArray[1] = month.toString()
        }else{
            val monthTen = month / 10 %10
            val monthBit = month % 10
            monthArray[0] = monthTen.toString()
            monthArray[1] = monthBit.toString()
        }
        return monthArray[0]+monthArray[1]
    }

    //获取当前时间的高位
    fun currentHour8(): String{
        //先定义一个数组
        val hourArray = arrayOf("0", "0")
        //获取当前的day
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        if (hour<10){
            hourArray[0] = "0"
            hourArray[1] = hour.toString()
        }else{
            val hourTen = hour / 10 %10
            val hourBit = hour % 10
            hourArray[0] = hourTen.toString()
            hourArray[1] = hourBit.toString()
        }
        return hourArray[0]+hourArray[1]
    }
    //获取当前的分钟
    fun currentMinute8(): String{
        //先定义一个数组
        val minuteArray = arrayOf("0", "0")
        //获取当前的day
        val minute = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        if (minute<10){
            minuteArray[0] = "0"
            minuteArray[1] = minute.toString()
        }else{
            val minuteTen = minute / 10 %10
            val minuteBit = minute % 10
            minuteArray[0] = minuteTen.toString()
            minuteArray[1] = minuteBit.toString()
        }
        return minuteArray[0]+minuteArray[1]
    }


    //获取当前的日期08/19
    fun currentData(): String{
        //先定义一个数组
        val dataArray = arrayOf("0", "0", "/", "0", "0")
        //获取当前的小时
        val month = Calendar.getInstance().get(Calendar.MONTH)
        //获取当前的月份
        if (month<10){
            dataArray[0] = "0"
            if (month == 0){
                dataArray[1] = "1"
            }else{
                dataArray[1] = month.toString()
            }
        }else{
            //获取当前的小时十位
            val monthTen = month/10%10
            val monthBit = month%10
            dataArray[0] = monthTen.toString()
            dataArray[1] = monthBit.toString()
        }
        //获取当前的day
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        if (day<10){
            dataArray[3] = "0"
            dataArray[4] = day.toString()
        }else{
            val dayTen = day / 10 %10
            val dayBit = day % 10
            dataArray[3] = dayTen.toString()
            dataArray[4] = dayBit.toString()
        }
        return dataArray[0]+dataArray[1]+dataArray[2]+dataArray[3]+dataArray[4]
    }


    //方案7获取当前day
    fun currentDay7(): Array<String>{
        val dayArray = arrayOf("1","1")
        //获取当前的时间
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        if (day<10){
            dayArray[0] = "0"
            dayArray[1] = day.toString()
        }else{
            val dayTen = day / 10 %10
            val dayBit = day % 10
            dayArray[0] = dayTen.toString()
            dayArray[1] = dayBit.toString()
        }
        return dayArray

    }

    //方案7获取当前的月份
    fun currentMonth7(): Array<String> {
        //先定义一个数组
        val dayArray = arrayOf("0", "0", "0")
        //获取当前的月份
        when (Calendar.getInstance().get(Calendar.MONTH)) {
            0 -> {
                dayArray[0] = "J"
                dayArray[1] = "A"
                dayArray[2] = "N"
            }
            1 -> {
                dayArray[0] = "F"
                dayArray[1] = "E"
                dayArray[2] = "B"
            }
            2 -> {
                dayArray[0] = "M"
                dayArray[1] = "A"
                dayArray[2] = "R"
            }
            3 -> {
                dayArray[0] = "A"
                dayArray[1] = "P"
                dayArray[2] = "R"
            }
            4 -> {
                dayArray[0] = "M"
                dayArray[1] = "A"
                dayArray[2] = "Y"
            }
            5 -> {
                dayArray[0] = "J"
                dayArray[1] = "U"
                dayArray[2] = "N"
            }
            6 -> {
                dayArray[0] = "J"
                dayArray[1] = "U"
                dayArray[2] = "L"
            }
            7 -> {
                dayArray[0] = "A"
                dayArray[1] = "U"
                dayArray[2] = "G"
            }
            8 -> {
                dayArray[0] = "S"
                dayArray[1] = "E"
                dayArray[2] = "T"
            }
            9 -> {
                dayArray[0] = "O"
                dayArray[1] = "C"
                dayArray[2] = "T"
            }
            10 -> {
                dayArray[0] = "N"
                dayArray[1] = "O"
                dayArray[2] = "V"
            }
            11 -> {
                dayArray[0] = "D"
                dayArray[1] = "E"
                dayArray[2] = "C"
            }
        }
        return dayArray

    }
    //方案九获取当前的月份
    fun currentDay9(): String{
        //先定义一个数组
        val dayArray = arrayOf("0","0","0", "/", "0", "0")
        //获取当前的月份
        when (Calendar.getInstance().get(Calendar.MONTH)) {
            0 -> {
                dayArray[0] = "J"
                dayArray[1] = "A"
                dayArray[2] = "N"
            }
            1 -> {
                dayArray [0] = "F"
                dayArray [1] = "E"
                dayArray [2] = "B"
            }
            2 -> {
                dayArray [0] = "M"
                dayArray [1] = "A"
                dayArray [2] = "R"
            }
            3 -> {
                dayArray [0] = "A"
                dayArray [1] = "P"
                dayArray [2] = "R"
            }
            4 -> {
                dayArray [0] = "M"
                dayArray [1] = "A"
                dayArray [2] = "Y"
            }
            5 -> {
                dayArray [0] = "J"
                dayArray [1] = "U"
                dayArray [2] = "N"
            }
            6 -> {
                dayArray [0] = "J"
                dayArray [1] = "U"
                dayArray [2] = "L"
            }
            7 -> {
                dayArray [0] = "A"
                dayArray [1] = "U"
                dayArray [2] = "G"
            }
            8 -> {
                dayArray [0] = "S"
                dayArray [1] = "E"
                dayArray [2] = "T"
            }
            9 -> {
                dayArray [0] = "O"
                dayArray [1] = "C"
                dayArray [2] = "T"
            }
            10 -> {
                dayArray [0] = "N"
                dayArray [1] = "O"
                dayArray [2] = "V"
            }
            11 -> {
                dayArray [0] = "D"
                dayArray [1] = "E"
                dayArray [2] = "C"
            }
        }

        //获取当前的时间
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        if (day<10){
            dayArray[4] = "0"
            dayArray[5] = day.toString()
        }else{
            val dayTen = day / 10 %10
            val dayBit = day % 10
            dayArray[4] = dayTen.toString()
            dayArray[5] = dayBit.toString()
        }
        return "${dayArray[0]}${dayArray[1]}${dayArray[2]}${dayArray[3]}${dayArray[4]}${dayArray[5]}"
    }







    fun currentColorItemPosition(id: String): Int {
        return when(id){
            ColorStyleIdAndResourceIds.COLOR1.id -> 0
            ColorStyleIdAndResourceIds.COLOR2.id -> 1
            ColorStyleIdAndResourceIds.COLOR3.id -> 2
            ColorStyleIdAndResourceIds.COLOR4.id -> 3
            ColorStyleIdAndResourceIds.COLOR5.id -> 4
            ColorStyleIdAndResourceIds.COLOR6.id -> 5
            ColorStyleIdAndResourceIds.COLOR7.id -> 6
            ColorStyleIdAndResourceIds.COLOR8.id -> 7
            ColorStyleIdAndResourceIds.COLOR9.id -> 8
            ColorStyleIdAndResourceIds.COLOR10.id -> 8
            ColorStyleIdAndResourceIds.COLOR11.id -> 10
            ColorStyleIdAndResourceIds.COLOR12.id -> 11
            ColorStyleIdAndResourceIds.COLOR13.id -> 12
            ColorStyleIdAndResourceIds.COLOR14.id -> 13
            ColorStyleIdAndResourceIds.COLOR15.id -> 14
            ColorStyleIdAndResourceIds.COLOR16.id -> 15
            ColorStyleIdAndResourceIds.COLOR17.id -> 16
            ColorStyleIdAndResourceIds.COLOR18.id -> 17
            ColorStyleIdAndResourceIds.COLOR19.id -> 18
            ColorStyleIdAndResourceIds.COLOR20.id -> 19
            ColorStyleIdAndResourceIds.COLOR21.id -> 20
            ColorStyleIdAndResourceIds.COLOR22.id -> 21
            ColorStyleIdAndResourceIds.COLOR23.id -> 22
            ColorStyleIdAndResourceIds.COLOR24.id -> 23
            ColorStyleIdAndResourceIds.COLOR25.id -> 24
            ColorStyleIdAndResourceIds.COLOR26.id -> 25
            ColorStyleIdAndResourceIds.COLOR27.id -> 26
            ColorStyleIdAndResourceIds.COLOR28.id -> 27
            ColorStyleIdAndResourceIds.COLOR29.id -> 28
            ColorStyleIdAndResourceIds.COLOR30.id -> 29
            ColorStyleIdAndResourceIds.COLOR31.id -> 30
            ColorStyleIdAndResourceIds.COLOR32.id -> 31
            ColorStyleIdAndResourceIds.COLOR33.id -> 32
            ColorStyleIdAndResourceIds.COLOR34.id -> 33
            ColorStyleIdAndResourceIds.COLOR35.id -> 34
            ColorStyleIdAndResourceIds.COLOR36.id -> 35
            else -> 0
        }
    }



}