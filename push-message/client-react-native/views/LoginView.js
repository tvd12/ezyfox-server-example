import React, {Component} from 'react';
import {Button, TextInput, View, StyleSheet} from 'react-native';
import Mvc from 'mvc-es6';
import SocketProxy from '../socket/SocketProxy';

class LoginView extends Component {
  constructor(props) {
    super(props);

    this.state = {
      username: 'dungtv',
      password: '123456',
    };
  }

  onLogin() {
    const {username, password} = this.state;
    let mvc = Mvc.getInstance();
    let models = mvc.models;
    models.connection = {
      username: username,
      password: password,
    };
    SocketProxy.getInstance().connect();
  }

  render() {
    return (
      <View style={styles.container}>
        <TextInput
          value={this.state.username}
          onChangeText={username => this.setState({username})}
          placeholder={'Username'}
          style={styles.input}
        />
        <TextInput
          value={this.state.password}
          onChangeText={password => this.setState({password})}
          placeholder={'Password'}
          secureTextEntry={true}
          style={styles.input}
        />

        <Button
          title={'Login'}
          style={styles.input}
          onPress={this.onLogin.bind(this)}
        />
      </View>
    );
  }
}

export default LoginView;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#ecf0f1',
  },
  input: {
    width: 200,
    height: 44,
    padding: 10,
    borderWidth: 1,
    borderColor: 'black',
    marginBottom: 10,
  },
});