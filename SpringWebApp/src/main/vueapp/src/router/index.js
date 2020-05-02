import Vue from 'vue'
import VueRouter from 'vue-router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import Home from '../views/Home.vue';
import HomeMed from '../views/HomeMed.vue';
import PregledLekova from '../views/adminKlinickogCentra/PregledLekova.vue';
import PregledDijagnoza from '../views/adminKlinickogCentra/PregledDijagnoza.vue';
import PregledKlinika from '../views/adminKlinickogCentra/PregledKlinika.vue';
import RegDijagnoza from '../views/adminKlinickogCentra/RegDijagnoza.vue';
import RegLekova from '../views/adminKlinickogCentra/RegLekova.vue';
import RegKlinika from '../views/adminKlinickogCentra/RegKlinika.vue';
import RegTipKlinike from '../views/adminKlinickogCentra/RegTipKlinike.vue';
import ProfilPacijentaIzmjena from '../views/ProfilPacijentaIzmjena.vue';
import HomePacijent from '../views/HomePacijent.vue';
import HomeLekar from '../views/HomeLekar.vue';
import HomeAdminKlinike from '../views/HomeAdminKlinike.vue';
import HomeAdminKC from '../views/HomeAdminKC.vue';
import ZdravstveniKartonPrikaz from '../views/ZdravstveniKartonPrikaz.vue';
import Registracija from '../views/Registracija.vue';


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
    path:"/pregledLekova",
    name:"PregledLekova",
    component:PregledLekova
  },
  {
    path:"/pregledDijagnoza",
    name:"PregledDijagnoza",
    component:PregledDijagnoza
  },
  {
    path:"/pregledKlinika",
    name:"PregledKlinika",
    component:PregledKlinika
  },
  {
    path:"/regLekova",
    name:"RegLekova",
    component:RegLekova
  },
  {
    path:"/regDijagnoza",
    name:"RegDijagnoza",
    component:RegDijagnoza
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
    path: '/loginPage',
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
  },
  {
    path: '/tipoviPoseta',
    name: 'TipPosete',
    component: () => import('../views/tipPosete/ListaTip.vue')
  },
  {
    path: '/tipoviPoseta/add',
    name: 'TipPoseteAdd',
    component: () => import('../views/tipPosete/AddTip.vue')
  },
  {
    path: '/DodavanjeTermina',
    name: 'DodavanjeTermina',
    component: () => import('../views/DodavanjeTermina.vue')
  },
  {
    path:"/homePacijent",
    name:"HomePacijent",
    component:HomePacijent
  },
  {
    path:"/homeLekar",
    name:"HomeLekar",
    component:HomeLekar
  },
  {
    path:"/homeAdminKlinike",
    name:"HomeAdminKlinike",
    component:HomeAdminKlinike
  },
  {
    path:"/homeAdminKC",
    name:"HomeAdminKC",
    component:HomeAdminKC
  },
  {
    path:"/registracija",
    name:"Registracija",
    component:Registracija
  },
  
]

const router = new VueRouter({
  routes
})


export default router