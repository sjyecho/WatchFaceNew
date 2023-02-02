package com.android.mi.wearable.albumwatchface.relay;

import static com.xiaomi.wear.protobuf.nano.WatchFaceProtos.EditResponse.SUCCESS;

import android.content.Context;
import android.util.Log;

import com.android.mi.wearable.albumwatchface.utils.Constants;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import com.xiaomi.wear.protobuf.nano.WatchFaceProtos;
import com.xiaomi.wear.protobuf.nano.WearProtos;
import com.xiaomi.wear.transmit.TransmitConsumer;
import com.xiaomi.wear.transmit.TransmitManager;

public class AlbumFaceConsumer implements TransmitConsumer {
    private static final String TAG = "wnjjjj";
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
        WearProtos.WearPacket request = WearProtos.WearPacket.parseFrom(data);
        Log.d(TAG, "onMessageReceived: ");
        Log.d(TAG, "onMessageReceived: "+path+data);
        mResponse.id = request.id;
        mResponse.type = request.type;
        Log.d(TAG, "onMessageReceived: "+request.id);
        Log.d(TAG, "onMessageReceived: "+mResponse.id);

        // 编辑表盘，信息在editRequest
        if (WatchFaceProtos.WatchFace.EDIT_WATCH_FACE == request.id){
            Log.d(TAG, "onMessageReceived: EDIT_WATCH_FACE");
            WatchFaceProtos.EditRequest editRequest = request.getWatchFace().getEditRequest();
            Log.d(TAG, "onMessageReceived: "+editRequest.backgroundImageSize);
            /*editRequest.backgroundColor*/ //背景颜色
            /*editRequest.backgroundImage*/ //背景图片
            /*editRequest.style*/ //样式
            Log.d(TAG, "onMessageReceived: "+editRequest.backgroundImage);
            // todo 背景替换、添加或者删除图片、表盘样式修改
//            //颜色替换 获取当前的rgb
//            editRequest.backgroundColor =
//            //背景替换
//            editRequest.backgroundImage =
//            //样式替换也就是位置替换
//            editRequest.style =





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
        mResponse.setWatchFace(watchFace);
        TransmitManager.getInstance().sendMessage(Constants.WATCH_FACE_PATH, MessageNano.toByteArray(mResponse),null);
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
