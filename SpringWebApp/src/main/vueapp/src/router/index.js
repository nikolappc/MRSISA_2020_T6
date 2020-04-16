import Vue from 'vue'
import VueRouter from 'vue-router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import Home from '../views/Home.vue';
import HomeMed from '../views/HomeMed.vue';
import ProfilPacijentaIzmjena from '../views/ProfilPacijentaIzmjena.vue';
Vue.use(VueRouter, VueAxios, axios)

  const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/HomeMed',
    name: 'HomeMed',
    component: HomeMed
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
    name: 'profilPacijenta',
    component: () => import('../views/ProfilPacijenta.vue')
  },
  {
    path: '/profilPacijentaIzmjena',
    name: 'profilPacijentaIzmjena',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: ProfilPacijentaIzmjena
  },
  {
    path: '/lekar/add',
    name: 'dodajLekara',
    component: () => import('../views/lekar/AddLekar.vue')
  },
  {
    path: '/lekari',
    name: 'listaLekara',
    component: () => import('../views/lekar/ListaLekara.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  
  },
  {
    path: '/sale',
    name: 'Sale',
    component: () => import('../views/sala/ListaSala.vue')
  
  },
  {
    path: '/sala/add',
    name: 'AddSala',
    component: () => import('../views/sala/AddSala.vue')
  
  }
  
]

const router = new VueRouter({
  routes
})


export default router