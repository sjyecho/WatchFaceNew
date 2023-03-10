package com.android.mi.wearable.albumwatchface.editor

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import androidx.activity.ComponentActivity
import androidx.core.view.InputDeviceCompat
import androidx.core.view.MotionEventCompat
import androidx.core.view.ViewConfigurationCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.android.mi.wearable.albumwatchface.WatchFaceService5
import com.android.mi.wearable.albumwatchface.data.watchface.*
import com.android.mi.wearable.albumwatchface.databinding.ActivityWatchFaceConfig5Binding
import com.android.mi.wearable.albumwatchface.utils.BitmapTranslateUtils
import com.android.mi.wearable.albumwatchface.utils.TOP_COMPLICATION_ID_1
import com.android.mi.wearable.albumwatchface.utils.BOTTOM_COMPLICATION_ID_1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

interface IComplicationClick {
    fun onStylePagerChange(isUp: Boolean)

    //    fun onColorPagerChange(isUp: Boolean)
    fun onPagerSelected(position: Int)
    fun onPositionSelected(position: Int, watchType: Int)

    fun onStylePagerSelected(position: Int)

    //位置
    fun onPositionPagerChange(isUp: Boolean)

    fun onTopClick()
    fun onBottomClick()

}

class WatchFace5ConfigActivity : ComponentActivity(), IComplicationClick {
    private lateinit var binding: ActivityWatchFaceConfig5Binding
    private lateinit var currentColorId: String
    private lateinit var currentStyleId: String
    private lateinit var currentPositionId: String
    private var currentPositionPosition = 0
    private var currentStylePosition = 0
    private var currentColorPosition = 0
    private var isFirst: Boolean = true
    var myAdapter: HorizontalPagerAdapter? = null
    var isSelected: Boolean = false
    private val stateHolder: WatchFaceConfigStateHolder by lazy {
        WatchFaceConfigStateHolder(
            lifecycleScope,
            this@WatchFace5ConfigActivity
        )
    }


    override fun onPositionPagerChange(isUp: Boolean) {
        var positionList = enumValues<PositionStyle>()
        val watchFaceData = WatchFaceData()
        //13459显示两个
        if (currentStylePosition == STYLE1 || currentStylePosition == STYLE3
            || currentStylePosition == STYLE4 || currentStylePosition == STYLE5
            || currentStylePosition == STYLE9
        ) {
            positionList = arrayOf(PositionStyle.TOP, PositionStyle.BOTTOM)
            currentPositionPosition = if (isUp) {
                if (currentPositionPosition == 1) 1 else (currentPositionPosition + 1)
            } else {
                if (currentPositionPosition == 0) 0 else (currentPositionPosition - 1)
            }
            val newPositionStyle: PositionStyle = positionList[currentPositionPosition]
            stateHolder.setPositionStyle(newPositionStyle.id)
        } else if (watchFaceData.shapeStyle.shapeType == STYLE6 ||
            watchFaceData.shapeStyle.shapeType == STYLE7 ||
            watchFaceData.shapeStyle.shapeType == STYLE8
        ) {
            positionList = arrayOf(PositionStyle.TOP, PositionStyle.BOTTOM, PositionStyle.LEFT)
            currentPositionPosition = if (isUp) {
                if (currentPositionPosition == 2) 2 else (currentPositionPosition + 1)
            } else {
                if (currentPositionPosition == 0) 0 else (currentPositionPosition - 1)
            }
            val newPositionStyle: PositionStyle = positionList[currentPositionPosition]
            stateHolder.setPositionStyle(newPositionStyle.id)

        } else {
            currentPositionPosition = if (isUp) {
                if (currentPositionPosition == 3) 3 else (currentPositionPosition + 1)
            } else {
                if (currentPositionPosition == 0) 0 else (currentPositionPosition - 1)
            }
            val newPositionStyle: PositionStyle = positionList[currentPositionPosition]
            stateHolder.setPositionStyle(newPositionStyle.id)

        }


    }

    override fun onStylePagerChange(isUp: Boolean) {
        val shapeStyleIdAndResourceIdsList = enumValues<ShapeStyleIdAndResourceIds>()
        currentStylePosition = if (isUp) {
            if (currentStylePosition == 8) 8 else (currentStylePosition + 1)
        } else {
            if (currentStylePosition == 0) 0 else (currentStylePosition - 1)
        }
        val newColorStyle: ShapeStyleIdAndResourceIds =
            shapeStyleIdAndResourceIdsList[currentStylePosition]
        stateHolder.setShapeStyle(newColorStyle.id)
    }

    override fun onPagerSelected(position: Int) {
        currentColorPosition = position
        val colorStyleIdAndResourceIdsList = enumValues<ColorStyleIdAndResourceIds>()
        val newColorStyle: ColorStyleIdAndResourceIds =
            colorStyleIdAndResourceIdsList[currentColorPosition]
        stateHolder.setColorStyle(newColorStyle.id)
    }

    override fun onPositionSelected(position: Int, watchType: Int) {
        currentPositionPosition = position
        var positionStyleIdAndResourceIdsList = enumValues<PositionStyle>()
        val newPosition: PositionStyle = positionStyleIdAndResourceIdsList[currentPositionPosition]
        stateHolder.setPositionStyle(newPosition.id)

    }

    override fun onStylePagerSelected(position: Int) {
        currentColorPosition = position
        val shapeStyleIdAndResourceIdsList = enumValues<ShapeStyleIdAndResourceIds>()
        val newShapeStyle: ShapeStyleIdAndResourceIds =
            shapeStyleIdAndResourceIdsList[currentColorPosition]
        stateHolder.setShapeStyle(newShapeStyle.id)
        myAdapter?.notifyItemChanged(2)
    }

    override fun onTopClick() {
        if (stateHolder.pageType == 3) {
            stateHolder.setComplication(TOP_COMPLICATION_ID_1)
        }
    }

    override fun onBottomClick() {
        if (stateHolder.pageType == 3) {
            stateHolder.setComplication(BOTTOM_COMPLICATION_ID_1)
        }
    }

//    override fun onColorPagerChange(isUp: Boolean){
//        val colorStyleIdAndResourceIdsList = enumValues<ColorStyleIdAndResourceIds>()
//        currentColorPosition = if (isUp){
//            if (currentColorPosition == 1) 1 else (currentColorPosition + 1)
//        }else{
//            if (currentColorPosition == 0) 0 else (currentColorPosition - 1)
//        }
//        val newColorStyle: ColorStyleIdAndResourceIds = colorStyleIdAndResourceIdsList[currentColorPosition]
//        stateHolder.setColorStyle(newColorStyle.id)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchFaceConfig5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.Main.immediate) {
            stateHolder.uiState
                .collect { uiState: WatchFaceConfigStateHolder.EditWatchFaceUiState ->
                    when (uiState) {
                        is WatchFaceConfigStateHolder.EditWatchFaceUiState.Loading -> {
                            Log.d("TAG", "StateFlow Loading: ${uiState.message}")
                        }
                        is WatchFaceConfigStateHolder.EditWatchFaceUiState.Success -> {
                            Log.d("TAG", "StateFlow Success.")
                            updateWatchFacePreview(uiState.userStylesAndPreview)
                        }
                        is WatchFaceConfigStateHolder.EditWatchFaceUiState.Error -> {
                            Log.e("TAG", "Flow error: ${uiState.exception}")
                        }
                    }
                }
        }

        binding.pager.post {
            Log.d("binding.pager.post", "post")
            binding.pager.setOnGenericMotionListener { v, ev ->
                if (ev.action == MotionEvent.ACTION_SCROLL &&
                    ev.isFromSource(InputDeviceCompat.SOURCE_ROTARY_ENCODER)
                ) {
                    // Don't forget the negation here
                    val delta = -ev.getAxisValue(MotionEventCompat.AXIS_SCROLL) *
                            ViewConfigurationCompat.getScaledVerticalScrollFactor(
                                ViewConfiguration.get(this), this
                            )
                    Log.d("binding.pager.post", "delta : $delta")
//                    // Swap these axes to scroll horizontally instead
//                    v.scrollBy(0, delta.roundToInt())
                    true
                } else {
                    false
                }
            }
            binding.pager.requestFocus()
        }

    }

    private fun initHorizontalViewPager() {
        myAdapter = HorizontalPagerAdapter(
            listener = this,
            context = this,
            stylePosition = currentStylePosition,
            colorPosition = currentColorPosition,
            positionPosition = currentPositionPosition,
            watchType = WatchFaceData().shapeStyle.shapeType
        )
        binding.pager.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        binding.pager.apply {
            adapter = myAdapter
        }
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                stateHolder.pageType = position
                val isShowHighLayer: Boolean = position == 3
                binding.preview.vPreviewMask.visibility =
                    if (isShowHighLayer) View.GONE else View.VISIBLE
//                binding.preview.leftHighlight.visibility= if (isShowHighLayer) View.VISIBLE else View.GONE
//                binding.preview.rightHighlight.visibility= if (isShowHighLayer) View.VISIBLE else View.GONE
                if (FinalStatic.currentStylePosition == 0 && FinalStatic.currentPositionPosition == 0) {
                    Log.d("qwer", "0 0 ")
                    binding.preview.watchFaceBackground.setImageBitmap(
                        stateHolder.createWatchFacePreview(
                            isShowHighLayer,
                            /*BOTTOM_COMPLICATION_ID // 101*/
                        )
                    )
                } else if (FinalStatic.currentStylePosition == 0 && FinalStatic.currentPositionPosition == 1)
                    Log.d("qwer", "0 1 ")
                    binding.preview.watchFaceBackground.setImageBitmap(
                        stateHolder.createWatchFacePreview(
                            isShowHighLayer,
                            /*TOP_COMPLICATION_ID // 100*/
                        )
                    )
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                Log.d("onPageScrollStateChanged", "onPageScrollStateChanged : $state")
            }
        })
    }

    fun onConfirmClick(view: View) {
        isSelected = true
        onBackPressedDispatcher.onBackPressed()
    }

    private fun updateWatchFacePreview(
        userStylesAndPreview: WatchFaceConfigStateHolder.UserStylesAndPreview
    ) {
        binding.preview.watchFaceBackground.setImageBitmap(userStylesAndPreview.previewImage)
        if (isFirst) {
            isFirst = false
            currentColorId = userStylesAndPreview.colorStyleId
            currentStyleId = userStylesAndPreview.shapeStyleId
            //position

            currentPositionId = userStylesAndPreview.positionStyleId
            currentPositionPosition =
                BitmapTranslateUtils.currentPositionItemPosition(currentPositionId)

            currentColorPosition = BitmapTranslateUtils.currentColorItemPosition(currentColorId)
            currentStylePosition = BitmapTranslateUtils.currentShapeItemPosition(currentStyleId)
            initHorizontalViewPager()
        }
    }

    override fun onPause() {
        if (!isSelected) {
            stateHolder.setColorStyle(currentColorId)
            stateHolder.setShapeStyle(currentStyleId)
            stateHolder.setPositionStyle(currentPositionId)
        }
        super.onPause()
    }
}