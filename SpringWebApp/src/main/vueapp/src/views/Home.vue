<template>
  <div class="home">
    <v-container>
      <h1>Klinicki centar</h1>
      
	</v-container>
  </div>
</template>

<script>
// @ is an alias to /src
import axios from 'axios';
import router from "../router/index.js"

export default {
  name: 'Home',
   data: () => ({
    ulogovan : {},
  }),
  mounted () {
      axios
      .get('api/ulogovan')
      .then(response => {
          this.ulogovan = response.data;
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
      })
      .catch(function (error) { console.log(error); router.push("/loginPage"); });
  }
  
}
</script>

<style>
</style>
