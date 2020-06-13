import Vue from 'vue'
import VueRouter from 'vue-router'
import axios from 'axios'
import store from '../store/index.js'
import VueAxios from 'vue-axios'
import Home from '../views/Home.vue';
import HomeMed from '../views/HomeMed.vue';
import PregledAdminaKC from '../views/adminKlinickogCentra/PregledAdminaKC.vue';
import PregledAdminaKlinika from '../views/adminKlinickogCentra/PregledAdminaKlinika.vue';
import PregledLekova from '../views/adminKlinickogCentra/PregledLekova.vue';
import PregledDijagnoza from '../views/adminKlinickogCentra/PregledDijagnoza.vue';
import PregledKlinika from '../views/adminKlinickogCentra/PregledKlinika.vue';
import RegDijagnoza from '../views/adminKlinickogCentra/RegDijagnoza.vue';
import RegAdminKC from '../views/adminKlinickogCentra/RegAdminKC.vue';
import RegAdminKlinika from '../views/adminKlinickogCentra/RegAdminKlinike.vue';
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
import PosjetePacijenta from '../views/PosjetePacijenta.vue';
import KlinikeZaPacijenta from '../views/KlinikeZaPacijenta.vue';
import PretragaKlinika from '../views/PretragaKlinika.vue';
import PretragaLjekara from '../views/PretragaLjekara.vue';
import PredefinisaniPregledi from '../views/PredefinisaniPregledi.vue';
import ZakazaniPregledi from '../views/ZakazaniPregledi.vue';
import StranicaKlinike from '../views/StranicaKlinike.vue';


function check(to,from,next,dozvoljeni){
  console.log(to,from);
  for(let tip of dozvoljeni){
    if(store.state.ulogovan.tip == tip){
      next();
    }
  }
}



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
    path:"/pregledAdminaKlinika",
    name:"PregledAdminaKlinika",
    component:PregledAdminaKlinika

  },
  {
    path:"/pregledAdminaKC",
    name:"PregledAdminaKC",
    component:PregledAdminaKC

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
    path:"/regAdminKlinika",
    name:"RegAdminKlinika",
    component:RegAdminKlinika
  },
  {
    path:"/regAdminKC",
    name:"RegAdminKC",
    component:RegAdminKC
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
    path: '/overiRecepte',
    name: 'OveriRecepte',
    props:true,
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/OveriRecepte.vue')
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../views/About.vue')
  },
  {
    path: '/profilPacijenta',
    name: 'profilPacijenta',
    component: () => import('../views/ProfilPacijenta.vue')
  },
  {
    path: '/izvestaj',
    name: 'IzvestajKlinike',
    beforeEnter: (to, from, next) => {
      check(to,from,next,["ADMIN_K"]);
    },
    component: () => import('../views/adminKlinike/Izvestaji.vue')
  },
  {
    path: '/profilPacijentaIzmjena',
    name: 'profilPacijentaIzmjena',
    component: ProfilPacijentaIzmjena
  },
  {
    path: '/zdravstveniKartonPrikaz/:id',
    name: 'zdravstveniKartonPrikaz',
    component: ZdravstveniKartonPrikaz
  },
  {
    path: '/lekar/add',
    name: 'dodajLekara',
    beforeEnter: (to, from, next) => {
      check(to,from,next,["ADMIN_K"]);
    },
    component: () => import('../views/lekar/AddLekar.vue')
  },
  {
    path: '/lekari',
    name: 'listaLekara',
    beforeEnter: (to, from, next) => {
      check(to,from,next,["ADMIN_K"]);
    },
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
    beforeEnter: (to, from, next) => {
      check(to,from,next,["ADMIN_K"]);
    },
    component: () => import('../views/sala/ListaSala.vue')

  },
  {
    path: '/potvrdaPosete/:id/:isPregled',
    name: 'SalePotvrda',
    beforeEnter: (to, from, next) => {
      check(to,from,next,["ADMIN_K"]);
    },
    component: () => import('../views/sala/DodelaSala.vue'),
    props:(route)=>({
      id:route.params.id,
      isPregled:route.params.isPregled,
    })

  },
  {
    path: '/pacijent/:id',
    name: 'RNG',
    beforeEnter: (to, from, next) => {
      check(to,from,next,["LEKAR"]);
    },
    component: () => import('../views/lekar/ProfilPacijenta.vue')

  },
  {
    path: '/sala/add',
    name: 'AddSala',
    beforeEnter: (to, from, next) => {
      check(to,from,next,["ADMIN_K"]);
    },
    component: () => import('../views/sala/AddSala.vue')
  },
  {
    path: '/tipoviPoseta',
    name: 'TipPosete',
    beforeEnter: (to, from, next) => {
      check(to,from,next,["ADMIN_K"]);
    },
    component: () => import('../views/tipPosete/ListaTip.vue')
  },
  {
    path: '/tipoviPoseta/add',
    name: 'TipPoseteAdd',
    beforeEnter: (to, from, next) => {
      check(to,from,next,["ADMIN_K"]);
    },
    component: () => import('../views/tipPosete/AddTip.vue')
  },
  {
    path: '/DodavanjeTermina',
    name: 'DodavanjeTermina',
    beforeEnter: (to, from, next) => {
      check(to,from,next,["ADMIN_K"]);
    },
    component: () => import('../views/DodavanjeTermina.vue')
  },
  {
    path: '/listaPacijenata',
    name: 'ListaPacijenata',
    beforeEnter: (to, from, next) => {
      check(to,from,next,["LEKAR","SESTRA"]);
    },
    component: () => import('../views/pacijenti/ListaPacijenta.vue')
  },
  {
    path: '/pregled/:id',
    name: 'PregledPacijenta',
    beforeEnter: (to, from, next) => {
      check(to,from,next,["LEKAR"]);
    },
    component: () => import('../views/pacijenti/Pregled.vue')
  },
  {
    path: '/operacija/:id',
    name: 'OperacijaPacijenta',
    beforeEnter: (to, from, next) => {
      check(to,from,next,["LEKAR"]);
    },
    component: () => import('../views/pacijenti/Operacija.vue')
  },
  {
    path: '/izmenaPregleda/:id',
    name: 'IzmenaPregleda',
    component: () => import('../views/lekar/IzmenaPregleda.vue')
  },
  {
    path: '/istorijaPoseta/',
    name: 'IstorijaPoseta',
    component: () => import('../views/lekar/IstorijaPoseta.vue')
  },
  {
    path: '/zakaziPregled',
    name: 'ZakazivanjePregleda',
    beforeEnter: (to, from, next) => {
      check(to,from,next,["LEKAR"]);
    },
    component: () => import('../views/pacijenti/ZakazivanjePregleda.vue')
  },
  {
    path: '/zakaziOdmor',
    name: 'ZakazivanjeOdmor',
    beforeEnter: (to, from, next) => {
      check(to,from,next,["LEKAR","SESTRA"]);
    },
    component: () => import('../views/lekar/GodisnjiOdmor.vue')
  },
  {
    path: '/izmenaNaloga',
    name: 'IzmenaProfila',
    component: () => import('../views/IzmenaProfila.vue')
  },
  {
    path: '/izmenaKlinike',
    name: 'IzmenaKlinike',
    beforeEnter: (to, from, next) => {
      check(to,from,next,["ADMIN_K", "ADMIN_KC"]);
    },
    component: () => import('../views/adminKlinike/IzmenaKlinike.vue')
  },
  {
    path:"/homePacijent",
    name:"HomePacijent",
    component:HomePacijent
  },
  {
    path:"/homeLekar",
    name:"HomeLekar",
    beforeEnter: (to, from, next) => {
      check(to,from,next,["LEKAR"]);
    },
    component:HomeLekar
  },
  {
    path:"/homeAdminKlinike",
    name:"HomeAdminKlinike",
    beforeEnter: (to, from, next) => {
      check(to,from,next,["ADMIN_K"]);
    },
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
  {
    path:"/posjetePacijenta",
    name:"PosjetePacijenta",
    component:PosjetePacijenta
  },
  {
    path:"/klinikeZaPacijenta",
    name:"KlinikeZaPacijenta",
    component:KlinikeZaPacijenta
  },
  {
    path:"/pretragaKlinika",
    name:"PretragaKlinika",
    component:PretragaKlinika
  },
  {
    //path:"/pretragaLjekara/:id/:cenaPregleda/:nazivTipa/:datum",
	path:"/pretragaLjekara",
    name:"PretragaLjekara",
    props:true,
    component:PretragaLjekara
  },
  {
    path:"/predefinisaniPregledi/:idKlinike",
    name:"PredefinisaniPregledi",
    component:PredefinisaniPregledi
  },
  {
    path:"/zakazaniPregledi",
    name:"ZakazaniPregledi",
    component:ZakazaniPregledi
  },
  {
    path:"/stranicaKlinike/:idKlinike",
    name:"StranicaKlinike",
    component:StranicaKlinike
  },
  
]

const router = new VueRouter({
  routes
})




export default router
