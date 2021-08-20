import React from 'react';
import {StyleSheet, View} from 'react-native';
import Mvc from 'mvc-es6';
import LoginView from './views/LoginView';
import MessageView from './views/MessageView';
import {SocketProxy} from './socket';

export default class App extends React.Component {
  constructor(args) {
    super(args);
    this.mvc = Mvc.getInstance();
    this.mvc.newController('router');
    this.mvc.newController('login');
    this.mvc.newController('message');
    this.state = {currentView: 'login'};
    SocketProxy.getInstance().setup();
  }

  componentDidMount() {
    let routerController = this.mvc.getController('router');
    routerController.addDefaultView('change', viewName => {
      this.setState({currentView: viewName});
    });
  }

  render() {
    let {currentView} = this.state;
    var displayView = <LoginView />;
    if (currentView === 'message') {
      displayView = <MessageView />;
    }
    return <View style={styles.container}>{displayView}</View>;
  }
}
var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
  },
  hello: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
});