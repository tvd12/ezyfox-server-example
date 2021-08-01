import 'package:flutter/cupertino.dart';

class StateManager {
  BuildContext? currentContext;
  static final StateManager _INSTANCE = StateManager._();

  StateManager._() {}

  static StateManager getInstance() {
    return _INSTANCE;
  }
}