package com.android.mi.wearable.albumwatchface

import android.util.Log
import android.view.SurfaceHolder
import androidx.wear.watchface.*
import androidx.wear.watchface.style.CurrentUserStyleRepository
import androidx.wear.watchface.style.UserStyleSchema
import com.android.mi.wearable.albumwatchface.data.watchface.FinalStatic
import com.android.mi.wearable.albumwatchface.utils.createComplicationSlotManager
import com.android.mi.wearable.albumwatchface.utils.createUserStyleSchema

class WatchFaceService5 : WatchFaceService(), WatchFace.TapListener {

    private var i = 1

    private lateinit var mRender: WatchFace3CanvasRenderer

    override fun createUserStyleSchema(): UserStyleSchema =
        createUserStyleSchema(context = applicationContext)

    override fun createComplicationSlotsManager(currentUserStyleRepository: CurrentUserStyleRepository): ComplicationSlotsManager {
        return createComplicationSlotManager(
            context = applicationContext,
            currentUserStyleRepository = currentUserStyleRepository
        )
    }

    override suspend fun createWatchFace(
        surfaceHolder: SurfaceHolder,
        watchState: WatchState,
        complicationSlotsManager: ComplicationSlotsManager,
        currentUserStyleRepository: CurrentUserStyleRepository
    ): WatchFace {
        // Creates class that renders the watch face.
        mRender = WatchFace3CanvasRenderer(
            context = applicationContext,
            surfaceHolder = surfaceHolder,
            watchState = watchState,
            complicationSlotsManager = complicationSlotsManager,
            currentUserStyleRepository = currentUserStyleRepository,
            canvasType = CanvasType.HARDWARE
        )

        //Creates the watch face
        val watchface = WatchFace(
            watchFaceType = WatchFaceType.ANALOG,
            renderer = mRender
        )
        watchface.setTapListener(this)
        return watchface
    }

    override fun onTapEvent(tapType: Int, tapEvent: TapEvent, complicationSlot: ComplicationSlot?) {

        if (complicationSlot == null){
            if (i % 2 == 0) {
                mRender.changeImageValue()
                mRender.postInvalidate()
            }
            i++
        }else{
            Log.d("WatchFaceService5", "onTapEvent: else")
        }


    }
}