package com.android.mi.wearable.watchface5;

import android.app.Application;

import com.android.mi.wearable.watchface5.relay.AlbumFaceConsumer;
import com.xiaomi.wear.transmit.TransmitManager;

public class GalleryFaceApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // relay init
        TransmitManager.initialize(this).addConsumer(AlbumFaceConsumer.getInstance(this));
    }
}
