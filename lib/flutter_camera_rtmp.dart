import 'dart:collection';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_panorama/platform_interface.dart';
import 'package:flutter_panorama/src/flutter_camera_rtmp_android.dart';
import 'package:flutter_panorama/src/flutter_camera_rtmp_ios.dart';

typedef void ImageLoadedCallback(int state);

final MethodChannel _channel = const MethodChannel('plugins.lqx/camerartmp');

class FlutterCameraRtmp extends StatelessWidget {
  final ImageLoadedCallback onImageLoaded;
  final int width;
  final int height;

  _PlatformCallbacksHandler _platformCallbacksHandler;

  FlutterCameraRtmp.pushRtmp(
      { this.onImageLoaded, this.width, this.height})
      : super();

  static FlutterPanoramaPlatform _platform;

  static set platform(FlutterPanoramaPlatform platform) {
    _platform = platform;
  }

  static FlutterPanoramaPlatform get platform {
    if (_platform == null) {
      switch (defaultTargetPlatform) {
        case TargetPlatform.android:
          _platform = AndroidPanoramaView();
          break;
        case TargetPlatform.iOS:
          _platform = IosPanoramaView();
          break;
        default:
          throw UnsupportedError(
              "Trying to use the default panorama implementation for $defaultTargetPlatform but there isn't a default one");
      }
    }
    return _platform;
  }

  @override
  Widget build(BuildContext context) {
    _platformCallbacksHandler = _PlatformCallbacksHandler(this);

    return FlutterCameraRtmp.platform
        .build(context, _toCreationParams(), _platformCallbacksHandler);
  }

  Map<String, dynamic> _toCreationParams() {
    Map<String, dynamic> creationParams = new HashMap();
    creationParams["enableStereoModeButton"] = "sss";
    creationParams["width"] = width;
    creationParams["height"] = height;
    return creationParams;
  }
}

class _PlatformCallbacksHandler implements PanoramaPlatformCallbacksHandler {
  FlutterCameraRtmp _widget;

  _PlatformCallbacksHandler(this._widget);

  @override
  void onImageLoaded(int state) {
    _widget.onImageLoaded(state);
  }
}

class CameraController {
  CameraController({
    this.enableAudio = true,
    this.androidUseOpenGL = false,
  }) : super();

  /// Whether to include audio when recording a video.
  final bool enableAudio;

  final bool androidUseOpenGL;

  Future<void> startPublish(String rtmpUrl) async {
    Map<String, dynamic> map = new HashMap();
    map["rtmpUrl"]=rtmpUrl;
    await _channel.invokeMethod<void>('startPublish',map);
  }

  Future<void> switchCameraFace() async {
    await _channel.invokeMethod<void>('switchCameraFace');
  }

  Future<void> pausePublish() async {
    await _channel.invokeMethod<void>('pausePublish');
  }

  Future<void> stopPublish() async {
    await _channel.invokeMethod<void>('stopPublish');
  }

  Future<void> resumePublish() async {
    await _channel.invokeMethod<void>('resumePublish');
  }

  Future<void> switchToSoftEncoder() async {
    await _channel.invokeMethod<void>('switchToSoftEncoder');
  }

  Future<void> switchToHardEncoder() async {
    await _channel.invokeMethod<void>('switchToHardEncoder');
  }

  /// Releases the resources of this camera.
  @override
  Future<void> dispose() async {}
}
