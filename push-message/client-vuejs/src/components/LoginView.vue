<template>
  <div class="col-sm-6 offset-sm-3">
    <img alt="Vue logo" src="./../assets/logo.png" >
    <h2>Login</h2>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
          <label for="username">Username</label>
          <input type="text" v-model="username" name="username" class="form-control" :class="{ 'is-invalid': submitted && !username }" />
          <div v-show="submitted && !username" class="invalid-feedback">Username is required</div>
      </div>
      <div class="form-group">
          <label htmlFor="password">Password</label>
          <input type="password" v-model="password" name="password" class="form-control" :class="{ 'is-invalid': submitted && !password }" />
          <div v-show="submitted && !password" class="invalid-feedback">Password is required</div>
      </div>
      <div class="form-group">
          <button class="btn btn-primary">Login</button>
      </div>
    </form>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import router from './../router'

export default {
  name: 'LoginView',
  data () {
      return {
          username: '',
          password: '',
          submitted: false
      }
    },
    computed: {
        ...mapState('account', ['status'])
    },
    methods: {
        ...mapActions('account', ['login']),
        login(username, password) {
          console.log("login with username: " + username + ", password: " + password);
          router.push('/message');
        },
        handleSubmit () {
            this.submitted = true;
            const { username, password } = this;
            if (username && password) {
                this.login(username, password)
            }
        }
    }
};
</script>
