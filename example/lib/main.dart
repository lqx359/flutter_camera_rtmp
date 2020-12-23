import 'package:flutter/material.dart';

import 'package:flutter_panorama/flutter_camera_rtmp.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  CameraController cameraController=new CameraController();
  @override
  void initState() {
    super.initState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        body: Column(
          children: <Widget>[
            Expanded(
              child: FlutterCameraRtmp.pushRtmp(
                onImageLoaded: (state) {
                  print("------------------------------- ${state == 1 ? '图片加载完成' : '图片加载失败'}");
                },
              ),
            ),
           Row(
             children: <Widget>[
               InkWell(
                 onTap: (){
                   cameraController.startPublish("rtmp://push.kaibotv.com:1935/live/5fa4fc7322000ca526d521e2");
                 },
                 child: Container(
                   height: 40,
                   width: 40,
                   color: Colors.red,
                   child: Text("推流"),
                 ),
               ),
               InkWell(
                 onTap: (){
                   cameraController.stopPublish();
                 },
                 child: Container(
                   height: 40,
                   width: 40,
                   color: Colors.red,
                   child: Text("停止推流"),
                 ),
               ),
               InkWell(
                 onTap: (){
                   cameraController.pausePublish();
                 },
                 child: Container(
                   height: 40,
                   width: 40,
                   color: Colors.red,
                   child: Text("暂停"),
                 ),
               ),
               InkWell(
                 onTap: (){
                   cameraController.resumePublish();
                 },
                 child: Container(
                   height: 40,
                   width: 40,
                   color: Colors.red,
                   child: Text("恢复"),
                 ),
               ),
               InkWell(
                 onTap: (){
                   cameraController.switchCameraFace();
                 },
                 child: Container(
                   height: 40,
                   width: 40,
                   color: Colors.red,
                   child: Text("旋转"),
                 ),
               ),
               InkWell(
                 onTap: (){
                   cameraController.switchToHardEncoder();
                 },
                 child: Container(
                   height: 40,
                   width: 40,
                   color: Colors.red,
                   child: Text("硬解码"),
                 ),
               ),
               InkWell(
                 onTap: (){
                   cameraController.switchToSoftEncoder();
                 },
                 child: Container(
                   height: 40,
                   width: 40,
                   color: Colors.red,
                   child: Text("软解码"),
                 ),
               )
             ],
           )
          ],
        )
      ),
    );
  }
}
