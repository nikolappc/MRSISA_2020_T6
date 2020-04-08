import Vue from 'vue'
import VueRouter from 'vue-router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import Home from '../views/Home.vue'

Vue.use(VueRouter, VueAxios, axios)

  const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/profilPacijenta',
    name: 'ProfilPacijenta',
    component: () => import('../views/ProfilPacijenta.vue')
  }
  
]

const router = new VueRouter({
  routes
})

export default router
