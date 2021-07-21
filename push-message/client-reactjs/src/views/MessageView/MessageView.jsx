import * as React from 'react';
import Mvc from 'mvc-es6';
import SocketProxy from '../../socket/SocketProxy';

class MessageItemView extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {data} = this.props;
        return (
            <li className="replies">
                {data}
            </li>
        );
    }
}

class MessageView extends React.Component {

    constructor(props) {
        super(props);
        this.messages = ["An usage of ezyfox-server"];

        this.state = {
            messages : this.messages
        };
        let mvc = Mvc.getInstance();
        this.messageController = mvc.getController("message");
    }

    componentDidMount() {
        this.messageController.addDefaultView("add", (message) => {
            console.log("message view add message: " + message);
            this.messages.push(message);
            this.setState({messages: this.messages});
        });
    }

    componentWillUnmount() {
        this.messageController.removeAllViews();
    }

    render() {
        const {messages} = this.state;
        return (
            <div className="messages-wrapper">
                <div className="messages" id="messageListDiv">
                    <ul id="messageListUl">
                        {
                            messages.map((msg, i) => <MessageItemView key={i} data={msg} />)
                        }
                    </ul>
                </div>
            </div>
        );
    }
}

export default MessageView
