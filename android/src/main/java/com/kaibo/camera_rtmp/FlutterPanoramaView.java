package com.kaibo.camera_rtmp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import com.github.faucamp.simplertmp.RtmpHandler;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;

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
        requestPermission(context);
        // 注册MethodChannel
        methodChannel = new MethodChannel(messenger, "plugins.lqx/camerartmp");
        methodChannel.setMethodCallHandler(this);
    }

    private void requestPermission(Context  context) {
        //1. 检查是否已经有该权限
        if (Build.VERSION.SDK_INT >= 23 && (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)) {
            //2. 权限没有开启，请求权限
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else{
            init();
        }
    }


    private void init(){
        mPublisher = new SrsPublisher(mCameraView);
        mPublisher.setEncodeHandler(new SrsEncodeHandler(this));
        mPublisher.setRtmpHandler(new RtmpHandler(this));
        mPublisher.setRecordHandler(new SrsRecordHandler(this));
        mPublisher.setPreviewResolution(1400, 720);
        mPublisher.setOutputResolution(720, 1400); // 这里要和preview反过来
        mPublisher.setVideoHDMode();
        mPublisher.startCamera();
    }


    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
        switch (call.method){
            case "startPublish":
                mPublisher.startPublish(call.argument("rtmpUrl").toString());
                mPublisher.startCamera();
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

    }

    @Override
    public void onNetworkResume() {

    }

    @Override
    public void onEncodeIllegalArgumentException(IllegalArgumentException e) {

    }

    @Override
    public void onRtmpConnecting(String msg) {

    }

    @Override
    public void onRtmpConnected(String msg) {

    }

    @Override
    public void onRtmpVideoStreaming() {

    }

    @Override
    public void onRtmpAudioStreaming() {

    }

    @Override
    public void onRtmpStopped() {

    }

    @Override
    public void onRtmpDisconnected() {

    }

    @Override
    public void onRtmpVideoFpsChanged(double fps) {

    }

    @Override
    public void onRtmpVideoBitrateChanged(double bitrate) {

    }

    @Override
    public void onRtmpAudioBitrateChanged(double bitrate) {

    }

    @Override
    public void onRtmpSocketException(SocketException e) {

    }

    @Override
    public void onRtmpIOException(IOException e) {

    }

    @Override
    public void onRtmpIllegalArgumentException(IllegalArgumentException e) {

    }

    @Override
    public void onRtmpIllegalStateException(IllegalStateException e) {

    }

    @Override
    public void onRecordPause() {

    }

    @Override
    public void onRecordResume() {

    }

    @Override
    public void onRecordStarted(String msg) {

    }

    @Override
    public void onRecordFinished(String msg) {

    }

    @Override
    public void onRecordIllegalArgumentException(IllegalArgumentException e) {

    }

    @Override
    public void onRecordIOException(IOException e) {

    }
}


