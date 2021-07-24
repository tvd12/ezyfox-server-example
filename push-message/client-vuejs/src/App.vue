<template>
  <div class="container">
    <router-view/>
  </div>
</template>

<script>
import Ezy from 'ezyfox-es6-client';
import Mvc from 'mvc-es6'
import SocketProxy from './socket/SocketProxy'
import router from './router'

export default {
  name: 'App',
    created: function () {
      // setup ezyfox
      Ezy.Logger.debug = () => true;
      this.clients = Ezy.Clients.getInstance();

      // setup mvc
      this.mvc = Mvc.getInstance();
      this.mvc.newController("router");
      this.mvc.newController("login");
      this.mvc.newController("message");
      this.mvc.models.connection = {};

      // setup socket
      this.socketProxy = SocketProxy.getInstance();
      this.socketProxy.setup();

      let routerController = this.mvc.getController("router");
      routerController.addDefaultView("change", viewURI => {
        router.push(viewURI);
      });
    }
};

</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
