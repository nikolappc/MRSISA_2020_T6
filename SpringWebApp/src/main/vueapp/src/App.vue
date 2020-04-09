<template>
  <v-app>
    <v-app-bar

      color="primary"
      dark
      max-height="50"
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
