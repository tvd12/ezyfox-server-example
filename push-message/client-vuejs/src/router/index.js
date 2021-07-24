import { createRouter, createWebHistory } from 'vue-router'
import Ezy from 'ezyfox-es6-client';
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
  const clients = Ezy.Clients.getInstance();
  const client = clients.getDefaultClient();
  const authenticated = client && client.isConnected();
  const publicPages = ['/'];
  const authRequired = !publicPages.includes(to.path);

  if (authRequired && !authenticated) {
    next('/');
  }

  next();
})

export default router
