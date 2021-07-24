<template>
    <div id="messages">
        <h3>Welcome to <a href="https://github.com/youngmonkeys/ezyfox-server" target="_blank">ezyfox-server</a> push message</h3>
        <ul>
            <li v-for="message in messages" :key="message">
                {{message}}
            </li>
        </ul>
    </div>
</template>

<script>
import Vuex from 'vuex'
import Mvc from 'mvc-es6';

const store = new Vuex.Store({
  state: {
    messages: []
  },
  mutations: {
    messages: state => state.messages
  }
})

export default {
    el: '#messages',
    computed: {
        messages() {
            return store.state.messages;
        }
    },
    created() {
        let mvc = Mvc.getInstance();
        this.messageController = mvc.getController("message");
        this.messageController.addDefaultView("add", (message) => {
            let newMessages = [JSON.stringify(message)].concat(store.state.messages);
            store.state.messages = newMessages.length > 30 ? newMessages.slice(0, 30) : newMessages;
        });
    }
}
</script>
