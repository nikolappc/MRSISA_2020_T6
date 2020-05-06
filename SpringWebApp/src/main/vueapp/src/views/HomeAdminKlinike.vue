<template>
  <div class="home">
    <v-container>
      <h1>Administrator klinike</h1>
      <v-col
      >
        <v-container>
          <v-card class="main-card">
    
            <v-row
              justify="center"
              align-content="stretch"
              align="stretch"
            >
              <v-col
                lg="6"
                md="12"
                sm="12"
                xs="12"
              >
              <router-link to="/lekari">
                <LinkCard
                  :img="lekari"
                  title="Lista lekara"
                >
                </LinkCard>
              </router-link>
              </v-col>
              <v-col
                lg="6"
                md="12"
                sm="12"
                xs="12"
              >
                <router-link to="/sale">
                  <LinkCard
                    :img="sale"
                    title="Lista sala"
                  >
                  </LinkCard>
                </router-link>
              </v-col>
              <v-col
                lg="6"
                md="12"
                sm="12"
                xs="12"
              >
                <router-link to="/tipoviPoseta">
                  <LinkCard
                    :img="tipPregleda"
                    title="Lista tipova pregleda"
                  >
                  </LinkCard>
                </router-link>
              </v-col>
              <v-col
                lg="6"
                md="12"
                sm="12"
                xs="12"
              >
                <router-link to="/DodavanjeTermina">
                  <LinkCard
                    :img="dodavanje"
                    title="Dodavanje slobodnih termina"
                  >
                  </LinkCard>
                </router-link>
              </v-col>
            </v-row>
          </v-card>
        </v-container>
      </v-col>

    </v-container>

  </div>




</template>

<script>
import axios from 'axios';
//import router from "../router/index.js"
import LinkCard from "../components/LinkCard.vue";
export default {
  name: 'HomeAdminKlinike',
  components:{
    LinkCard
  },
   data: () => ({
      lekari:require("../assets/lekar.jpg"),
      sale:require("../assets/sale.jpg"),
      tipPregleda:require("../assets/tipPregleda.jpg"),
      dodavanje:require("../assets/dodavanje.jpg")
  }),
  mounted () {
      axios
      .get('api/ulogovan')
      .then(response => {
          this.ulogovan = response.data;
      })
      .catch(function (error) { console.log(error); //router.push("/loginPage"); 
      });
  },
  
  
}
</script>

<style>
</style>
