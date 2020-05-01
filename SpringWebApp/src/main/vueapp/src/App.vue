<template>
  <v-app>
     <Header></Header>
    <div style="height: 56px" v-if="$route.path != '/login'"></div>
    <router-view/>
  </v-app>
</template>

<script>
import Header from "./views/Header.vue";
export default {
  name: 'App',
  components:{
    Header
  },
  
  data: () => ({
    header:require("./assets/header.jpg")
  }),
};
</script>
