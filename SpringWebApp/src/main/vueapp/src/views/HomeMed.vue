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
            <v-card
              min-width="300px"
              min-height="300px"
              :img="patients"
              class="card"
            >
            <v-container>
              <v-card>
                <v-card-title>
                  Pregled pacijenata
                </v-card-title>
              </v-card>
            </v-container>
            </v-card>
          </v-col>
          <v-col
            cols="4"
          >
            <v-card
              min-width="300px"
              min-height="300px"
              :img="kalendar"
              >
              <v-container>
                <v-card>
                  <v-card-title>
                    Radni kalendar
                  </v-card-title>
                </v-card>
              </v-container>
            </v-card>
          </v-col>
          <v-col
            cols="4"
          >
            <v-card
              min-width="300px" 
              min-height="300px"
              :img="vacation"

            >
              <v-container>
                <v-card>
                  <v-card-title>
                    Zahtevi za odmor ili odsustvo
                  </v-card-title>
                </v-card>
              </v-container>
            </v-card>
          </v-col>
          <v-col
            cols="4"
          >
            <v-card 
              min-width="300px" 
              min-height="300px"
              :img="profile"
            >
              <v-container>
                <v-card>
                  <v-card-title>
                    Profil
                  </v-card-title>
                </v-card>
              </v-container>
            </v-card>
          </v-col>
          <v-col
            cols="4"
          >
            <v-card 
              min-width="300px" 
              min-height="300px"
              :img="recepti"
            >
              <v-container>
                <v-card>
                  <v-card-title>
                    Overavanje recepata
                  </v-card-title>
                </v-card>
              </v-container>
            </v-card>
          </v-col>
        </v-row>
      </v-card>

      <v-btn @click="logoutFunc()" dark medium left class="blue" slot="action">Odjavi se</v-btn>
    </v-container>
</div>
</template>

<script>
// @ is an alias to /src
import router from "../router/index.js"
import axios from 'axios';


export default {
  name: 'HomeMed',
  data:function(){
    return {
      patients:require("../assets/patients.jpg"),
      kalendar:require("../assets/kalendar.jpg"),
      profile:require("../assets/profile.jpg"),
      recepti:require("../assets/recepti.jpg"),
      vacation:require("../assets/vacation.jpg"),
    }
  },
  mounted () {
      axios
      .get('api/ulogovan')
      .then(response => {
          this.ulogovan = response.data;
      })
      .catch(function (error) { console.log(error); router.push("/loginPage"); });
  },
  methods: {
    logoutFunc: function() {
        axios
        .get('api/logout')
        .then(() => {
            router.push("/loginPage");
        })
        .catch(function (error) { console.log(error); });
    }
  }
}
</script>

<style>
  .home{
    margin-top: 100px;
  }
  .main-card{
    padding: 20px;
  }
</style>
