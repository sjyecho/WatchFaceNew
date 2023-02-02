    package com.android.mi.wearable.albumwatchface

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.*
import android.os.BatteryManager
import android.view.SurfaceHolder
import androidx.core.graphics.withRotation
import androidx.wear.watchface.*
import androidx.wear.watchface.complications.data.ShortTextComplicationData
import androidx.wear.watchface.style.CurrentUserStyleRepository
import androidx.wear.watchface.style.UserStyle
import androidx.wear.watchface.style.UserStyleSetting
import com.android.mi.wearable.albumwatchface.data.watchface.*
import com.android.mi.wearable.albumwatchface.utils.*
import com.android.mi.wearable.albumwatchface.utils.TOP_COMPLICATION_ID_1
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.ZonedDateTime
import java.util.Calendar
import kotlin.math.abs


    // Default for how long each frame is displayed at expected frame rate.
private const val FRAME_PERIOD_MS_DEFAULT: Long = 16L
//private const val DEFAULT_COMPLICATION_STYLE_DRAWABLE_ID = R.drawable.complication_red_style
//private const val DEFAULT_COMPLICATION_STYLE_DRAWABLE_ID_TEST = R.drawable.complication_left_style1
class WatchFace3CanvasRenderer(
    private val context: Context,
    surfaceHolder: SurfaceHolder,
    watchState: WatchState,
    private val complicationSlotsManager: ComplicationSlotsManager,
    currentUserStyleRepository: CurrentUserStyleRepository,
    canvasType: Int
) : Renderer.CanvasRenderer2<WatchFace3CanvasRenderer.AnalogSharedAssets>(
    surfaceHolder,
    currentUserStyleRepository,
    watchState,
    canvasType,
    FRAME_PERIOD_MS_DEFAULT,
    clearWithBackgroundTintBeforeRenderingHighlightLayer = false
) {
    //绘制当前的方案七旋转字体
    private val style7PathPaint = Paint().apply {
        color = Color.TRANSPARENT
        isAntiAlias = true
        textAlign = Paint.Align.RIGHT
    }

    private val style7PathPaint1 = Paint().apply {
        color = Color.TRANSPARENT
        isAntiAlias = true
        textAlign = Paint.Align.RIGHT
    }



    /**
     * 相册表表盘的字体文件
     */
    private var watchFaceData: WatchFaceData = WatchFaceData()
    private val textPaint = Paint().apply {
        isAntiAlias = true
    }

    //绘制方案三的描边
    private val textBoardPaint = Paint().apply {
        isAntiAlias = true
    }

    //绘制圆形的笔
    private val roundPaint = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
    }

    private val roundPaint7 = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
    }

    //绘制圆形的笔
    private val roundPaint5 = Paint().apply {
        isAntiAlias = true
        color = Color.WHITE
    }

    //style1paint
    private val style1Paint1 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets,"fonts/BalooChettan2-Bold.ttf")
        color = context.getColor(watchFaceData.activeColorStyle.colorInt)
        textSize = (screenBounds.width() * 104f) / 466f
        isAntiAlias = true
    }
    private val style1Paint2 = Paint().apply {
       typeface = Typeface.createFromAsset(context.assets,"fonts/BalooChettan2-Bold.ttf")
       color = context.getColor(R.color.style1_always)
       textSize =  (screenBounds.width() * 104f) / 466f
       isAntiAlias = true
    }

    //style2paint
    private val style2paint1 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets,"fonts/MiSans_Demibold.ttf")
        color = Color.WHITE
        textSize = (screenBounds.width() * 22f) / 466f
        letterSpacing = 0.19f
        isAntiAlias = true
    }

    //style3paint
    private val style3Paint1 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets,"fonts/RobotoText-Regular.otf")
        color = Color.WHITE
        alpha =  20
        textSize = (screenBounds.width() * 42f) / 466f
        isAntiAlias = true
    }
    private val style3Paint2 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets,"fonts/RobotoFlex-Regular.ttf")
        textSize = (screenBounds.width() * 22f) / 466f
        color = Color.WHITE
        isAntiAlias = true
    }
    private val style3Paint3 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets,"fonts/RobotoFlex-Regular.ttf")
        textSize = (screenBounds.width() * 22f) / 466f
        color = context.getColor(R.color.style3_always)
        isAntiAlias = true
    }

    //style4paint
    private val style4Paint1 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets,"fonts/MiSans_Light.ttf")
        color = Color.WHITE
        textSize = (screenBounds.width() * 36f) / 466f
        isAntiAlias = true

    }
    private val style4Paint2 = Paint().apply {
        isAntiAlias = true
        typeface = Typeface.createFromAsset(context.assets, "fonts/MiSans_Semibold.ttf")
        color = Color.WHITE
        textSize = (screenBounds.width() * 36f) / 466f
    }
    private val style4Paint3 = Paint().apply {
        isAntiAlias = true
        typeface = Typeface.createFromAsset(context.assets, "fonts/MiSans_Semibold.ttf")
        color = Color.WHITE
        textSize = (screenBounds.width() * 20f) / 466f
    }
    private val style4Paint4 = Paint().apply {
        isAntiAlias = true
        textSize = (screenBounds.width() * 36f) / 466f
        typeface = Typeface.createFromAsset(context.assets, "fonts/MiSans_Semibold.ttf")
        color = Color.WHITE
    }

    //style5paint
    private val style5Paint1 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets, "fonts/MiSans_Light.ttf")
        textSize = (screenBounds.width() * 36f) / 466f
        color = Color.WHITE
        isAntiAlias = true
    }
    private val style5Paint2 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets, "fonts/MiSans_Medium.ttf")
        textSize = (screenBounds.width() * 22f) / 466f
        color = Color.WHITE
        isAntiAlias = true
    }
    private val style5Paint3 = Paint().apply {
        textSize = (screenBounds.width() * 36f) / 466f
        typeface = Typeface.createFromAsset(context.assets, "fonts/MiSans_Semibold.ttf")
        color = Color.WHITE
        isAntiAlias = true
    }
    private val style5Paint4 = Paint().apply {
        textSize = (screenBounds.width() * 24f) / 466f
        typeface = Typeface.createFromAsset(context.assets, "fonts/MiSans_Semibold.ttf")
        color = Color.WHITE
        isAntiAlias = true
    }
    //style5always
    private val style5Paint5 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets, "fonts/MiSans_Light.ttf")
        textSize = (screenBounds.width() * 36f) / 466f
        color = context.getColor(R.color.style3_always)
        isAntiAlias = true
    }
    private val style5Paint6 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets, "fonts/MiSans_Medium.ttf")
        textSize = (screenBounds.width() * 22f) / 466f
        color = context.getColor(R.color.style3_always)
        isAntiAlias = true
    }
    private val style5Paint7 = Paint().apply {
        textSize = (screenBounds.width() * 36f) / 466f
        typeface = Typeface.createFromAsset(context.assets, "fonts/MiSans_Semibold.ttf")
        color = context.getColor(R.color.style3_always)
        isAntiAlias = true
    }
    private val style5Paint8 = Paint().apply {
        textSize = (screenBounds.width() * 24f) / 466f
        typeface = Typeface.createFromAsset(context.assets, "fonts/MiSans_Semibold.ttf")
        color = context.getColor(R.color.style3_always)
        isAntiAlias = true
    }

    //style6
    private val style6Paint1 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets,"fonts/yundong.ttf")
        color = Color.WHITE
        textSize = (screenBounds.width() * 56f) / 466f
        isAntiAlias = true
    }
    private val style6Paint2 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets,"fonts/MiSans_Medium.ttf")
        textSize = (screenBounds.width() * 24f) / 466f
        color = Color.WHITE
        isAntiAlias = true
    }
    private val style6Paint3 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets,"fonts/yundong.ttf")
        color = context.getColor(R.color.style6_always_1)
        textSize = (screenBounds.width() * 56f) / 466f
        isAntiAlias = true
    }
    private val style6Paint4 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets,"fonts/MiSans_Medium.ttf")
        textSize = (screenBounds.width() * 32f) / 466f
        color = context.getColor(R.color.style6_always)
        isAntiAlias = true
    }

    //style7
    private val style7Paint1 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets,"fonts/yundong.ttf")
        color = Color.WHITE
        textSize = (screenBounds.width() * 64f) / 466f
        isAntiAlias = true
    }
    private val style7Paint2 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets,"fonts/yundong.ttf")
        color = Color.WHITE
        textSize = (screenBounds.width() * 32f) / 466f
        isAntiAlias = true
    }
    private val style7Paint3 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets,"fonts/yundong.ttf")
        color = context.getColor(R.color.style1_always)
        textSize = (screenBounds.width() * 64f) / 466f
        isAntiAlias = true
    }

    //style8
    private val style8Paint1 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets, "fonts/MiSans_Semibold.ttf")
        textSize = (screenBounds.width() * 22f) / 466f
        color = Color.WHITE
        isAntiAlias = true
    }
    private val style8Paint2 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets, "fonts/MiSans_Semibold.ttf")
        textSize = (screenBounds.width() * 18f) / 466f
        color = Color.WHITE
        isAntiAlias = true
    }
    private val style8Paint3 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets, "fonts/MiSans_Semibold.ttf")
        textSize = (screenBounds.width() * 18f) / 466f
        color = Color.WHITE
        letterSpacing = 0.04f
        isAntiAlias = true
    }
    private val style8Paint4 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets, "fonts/MiSans_Semibold.ttf")
        textSize = (screenBounds.width() * 120f) / 466f
        color = Color.WHITE
        alpha = 40
        isAntiAlias = true
    }

    //style9
    private val style9Paint1 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets,"fonts/MiSans_Normal.ttf")
        color = Color.WHITE
        textSize = (screenBounds.width() * 59f) / 466f
        letterSpacing = -0.02f
        isAntiAlias = true

    }
    private val style9Paint2 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets,"fonts/MiSans_Regular.ttf")
        color = Color.WHITE
        alpha = 30
        textSize = (screenBounds.width() * 108f) / 466f
        isAntiAlias = true
    }

    private val clockPaint = Paint().apply {
        isAntiAlias = true
    }



    private val complicationPaint1 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets,"fonts/BalooChettan2-SemiBold.ttf")
        isAntiAlias = true
        textSize = (screenBounds.width() * 24f) / 466f
        color = Color.WHITE
    }

    private val complicationPaint2 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets,"fonts/MiSans_Demibold.ttf")
        color = Color.WHITE
        isAntiAlias = true
        textSize = (screenBounds.width() * 24f) / 466f
    }

    private val complicationPaint3 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets,"fonts/MiSans_Light.ttf")
        color = Color.WHITE
        isAntiAlias = true
        textSize = (screenBounds.width() * 36f) / 466f
    }
    private val complicationPaint4 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets,"fonts/MiSans_Demibold.ttf")
        color = Color.WHITE
        isAntiAlias = true
        textSize = (screenBounds.width() * 36f) / 466f
    }


    private val complicationPaint5 = Paint().apply {
        typeface = Typeface.createFromAsset(context.assets,"fonts/MiSans_Medium.ttf")
        color = Color.WHITE
        isAntiAlias = true
        textSize = (screenBounds.width() * 24f) / 466f
    }






    private val scope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)



    init {
        /**
         * TODO 需要替换资源文件
         */


        scope.launch {
            currentUserStyleRepository.userStyle.collect { userStyle ->
                updateWatchFaceData(userStyle)
            }
        }
    }

    /*
    * Triggered when the user makes changes to the watch face through the settings activity. The
    * function is called by a flow.
    */
    private fun updateWatchFaceData(userStyle: UserStyle) {
        var newWatchFaceData: WatchFaceData = watchFaceData

        // Loops through user style and applies new values to watchFaceData.
        for (options in userStyle) {
            when (options.key.id.toString()) {
                COLOR_STYLE_SETTING -> {
                    val listOption = options.value as
                            UserStyleSetting.ListUserStyleSetting.ListOption

                    newWatchFaceData = newWatchFaceData.copy(
                        activeColorStyle = ColorStyleIdAndResourceIds.getColorStyleConfig(
                            listOption.id.toString()
                        )
                    )
                }
                SHAPE_STYLE_SETTING -> {
                    val listOption = options.value as
                            UserStyleSetting.ListUserStyleSetting.ListOption
                    newWatchFaceData = newWatchFaceData.copy(
                        shapeStyle = ShapeStyleIdAndResourceIds.getShapeStyleConfig(
                            listOption.id.toString()
                        )
                    )
                }
                POSITION_STYLE_SETTING -> {
                    val listOption = options.value as
                            UserStyleSetting.ListUserStyleSetting.ListOption
                    newWatchFaceData = newWatchFaceData.copy(
                        positionStyle = PositionStyle.getPositionStyleConfig(
                            listOption.id.toString()
                        )
                    )
                }

            }

            // Only updates if something changed.
            if (watchFaceData != newWatchFaceData) {
                watchFaceData = newWatchFaceData

            }

        }
    }

    class AnalogSharedAssets : Renderer.SharedAssets {
        override fun onDestroy() {
        }
    }

    override suspend fun createSharedAssets(): AnalogSharedAssets {
        return AnalogSharedAssets()
    }

    override fun onRenderParametersChanged(renderParameters: RenderParameters) {
        super.onRenderParametersChanged(renderParameters)
    }

    override fun render(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: AnalogSharedAssets
    ) {
        //绘制当前的背景，但是之后需要和相册表盘结合在一起
        //TODO
        val drawAmbient = renderParameters.drawMode == DrawMode.AMBIENT
        if (!drawAmbient){
            val bg = BitmapFactory.decodeResource(context.resources,BitmapTranslateUtils.currentBg(watchFaceData.shapeStyle.shapeType))
            canvas.drawBitmap(bg,null, bounds   ,clockPaint)
            drawComplications(canvas,bounds,zonedDateTime)
        }else{
            val bg = BitmapFactory.decodeResource(context.resources,R.drawable.bg)
            canvas.drawBitmap(bg,0f,0f,clockPaint)
        }

        drawTime(canvas,bounds,zonedDateTime)
    }



    //绘制当前的背景和时间
    private fun drawTime(canvas: Canvas, bounds: Rect, zonedDateTime: ZonedDateTime){
        val drawAmbient = renderParameters.drawMode == DrawMode.AMBIENT
        //style1 样式正确
        if (watchFaceData.shapeStyle.shapeType == STYLE1&&!drawAmbient){
            val textWidth: Float = style1Paint1.measureText(BitmapTranslateUtils.currentTime())
            val baseLineY: Float = abs(style1Paint1.ascent() + style1Paint1.descent()) / 2
            style1Paint1.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
            if (watchFaceData.positionStyle.positionStyle == PositionStyle1){
                canvas.drawText(BitmapTranslateUtils.currentTime(),233f-textWidth / 2, baseLineY+340f,style1Paint1)
            }else if (watchFaceData.positionStyle.positionStyle == PositionStyle2){
                canvas.drawText(BitmapTranslateUtils.currentTime(),233f-textWidth / 2,baseLineY+87f,style1Paint1)
            }
        }else if (watchFaceData.shapeStyle.shapeType == STYLE1&&drawAmbient){
            val textWidth: Float = style1Paint2.measureText(BitmapTranslateUtils.currentTime())
            canvas.drawText(BitmapTranslateUtils.currentTime(),233f-textWidth/2,261f,style1Paint2)
        }

        //style2 样式正确
        val secondOfDay = zonedDateTime.toLocalTime().toSecondOfDay()
        val secondsPerHourHandRotation = Duration.ofHours(12).seconds
        val secondsPerMinuteHandRotation = Duration.ofHours(1).seconds
        val hourRotation = secondOfDay.rem(secondsPerHourHandRotation) * 360.0f /
                secondsPerHourHandRotation
        val minuteRotation = secondOfDay.rem(secondsPerMinuteHandRotation) * 360.0f /
                secondsPerMinuteHandRotation
        val hourHandBitmap = BitmapFactory.decodeResource(context.resources,R.drawable.hour)
        val minuteHandBitmap = BitmapFactory.decodeResource(context.resources,R.drawable.minute)
        val centerBitmap = BitmapFactory.decodeResource(context.resources,R.drawable.center)
        if (watchFaceData.shapeStyle.shapeType == STYLE2 && !drawAmbient){
            //绘制方案二的index
            val indexBg = BitmapFactory.decodeResource(context.resources,R.drawable.index)
            canvas.drawBitmap(indexBg,null, bounds  ,clockPaint)
            //当前字体的样式
            val textWidth: Float = style2paint1.measureText(BitmapTranslateUtils.currentWeekdayAll())
            //绘制当前的星期
            style2paint1.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
            when (watchFaceData.positionStyle.positionStyle) {
                 PositionStyle1 -> {
                    canvas.drawText(BitmapTranslateUtils.currentWeekdayAll(),233f-textWidth / 2,146f,style2paint1)
                }
                PositionStyle2 -> {
                    canvas.drawText(BitmapTranslateUtils.currentWeekdayAll(),233f-textWidth / 2,292f,style2paint1)
                }
                PositionStyle3 -> {
                        canvas.drawText(BitmapTranslateUtils.currentWeekdayAll(),36f,225f,style2paint1)
                }
                PositionStyle4 -> {
                        canvas.drawText(BitmapTranslateUtils.currentWeekdayAll(),270f,225f,style2paint1)
                }
            }
            //绘制指针，轴心
            canvas.withRotation(hourRotation, bounds.exactCenterX(), bounds.exactCenterY()) {
                drawBitmap(hourHandBitmap, bounds.exactCenterX() - hourHandBitmap.width/2, bounds.exactCenterY() - hourHandBitmap.height/2, clockPaint)
            }
            canvas.withRotation(minuteRotation, bounds.exactCenterX(), bounds.exactCenterY()) {
                drawBitmap(minuteHandBitmap, bounds.exactCenterX() - minuteHandBitmap.width/2, bounds.exactCenterY() - minuteHandBitmap.height/2, clockPaint)
            }
            canvas.drawBitmap(centerBitmap,bounds.exactCenterX()-centerBitmap.width/2,bounds.exactCenterY()-centerBitmap.height/2,clockPaint)
        }else if (watchFaceData.shapeStyle.shapeType == STYLE2 && drawAmbient){
            canvas.withRotation(hourRotation, bounds.exactCenterX(), bounds.exactCenterY()) {
                drawBitmap(hourHandBitmap, bounds.exactCenterX() - hourHandBitmap.width/2, bounds.exactCenterY() - hourHandBitmap.height/2, clockPaint)
            }
            canvas.withRotation(minuteRotation, bounds.exactCenterX(), bounds.exactCenterY()) { drawBitmap(minuteHandBitmap, bounds.exactCenterX() - minuteHandBitmap.width/2, bounds.exactCenterY() - minuteHandBitmap.height/2, clockPaint)
            }
            canvas.drawBitmap(centerBitmap,bounds.exactCenterX()-centerBitmap.width/2,bounds.exactCenterY()-centerBitmap.height/2,clockPaint)
        }

        //style3 样式正确
        if (watchFaceData.shapeStyle.shapeType == STYLE3 && !drawAmbient){
            //新建一个描边的字体画笔
            textBoardPaint.style = Paint.Style.STROKE
            textBoardPaint.strokeWidth = 1f
            textBoardPaint.typeface = Typeface.createFromAsset(context.assets,"fonts/RobotoText-Regular.otf")
            textBoardPaint.color = Color.WHITE
            textBoardPaint.textSize = 42f

           if (watchFaceData.positionStyle.positionStyle == PositionStyle1){
               style3Paint1.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
               style3Paint1.alpha = 20
               textBoardPaint.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
               var textWidth: Float = style3Paint1.measureText(BitmapTranslateUtils.currentTime())
               canvas.drawText(BitmapTranslateUtils.currentTime(),233f-textWidth/2,102f,style3Paint1)
               canvas.drawText(BitmapTranslateUtils.currentTime(),233f-textWidth/2,102f,textBoardPaint)
               //绘制星期
               style3Paint2.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
               canvas.drawText(BitmapTranslateUtils.currentWeekdayNotAll(),240f,125f,style3Paint2)
               //绘制当前日期
               textWidth = style3Paint2.measureText(BitmapTranslateUtils.currentData())
               canvas.drawText(BitmapTranslateUtils.currentData(),233f-textWidth,125f,style3Paint2)
           }else if (watchFaceData.positionStyle.positionStyle == PositionStyle2) {
               style3Paint1.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
               textBoardPaint.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
               style3Paint2.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
               style3Paint1.alpha = 20
               var textWidth: Float = style3Paint1.measureText(BitmapTranslateUtils.currentTime())
               canvas.drawText(BitmapTranslateUtils.currentTime(),233f-textWidth/2,380f,style3Paint1)
               canvas.drawText(BitmapTranslateUtils.currentTime(),233f-textWidth/2,380f,textBoardPaint)
               //绘制星期
               canvas.drawText(BitmapTranslateUtils.currentWeekdayNotAll(),240f,400f,style3Paint2)
               //绘制当前日期
               textWidth = style3Paint2.measureText(BitmapTranslateUtils.currentData())
               canvas.drawText(BitmapTranslateUtils.currentData(),233f-textWidth,400f,style3Paint2)
           }
        }else if (watchFaceData.shapeStyle.shapeType == STYLE3 && drawAmbient){
            textBoardPaint.color = context.getColor(R.color.style3_always)
            var textWidth: Float = textBoardPaint.measureText(BitmapTranslateUtils.currentTime())
            canvas.drawText(BitmapTranslateUtils.currentTime(),233f-textWidth/2,236f,textBoardPaint)
            //绘制星期
            textPaint.typeface = Typeface.createFromAsset(context.assets,"fonts/RobotoFlex-Regular.ttf")
            textPaint.textSize = 22f
            textPaint.color = context.getColor(R.color.style3_always)
            canvas.drawText(BitmapTranslateUtils.currentWeekdayNotAll(),250f,265f,style3Paint3)
            //绘制当前日期
            textWidth = style3Paint3.measureText(BitmapTranslateUtils.currentData())
            canvas.drawText(BitmapTranslateUtils.currentData(),233f-textWidth,265f,style3Paint3)
        }

        //style4 样式正确
        if (watchFaceData.shapeStyle.shapeType == STYLE4 && !drawAmbient){

            canvas.skew(0f,-0.15f)
            style4Paint1.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
            style4Paint2.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
            style4Paint3.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
            style4Paint4.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
            roundPaint.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
            if (watchFaceData.positionStyle.positionStyle == PositionStyle1){

                /**
                 * paint1
                 */
                //绘制当前的月份
                canvas.drawText(BitmapTranslateUtils.currentMonth4(),120f,115f,style4Paint1)
                //绘制当前的星期
                val weekday = "("+Calendar.getInstance().get(Calendar.DAY_OF_WEEK).toString()+")"
                canvas.drawText(weekday,220f,115f,style4Paint1)
                //绘制[Batt]
                canvas.drawText("[Batt]",120f,184f,style4Paint1)

                /**
                 * paint2
                 */
                //绘制当前的时间
                canvas.drawText(BitmapTranslateUtils.currentTime(),120f,150f,style4Paint2)
                //绘制当前的日期
                canvas.drawText(BitmapTranslateUtils.currentDay4(),170f,115f,style4Paint2)

                /**
                 * paint3
                 */
                //绘制当前AM和PM
                val timeZone = Calendar.getInstance().get(Calendar.AM_PM)
                var timeSign = ""
                timeSign = if (timeZone == 0){ "AM" }else{ "PM" }
                canvas.drawText(timeSign,230f,135f,style4Paint3)

                /**
                 * paintround
                 */
                //绘制包含秒的圆
                roundPaint.style = Paint.Style.STROKE
                roundPaint.strokeWidth = 3f
                canvas.drawCircle(285f,135f,18f,roundPaint)

                /**
                 * paint3
                 */
                //绘制当前秒
                val secondText = (Calendar.getInstance().get(Calendar.SECOND)+1).toString()
                if (Calendar.getInstance().get(Calendar.SECOND)<10){
                    canvas.drawText(secondText,280f,143f,style4Paint3)
                }else{
                    canvas.drawText(secondText,273f,143f,style4Paint3)
                }

                /**
                 * paint4
                 */
                //绘制当前的电量
                //获取当前的电量
                //battery
                val batteryStatus: Intent? =
                    context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
                var batteryPct: Float? = batteryStatus?.let { intent ->
                    val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                    val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                    ((level * 100 / scale.toFloat()))
                }
                val batteryValue = batteryPct?.toInt() ?: 0
                val batteryTemplate = "$batteryValue%"
                canvas.drawText(batteryTemplate,208f,184f,style4Paint4)
                val batteryIcon = BitmapFactory.decodeResource(context.resources,R.drawable.batt_icon)
                canvas.drawBitmap(batteryIcon,308f,143f,style4Paint4)

            }else if (watchFaceData.positionStyle.positionStyle == PositionStyle2){

                //绘制当前的月份
                canvas.drawText(BitmapTranslateUtils.currentMonth4(),120f,355f,style4Paint1)
                //绘制当前的星期
                val weekday = "("+Calendar.getInstance().get(Calendar.DAY_OF_WEEK).toString()+")"
                canvas.drawText(weekday,220f,355f,style4Paint1)
                //绘制[Batt]
                canvas.drawText("[Batt]",120f,424f,style4Paint1)

                canvas.drawText(BitmapTranslateUtils.currentTime(),120f,390f,style4Paint2)
                //绘制当前的日期
                canvas.drawText(BitmapTranslateUtils.currentDay4(),170f,355f,style4Paint2)

                //绘制当前AM和PM
                val timeZone = Calendar.getInstance().get(Calendar.AM_PM)
                var timeSign = ""
                timeSign = if (timeZone == 0){ "AM" }else{ "PM" }
                canvas.drawText(timeSign,230f,375f,style4Paint3)

                //绘制包含秒的圆
                roundPaint.style = Paint.Style.STROKE
                roundPaint.strokeWidth = 3f
                canvas.drawCircle(285f,375f,18f,roundPaint)

                //绘制当前秒
                val secondText = (Calendar.getInstance().get(Calendar.SECOND)+1).toString()
                if (Calendar.getInstance().get(Calendar.SECOND)<10){
                    canvas.drawText(secondText,280f,383f,style4Paint3)
                }else{
                    canvas.drawText(secondText,273f,383f,style4Paint3)
                }
                //绘制当前的电量
                //获取当前的电量
                //battery
                val batteryStatus: Intent? =
                    context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
                var batteryPct: Float? = batteryStatus?.let { intent ->
                    val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                    val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                    ((level * 100 / scale.toFloat()))
                }
                val batteryValue = batteryPct?.toInt() ?: 0
                val batteryTemplate = "$batteryValue%"
                canvas.drawText(batteryTemplate,208f,424f,style4Paint4)
                val batteryIcon = BitmapFactory.decodeResource(context.resources,R.drawable.batt_icon)
                canvas.drawBitmap(batteryIcon,308f,383f,style4Paint4)
            }
        }else if (watchFaceData.shapeStyle.shapeType == STYLE4 && drawAmbient){
            style4Paint1.color = Color.WHITE
            style4Paint2.color = Color.WHITE
            style4Paint3.color = Color.WHITE
            style4Paint4.color = Color.WHITE
            canvas.skew(0f,-0.15f)
            //绘制当前的月份
            canvas.drawText(BitmapTranslateUtils.currentMonth4(),150f,235f,style4Paint1)
            //绘制当前的星期
            val weekday = "("+Calendar.getInstance().get(Calendar.DAY_OF_WEEK).toString()+")"
            canvas.drawText(weekday,250f,235f,style4Paint1)
            //绘制[Batt]
            canvas.drawText("[Batt]",150f,304f,style4Paint1)

            canvas.drawText(BitmapTranslateUtils.currentTime(),150f,270f,style4Paint2)
            //绘制当前的日期
            canvas.drawText(BitmapTranslateUtils.currentDay4(),200f,235f,style4Paint2)

            //绘制当前AM和PM
            val timeZone = Calendar.getInstance().get(Calendar.AM_PM)
            var timeSign = ""
            timeSign = if (timeZone == 0){ "AM" }else{ "PM" }
            canvas.drawText(timeSign,260f,255f,style4Paint3)
            //绘制包含秒的圆
            roundPaint.color = context.getColor(R.color.style3_always)
            roundPaint.style = Paint.Style.STROKE
            roundPaint.strokeWidth = 3f
            canvas.drawCircle(315f,255f,18f,roundPaint)
            //绘制当前秒
            val secondText = (Calendar.getInstance().get(Calendar.SECOND)+1).toString()
            if (Calendar.getInstance().get(Calendar.SECOND)<10){
                canvas.drawText(secondText,310f,263f,style4Paint3)
            }else{
                canvas.drawText(secondText,303f,263f,style4Paint3)
            }

            //绘制当前的电量
            //获取当前的电量
            //battery
            val batteryStatus: Intent? =
                context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            var batteryPct: Float? = batteryStatus?.let { intent ->
                val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                ((level * 100 / scale.toFloat()))
            }
            val batteryValue = batteryPct?.toInt() ?: 0
            val batteryTemplate = "$batteryValue%"
            canvas.drawText(batteryTemplate,238f,304f,style4Paint4)
            val batteryIcon = BitmapFactory.decodeResource(context.resources,R.drawable.batt_icon)
            canvas.drawBitmap(batteryIcon,338f,263f,style4Paint4)
        }

        //style5 样式正确
        if (watchFaceData.shapeStyle.shapeType == STYLE5 &&!drawAmbient){
            style5Paint1.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
            style5Paint2.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
            style5Paint3.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
            style5Paint4.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
            roundPaint5.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
            if (watchFaceData.positionStyle.positionStyle == PositionStyle1){
                //绘month
                canvas.drawText(BitmapTranslateUtils.currentMonth4(),40f,193f,style5Paint1)

                //绘制电量
                val batteryStatus: Intent? =
                    context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
                var batteryPct: Float? = batteryStatus?.let { intent ->
                    val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                    val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                    ((level * 100 / scale.toFloat()))
                }
                val batteryValue = batteryPct?.toInt() ?: 0
                val batteryTemplate = "$batteryValue% Batt"
                canvas.drawText(batteryTemplate,40f,295f,style5Paint2)

                //绘day
                canvas.drawText(BitmapTranslateUtils.currentDay4(),92f,193f,style5Paint3)
                //绘制时间
                canvas.drawText(BitmapTranslateUtils.currentTime(),40f,229f,style5Paint3)
                //绘制AM PM
                val timeZone = Calendar.getInstance().get(Calendar.AM_PM)
                var timeSign = ""
                timeSign = if (timeZone == 0){ "AM" }else{ "PM" }
                canvas.drawText(timeSign,140f,229f,style5Paint3)

                //绘制当前星期
                val weekday = Calendar.getInstance().get(Calendar.DAY_OF_WEEK).toString()
                canvas.drawText(weekday,143f,190f,style5Paint4)

                //绘制圆形
                roundPaint5.style = Paint.Style.STROKE
                roundPaint5.strokeWidth = 3f
                canvas.drawCircle(150f,180f,15f,roundPaint5)
            }else if (watchFaceData.positionStyle.positionStyle == PositionStyle2){

                //绘month
                canvas.drawText(BitmapTranslateUtils.currentMonth4(),240f,193f,style5Paint1)

                //绘制电量
                val batteryStatus: Intent? =
                    context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
                var batteryPct: Float? = batteryStatus?.let { intent ->
                    val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                    val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                    ((level * 100 / scale.toFloat()))
                }
                val batteryValue = batteryPct?.toInt() ?: 0
                val batteryTemplate = "$batteryValue% Batt"
                canvas.drawText(batteryTemplate,240f,295f,style5Paint2)

                //绘day
                canvas.drawText(BitmapTranslateUtils.currentDay4(),292f,193f,style5Paint3)
                //绘制时间
                canvas.drawText(BitmapTranslateUtils.currentTime(),240f,229f,style5Paint3)
                //绘制AM PM
                val timeZone = Calendar.getInstance().get(Calendar.AM_PM)
                var timeSign = ""
                timeSign = if (timeZone == 0){ "AM" }else{ "PM" }
                canvas.drawText(timeSign,340f,229f,style5Paint3)

                //绘制当前星期
                val weekday = Calendar.getInstance().get(Calendar.DAY_OF_WEEK).toString()
                canvas.drawText(weekday,343f,190f,style5Paint4)

                //绘制圆形
                roundPaint5.style = Paint.Style.STROKE
                roundPaint5.strokeWidth = 3f
                canvas.drawCircle(350f,180f,15f,roundPaint5)
                canvas.save()
            }
        }else if(watchFaceData.shapeStyle.shapeType == STYLE5 &&drawAmbient){

            //绘month
            canvas.drawText(BitmapTranslateUtils.currentMonth4(),160f,193f,style5Paint5)

            //绘制电量
            val batteryStatus: Intent? =
                context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            var batteryPct: Float? = batteryStatus?.let { intent ->
                val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                ((level * 100 / scale.toFloat()))
            }
            val batteryValue = batteryPct?.toInt() ?: 0
            val batteryTemplate = "$batteryValue% Batt"
            canvas.drawText(batteryTemplate,160f,295f,style5Paint6)

            //绘day
            canvas.drawText(BitmapTranslateUtils.currentDay4(),212f,193f,style5Paint7)
            //绘制时间
            canvas.drawText(BitmapTranslateUtils.currentTime(),160f,229f,style5Paint7)
            //绘制AM PM
            val timeZone = Calendar.getInstance().get(Calendar.AM_PM)
            var timeSign = ""
            timeSign = if (timeZone == 0){ "AM" }else{ "PM" }
            canvas.drawText(timeSign,260f,229f,style5Paint7)

            //绘制当前星期
            val weekday = Calendar.getInstance().get(Calendar.DAY_OF_WEEK).toString()
            canvas.drawText(weekday,263f,190f,style5Paint8)
            //绘制圆形
            roundPaint.style = Paint.Style.STROKE
            roundPaint.strokeWidth = 3f
            roundPaint.color =context.getColor(R.color.style3_always)
            canvas.drawCircle(270f,180f,15f,roundPaint)
            canvas.save()
        }

        //style6 样式正确
        if (watchFaceData.shapeStyle.shapeType == STYLE6&&!drawAmbient){
            style6Paint1.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
            style6Paint2.color = context.getColor(watchFaceData.activeColorStyle.colorInt)

            when (watchFaceData.positionStyle.positionStyle) {
                PositionStyle1 -> {
                    //绘制当前的时间
                    val textWidth: Float = style6Paint1.measureText(BitmapTranslateUtils.currentTime())
                    canvas.drawText(BitmapTranslateUtils.currentTime(),233f-textWidth/2,390f,style6Paint1)
                    //绘制当前的电量
                    //绘制电量
                    val batteryStatus: Intent? =
                        context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
                    var batteryPct: Float? = batteryStatus?.let { intent ->
                        val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                        val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                        ((level * 100 / scale.toFloat()))
                    }
                    val batteryValue = batteryPct?.toInt() ?: 0
                    val batteryTemplate = "$batteryValue%"
                    canvas.drawText(batteryTemplate,217f,328f,style6Paint2)
                    //绘制电量图标
                    val batteryIcon = BitmapFactory.decodeResource(context.resources,R.drawable.battery_icon_6)
                    canvas.drawBitmap(batteryIcon,195f,305f,style6Paint2)
                }
                PositionStyle2 -> {
                    //绘制当前的时间
                    var textWidth: Float = style6Paint1.measureText(BitmapTranslateUtils.currentTime())
                    canvas.drawText(BitmapTranslateUtils.currentTime(),233f-textWidth/2,108f,style6Paint1)
                    //绘制当前的电量
                    //绘制电量
                    textPaint.textSize = 22f
                    textPaint.typeface = Typeface.createFromAsset(context.assets, "fonts/MiSans_Medium.ttf")
                    val batteryStatus: Intent? =
                        context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
                    val batteryPct: Float? = batteryStatus?.let { intent ->
                        val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                        val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                        ((level * 100 / scale.toFloat()))
                    }
                    val batteryValue = batteryPct?.toInt() ?: 0
                    val batteryTemplate = "$batteryValue%"
                    canvas.drawText(batteryTemplate,217f,51f,style6Paint2)
                    //绘制电量图标
                    val batteryIcon = BitmapFactory.decodeResource(context.resources,R.drawable.battery_icon_6)
                    canvas.drawBitmap(batteryIcon,195f,27f,style6Paint2)
                }
                PositionStyle3 -> {
                    //绘制当前的时间
                    canvas.drawText(BitmapTranslateUtils.currentTime(),260f,249f,style6Paint1)
                    //绘制当前的电量
                    //绘制电量
                    val batteryStatus: Intent? =
                        context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
                    val batteryPct: Float? = batteryStatus?.let { intent ->
                        val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                        val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                        ((level * 100 / scale.toFloat()))
                    }
                    val batteryValue = batteryPct?.toInt() ?: 0
                    val batteryTemplate = "$batteryValue%"
                    canvas.drawText(batteryTemplate,372f,187f,style6Paint2)
                    //绘制电量图标
                    val batteryIcon = BitmapFactory.decodeResource(context.resources,R.drawable.battery_icon_6)
                    canvas.drawBitmap(batteryIcon,352f,168f,style6Paint2)
                }
            }
        }else if (watchFaceData.shapeStyle.shapeType == STYLE6 && drawAmbient){
            //绘制当前的时间
            val textWidth: Float = style6Paint3.measureText(BitmapTranslateUtils.currentTime())
            canvas.drawText(BitmapTranslateUtils.currentTime(),233f-textWidth/2,249f,style6Paint3)
            //绘制当前的电量
            //绘制电量
            val batteryStatus: Intent? =
                context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            val batteryPct: Float? = batteryStatus?.let { intent ->
                val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                ((level * 100 / scale.toFloat()))
            }
            val batteryValue = batteryPct?.toInt() ?: 0
            val batteryTemplate = "$batteryValue%"
            val textWidth1: Float = style6Paint3.measureText(batteryTemplate)
            canvas.drawText(batteryTemplate,210f,187f,style6Paint4)
            //绘制电量图标
            val batteryIcon = BitmapFactory.decodeResource(context.resources,R.drawable.style_6_icon)
            canvas.drawBitmap(batteryIcon,185f,160f,style6Paint4)
        }

        //style7 样式正确
        if (watchFaceData.shapeStyle.shapeType == STYLE7&&!drawAmbient){
            //经当前笔的样式绘制下来
            style7Paint1.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
            style7Paint2.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
            if (watchFaceData.positionStyle.positionStyle == PositionStyle1){
                //绘制当前的时间
                val path = Path()
                val rectF  = RectF(15f,15f,436f,436f)
                path.addArc(rectF,-72.49f,60f)
                canvas.drawPath(path,style7PathPaint)
                canvas.drawTextOnPath(BitmapTranslateUtils.currentTime(),path,0f,50f,style7Paint1)
                //绘制当前的月份和日期和星期
//                val path1 = Path()
//                val rectF1  = RectF(25f,25f,416f,400f)
//                path1.addOval(rectF1,Path.Direction.CCW)
//                canvas.drawPath(path1,style7PathPaint1)
//                canvas.withRotation(180f,bounds.exactCenterX(),bounds.exactCenterY()){
//                    canvas.drawTextOnPath(BitmapTranslateUtils.currentMonthAll7()+" "+BitmapTranslateUtils.currentDayAll7()+"  "+BitmapTranslateUtils.currentWeekdayAll7(),path1,0f,20f,style7Paint2)
//                }
                //绘制旋转的月份
                val monthArray = BitmapTranslateUtils.currentMonth7()
                canvas.withRotation (85.99f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(monthArray[0],220f,440f,style7Paint2)
                }
                canvas.withRotation (78.52f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(monthArray[1],225f,440f,style7Paint2)
                }
                canvas.withRotation (71.57f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(monthArray[2],230f,440f,style7Paint2)
                }
                //绘制day
                val dayArray = BitmapTranslateUtils.currentDay7()
                canvas.withRotation (61.73f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(dayArray[0],245f,440f,style7Paint2)
                }
                canvas.withRotation (53.95f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(dayArray[1],245f,440f,style7Paint2)
                }
                //绘制星期
                val weekday = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                val weekArray = BitmapTranslateUtils.currentWeekday(weekday)
                //"S","u","n","d","a","y"
                if (weekday == 1){
                    canvas.withRotation (41.27f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[0],250f,440f,style7Paint2)
                    }
                    canvas.withRotation (34.44f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[1],250f,445f,style7Paint2)
                    }
                    canvas.withRotation (27.69f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[2],250f,446f,style7Paint2)
                    }
                    canvas.withRotation (21.02f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[3],250f,448f,style7Paint2)
                    }
                    canvas.withRotation (14.01f,bounds.exactCenterX(),bounds.exactCenterY()) {
                        drawText(weekArray[4], 250f, 448f, style7Paint2)
                    }
                    canvas.withRotation (7.09f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[5],250f,450f,style7Paint2)
                    }
                }
                //Monday
                if (weekday == 2){
                    canvas.withRotation (41.27f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[0],240f,440f,style7Paint2)
                    }
                    canvas.withRotation (34.44f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[1],250f,445f,style7Paint2)
                    }
                    canvas.withRotation (27.69f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[2],250f,446f,style7Paint2)
                    }
                    canvas.withRotation (21.02f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[3],250f,448f,style7Paint2)
                    }
                    canvas.withRotation (14.01f,bounds.exactCenterX(),bounds.exactCenterY()) {
                        drawText(weekArray[4], 250f, 448f, style7Paint2)
                    }
                    canvas.withRotation (7.09f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[5],250f,450f,style7Paint2)
                    }
                }
                //Tuesday
                if (weekday == 3){
                canvas.withRotation (41.27f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(weekArray[0],250f,440f,style7Paint2)
                }
                canvas.withRotation (34.44f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(weekArray[1],250f,445f,style7Paint2)
                }
                canvas.withRotation (27.69f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(weekArray[2],250f,446f,style7Paint2)
                }
                canvas.withRotation (21.02f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(weekArray[3],250f,447f,style7Paint2)
                }
                canvas.withRotation (14.01f,bounds.exactCenterX(),bounds.exactCenterY()) {
                    drawText(weekArray[4], 250f, 448f, style7Paint2)
                }
                    canvas.withRotation (7.09f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(weekArray[5],250f,450f,style7Paint2)
                }
                canvas.withRotation (0.62f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(weekArray[6],250f,450f,style7Paint2)
                }
                }
                //"W","e","d","n","e","s","d","a","y"
                if (weekday == 4){
                    canvas.withRotation (41.27f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[0],238f,440f,style7Paint2)
                    }
                    canvas.withRotation (34.44f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[1],250f,445f,style7Paint2)
                    }
                    canvas.withRotation (27.69f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[2],250f,446f,style7Paint2)
                    }
                    canvas.withRotation (21.02f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[3],250f,447f,style7Paint2)
                    }
                    canvas.withRotation (14.01f,bounds.exactCenterX(),bounds.exactCenterY()) {
                        drawText(weekArray[4], 250f, 448f, style7Paint2)
                    }
                    canvas.withRotation (7.09f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[5],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (0.62f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[6],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (-11.71f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[7],230f,450f,style7Paint2)
                    }
                    canvas.withRotation (-18.25f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[8],230f,450f,style7Paint2)
                    }
                }
                //("T","h","u","r","s","d","a","y")
                if (weekday == 5){
                    canvas.withRotation (41.27f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[0],245f,440f,style7Paint2)
                    }
                    canvas.withRotation (34.44f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[1],250f,445f,style7Paint2)
                    }
                    canvas.withRotation (27.69f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[2],250f,446f,style7Paint2)
                    }
                    canvas.withRotation (21.02f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[3],250f,447f,style7Paint2)
                    }
                    canvas.withRotation (14.01f,bounds.exactCenterX(),bounds.exactCenterY()) {
                        drawText(weekArray[4], 250f, 448f, style7Paint2)
                    }
                    canvas.withRotation (7.09f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[5],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (0.62f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[6],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (-11.71f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[7],230f,450f,style7Paint2)
                    }
                }
                //("F","r","i","d","a","y")
                if (weekday == 6){
                    canvas.withRotation (41.27f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[0],250f,440f,style7Paint2)
                    }
                    canvas.withRotation (34.44f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[1],250f,445f,style7Paint2)
                    }
                    canvas.withRotation (27.69f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[2],250f,446f,style7Paint2)
                    }
                    canvas.withRotation (21.02f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[3],245f,448f,style7Paint2)
                    }
                    canvas.withRotation (14.01f,bounds.exactCenterX(),bounds.exactCenterY()) {
                        drawText(weekArray[4], 250f, 448f, style7Paint2)
                    }
                    canvas.withRotation (7.09f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[5],250f,450f,style7Paint2)
                    }
                }
                //("S","a","t","u","r","d","a","y")
                if (weekday == 7){
                    canvas.withRotation (41.27f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[0],245f,440f,style7Paint2)
                    }
                    canvas.withRotation (34.44f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[1],250f,445f,style7Paint2)
                    }
                    canvas.withRotation (27.69f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[2],250f,446f,style7Paint2)
                    }
                    canvas.withRotation (21.02f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[3],250f,447f,style7Paint2)
                    }
                    canvas.withRotation (14.01f,bounds.exactCenterX(),bounds.exactCenterY()) {
                        drawText(weekArray[4], 250f, 448f, style7Paint2)
                    }
                    canvas.withRotation (7.09f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[5],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (0.62f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[6],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (-11.71f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[7],230f,450f,style7Paint2)
                    }
                }
            }else if (watchFaceData.positionStyle.positionStyle == PositionStyle2){
                //绘制旋转时间
                val timeArray = BitmapTranslateUtils.currentTime17()
                canvas.withRotation (-28.09f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(timeArray[0],205f,80f,style7Paint1)
                }
                canvas.withRotation (-11.41f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(timeArray[1],210f,80f,style7Paint1)
                }
                canvas.withRotation (0f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(timeArray[2],225f,80f,style7Paint1)
                }
                canvas.withRotation (11.41f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(timeArray[3],210f,80f,style7Paint1)
                }
                canvas.withRotation (28.09f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(timeArray[4],208f,80f,style7Paint1)
                }
                //绘制旋转的月份
                val monthArray = BitmapTranslateUtils.currentMonth7()
                canvas.withRotation (41.89f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(monthArray[0],220f,440f,style7Paint2)
                }
                canvas.withRotation (34.42f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(monthArray[1],225f,440f,style7Paint2)
                }
                canvas.withRotation (27.46f,bounds.exactCenterX(),bounds.exactCenterY()) {
                    drawText(monthArray[2], 230f, 440f, style7Paint2)
                }
                //绘制day
                val dayArray = BitmapTranslateUtils.currentDay7()
                canvas.withRotation (17.63f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(dayArray[0],245f,440f,style7Paint2)
                }
                canvas.withRotation (9.85f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(dayArray[1],245f,440f,style7Paint2)
                }
                //绘制星期
                val weekday = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                val weekArray = BitmapTranslateUtils.currentWeekday(weekday)
                //"S","u","n","d","a","y"
                if (weekday == 1){
                    canvas.withRotation (-3.26f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[0],250f,440f,style7Paint2)
                    }
                    canvas.withRotation (-10.88f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[1],250f,445f,style7Paint2)
                    }
                    canvas.withRotation (-17.62f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[2],250f,446f,style7Paint2)
                    }
                    canvas.withRotation (-24.49f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[3],250f,448f,style7Paint2)
                    }
                    canvas.withRotation (-31.48f,bounds.exactCenterX(),bounds.exactCenterY()) {
                        drawText(weekArray[4], 250f, 448f, style7Paint2)
                    }
                    canvas.withRotation (-37.98f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[5],250f,450f,style7Paint2)
                    }
                }
                //Monday
                if (weekday == 2){
                    canvas.withRotation (-4.71f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[0],240f,440f,style7Paint2)
                    }
                    canvas.withRotation (-13.27f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[1],250f,445f,style7Paint2)
                    }
                    canvas.withRotation (-19.98f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[2],250f,446f,style7Paint2)
                    }
                    canvas.withRotation (-26.82f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[3],250f,448f,style7Paint2)
                    }
                    canvas.withRotation (-33.78f,bounds.exactCenterX(),bounds.exactCenterY()) {
                        drawText(weekArray[4], 250f, 448f, style7Paint2)
                    }
                    canvas.withRotation (-40.47f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[5],250f,450f,style7Paint2)
                    }
                }
                //Tuesday
                if (weekday == 3){
                    canvas.withRotation (-3.5f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[0],250f,440f,style7Paint2)
                    }
                    canvas.withRotation (-10.33f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[1],250f,445f,style7Paint2)
                    }
                    canvas.withRotation (-17.09f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[2],250f,446f,style7Paint2)
                    }
                    canvas.withRotation (-23.76f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[3],250f,447f,style7Paint2)
                    }
                    canvas.withRotation (-30.76f,bounds.exactCenterX(),bounds.exactCenterY()) {
                        drawText(weekArray[4], 250f, 448f, style7Paint2)
                    }
                    canvas.withRotation (-37.68f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[5],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (-44.15f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[6],250f,450f,style7Paint2)
                    }
                }
                //"W","e","d","n","e","s","d","a","y"
                if (weekday == 4){
                    canvas.withRotation (-6.2f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[0],238f,440f,style7Paint2)
                    }
                    canvas.withRotation (-15.62f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[1],250f,445f,style7Paint2)
                    }
                    canvas.withRotation (-22.53f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[2],250f,446f,style7Paint2)
                    }
                    canvas.withRotation (-29.55f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[3],250f,447f,style7Paint2)
                    }
                    canvas.withRotation (-36.07f,bounds.exactCenterX(),bounds.exactCenterY()) {
                        drawText(weekArray[4], 250f, 448f, style7Paint2)
                    }
                    canvas.withRotation (-42.75f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[5],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (-49.62f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[6],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (-56.52f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[7],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (-63.06f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[8],250f,450f,style7Paint2)
                    }
                }
                //("T","h","u","r","s","d","a","y")
                if (weekday == 5){
                    canvas.withRotation (-3.15f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[0],245f,440f,style7Paint2)
                    }
                    canvas.withRotation (-10.34f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[1],250f,445f,style7Paint2)
                    }
                    canvas.withRotation (-17.1f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[2],250f,446f,style7Paint2)
                    }
                    canvas.withRotation (-22.48f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[3],250f,447f,style7Paint2)
                    }
                    canvas.withRotation (-28.02f,bounds.exactCenterX(),bounds.exactCenterY()) {
                        drawText(weekArray[4], 250f, 448f, style7Paint2)
                    }
                    canvas.withRotation (-34.97f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[5],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (-41.87f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[6],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (-48.32f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[7],250f,450f,style7Paint2)
                    }
                }
                //("F","r","i","d","a","y")
                if (weekday == 6){
                    canvas.withRotation (-2.81f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[0],250f,440f,style7Paint2)
                    }
                    canvas.withRotation (-8.32f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[1],250f,445f,style7Paint2)
                    }
                    canvas.withRotation (-11.83f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[2],253f,446f,style7Paint2)
                    }
                    canvas.withRotation (-16.62f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[3],245f,448f,style7Paint2)
                    }
                    canvas.withRotation (-23.5f,bounds.exactCenterX(),bounds.exactCenterY()) {
                        drawText(weekArray[4], 250f, 448f, style7Paint2)
                    }
                    canvas.withRotation (-30.08f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[5],250f,450f,style7Paint2)
                    }
                }
                //("S","a","t","u","r","d","a","y")
                if (weekday == 7){
                    canvas.withRotation (-3.49f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[0],245f,440f,style7Paint2)
                    }
                    canvas.withRotation (-10.97f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[1],250f,445f,style7Paint2)
                    }
                    canvas.withRotation (-16.85f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[2],250f,446f,style7Paint2)
                    }
                    canvas.withRotation (-22.45f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[3],250f,447f,style7Paint2)
                    }
                    canvas.withRotation (-27.98f,bounds.exactCenterX(),bounds.exactCenterY()) {
                        drawText(weekArray[4], 250f, 448f, style7Paint2)
                    }
                    canvas.withRotation (-33.88f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[5],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (-40.78f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[6],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (-47.23f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[7],250f,450f,style7Paint2)
                    }
                }

            }else if (watchFaceData.positionStyle.positionStyle == PositionStyle3){
                //绘制旋转时间
                val timeArray = BitmapTranslateUtils.currentTime17()
                canvas.withRotation(-72.91f, bounds.exactCenterX(), bounds.exactCenterY()) {
                        drawText(timeArray[0], 205f, 80f, style7Paint1)
                    }
                canvas.withRotation(-56.22f, bounds.exactCenterX(), bounds.exactCenterY()) {
                        drawText(timeArray[1], 210f, 80f, style7Paint1)
                    }
                canvas.withRotation(-44.83f, bounds.exactCenterX(), bounds.exactCenterY()) {
                        drawText(timeArray[2], 225f, 80f, style7Paint1)
                    }
                canvas.withRotation(-33.37f, bounds.exactCenterX(), bounds.exactCenterY()) {
                        drawText(timeArray[3], 210f, 80f, style7Paint1)
                    }
                canvas.withRotation(-16.99f, bounds.exactCenterX(), bounds.exactCenterY()) {
                        drawText(timeArray[4], 208f, 80f, style7Paint1)
                    }
                //绘制旋转的月份
                val monthArray = BitmapTranslateUtils.currentMonth7()
                canvas.withRotation (-3.79f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(monthArray[0],220f,440f,style7Paint2)
                }
                canvas.withRotation (-11.26f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(monthArray[1],225f,440f,style7Paint2)
                }
                canvas.withRotation (-18.22f,bounds.exactCenterX(),bounds.exactCenterY()) {
                    drawText(monthArray[2], 230f, 440f, style7Paint2)
                }
                //绘制day
                val dayArray = BitmapTranslateUtils.currentDay7()
                canvas.withRotation (-28.05f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(dayArray[0],245f,440f,style7Paint2)
                }
                canvas.withRotation (-35.83f,bounds.exactCenterX(),bounds.exactCenterY()){
                    drawText(dayArray[1],245f,440f,style7Paint2)
                }
                //绘制星期
                val weekday = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                val weekArray = BitmapTranslateUtils.currentWeekday(weekday)
                //"S","u","n","d","a","y"
                if (weekday == 1){
                    canvas.withRotation (-48.56f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[0],250f,440f,style7Paint2)
                    }
                    canvas.withRotation (-55.82f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[1],250f,445f,style7Paint2)
                    }
                    canvas.withRotation (-62.57f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[2],250f,446f,style7Paint2)
                    }
                    canvas.withRotation (-69.44f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[3],250f,448f,style7Paint2)
                    }
                    canvas.withRotation (-76.43f,bounds.exactCenterX(),bounds.exactCenterY()) {
                        drawText(weekArray[4], 250f, 448f, style7Paint2)
                    }
                    canvas.withRotation (-82.93f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[5],250f,450f,style7Paint2)
                    }
                }
                //Monday
                if (weekday == 2){
                    canvas.withRotation (-49.77f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[0],240f,440f,style7Paint2)
                    }
                    canvas.withRotation (-58.33f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[1],250f,445f,style7Paint2)
                    }
                    canvas.withRotation (-65.04f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[2],250f,446f,style7Paint2)
                    }
                    canvas.withRotation (-71.88f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[3],250f,448f,style7Paint2)
                    }
                    canvas.withRotation (-78.84f,bounds.exactCenterX(),bounds.exactCenterY()) {
                        drawText(weekArray[4], 250f, 448f, style7Paint2)
                    }
                    canvas.withRotation (-85.52f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[5],250f,450f,style7Paint2)
                    }
                }
                //Tuesday
                if (weekday == 3){
                    canvas.withRotation (-48.51f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[0],250f,440f,style7Paint2)
                    }
                    canvas.withRotation (-55.34f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[1],250f,445f,style7Paint2)
                    }
                    canvas.withRotation (-62.1f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[2],250f,446f,style7Paint2)
                    }
                    canvas.withRotation (-68.77f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[3],250f,447f,style7Paint2)
                    }
                    canvas.withRotation (-75.77f,bounds.exactCenterX(),bounds.exactCenterY()) {
                        drawText(weekArray[4], 250f, 448f, style7Paint2)
                    }
                    canvas.withRotation (-82.69f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[5],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (-89.16f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[6],250f,450f,style7Paint2)
                    }
                }
                //"W","e","d","n","e","s","d","a","y"
                if (weekday == 4){
                    canvas.withRotation (-51.07f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[0],238f,440f,style7Paint2)
                    }
                    canvas.withRotation (-60.49f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[1],250f,445f,style7Paint2)
                    }
                    canvas.withRotation (-67.39f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[2],250f,446f,style7Paint2)
                    }
                    canvas.withRotation (-74.42f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[3],250f,447f,style7Paint2)
                    }
                    canvas.withRotation (-80.94f,bounds.exactCenterX(),bounds.exactCenterY()) {
                        drawText(weekArray[4], 250f, 448f, style7Paint2)
                    }
                    canvas.withRotation (-87.62f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[5],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (-94.49f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[6],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (-101.39f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[7],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (-107.93f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[8],250f,450f,style7Paint2)
                    }
                }
                //("T","h","u","r","s","d","a","y")
                if (weekday == 5){
                    canvas.withRotation (-48.57f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[0],245f,440f,style7Paint2)
                    }
                    canvas.withRotation (-55.39f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[1],250f,445f,style7Paint2)
                    }
                    canvas.withRotation (-62.15f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[2],250f,446f,style7Paint2)
                    }
                    canvas.withRotation (-67.54f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[3],250f,447f,style7Paint2)
                    }
                    canvas.withRotation (-73.08f,bounds.exactCenterX(),bounds.exactCenterY()) {
                        drawText(weekArray[4], 250f, 448f, style7Paint2)
                    }
                    canvas.withRotation (-80.03f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[5],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (-86.92f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[6],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (-93.37f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[7],250f,450f,style7Paint2)
                    }
                }
                //("F","r","i","d","a","y")
                if (weekday == 6){
                    canvas.withRotation (-47.92f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[0],250f,440f,style7Paint2)
                    }
                    canvas.withRotation (-53.43f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[1],250f,445f,style7Paint2)
                    }
                    canvas.withRotation (-56.94f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[2],253f,446f,style7Paint2)
                    }
                    canvas.withRotation (-61.72f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[3],245f,448f,style7Paint2)
                    }
                    canvas.withRotation (-68.61f,bounds.exactCenterX(),bounds.exactCenterY()) {
                        drawText(weekArray[4], 250f, 448f, style7Paint2)
                    }
                    canvas.withRotation (-75.19f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[5],250f,450f,style7Paint2)
                    }
                }
                //("S","a","t","u","r","d","a","y")
                if (weekday == 7){
                    canvas.withRotation (-48.57f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[0],245f,440f,style7Paint2)
                    }
                    canvas.withRotation (-56.05f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[1],250f,445f,style7Paint2)
                    }
                    canvas.withRotation (-61.93f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[2],250f,446f,style7Paint2)
                    }
                    canvas.withRotation (-67.53f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[3],250f,447f,style7Paint2)
                    }
                    canvas.withRotation (-73.06f,bounds.exactCenterX(),bounds.exactCenterY()) {
                        drawText(weekArray[4], 250f, 448f, style7Paint2)
                    }
                    canvas.withRotation (-78.96f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[5],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (-85.86f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[6],250f,450f,style7Paint2)
                    }
                    canvas.withRotation (-92.32f,bounds.exactCenterX(),bounds.exactCenterY()){
                        drawText(weekArray[7],250f,450f,style7Paint2)
                    }
                }
            }
        } else if (watchFaceData.shapeStyle.shapeType == STYLE7&&drawAmbient){
            val textWidth =  style7Paint3.measureText(BitmapTranslateUtils.currentTime())
            canvas.drawText(BitmapTranslateUtils.currentTime(),233f-textWidth/2,252f,style7Paint3)
        }

        //style8 样式正确
        if (watchFaceData.shapeStyle.shapeType == STYLE8 && !drawAmbient){
            style8Paint1.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
            style8Paint2.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
            style8Paint3.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
            roundPaint7.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
            if (watchFaceData.positionStyle.positionStyle == PositionStyle1){
                //绘制月份
                canvas.drawText(BitmapTranslateUtils.currentData(),392f,250f,style8Paint1)
                //绘制时间
                canvas.drawText(BitmapTranslateUtils.currentTime(), 10f,250f,style8Paint1)
                //绘制星期
                val week = BitmapTranslateUtils.currentWeek8()
                canvas.drawText(week[0],226f,38f,style8Paint2)
                canvas.drawText(week[1],226f,60f,style8Paint2)
                canvas.drawText(week[2],226f,82f,style8Paint2)
                //绘制线
                roundPaint7.style = Paint.Style.STROKE
                roundPaint7.strokeWidth = 1f
                canvas.drawArc(43f,43f,426f,426f,-8f,-74f,false,roundPaint7)
                canvas.drawArc(43f,43f,426f,426f,188f,74f,false,roundPaint7)
                //绘制带有圆弧的矩形
                canvas.drawRoundRect(220f,15f,246f,88f,5.56f,5.56f,roundPaint7)
                canvas.save()
            }else if (watchFaceData.positionStyle.positionStyle == PositionStyle2){
                //绘制月份
                canvas.drawText(BitmapTranslateUtils.currentData(),392f,250f,style8Paint1)
                //绘制时间
                canvas.drawText(BitmapTranslateUtils.currentTime(), 10f,250f,style8Paint1)
                //绘制星期
                textPaint.textSize = 18f
                val week = BitmapTranslateUtils.currentWeek8()
                canvas.drawText(week[0],226f,411f,style8Paint2)
                canvas.drawText(week[1],226f,429f,style8Paint2)
                canvas.drawText(week[2],226f,447f,style8Paint2)
                //绘制线
                roundPaint7.style = Paint.Style.STROKE
                roundPaint7.strokeWidth = 1f
                canvas.drawArc(43f,43f,426f,426f,8f,74f,false,roundPaint7)
                canvas.drawArc(43f,43f,426f,426f,98f,74f,false,roundPaint7)
                //绘制带有圆弧的矩形
                canvas.drawRoundRect(220f,391f,246f,457f,5.56f,5.56f,roundPaint7)
                canvas.save()
            }else if (watchFaceData.positionStyle.positionStyle == PositionStyle3){
                //绘制月份高位低位
                canvas.drawText(BitmapTranslateUtils.currentMonth8(),217f,422f,style8Paint1)
                canvas.drawText(BitmapTranslateUtils.currentDay4(),217f,450f,style8Paint1)
                //绘制时间高位低位
                canvas.drawText(BitmapTranslateUtils.currentHour8(),217f,43f,style8Paint1)
                canvas.drawText(BitmapTranslateUtils.currentMinute8(),217f,65f,style8Paint1)
                //绘制星期
                canvas.drawText(BitmapTranslateUtils.currentWeekdayNotAll(),21f,245f,style8Paint3)
                //绘制线
                roundPaint7.style = Paint.Style.STROKE
                roundPaint7.strokeWidth = 1f
                canvas.drawArc(43f,43f,426f,426f,-172f,74f,false,roundPaint7)
                canvas.drawArc(43f,43f,426f,426f,98f,74f,false,roundPaint7)
                //绘制带有圆弧的矩形
                canvas.drawRoundRect(9f,225f,75f,247f,5.56f,5.56f,roundPaint7)
                canvas.save()
            }
        }else if (watchFaceData.shapeStyle.shapeType == STYLE8 && drawAmbient){
            canvas.drawText(BitmapTranslateUtils.currentHour8(),170f,220f,style8Paint4)
            canvas.drawText(BitmapTranslateUtils.currentMinute8(),170f,340f,style8Paint4)
        }

        //style9 样式正确
        if (watchFaceData.shapeStyle.shapeType == STYLE9&&!drawAmbient){
            style9Paint1.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
            if (watchFaceData.positionStyle.positionStyle == PositionStyle1){
                canvas.drawText(BitmapTranslateUtils.currentTime(),80f,129f,style9Paint1)
                canvas.drawText(BitmapTranslateUtils.currentDay9(),80f,189f,style9Paint1)
                canvas.drawText(BitmapTranslateUtils.currentWeekdayNotAll(),80f,249f,style9Paint1)
            }else if (watchFaceData.positionStyle.positionStyle == PositionStyle2){
                var textWidth: Float = textPaint.measureText(BitmapTranslateUtils.currentTime())
                canvas.drawText(BitmapTranslateUtils.currentTime(),300f-textWidth,129f,style9Paint1)
                textWidth = textPaint.measureText(BitmapTranslateUtils.currentDay9())
                canvas.drawText(BitmapTranslateUtils.currentDay9(),300f-textWidth,189f,style9Paint1)
                textWidth = textPaint.measureText(BitmapTranslateUtils.currentWeekdayNotAll())
                canvas.drawText(BitmapTranslateUtils.currentWeekdayNotAll(),300f-textWidth,249f,style9Paint1)
            }
        }else if (watchFaceData.shapeStyle.shapeType == STYLE9 && drawAmbient){
            canvas.drawText(BitmapTranslateUtils.currentTime(),98f,261f,style9Paint2)
        }
    }

    override fun renderHighlightLayer(
        canvas: Canvas,
        bounds: Rect,
        zonedDateTime: ZonedDateTime,
        sharedAssets: AnalogSharedAssets
    ) {
        canvas.drawColor(renderParameters.highlightLayer!!.backgroundTint)

        for ((_, complication) in complicationSlotsManager.complicationSlots) {
            if (complication.enabled) {
                complication.renderHighlightLayer(canvas, zonedDateTime, renderParameters)
            }
        }
    }



    // ----- All drawing functions -----
    private fun drawComplications(canvas: Canvas, bounds: Rect, zonedDateTime: ZonedDateTime) {
        for ((_, complication) in complicationSlotsManager.complicationSlots) {
            if (complication.enabled) {
                renderParameters.lastComplicationTapDownEvents
                complication.render(canvas, zonedDateTime, renderParameters)
                val batteryStatus: Intent? =
                    context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
                val batteryPct: Float? = batteryStatus?.let { intent ->
                    val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                    val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                    ((level * 100 / scale.toFloat()))
                }
                val batteryValue = batteryPct?.toInt() ?: 0
                //获取日期
                val date = (Calendar.getInstance().get(Calendar.MONTH)+1).toString()+
                            "/"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()
                when (complication.complicationData.value) {
                    is ShortTextComplicationData -> {
                        val rangedValueComplicationData =
                            complication.complicationData.value as ShortTextComplicationData
                        val applicationIdStr = rangedValueComplicationData.dataSource?.packageName
                        if (watchFaceData.shapeStyle.shapeType == STYLE1&&watchFaceData.positionStyle.positionStyle == PositionStyle1&&complication.id == TOP_COMPLICATION_ID_1){
                            complicationPaint1.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
                            val textWidth = complicationPaint1.measureText("BATT $batteryValue%")
                            canvas.drawText("BATT $batteryValue%",233f-textWidth/2,411f,complicationPaint1)
                        }else if (watchFaceData.shapeStyle.shapeType == STYLE1&&watchFaceData.positionStyle.positionStyle == PositionStyle2&&complication.id == BOTTOM_COMPLICATION_ID_1){
                            complicationPaint1.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
                            val textWidth = complicationPaint1.measureText("BATT $batteryValue%")
                            canvas.drawText("BATT $batteryValue%",233f-textWidth/2,50f,complicationPaint1)
                        }else if(watchFaceData.shapeStyle.shapeType == STYLE2 && watchFaceData.positionStyle.positionStyle == PositionStyle1){
                            complicationPaint2.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
                            val textWidth = complicationPaint2.measureText(date)
                            canvas.drawText(date,233f-textWidth/2,183f,complicationPaint2)
                        }else if(watchFaceData.shapeStyle.shapeType == STYLE2 && watchFaceData.positionStyle.positionStyle == PositionStyle2){
                            complicationPaint2.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
                            val textWidth = complicationPaint2.measureText(date)
                            canvas.drawText(date,233f-textWidth/2,336f,complicationPaint2)
                        }else if(watchFaceData.shapeStyle.shapeType == STYLE2 && watchFaceData.positionStyle.positionStyle == PositionStyle3){
                            complicationPaint2.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
                            canvas.drawText(date,70f,260f,complicationPaint2)
                        }else if(watchFaceData.shapeStyle.shapeType == STYLE2 && watchFaceData.positionStyle.positionStyle == PositionStyle4){
                            complicationPaint2.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
                            canvas.drawText(date,300f,260f,complicationPaint2)
                        }else if (watchFaceData.shapeStyle.shapeType == STYLE5 && watchFaceData.positionStyle.positionStyle == PositionStyle1){
                            complicationPaint3.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
                            complicationPaint4.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
                            canvas.drawText("[Steps]",40f,265f,complicationPaint3)
                            canvas.drawText("2526",156f,265f,complicationPaint4)
                        }else if (watchFaceData.shapeStyle.shapeType == STYLE5 && watchFaceData.positionStyle.positionStyle == PositionStyle2){
                            complicationPaint3.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
                            complicationPaint4.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
                            canvas.drawText("[Steps]",241f,265f,complicationPaint3)
                            canvas.drawText("2526",355f,265f,complicationPaint4)
                        }else if (watchFaceData.shapeStyle.shapeType == STYLE6&& watchFaceData.positionStyle.positionStyle == PositionStyle1){
                            complicationPaint5.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
                            canvas.drawText("2560 steps",179f,423f,complicationPaint5)
                        }else if (watchFaceData.shapeStyle.shapeType == STYLE6&& watchFaceData.positionStyle.positionStyle == PositionStyle2){
                            complicationPaint5.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
                            canvas.drawText("2560 steps",179f,140f,complicationPaint5)
                        }else if (watchFaceData.shapeStyle.shapeType == STYLE6&& watchFaceData.positionStyle.positionStyle == PositionStyle3){
                            complicationPaint5.color = context.getColor(watchFaceData.activeColorStyle.colorInt)
                            canvas.drawText("2560 steps",310f,280f,complicationPaint5)
                        }
                    }

                    else -> {}
                }
            }
        }
    }




    companion object {
//        private const val TAG = "AnalogWatchCanvasRenderer"

        // Painted between pips on watch face for hour marks.
        private val HOUR_MARKS = arrayOf("3", "6", "9", "12")

        // Used to canvas.scale() to scale watch hands in proper bounds. This will always be 1.0.
        private const val WATCH_HAND_SCALE = 1.0f
    }
}