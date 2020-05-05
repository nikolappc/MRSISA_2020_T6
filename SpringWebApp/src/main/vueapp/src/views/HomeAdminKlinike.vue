<template>
  <div class="home">
    <v-container>
      <h1>AdministratorKlinike</h1>
      <v-btn :to="{path: 'lekari'}" dark medium left class="blue" slot="action">Lista lekara</v-btn>
      <v-btn :to="{path: 'sale'}" dark medium left class="blue" slot="action">Lista sala</v-btn>
      <v-btn :to="{path: 'tipoviPregleda'}" dark medium left class="blue" slot="action">Lista tipovaPregleda</v-btn>
      <v-btn :to="{path: 'DodavanjeTermina'}" dark medium left class="blue" slot="action">Dodavanje slobodnih termina</v-btn>
      <v-btn @click="logoutFunc()" dark medium left class="blue" slot="action">Odjavi se</v-btn>
    </v-container>
  </div>
</template>

<script>
// @ is an alias to /src
import axios from 'axios';
import router from "../router/index.js"

export default {
  name: 'HomeAdminKlinike',
   data: () => ({
    ulogovan : {},
  }),
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
</style>
