import 'package:flutter/material.dart';

import 'message_view.dart';
import 'socket_proxy.dart';
import 'login_view.dart';
import 'state_manager.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    var socketProxy = SocketProxy.getInstance();
    socketProxy.onLoggedIn(() {
      Navigator.pushReplacement(
        StateManager.getInstance().currentContext!,
        MaterialPageRoute(builder: (context) => MessageView()),
      );
    });
    socketProxy.onDisconnected(() {
      Navigator.pushReplacement(
        StateManager.getInstance().currentContext!,
        MaterialPageRoute(builder: (context) => LoginView()),
      );
    });
    return MaterialApp(
      title: 'Push Message',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Push Message Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        theme: ThemeData(primarySwatch: Colors.blue), home: LoginView());
  }
}
