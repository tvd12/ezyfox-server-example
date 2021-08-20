import React, {Component} from 'react';
import {FlatList, Text, View, StyleSheet} from 'react-native';
import Mvc from 'mvc-es6';
import { Command } from '../socket';

class MessageView extends Component {
  constructor(props) {
    super(props);
    this.messages = [];
    this.state = {
      messages: this.messages
    };
    this.mvc = Mvc.getInstance();
    this.messageController = this.mvc.getController('message');
  }

  componentDidMount() {
    this.messageController.addDefaultView(Command.MESSAGE, message => {
      this.messages = [message].concat(this.messages);
      this.setState({messages: this.messages});
    });
  }

  render() {
    const {messages} = this.state;
    var contentView;
    if(messages.length == 0) {
      contentView = <Text style={styles.noMessage}>There is no messages</Text>;
    }
    else {
        contentView = <FlatList 
          data={messages} 
          renderItem={({item}) => <Text style={styles.item}>{item.title}: {item.content}</Text>} />;
    }
    return (
      <View style={styles.container}>
        {contentView}
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    paddingTop: 50
  },
  noMessage: {
    color: 'gray'
  },
  item: {
    padding: 10,
    fontSize: 18,
    height: 44,
  },
});


export default MessageView;