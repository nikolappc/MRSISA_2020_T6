<template>
  <div class="home">
    <v-container>
      <h1>Pacijent</h1>
      <v-btn :to="{path: 'profilPacijenta'}" dark medium left class="blue" slot="action">Profil pacijenta</v-btn>
      <v-btn :to="{path: 'zdravstveniKartonPrikaz'}" dark medium left class="blue" slot="action">Zdravstveni karton</v-btn>
    </v-container>
  </div>
</template>

<script>
// @ is an alias to /src
//     import axios from 'axios';
// import router from "../router/index.js"

export default {
  name: 'HomePacijent',
   data: () => ({
  }),
  mounted () {
  },
        methods: {

  }
  
}
</script>

<style>
</style>
