package com.android.mi.wearable.albumwatchface

import android.view.SurfaceHolder
import androidx.wear.watchface.*
import androidx.wear.watchface.style.CurrentUserStyleRepository
import androidx.wear.watchface.style.UserStyleSchema
import com.android.mi.wearable.albumwatchface.utils.createComplicationSlotManager
import com.android.mi.wearable.albumwatchface.utils.createUserStyleSchema

class WatchFaceService5 : WatchFaceService(){


    override fun createUserStyleSchema(): UserStyleSchema = createUserStyleSchema(context = applicationContext)

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
        val renderer = WatchFace3CanvasRenderer(
            context = applicationContext,
            surfaceHolder = surfaceHolder,
            watchState = watchState,
            complicationSlotsManager = complicationSlotsManager,
            currentUserStyleRepository = currentUserStyleRepository,
            canvasType = CanvasType.HARDWARE
        )

        //Creates the watch face
        return WatchFace(
            watchFaceType = WatchFaceType.ANALOG,
            renderer = renderer
        )
    }

//    override fun onTapEvent(tapType: Int, tapEvent: TapEvent, complicationSlot: ComplicationSlot?) {
//
//    }

}