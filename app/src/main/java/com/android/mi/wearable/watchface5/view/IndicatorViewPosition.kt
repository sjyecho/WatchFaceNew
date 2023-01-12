package com.android.mi.wearable.watchface5.view

import android.animation.ArgbEvaluator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ScrollView
import androidx.core.graphics.withRotation
import androidx.core.graphics.withTranslation
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.mi.wearable.watchface5.R
import com.android.mi.wearable.watchface5.data.watchface.*
import kotlinx.coroutines.*
import kotlin.math.absoluteValue

class IndicatorViewPosition @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // 根据样式返回不同的dot数量
    private var mSlideCount = 1

    // 间隔角度
    private var mDotIntervalAngle = 15f

    // 选中圆环所在角度
    private var mRingAngle = 0f

    // 选中圆环半径
    private var mRingRadius = 22f

    // 选中圆环Stroke
    private var mRingStroke = 3f

    // 已滑动的角度
    private var mScrollAngle = 0f

    // 存放锚点的List
    private val mDotAnchorList = arrayListOf<DotItem>()

    // 存放Dot的List
    private val mDotItemList = arrayListOf<DotItem>()

    // 默认滚动半径
    private val mScrollRadius by lazy { width / 2.4f }

    // 颜色估值器
    private val mColorEvaluator by lazy { ArgbEvaluator() }

    // 绘制Dot的Paint
    private val mDotPaint by lazy { Paint().apply { isAntiAlias = true } }

    private val colorArray =
        intArrayOf(R.color.index_21_bg, R.color.index_22_bg, R.color.index_23_bg)

    // 是否支持多彩
    private var isColorFul = false

    // 绘制圆环的Paint
    private val mRingPaint by lazy {
        Paint().apply {
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = 2f
            color = Color.WHITE
        }
    }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.IndicatorView).apply {

            var dotCount = getInt(R.styleable.IndicatorView_iv_dot_count, 4)

            if (FinalStatic.currentStylePosition == 0 || FinalStatic.currentStylePosition == 2 || FinalStatic.currentStylePosition == 3 || FinalStatic.currentStylePosition == 4 || FinalStatic.currentStylePosition == 8) {
                dotCount = 2
                mSlideCount = 2
            } else if (FinalStatic.currentStylePosition == 1) {
                dotCount = 4
                mSlideCount = 4
            } else if (FinalStatic.currentStylePosition == 5 || FinalStatic.currentStylePosition == 6 || FinalStatic.currentStylePosition == 7) {
                dotCount = 3
                mSlideCount = 3
            }
            mRingPaint.color = getInt(R.styleable.IndicatorView_iv_ring_color, Color.WHITE)
            mRingAngle = getFloat(R.styleable.IndicatorView_iv_ring_angle, mRingAngle)
            mRingRadius = getFloat(R.styleable.IndicatorView_iv_ring_radius, mRingRadius)
            mRingStroke = getDimension(R.styleable.IndicatorView_iv_ring_stroke, mRingStroke)
            mRingPaint.strokeWidth = mRingStroke
            mDotIntervalAngle =
                getFloat(R.styleable.IndicatorView_iv_dot_interval_angle, mDotIntervalAngle)
            val colorList =
                listOf(
                    getInt(R.styleable.IndicatorView_iv_anchor_color_level_1, Color.WHITE),
                    getInt(R.styleable.IndicatorView_iv_anchor_color_level_2, Color.GRAY),
                    getInt(R.styleable.IndicatorView_iv_anchor_color_level_3, Color.GRAY),
                    getInt(R.styleable.IndicatorView_iv_anchor_color_level_4, Color.GRAY),
                    getInt(R.styleable.IndicatorView_iv_anchor_color_level_5, Color.GRAY)
                )

            val sizeList = listOf(
                getFloat(R.styleable.IndicatorView_iv_anchor_size_level_1, 0f),
                getFloat(R.styleable.IndicatorView_iv_anchor_size_level_2, 0f),
                getFloat(R.styleable.IndicatorView_iv_anchor_size_level_3, 0f),
                getFloat(R.styleable.IndicatorView_iv_anchor_size_level_4, 0f),
                getFloat(R.styleable.IndicatorView_iv_anchor_size_level_5, 0f),
            )
            generateAnchor(colorList, sizeList)
//            generateDot(dotCount)
            generateDotList(FinalStatic.currentStylePosition, dotCount, colorList)
        }.recycle()
    }

    /**
     * 代码配置IndicatorView
     */
    fun configIndicator(dotCount: Int, anchorColors: List<Int>, anchorSizes: List<Float>) {
        generateAnchor(anchorColors, anchorSizes)
        generateDot(dotCount)
        invalidate()
    }

    private var mOnDotSelected: ((Int) -> Unit)? = null

    /**
     * 设置圆点选中监听
     */
    fun setOnDotSelectedListener(block: (pos: Int) -> Unit) {
        mOnDotSelected = block
    }

    /**
     * 设置默认选中位置
     */
    fun setSelectedPosition(pos: Int) {
        mScrollAngle = mDotIntervalAngle * pos
        invalidate()
    }

    /**
     * 设置默认选中位置
     */
    fun setSelectedPosition1(pos: Int) {
        if (FinalStatic.currentStylePosition == 0 || FinalStatic.currentStylePosition == 2 || FinalStatic.currentStylePosition == 3 || FinalStatic.currentStylePosition == 4 || FinalStatic.currentStylePosition == 8) {
            mSlideCount = 2
        } else if (FinalStatic.currentStylePosition == 1) {
            mSlideCount = 4
        } else if (FinalStatic.currentStylePosition == 5 || FinalStatic.currentStylePosition == 6 || FinalStatic.currentStylePosition == 7) {
            mSlideCount = 3
        }

        generateDotList(FinalStatic.currentStylePosition, mSlideCount, null)
        invalidate()
    }


    /**
     * 获取当前选中位置
     */
    fun getCurrentPos() = (mScrollAngle + mNeedFixedAngle).toInt() / mDotIntervalAngle.toInt()

    /**
     * 生成锚点列表
     */
    private fun generateAnchor(colorList: List<Int>, sizeList: List<Float>) {
        val filterSizeList = sizeList.filter { it > 0 }
        if (filterSizeList.size > colorList.size) return
        mDotAnchorList.clear()
        var isColorful: Boolean
        filterSizeList.forEachIndexed { i, size ->
            isColorful = i == 1
            mDotAnchorList.add(
                DotItem(
                    angle = (mRingAngle + i * mDotIntervalAngle) % 360,
                    size = size,
                    color = colorList[i]
                )
            )
            mDotAnchorList.add(
                DotItem(
                    (mRingAngle - i * mDotIntervalAngle) % 360,
                    size,
                    colorList[i]
                )
            )
        }
        mDotAnchorList.distinctBy { it.angle }
        mDotAnchorList.sortBy { it.angle }
    }

    /**
     * 生成Dot列表
     */
    private fun generateDot(dotCount: Int) {
        mDotItemList.clear()
        (0 until dotCount).forEach {
            mDotItemList.add(DotItem((mRingAngle + it * mDotIntervalAngle) % 360))
        }
    }

    private fun generateDotList(mWatchType: Int, dotCount: Int, colorList: List<Int>?) {

        var bitmapList: List<Int>


        // 样式1.3.4.5.9
        if (mWatchType == 0 || mWatchType == 2 || mWatchType == 3 || mWatchType == 4 || mWatchType == 8) {
            bitmapList = listOf(
                R.drawable.style_point,
            )
            // 样式2
        } else if (mWatchType == 1) {
            bitmapList = listOf(
                R.drawable.style_point
            )

            //样式6.7.8
        } else if (mWatchType == 5 || mWatchType == 6 || mWatchType == 7) {
            bitmapList = listOf(
                R.drawable.style_point
            )

        } else {
            bitmapList = listOf(
                R.drawable.style_point
            )
        }

        val length = bitmapList.size
        Log.d("generateDotList", "length : $length")
        mDotItemList.clear()
        (0 until dotCount).forEach {
            mDotItemList.add(
                DotItem(
                    angle = (mRingAngle * mDotIntervalAngle) % 360,
                    color = bitmapList[0]
                )
            )
        }
    }

    private data class DotItem(
        val angle: Float = 0f,
        var size: Float = 20f,
        var color: Int = Color.GRAY,
    )

    /**
     * 绘制dot
     */
    private fun Canvas.drawDot(dotItem: DotItem) {
        withTranslation(measuredWidth / 2f, measuredHeight / 2f) {
            Log.d("Canvas.drawDot", "withTranslation : X:${measuredWidth / 2f}---Y:${measuredHeight / 2f} ")
            withRotation(dotItem.angle + mScrollAngle) {
                Log.d("Canvas.drawDot", "withRotation : ${dotItem.angle + mScrollAngle}")
                refreshDot(dotItem)
                val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, dotItem.color)
                drawBitmap(bitmap, 191.8f, -47.5f, mDotPaint)
            }
        }
    }

    /**
     * 绘制圆环
     */
    private fun Canvas.drawRing() {
        withTranslation(measuredWidth / 2f, measuredHeight / 2f) {
            withRotation(mRingAngle) {
                //drawCircle(mScrollRadius, 0f, mRingRadius, mRingPaint)
            }
        }
    }

    /**
     * 根据滑动的角度更新每个点的颜色和size
     */
    private fun refreshDot(dotItem: DotItem) {
        mDotAnchorList.let {
            it.reduce { acc, item ->
                val a = dotItem.angle - mScrollAngle
                if (a <= acc.angle) {
                    dotItem.size = acc.size
//                    dotItem.color = acc.color
                    return@let
                } else if (a < item.angle) {
                    val factor = (a - acc.angle) / (item.angle - acc.angle)
                    dotItem.size = acc.size + (item.size - acc.size) * factor
//                    dotItem.color = mColorEvaluator.evaluate(factor, acc.color, item.color) as Int
                    return@let
                } else {
                    dotItem.size = item.size
//                    dotItem.color = item.color
                }
                item
            }
        }
    }

    private var mLastEventY = 0f

    private val mCanOverScrollAngle = mDotIntervalAngle * .4f
    private var mMaxScroll = 0f
    private var mMinScroll = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                fixedScroll(false)
                mMaxScroll = mDotIntervalAngle * (mDotItemList.size - 1) + mCanOverScrollAngle
                mMinScroll = -mCanOverScrollAngle
                mLastEventY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                fixedScroll(false)
                val dis = (event.y - mLastEventY) / 16f
                mScrollAngle -= dis
                mScrollAngle = mScrollAngle.coerceAtMost(mMaxScroll).coerceAtLeast(mMinScroll)
                invalidate()
                mLastEventY = event.y
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                val overAngle = mScrollAngle % mDotIntervalAngle
                if (overAngle != 0f) {
                    mNeedFixedAngle = if (overAngle > mDotIntervalAngle / 2) {
                        mDotIntervalAngle - overAngle
                    } else {
                        -overAngle
                    }
                    fixedScroll(true)
                } else {
                    fixedScroll(false)
                }
                mOnDotSelected?.invoke(getCurrentPos())
            }
        }
        return true
    }

    // 需要调整的滑动角度
    private var mNeedFixedAngle = 0f

    // 定义每一次调整时滑动的角度
    private val mFixedScrollStep = .5f

    private val mScrollRunnable: java.lang.Runnable = java.lang.Runnable {
        if (mNeedFixedAngle.absoluteValue > mFixedScrollStep) {
            val step = (if (mNeedFixedAngle > 0) 1 else -1) * mFixedScrollStep
            mNeedFixedAngle -= step
            mScrollAngle += step
            invalidate()
            fixedScroll(true)
        } else {
            mScrollAngle += mNeedFixedAngle
            mNeedFixedAngle = 0f
            invalidate()
        }
    }

    /**
     * 开始/停止 滑动调整（将圆点调整到指定位置）
     */
    private fun fixedScroll(startScroll: Boolean) {
        if (startScroll) {
            postOnAnimation(mScrollRunnable)
        } else {
            mNeedFixedAngle = 0f
            removeCallbacks(mScrollRunnable)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mDotItemList.forEach { canvas.drawDot(it) }
        canvas.drawRing()
    }

    /**
     * 将指示器绑定到指定View
     */
    fun attachView(view: View) {
        view.postDelayed({
            when (view) {
                is ScrollView, is NestedScrollView -> {
                    view.setOnScrollChangeListener { _, _, scrollY, _, _ ->
                        calculateScroll(view, scrollY.toFloat())
                    }
                }
                is RecyclerView -> {
                    var scrollY = 0
                    view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            scrollY += dy
                            calculateScroll(view, scrollY.toFloat())
                        }
                    })
                }
                is ViewPager2 -> {
                    view.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageScrolled(
                            position: Int,
                            positionOffset: Float,
                            positionOffsetPixels: Int
                        ) {
                            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                            calculateScroll(view, (position + positionOffset) * 1000)
                        }
                    })
                }
            }
        }, 300)
    }

    /**
     * 计算滚动进度
     */
    private fun calculateScroll(view: View, scrollY: Float) {
        val viewHeight = view.height
        val paddingVertical = view.paddingTop + view.paddingBottom
        val contentHeight = when (view) {
            is ScrollView -> {
                view.getChildAt(0).height - viewHeight + paddingVertical
            }
            is NestedScrollView -> {
                view.getChildAt(0).height - viewHeight + paddingVertical
            }
            is RecyclerView -> {
                view.computeVerticalScrollRange() - viewHeight + paddingVertical
            }
            is ViewPager2 -> {
                ((view.adapter?.itemCount ?: 1) - 1) * 1000
            }
            else -> {
                view.height - viewHeight + paddingVertical
            }
        }
        val progress = scrollY / contentHeight * 100f
        progressToSweepAngle(progress)
        invalidate()
    }

    /**
     * 滚动进度转换为滑动角度
     */
    private fun progressToSweepAngle(progress: Float, maxProgress: Float = 100f) {
        val dotCount = mDotItemList.size
        val maxSweepAngle = (dotCount - 1) * mDotIntervalAngle
        mScrollAngle = maxSweepAngle * progress / maxProgress
    }

}