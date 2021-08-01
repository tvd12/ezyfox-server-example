import 'package:flutter/material.dart';

import 'socket_proxy.dart';
import 'state_manager.dart';


class MessageView extends StatefulWidget {

  @override
  State<StatefulWidget> createState() => new _State();
}

class _State extends State<MessageView> {
  List<Map> _messages = [];
  TextEditingController nameController = TextEditingController();
  TextEditingController passwordController = TextEditingController();

  _State() {
    var socketProxy = SocketProxy.getInstance();
    socketProxy.onMessage(this._onMessageReceived);
  }

  void _onMessageReceived(Map data) {
    _messages.insert(0, data);
    setState(() {
      _messages = _messages;
    });
  }

  @override
  Widget build(BuildContext context) {
    StateManager.getInstance().currentContext = context;
    return Scaffold(
        appBar: AppBar(
          title: Text('Message List'),
        ),
      body: _messages.isNotEmpty
          ? ListView.builder(
            itemCount: _messages.length,
            itemBuilder: (BuildContext context, int index) {
              return ListTile(
                title: Text(_messages[index]["title"]),
                subtitle: Text(_messages[index]["content"]),
              );
            },
          )
          : const Center(child: Text('No items')),
    );
  }
}