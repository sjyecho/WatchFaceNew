/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.mi.wearable.albumwatchface.utils

import android.content.Context
import android.graphics.RectF
import androidx.wear.watchface.CanvasComplicationFactory
import androidx.wear.watchface.ComplicationSlot
import androidx.wear.watchface.ComplicationSlotsManager
import androidx.wear.watchface.complications.ComplicationSlotBounds
import androidx.wear.watchface.complications.DefaultComplicationDataSourcePolicy
import androidx.wear.watchface.complications.SystemDataSources
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.rendering.CanvasComplicationDrawable
import androidx.wear.watchface.complications.rendering.ComplicationDrawable
import androidx.wear.watchface.style.CurrentUserStyleRepository
import com.android.mi.wearable.albumwatchface.R

// Information needed for complications.
// Creates bounds for the locations of both right and left complications. (This is the
// location from 0.0 - 1.0.)
// Both left and right complications use the same top and bottom bounds.

private const val DEFAULT_COMPLICATION_STYLE_DRAWABLE_ID = R.drawable.complication_right_style1

//private const val DEFAULT_COMPLICATION_STYLE_DRAWABLE_ID_LEFT = R.drawable.complication_left_style1

// Unique IDs for each complication. The settings activity that supports allowing users
// to select their complication data provider requires numbers to be >= 0.

//style1的两个
internal const val TOP_COMPLICATION_ID_1 = 100
internal const val BOTTOM_COMPLICATION_ID_1 = 101

//style2的四个
internal const val TOP_COMPLICATION_ID_2 = 102
internal const val BOTTOM_COMPLICATION_ID_2 = 103
internal const val LEFT_COMPLICATION_ID_2= 104
internal const val RIGHT_COMPLICATION_ID_2 = 105

//style5的两个
internal const val LEFT_COMPLICATION_ID_5 = 106
internal const val RIGHT_COMPLICATION_ID_5 = 107

//Style6的三个
internal const val TOP_COMPLICATION_ID_6 = 108
internal const val BOTTOM_COMPLICATION_ID_6= 109
internal const val RIGHT_COMPLICATION_ID_6= 110


/**
 * Represents the unique id associated with a complication and the complication types it supports.
 */
sealed class ComplicationConfig(val id: Int, val supportedTypes: List<ComplicationType>) {
    //STYLE1
    object Top1 : ComplicationConfig(
        TOP_COMPLICATION_ID_1,
        listOf(
            ComplicationType.LONG_TEXT,
            ComplicationType.SHORT_TEXT,

        )
    )

    object Bottom1 : ComplicationConfig(
        BOTTOM_COMPLICATION_ID_1,
        listOf(

            ComplicationType.SHORT_TEXT,

        )
    )


    //STYLE2
    object Top2 : ComplicationConfig(
        TOP_COMPLICATION_ID_2,
        listOf(

            ComplicationType.SHORT_TEXT,

        )
    )

    object Bottom2 : ComplicationConfig(
        BOTTOM_COMPLICATION_ID_2,
        listOf(

            ComplicationType.SHORT_TEXT,

        )
    )
    object Left2 : ComplicationConfig(
        LEFT_COMPLICATION_ID_2,
        listOf(


           ComplicationType.SHORT_TEXT,

        )
    )

    object Right2 : ComplicationConfig(
        RIGHT_COMPLICATION_ID_2,
        listOf(

            ComplicationType.SHORT_TEXT,

        )
    )

    //style5
    object Left5 : ComplicationConfig(
        LEFT_COMPLICATION_ID_5,
        listOf(

            ComplicationType.SHORT_TEXT,

        )
    )
    object Right5 : ComplicationConfig(
        RIGHT_COMPLICATION_ID_5,
        listOf(
            ComplicationType.SHORT_TEXT,

        )
    )

    //style6
    object Top6 : ComplicationConfig(
        TOP_COMPLICATION_ID_6,
        listOf(

           ComplicationType.SHORT_TEXT,
        )
    )

    object Bottom6 : ComplicationConfig(
        BOTTOM_COMPLICATION_ID_6,
        listOf(

           ComplicationType.SHORT_TEXT,

        )
    )
    object Right6 : ComplicationConfig(
        RIGHT_COMPLICATION_ID_6,
        listOf(

            ComplicationType.SHORT_TEXT,

        )
    )
}

// Utility function that initializes default complication slots (left and right).
fun createComplicationSlotManager(
    context: Context,
    currentUserStyleRepository: CurrentUserStyleRepository,
    drawableRightId: Int = DEFAULT_COMPLICATION_STYLE_DRAWABLE_ID
): ComplicationSlotsManager {
    val defaultRightCanvasComplicationFactory =
        CanvasComplicationFactory { watchState, listener ->
            CanvasComplicationDrawable(
                ComplicationDrawable.getDrawable(context, drawableRightId)!!,
                watchState,
                listener
            )
        }

    val defaultLeftCanvasComplicationFactory =
        CanvasComplicationFactory { watchState, listener ->
            CanvasComplicationDrawable(
                ComplicationDrawable.getDrawable(context, drawableRightId)!!,
                watchState,
                listener
            )
        }

    val topComplication1 = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Top1.id,
        canvasComplicationFactory = defaultLeftCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Top1.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_WATCH_BATTERY,
            ComplicationType.SHORT_TEXT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                0.3f,
                0.05f,
                0.72f,
                0.11f
            )
        )
    ).build()

    val bottomComplication1 = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Bottom1.id,
        canvasComplicationFactory = defaultRightCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Bottom1.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_WATCH_BATTERY,
            ComplicationType.SHORT_TEXT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                0.3f,
                0.85f,
                0.72f,
                0.91f
            )
        )
    ).build()


    //Style2
    val topComplication2 = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Top2.id,
        canvasComplicationFactory = defaultLeftCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Top2.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_WATCH_BATTERY,
            ComplicationType.SHORT_TEXT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                0.4f,
                0.35f,
                0.62f,
                0.4f
            )
        )
    ).build()

    val bottomComplication2 = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Bottom2.id,
        canvasComplicationFactory = defaultRightCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Bottom2.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_WATCH_BATTERY,
            ComplicationType.SHORT_TEXT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                0.4f,
                0.67f,
                0.62f,
                0.72f
            )
        )
    ).build()

    val leftComplication2 = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Left2.id,
        canvasComplicationFactory = defaultLeftCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Left2.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_WATCH_BATTERY,
            ComplicationType.SHORT_TEXT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                0.1f,
                0.53f,
                0.32f,
                0.58f
            )
        )
    ).build()

    val rightComplication2 = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Right2.id,
        canvasComplicationFactory = defaultRightCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Right2.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_WATCH_BATTERY,
            ComplicationType.SHORT_TEXT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                0.6f,
                0.53f,
                0.82f,
                0.58f
            )
        )
    ).build()

    //style5
    val leftComplication5 = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Left5.id,
        canvasComplicationFactory = defaultLeftCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Left5.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_WATCH_BATTERY,
            ComplicationType.SHORT_TEXT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                0.1f,
                0.50f,
                0.55f,
                0.58f
            )
        )
    ).build()

    val rightComplication5 = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Right5.id,
        canvasComplicationFactory = defaultRightCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Right5.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_WATCH_BATTERY,
            ComplicationType.SHORT_TEXT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                0.55f,
                0.51f,
                0.92f,
                0.58f
            )
        )
    ).build()


    //style6
    val topComplication6 = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Top6.id,
        canvasComplicationFactory = defaultLeftCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Top6.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_WATCH_BATTERY,
            ComplicationType.SHORT_TEXT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                0.3f,
                0.9f,
                0.72f,
                0.95f
            )
        )
    ).build()

    val bottomComplication6 = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Bottom6.id,
        canvasComplicationFactory = defaultRightCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Bottom6.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_WATCH_BATTERY,
            ComplicationType.SHORT_TEXT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                0.3f,
                0.25f,
                0.72f,
                0.30f
            )
        )
    ).build()


    val rightComplication6 = ComplicationSlot.createRoundRectComplicationSlotBuilder(
        id = ComplicationConfig.Right6.id,
        canvasComplicationFactory = defaultLeftCanvasComplicationFactory,
        supportedTypes = ComplicationConfig.Right6.supportedTypes,
        defaultDataSourcePolicy = DefaultComplicationDataSourcePolicy(
            SystemDataSources.DATA_SOURCE_WATCH_BATTERY,
            ComplicationType.SHORT_TEXT
        ),
        bounds = ComplicationSlotBounds(
            RectF(
                0.58f,
                0.58f,
                0.95f,
                0.62f
            )
        )
    ).build()






    return ComplicationSlotsManager(
        listOf(topComplication1, bottomComplication1,topComplication2,bottomComplication2,leftComplication2,rightComplication2,leftComplication5,rightComplication5,topComplication6,bottomComplication6,rightComplication6),
        currentUserStyleRepository
    )
}
