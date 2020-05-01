<template>
  <div class="home">
    <v-container>
      <v-card class="main-card">

        <v-row
          justify="center"
          align-content="stretch"
          align="stretch"
        >
          <v-col
            cols="4"
          >
          <router-link to="/pregledKlinika">
            <v-card
              min-width="300px"
              min-height="300px"
              :img="klinike"
              class="card"
            >
            <v-container>
                <v-card>
                  <v-card-title>
                    Pregled klinika
                  </v-card-title>
                </v-card>
              </v-container>
            </v-card>
          </router-link>
          </v-col>
          <v-col
            cols="4"
          >
          <router-link to="/pregledLekova">
            <v-card
              min-width="300px"
              min-height="300px"
              :img="lekovi"
              >
              <v-container>
                  <v-card>
                    <v-card-title>
                      Šifarnik lekova
                    </v-card-title>
                  </v-card>
                </v-container>
              </v-card>
            </router-link>
          </v-col>
          <v-col
            cols="4"
          >
            <router-link to="/pregledDijagnoza">
              <v-card
                min-width="300px" 
                min-height="300px"
                :img="dijagnoze"
  
              >
                <v-container>
                  <v-card>
                    <v-card-title>
                      Šifarnik dijagnoza
                    </v-card-title>
                  </v-card>
                </v-container>
              </v-card>
            </router-link>
          </v-col>
        </v-row>
      </v-card>
    </v-container>
  </div>
</template>

<script>
// @ is an alias to /src
// import axios from 'axios';
// import router from "../router/index.js"

export default {
  name: 'HomeAdminKC',
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
