import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../components/LoginView'
import MessageView from '../components/MessageView'

const routes = [
  {
    path: '/',
    name: 'LoginView',
    component: LoginView
  },
  {
    path: '/message',
    name: 'MessageView',
    component: MessageView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  // // redirect to login page if not logged in and trying to access a restricted page
  // const publicPages = ['/login', '/register'];
  // const authRequired = !publicPages.includes(to.path);
  // const loggedIn = localStorage.getItem('user');

  // if (authRequired && !loggedIn) {
  //   return next('/login');
  // }

  next();
})

export default router
