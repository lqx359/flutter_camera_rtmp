package com.kaibo.camera_rtmp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.simplertmp.RtmpHandler;

import net.ossrs.yasea.SrsCameraView;
import net.ossrs.yasea.SrsEncodeHandler;
import net.ossrs.yasea.SrsPublisher;
import net.ossrs.yasea.SrsRecordHandler;

import java.io.IOException;
import java.net.SocketException;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.platform.PlatformView;

public class FlutterPanoramaView implements PlatformView, MethodChannel.MethodCallHandler, SrsEncodeHandler.SrsEncodeListener, RtmpHandler.RtmpListener, SrsRecordHandler.SrsRecordListener {

    private final MethodChannel methodChannel;
    private SrsPublisher mPublisher;
    private SrsCameraView mCameraView;
    Context context;

    FlutterPanoramaView(final Context context,
                        BinaryMessenger messenger,
                        int id,
                        Map<String, Object> params) {
        this.context=context;
        mCameraView =new SrsCameraView(context);
        requestPermission(context,params);
        // 注册MethodChannel
        methodChannel = new MethodChannel(messenger, "plugins.lqx/camerartmp");
        methodChannel.setMethodCallHandler(this);
    }

    private void requestPermission(Context  context,Map<String, Object> params) {
        //1. 检查是否已经有该权限
        if (Build.VERSION.SDK_INT >= 23 && (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)) {
            //2. 权限没有开启，请求权限
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else{
            Log.e("uuuuuuuuuuu", String.valueOf(params));
//            Toast.makeText(context,(int) params.get("width"),Toast.LENGTH_LONG).show();
            init(params);
        }
    }


    private void init(Map<String, Object> params){
        mPublisher = new SrsPublisher(mCameraView);
        mPublisher.setEncodeHandler(new SrsEncodeHandler(this));
        mPublisher.setRtmpHandler(new RtmpHandler(this));
        mPublisher.setRecordHandler(new SrsRecordHandler(this));
        mPublisher.setPreviewResolution((int) params.get("height"), (int) params.get("width"));
        mPublisher.setOutputResolution((int) params.get("width"), (int) params.get("height")); // 这里要和preview反过来
        mPublisher.setVideoHDMode();
        mPublisher.startCamera();
        if((boolean) params.get("changeCamera")){
            mPublisher.switchCameraFace((mPublisher.getCameraId() + 1) % Camera.getNumberOfCameras());
        }
    }


    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
        switch (call.method){
            case "releaseCamera":
                if(mCameraView!=null){
                    mCameraView.stopCamera();
                    mCameraView=null;
                }
                if(mPublisher!=null){
                    mPublisher.setEncodeHandler(null);
                    mPublisher.setRtmpHandler(null);
                    mPublisher.setRecordHandler(null);
                    mPublisher.stopCamera();
                    mPublisher=null;
                }
                break;
            case "openCamera":
                mPublisher.startCamera();
                break;
            case "startPublish":
                mPublisher.startPublish(call.argument("rtmpUrl").toString());
                mPublisher.startCamera();
                mCameraView.stopTorch();
                break;
            case "setVideoHDMode":
                if(call.argument("call").toString().equals("hd")){
                    mPublisher.setVideoHDMode();
                }else{
                    mPublisher.setVideoSmoothMode();
                }
                break;
            case "stopPublish":
                mPublisher.stopPublish();
                break;
            case "pausePublish":
                mPublisher.pausePublish();
                break;
            case "resumePublish":
                mPublisher.resumePublish();
                break;
            case "switchCameraFace":
                mPublisher.switchCameraFace((mPublisher.getCameraId() + 1) % Camera.getNumberOfCameras());
                break;
            case "startRecord":
                mPublisher.startRecord(call.argument("path").toString());
                break;
            case "pauseRecord":
                mPublisher.pauseRecord();
                break;
            case "resumeRecord":
                mPublisher.resumeRecord();
                break;
            case "switchToSoftEncoder":
                mPublisher.switchToSoftEncoder();
                break;
            case "switchToHardEncoder":
                mPublisher.switchToHardEncoder();
                break;
            case "setSendVideoOnly":
                mPublisher.setSendVideoOnly((Boolean) call.argument("flag"));
                break;
            case "setPreview":
                mPublisher.stopCamera();
                mPublisher.setPreviewResolution((int)call.argument("height"), (int) call.argument("width"));
                mPublisher.setOutputResolution((int) call.argument("width"), (int)call.argument("height")); // 这里要和preview反过来
                mPublisher.startCamera();
                break;
            case "setBitrate":
                mPublisher.setBitrate((int) call.argument("bitrate"));
                break;
            default:break;
        }
    }

    @Override
    public View getView() {
        return mCameraView;
    }

    @Override
    public void dispose() {
        mCameraView = null;
    }

    private boolean isHTTP(Uri uri) {
        if (uri == null || uri.getScheme() == null) {
            return false;
        }
        String scheme = uri.getScheme();
        return scheme.equals("http") || scheme.equals("https");
    }

    @Override
    public void onNetworkWeak() {
        Log.e("trrrrrrrrrrr", "onNetworkWeak");
    }

    @Override
    public void onNetworkResume() {
        Log.e("trrrrrrrrrrr", "onNetworkResume");
    }

    @Override
    public void onEncodeIllegalArgumentException(IllegalArgumentException e) {
        Log.e("trrrrrrrrrrr", "onEncodeIllegalArgumentException");
    }

    @Override
    public void onRtmpConnecting(String msg) {
        Log.e("trrrrrrrrrrr", "推流连接中。。。。。");
    }

    @Override
    public void onRtmpConnected(String msg) {
        Log.e("trrrrrrrrrrr", "推流连接成功。。。。。");
    }

    @Override
    public void onRtmpVideoStreaming() {
//        Log.e("trrrrrrrrrrr", "onRtmpVideoStreaming");
    }

    @Override
    public void onRtmpAudioStreaming() {
//        Log.e("trrrrrrrrrrr", "onRtmpAudioStreaming");
    }

    @Override
    public void onRtmpStopped() {
        Log.e("trrrrrrrrrrr", "停止推流。。。。。");
    }

    @Override
    public void onRtmpDisconnected() {
        Log.e("trrrrrrrrrrr", "断流了。。。。。");
    }

    @Override
    public void onRtmpVideoFpsChanged(double fps) {
        Log.e("trrrrrrrrrrr", "onRtmpVideoFpsChanged");
    }

    @Override
    public void onRtmpVideoBitrateChanged(double bitrate) {
        int rate = (int) bitrate;
        if (rate / 1000 > 0) {
            Log.e("trrrrrrrrrrr", String.format("Video bitrate: %f kbps", bitrate / 1000));
        } else {
            Log.e("trrrrrrrrrrr", String.format("Video bitrate: %d bps", rate));
        }
    }

    @Override
    public void onRtmpAudioBitrateChanged(double bitrate) {
        Log.e("trrrrrrrrrrr", "onRtmpAudioBitrateChanged");
    }

    @Override
    public void onRtmpSocketException(SocketException e) {
        Log.e("trrrrrrrrrrr", "onRtmpSocketException");
    }

    @Override
    public void onRtmpIOException(IOException e) {
        Log.e("trrrrrrrrrrr", "onRtmpIOException");
    }

    @Override
    public void onRtmpIllegalArgumentException(IllegalArgumentException e) {
        Log.e("trrrrrrrrrrr", "onRtmpIllegalArgumentException");
    }

    @Override
    public void onRtmpIllegalStateException(IllegalStateException e) {
        Log.e("trrrrrrrrrrr", "onRtmpIllegalStateException");
    }

    @Override
    public void onRecordPause() {
        Log.e("trrrrrrrrrrr", "onRecordPause");
    }

    @Override
    public void onRecordResume() {
        Log.e("trrrrrrrrrrr", "onRecordResume");
    }

    @Override
    public void onRecordStarted(String msg) {
        Log.e("trrrrrrrrrrr", "onRecordStarted");
    }

    @Override
    public void onRecordFinished(String msg) {
        Log.e("trrrrrrrrrrr", "onRecordFinished");
    }

    @Override
    public void onRecordIllegalArgumentException(IllegalArgumentException e) {
        Log.e("trrrrrrrrrrr", "onRecordIllegalArgumentException");
    }

    @Override
    public void onRecordIOException(IOException e) {
        Log.e("trrrrrrrrrrr", "onRecordIOException");
    }
}


