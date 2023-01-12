package com.android.mi.wearable.watchface5.editor

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.mi.wearable.watchface5.R
import com.android.mi.wearable.watchface5.WatchFaceService5
import com.android.mi.wearable.watchface5.data.watchface.*
import com.android.mi.wearable.watchface5.data.watchface.FinalStatic.Companion.currentStylePosition
import com.android.mi.wearable.watchface5.view.IndicatorView
import com.android.mi.wearable.watchface5.view.IndicatorViewPosition
import com.android.mi.wearable.watchface5.view.IndicatorViewStyle

class HorizontalPagerAdapter(
    private val listener: IComplicationClick,
    val context: Context,
    private val stylePosition: Int = 0,
    private val colorPosition: Int = 0,
    private val positionPosition: Int = 0,
    private val watchType: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var indicatorPosition: IndicatorViewPosition? = null
    private var mCurrentItem: Int = 0

    private lateinit var topButton: Button
    private lateinit var bottomButton: Button

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        //style选择
        return when (viewType) {
            0 -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_watch_face_config_style, parent, false)
                val indicator: IndicatorViewStyle = itemView.findViewById(R.id.indicatorViewStyle)
                indicator.setOnDotSelectedListener {
                    currentStylePosition = it
                    listener.onStylePagerSelected(it)
                    indicatorPosition?.setSelectedPosition1(it)
                }
                indicator.setSelectedPosition(stylePosition)
                StyleViewHolder(itemView)

                //position选择
            }
            1 -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_watch_face_config_position, parent, false)
                indicatorPosition = itemView.findViewById(R.id.indicatorViewPosition)
                indicatorPosition?.setOnDotSelectedListener {
                    FinalStatic.currentPositionPosition = it
                    listener.onPositionSelected(it, watchType)
                }
                indicatorPosition?.setSelectedPosition(positionPosition)
                PositionViewHolder(itemView)

                //color选择
            }
            2 -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_watch_face_config_color, parent, false)
                val indicator: IndicatorView = itemView.findViewById(R.id.indicatorView)
                indicator.setOnDotSelectedListener {
                    listener.onPagerSelected(it)
                }
                indicator.setSelectedPosition(colorPosition)
                ColorViewHolder(itemView)

            }
            3 -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.activity_watch_face_config_complication, parent, false)
                topButton=itemView.findViewById(R.id.top_complication)
                bottomButton=itemView.findViewById(R.id.bottom_complication)
                if (currentStylePosition == 0 && FinalStatic.currentPositionPosition == 0){
                    Log.d("3->", " position 1 --- 位置在下 ")
                    topButton.visibility = View.GONE
                }else if (currentStylePosition == 0 && FinalStatic.currentPositionPosition == 1){
                    Log.d("3->", " position 2 --- 位置在上 ")
                    bottomButton.visibility = View.GONE
                }
                ComplicationViewHolder(itemView)

            }
            else -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.activity_watch_face_config_complication, parent, false)
                ComplicationViewHolder(itemView)
            }
        }
    }

    fun currentPagerItem(position: Int) {
        mCurrentItem = position
        notifyItemChanged(0)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ColorViewHolder) {
//            holder.mViewPager.apply {
//                orientation = ViewPager2.ORIENTATION_VERTICAL
//                offscreenPageLimit = 4
//                val recyclerView = getChildAt(0) as RecyclerView
//                recyclerView.apply {
//                    val padding = resources.getDimensionPixelOffset(R.dimen.watch_face_view_pager_padding) +
//                            resources.getDimensionPixelOffset(R.dimen.watch_face_view_pager_padding)
//                    setPadding(padding,0,padding,0)
//                    clipToPadding = false
//                }
//            }
//            holder.mViewPager.adapter = holder.verticalAdapter
//            val compositePageTransformer = CompositePageTransformer()
//            compositePageTransformer.addTransformer(ScaleInTransformer())
//            compositePageTransformer.addTransformer(MarginPageTransformer(context.resources.getDimension(R.dimen.watch_face_view_pager_padding).toInt()))
//            holder.mViewPager.setPageTransformer(compositePageTransformer)
//            holder.mViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//                override fun onPageSelected(position: Int) {
//                    super.onPageSelected(position)
//                    listener.onPagerChange(position)
//                }
//            })
//            holder.mViewPager.currentItem = mCurrentItem
        } else if (holder is PositionViewHolder) {
            val complicationViewHolder = holder as PositionViewHolder
        } else if (holder is ComplicationViewHolder) {
            val complicationViewHolder = holder as ComplicationViewHolder
            complicationViewHolder.topBt.setOnClickListener {
                listener.onTopClick()
            }
            complicationViewHolder.bottomBt.setOnClickListener {
                listener.onBottomClick()
            }
        } else {
            val complicationViewHolder = holder as StyleViewHolder
        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        if (currentStylePosition == STYLE1 || currentStylePosition == STYLE2 || currentStylePosition == STYLE5 || currentStylePosition == STYLE6) {
            return 4
        }
        return 3
    }

    class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    class PositionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class StyleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    class ComplicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val topBt: Button = itemView.findViewById(R.id.top_complication)
        val bottomBt: Button = itemView.findViewById(R.id.bottom_complication)
    }
}

class ScaleInTransformer : ViewPager2.PageTransformer {
    private val mMinScale = DEFAULT_MIN_SCALE
    override fun transformPage(view: View, position: Float) {
        view.elevation = -kotlin.math.abs(position)
        val pageWidth = view.width
        val pageHeight = view.height

        view.pivotY = (pageHeight / 2).toFloat()
        view.pivotX = (pageWidth / 2).toFloat()
        if (position < -1) {
            view.scaleX = mMinScale
            view.scaleY = mMinScale
            view.pivotX = pageWidth.toFloat()
        } else if (position <= 1) {
            if (position < 0) {
                val scaleFactor = (1 + position) * (1 - mMinScale) + mMinScale
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
                view.pivotX = pageWidth * (DEFAULT_CENTER + DEFAULT_CENTER * -position)
            } else {
                val scaleFactor = (1 - position) * (1 - mMinScale) + mMinScale
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
                view.pivotX = pageWidth * ((1 - position) * DEFAULT_CENTER)
            }
        } else {
            view.pivotX = 0f
            view.scaleX = mMinScale
            view.scaleY = mMinScale
        }
    }

    companion object {
        const val DEFAULT_MIN_SCALE = 0.85f
        const val DEFAULT_CENTER = 0.5f
    }
}