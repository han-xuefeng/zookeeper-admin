import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    children:[
      {
        path: '',
        name: 'clusterList',
        component: () => import('../components/cluster/List.vue')
      },
      {
        path: '/clusterItem/:id',
        name: 'clusterItem',
        component: () => import('../components/cluster/Item.vue')
      },
      {
        path: '/changePassword',
        name: 'userChangePassword',
        component: () => import('../components/user/ChangePassword.vue')
      }
    ]
  },
  
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(process.env.BASE_URL),
  routes
})

export default router
