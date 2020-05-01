<template>
  <div class="home">
    <v-container>
      <h1>Klinicki centar</h1>
      
	</v-container>
  </div>
</template>

<script>
// @ is an alias to /src
// import axios from 'axios';
import router from "../router/index.js"

export default {
  name: 'Home',
  computed:{
    ulogovan(){return this.$store.state.ulogovan},
  },
  mounted () {
    if(this.ulogovan){

      if (this.ulogovan.tip == "PACIJENT") {
        router.push("/homePacijent");
      } else if (this.ulogovan.tip == "LEKAR") {
        router.push("/homeLekar");
      }
      else if (this.ulogovan.tip == "SESTRA") {
        router.push("/homeMed");
      }
      else if (this.ulogovan.tip == "ADMIN_K") {
        router.push("/homeAdminKlinike");
      }
      else {
        router.push("/homeAdminKC");
      }
    }else{
      this.$router.push("/loginPage");
    }
  }
  
}
</script>

<style>
</style>
