<template>
  <v-app>
    <v-app-bar
      app
      color="primary"
      dark
      :img="header"
    >
      Klinički centar
      <v-spacer></v-spacer>

      <v-btn icon><router-link to="/"><v-icon color="white">mdi-home</v-icon></router-link></v-btn>
    </v-app-bar>  

    <router-view/>
  </v-app>


</template>

<script>

export default {
  name: 'App',

  data: () => ({
    header:require("./assets/header.jpg")
  }),
};
</script>
