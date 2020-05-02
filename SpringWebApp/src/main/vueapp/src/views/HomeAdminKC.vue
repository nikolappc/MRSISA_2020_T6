<template>
  <div class="home">
    <v-row>
      <v-col
        cols="9"
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
                md="6"
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
                md="6"
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
                md="6"
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
            </v-row>
          </v-card>
        </v-container>
      </v-col>
      <v-col
        cols="3"
      >
      <v-container>
        <v-card
              color="primary"
            >
          <v-container>
            <v-card-title
              dark
            >
              Zahtevi za registraciju
            </v-card-title>
            <v-row>
              <v-col
                cols="12"
              >
              </v-col>
              <v-col
                cols="12"
              >
                <RegConf username="pero">
            
                </RegConf>
              </v-col>
              <v-col
                cols="12"
              >
                <RegConf username="pero">
            
                </RegConf>
              </v-col>
              <v-col
                cols="12"
              >
                <RegConf username="pero">
            
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
// import axios from 'axios';
// import router from "../router/index.js"
import LinkCard from "../components/LinkCard.vue";
import RegConf from "../components/RegistrationConfirmation.vue";
export default {
  name: 'HomeAdminKC',
  components:{
    LinkCard,
    RegConf
  },
   data: () => ({
      klinike:require("../assets/klinike.jpg"),
      lekovi:require("../assets/lekovi.jpg"),
      dijagnoze:require("../assets/dijagnoze.jpg"),
  }),
  mounted () {
      // axios
      // .get('api/ulogovan')
      // .then(response => {
      //     this.ulogovan = response.data;
      // })
      // .catch(function (error) { console.log(error); router.push("/loginPage"); });
  },
  methods: {
    
  }
  
}
</script>

<style>
</style>
