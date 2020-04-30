<template>
  <div class="home">
    <v-container>
      <h1>Lekar</h1>
      <v-btn @click="logoutFunc()" dark medium left class="blue" slot="action">Odjavi se</v-btn>
    </v-container>
  </div>
</template>

<script>
// @ is an alias to /src
import axios from 'axios';
import router from "../router/index.js"

export default {
  name: 'HomeLekar',
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
