<template>
  <v-app>
    <v-app-bar

      color="primary"
      dark
      max-height="50"
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
    //
  }),
};
</script>
