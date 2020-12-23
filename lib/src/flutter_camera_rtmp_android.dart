import 'package:flutter/widgets.dart';
import 'package:flutter/services.dart';
import 'package:flutter_panorama/platform_interface.dart';

import 'flutter_camera_rtmp_method_channel.dart';

class AndroidPanoramaView extends FlutterPanoramaPlatform {

  @override
  Widget build(BuildContext context, Map<String, dynamic> creationParams, PanoramaPlatformCallbacksHandler callbacksHandler) {
    return AndroidView(
      viewType: "plugins.lqx/camerartmp",
      creationParams: creationParams,
      creationParamsCodec: const StandardMessageCodec(),
      onPlatformViewCreated: (int id) {
        MethodChannelPanoramaPlatform(id, callbacksHandler);
      },
    );
  }

}