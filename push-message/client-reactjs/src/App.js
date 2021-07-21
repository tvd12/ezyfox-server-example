import logo from './logo.svg';
import './App.css';

import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Redirect } from "react-router-dom";
import Ezy from 'ezyfox-es6-client';
import Mvc from 'mvc-es6'
import SocketProxy from './socket/SocketProxy'

import LoginView from './views/LoginView'
import MessageView from './views/MessageView'

class App extends Component {
  constructor() {
    super(...arguments);
    this.authenticated = false;
    this.state = {currentViewURI: ""};

    // setup ezyfox
    Ezy.Logger.debug = () => true;
    this.clients = Ezy.Clients.getInstance();

    // setup mvc
    this.mvc = Mvc.getInstance();
    this.mvc.newController("router");
    this.mvc.newController("login");
    this.mvc.newController("message");
    this.mvc.models.chat = {};

    // setup socket
    this.socketProxy = SocketProxy.getInstance();
    this.socketProxy.setup();
  }

  componentWillMount() {
    let routerController = this.mvc.getController("router");
    routerController.addDefaultView("change", viewURI => {
      this.setState({currentViewURI : viewURI});
    });
  }

  isAuthenticated() {
    let clients = Ezy.Clients.getInstance();
    let client = clients.getDefaultClient();
    return client && client.isConnected();
  }

  render() {
    let {currentViewURI} = this.state;
    let view;
    switch(currentViewURI) {
      case "/login":
        view = <LoginView />;
        break;
      case "/message":
          view = <MessageView />;
          break;
      default:
        view = <LoginView />;
        currentViewURI = "/login";
        break;
    };
    window.history.pushState('', '', currentViewURI);
    return (

        <div>
            {view}
        </div>

    );
  }
}

const AuthRoute = ({component: Component, ...rest}) => {
  const clients = Ezy.Clients.getInstance();
  const client = clients.getDefaultClient();
  const authenticated = client && client.isConnected();
  return (
    <Route
      {...rest}
      render = {props => authenticated === true
        ? (<Component {...props} {...rest} />)
        : (<Redirect to="/" />)
      }
    />
  );
};

export default App;