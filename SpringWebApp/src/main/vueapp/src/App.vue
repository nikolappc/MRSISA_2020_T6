<template>
  <v-app>
    <v-app-bar v-if="$route.path != '/login'"
      app
      color="primary"
      dark
      :img="header"
    >
      Klinički centar
      <v-spacer></v-spacer>

      <router-link  to="/"><v-btn icon><v-icon color="white">mdi-home</v-icon></v-btn></router-link>
      <router-link  to="/login"><v-btn icon><v-icon color="white">mdi-account</v-icon></v-btn></router-link>
    </v-app-bar>  
    <div style="height: 56px" v-if="$route.path != '/login'"></div>
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
