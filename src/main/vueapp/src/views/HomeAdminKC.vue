<template>
  <div class="home">
    <v-container>
      <label class="display-1">
        Administrator kliničkog centra
      </label>
      <br>
      <label  class="subtitle-1">
        Dobrodošli, {{ulogovan.ime}}
      </label>
    </v-container>
    <v-row>
      <v-col
        lg="9"
        md="6"
        sm="12"
      >
        <v-container>
          <v-card class="main-card">
            <v-row
              justify="center"
              align-content="stretch"
              align="stretch"
            >
              <v-col
                lg="4"
                md="12"
                sm="12"
                xs="12"
              >
              <router-link to="/pregledKlinika">
                <LinkCard
                  :img="klinike"
                  title="Pregled klinika"
                >
                </LinkCard>
              </router-link>
              </v-col>
              <v-col
                lg="4"
                md="12"
                sm="12"
                xs="12"
              >
                <router-link to="/pregledLekova">
                  <LinkCard
                    :img="lekovi"
                    title="Šifarnik lekova"
                  >
                  </LinkCard>
                </router-link>
              </v-col>
              <v-col
                lg="4"
                md="12"
                sm="12"
                xs="12"
              >
                <router-link to="/pregledDijagnoza">
                  <LinkCard
                    :img="dijagnoze"
                    title="Šifarnik dijagnoza"
                  >
                  </LinkCard>
                </router-link>
              </v-col>
              <v-col
                lg="4"
                md="12"
                sm="12"
                xs="12"
              >
                <router-link to="/pregledAdminaKlinika">
                  <LinkCard
                    :img="adminKlinike"
                    title="Upravljanje administratorima klinika"
                  >
                  </LinkCard>
                </router-link>
              </v-col>
              <v-col
                lg="4"
                md="12"
                sm="12"
                xs="12"
              >
                <router-link to="/pregledAdminaKC">
                  <LinkCard
                    :img="adminKC"
                    title="Upravljanje administratorima kliničkog centra"
                  >
                  </LinkCard>
                </router-link>
              </v-col>
            </v-row>
          </v-card>
        </v-container>
      </v-col>
      <v-col
        lg="3"
        md="6"
        sm="12"
      >
      <v-container>
        <v-card
              color="primary"
            >
          <v-container>
            <v-row>
              <v-col
                cols="12"
              >
                <v-card>
                  <v-card-title
                  >
                    Zahtevi za registraciju
                  </v-card-title>
                </v-card>
              </v-col>
              <v-col
                cols="12"
                v-if="nepotvrdjeniKorisnici.length==0"
              >
                <v-card>
                  <v-container>
                    Trenutno nema zahteva za registraciju.
                  </v-container>
                </v-card>
              </v-col>
              <v-col
                v-else
                cols="12"
                v-for="(user, i) in nepotvrdjeniKorisnici"
                :key="i"
              >
                <RegConf :username="user.email" @resolved="onResolved()">
                </RegConf>
              </v-col>
            </v-row>
          </v-container>
        </v-card>
      </v-container>
      </v-col>
    </v-row>
    
  </div>
</template>

<script>
// @ is an alias to /src
import axios from 'axios';
// import router from "../router/index.js"
import LinkCard from "../components/LinkCard.vue";
import RegConf from "../components/RegistrationConfirmation.vue";
export default {
  name: 'HomeAdminKC',
  computed:{
    ulogovan:function () {
      return this.$store.state.ulogovan;
    }
  },
  components:{
    LinkCard,
    RegConf
  },
   data: () => ({
      klinike:require("../assets/klinike.jpg"),
      lekovi:require("../assets/lekovi.jpg"),
      dijagnoze:require("../assets/dijagnoze.jpg"),
      adminKlinike:require("../assets/adminKlinike.jpg"),
      adminKC:require("../assets/adminKC.jpg"),
      nepotvrdjeniKorisnici:[
      ]
    }),
    mounted () {
        this.refresh();
    },
    methods: {
      refresh:function(){
        axios
        .get('api/nepotvrdjeni')
        .then(response => {
            this.nepotvrdjeniKorisnici = response.data;
        })
        .catch(function (error) { console.log(error); });
      },
      onResolved:function () {
        this.refresh();
      }
    }
  
}
</script>

<style>
</style>
