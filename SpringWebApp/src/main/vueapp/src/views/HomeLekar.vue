<template>
  <div class="home">
    <v-container>
      <h1>Lekar</h1>
      <v-btn :to="{path: 'listaPacijenata'}" dark medium left class="blue" slot="action">Lista pacijenata</v-btn>
    </v-container>
  </div>
</template>

<script>
// @ is an alias to /src
// import axios from 'axios';
// import router from "../router/index.js"

export default {
  name: 'HomeLekar',
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
