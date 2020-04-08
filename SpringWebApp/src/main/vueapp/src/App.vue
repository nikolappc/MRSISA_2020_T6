<template>
  <v-app>
    <v-app-bar
      app
      color="primary"
      dark
    >
      Klinički centar
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
