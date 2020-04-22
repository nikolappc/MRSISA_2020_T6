import Vue from 'vue'
import VueRouter from 'vue-router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import Home from '../views/Home.vue';
import HomeMed from '../views/HomeMed.vue';
import RegKlinika from '../views/adminKlinickogCentra/RegKlinika.vue';
import RegTipKlinike from '../views/adminKlinickogCentra/RegTipKlinike.vue';
import ProfilPacijentaIzmjena from '../views/ProfilPacijentaIzmjena.vue';
import ZdravstveniKartonPrikaz from '../views/ZdravstveniKartonPrikaz.vue';
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
    path:"/regKlinika",
    name:"RegKlinika",
    component:RegKlinika
  },
  {
    path:"/regTipKlinike",
    name:"RegTipKlinike",
    component:RegTipKlinike
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
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about1" */ '../views/ProfilPacijenta.vue')
  },
  {
    path: '/profilPacijentaIzmjena',
    name: 'profilPacijentaIzmjena',
    component: ProfilPacijentaIzmjena
  },
  {
    path: '/zdravstveniKartonPrikaz',
    name: 'zdravstveniKartonPrikaz',
    component: ZdravstveniKartonPrikaz
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