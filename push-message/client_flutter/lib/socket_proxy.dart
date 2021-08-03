import 'package:ezyfox_server_flutter_client/ezy_client.dart';
import 'package:ezyfox_server_flutter_client/ezy_clients.dart';
import 'package:ezyfox_server_flutter_client/ezy_config.dart';
import 'package:ezyfox_server_flutter_client/ezy_constants.dart';
import 'package:ezyfox_server_flutter_client/ezy_entities.dart';
import 'package:ezyfox_server_flutter_client/ezy_handlers.dart';

const ZONE_NAME = "push-message";
const APP_NAME = "push-message";

class SocketProxy {
  bool settedUp = false;
  late String username;
  late String password;
  late EzyClient _client;
  late Function(Map)? _messageCallback;
  late Function? _loggedInCallback;
  late Function? _disconnectedCallback;
  static final SocketProxy _INSTANCE = SocketProxy._();

  SocketProxy._() {}

  static SocketProxy getInstance() {
    return _INSTANCE;
  }

  void _setup() {
    EzyConfig config = EzyConfig();
    config.clientName = ZONE_NAME;
    EzyClients clients = EzyClients.getInstance();
    _client = clients.newDefaultClient(config);
    _client.setup.addEventHandler(EzyEventType.CONNECTION_FAILURE, _ConnectionFailureHandler(_disconnectedCallback!));
    _client.setup.addDataHandler(EzyCommand.HANDSHAKE, _HandshakeHandler());
    _client.setup.addDataHandler(EzyCommand.LOGIN, _LoginSuccessHandler());
    _client.setup.addDataHandler(EzyCommand.APP_ACCESS, _AppAccessHandler(_loggedInCallback!));
    var appSetup = _client.setup.setupApp(APP_NAME);
    appSetup.addDataHandler("message", _MessageResponseHandler((data) => {
      _messageCallback!(data)
    }));

  }

  void connectToServer(String username, String password) {
    if(!settedUp) {
      settedUp = true;
      _setup();
    }
    this.username = username;
    this.password = password;
    this._client.connect("localhost", 3005);
  }

  void onLoggedIn(Function callback) {
    this._loggedInCallback = callback;
  }

  void onDisconnected(Function callback) {
    this._disconnectedCallback = callback;
  }

  void onMessage(Function(Map) callback) {
    this._messageCallback = callback;
  }
}

class _ConnectionFailureHandler extends EzyConnectionFailureHandler {
  late Function _callback;

  _ConnectionFailureHandler(Function callback) {
    _callback = callback;
  }

  @override
  void onConnectionFailed(Map event) {
    _callback();
  }
}

class _HandshakeHandler extends EzyHandshakeHandler {
  @override
  List getLoginRequest() {
    var request = [];
    request.add(ZONE_NAME);
    request.add(SocketProxy.getInstance().username);
    request.add(SocketProxy.getInstance().password);
    request.add([]);
    return request;
  }
}

class _LoginSuccessHandler extends EzyLoginSuccessHandler {
  @override
  void handleLoginSuccess(responseData) {
    client.send(EzyCommand.APP_ACCESS, [APP_NAME]);
  }
}

class _AppAccessHandler extends EzyAppAccessHandler {
  late Function _callback;

  _AppAccessHandler(Function callback) {
    _callback = callback;
  }

  @override
  void postHandle(EzyApp app, List data) {
    _callback();
  }
}

class _MessageResponseHandler extends EzyAppDataHandler<Map> {

  late Function(Map) _callback;

  _MessageResponseHandler(Function(Map) callback) {
    _callback = callback;
  }

  @override
  void handle(EzyApp app, Map data) {
    _callback(data);
  }
}