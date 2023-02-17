package com.android.mi.wearable.albumwatchface.relay;

import static com.xiaomi.wear.protobuf.nano.CommonProtos.NO_ERROR;
import static com.xiaomi.wear.protobuf.nano.WatchFaceProtos.EditResponse.SUCCESS;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.media.MediaDrm;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.android.mi.wearable.albumwatchface.data.watchface.FinalStatic;
import com.android.mi.wearable.albumwatchface.utils.BitmapTranslateUtils;
import com.android.mi.wearable.albumwatchface.utils.Constants;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import com.xiaomi.wear.protobuf.nano.WatchFaceProtos;
import com.xiaomi.wear.protobuf.nano.WearProtos;
import com.xiaomi.wear.transmit.TransmitConsumer;
import com.xiaomi.wear.transmit.TransmitManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AlbumFaceConsumer implements TransmitConsumer {
    private static final String TAG = AlbumFaceConsumer.class.getName();
    private Context mContext;
    private static WearProtos.WearPacket request = new WearProtos.WearPacket();
    private static WearProtos.WearPacket mResponse = new WearProtos.WearPacket();
    private static AlbumFaceConsumer mConsumer;

    public AlbumFaceConsumer(Context context) {
        Log.d(TAG, "AlbumFaceConsumer: "+"当前是否走这里");
        this.mContext = context;
    }

    public static AlbumFaceConsumer getInstance(Context context) {
        if (null == mConsumer) {
            mConsumer = new AlbumFaceConsumer(context);
        }
        return mConsumer;
    }

    @Override
    public void onMessageReceived(String path, byte[] data) throws InvalidProtocolBufferNanoException {
        TransmitConsumer.super.onMessageReceived(path, data);
        Log.d(TAG, "data: "+data);
        if (data == null){
            return;
        }
        WearProtos.WearPacket request = WearProtos.WearPacket.parseFrom(data);
        mResponse.id = request.id;
        mResponse.type = request.type;
        Log.d(TAG, "request: "+request);
        Log.d(TAG, "request.id: "+request.id);
        Log.d(TAG, "mResponse.id: "+mResponse.id);
        // 编辑表盘，信息在editRequest
        if (WatchFaceProtos.WatchFace.REMOVE_WATCH_FACE_PHOTO == request.id){
            Log.d(TAG, "getWatchFace: "+request.getWatchFace());
//            WatchFaceProtos.EditRequest editRequest = request.getWatchFace().getEditRequest();
//           // Log.d(TAG, "onMessageReceived: "+editRequest.backgroundImageSize);
//            /*editRequest.backgroundColor*/ //背景颜色
//            /*editRequest.backgroundImage*/ //背景图片
//            /*editRequest.style*/ //样式
//            Log.d(TAG, "editRequest: "+editRequest);
            String deletePath = request.getWatchFace().getPath().substring(request.getWatchFace().getPath().length()-17,request.getWatchFace().getPath().length()-1);
            ArrayList<String> list = BitmapTranslateUtils.INSTANCE.getDeletePictureList();
            Log.d(TAG, "deletePath: "+deletePath);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).contains(deletePath)){
                    File pdfFileOld = new File("/storage/emulated/0/Pictures/watchface"+File.separator+list.get(i));
                    Log.d(TAG, "list.get(i): "+list.get(i));
                    pdfFileOld.delete();
                }
            }

            mResponse.setErrorCode(NO_ERROR);
            Log.d(TAG, "mResponse.getWatchFace(): "+mResponse.getWatchFace());
            TransmitManager.getInstance().sendMessage(Constants.WATCH_FACE_PATH, MessageNano.toByteArray(mResponse), (int val, Bundle bundle) -> {
                Log.d(TAG, "onMessageReceived: "+bundle);
            });

            //sendEditResponseToPhone();
            // todo 背景替换、添加或者删除图片、表盘样式修改



        }else{
            Log.d(TAG, "当前是其他的指令" );
        }

        if (WatchFaceProtos.WatchFace.BG_IMAGE_RESULT == request.id){
            Log.d(TAG, "onMessageReceived: BG_IMAGE_RESULT");

        }
    }
    /*****  表盘协议说明文档：https://xiaomi.f.mioffice.cn/docs/dock4llfip5xxNpu5qp01QSrzh0#votigB  ****/
    /**
     * 发送编辑结果信息给手机
     * */
    public synchronized void sendEditResponseToPhone(){
        WatchFaceProtos.WatchFace watchFace = new WatchFaceProtos.WatchFace();
        WatchFaceProtos.EditResponse response = new WatchFaceProtos.EditResponse();
        response.id = request.getWatchFace().getEditRequest().id;
        response.code = SUCCESS /*成功*/; /*SUCCESS_BUT_LOW_STORAGE 成功但是存储不足;FAIL_BUT_LOW_STORAGE 失败存储不足;FAIL失败*/
        response.canAcceptImageCount = 2; //SUCCESS_BUT_LOW_STORAGE才需要该参数
        response.expectedSliceLength = 4096; // needed if EditRequest contains image
        watchFace.setEditResponse(response);
        TransmitManager.getInstance().sendMessage(Constants.WATCH_FACE_PATH, MessageNano.toByteArray(mResponse),null);
        Log.d(TAG, "sendEditResponseToPhone: ");
    }

    /**
     * 上报相册表盘图片接收结果
     * */
    public synchronized void sendBgImgResultToPhone(){
        Log.d("wjjjjj", "sendBgImgResultToPhone: ");
        WearProtos.WearPacket response = new WearProtos.WearPacket();
        WatchFaceProtos.WatchFace watchFace = new WatchFaceProtos.WatchFace();
        WatchFaceProtos.BgImageResult bgImageResult = new WatchFaceProtos.BgImageResult();
        bgImageResult.code = WatchFaceProtos.BgImageResult.SUCCESS;/*IMAGE_SAVE_FAILED;IMAGE_RESOLVE_FAILED*/
        bgImageResult.id = String.valueOf(2); //if success, id is neccessary
        bgImageResult.backgroundImage = String.valueOf(3); // image id: md5
        response.id = WatchFaceProtos.WatchFace.BG_IMAGE_RESULT;
        watchFace.setBgImageResult(bgImageResult);
        response.setWatchFace(watchFace);
        TransmitManager.getInstance().sendMessage(Constants.WATCH_FACE_PATH, MessageNano.toByteArray(response),null);
    }


}
