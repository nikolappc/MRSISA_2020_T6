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
      <v-row>
        <v-col
          lg="4"
          md="6"
          sm="12"
        >
          <v-container>
            <v-card
                  color="primary"
                >
              <v-container>
                  <v-col
                    cols="12"
                  >
                    <v-card>
                      <v-card-title
                      >
                        Zahtevi za pregled
                      </v-card-title>
                    </v-card>
                  </v-col>
                  <v-col
                    cols="12"
                  >
                    <v-card>
                      <v-container>
                        Trenutno nema zahteva za pregled.
                      </v-container>
                    </v-card>
                  </v-col>
                  <v-col
                    cols="12"
                    v-for="idPregleda in pregledi"
                    :key="idPregleda"
                  >
                    <PregledConf :idPregleda="idPregleda">
                    </PregledConf>
                  </v-col>
              </v-container>
            </v-card>
          </v-container>
        </v-col>

        <v-col
          lg="4"
          md="6"
          sm="12"
        >
          <v-container>
            <v-card
                  color="primary"
                >
              <v-container>
                    
                    <v-col
                      cols="12"
                    >
                      <v-card>
                        <v-card-title
                        >
                          Zahtevi za odsustvo
                        </v-card-title>
                      </v-card>
                    </v-col>
                    <v-col
                      cols="12"
                      v-if="odsustvo"
                    >
                    </v-col>
                    <v-col
                      cols="12"
                      v-for="odsustvo in odsustva"
                      :key="odsustvo.odmor.id"
                    >
                      <OdmorCard :zahtev="odsustvo.odmor" :osoba="odsustvo.mo" v-on:zatvori="zatvori">
                      </OdmorCard>
                    </v-col>
                </v-container>
              </v-card>
          </v-container>

        </v-col>
      
      <v-row>
    </v-container>

  </div>




</template>
<script>
import axios from 'axios';
//import router from "../router/index.js"
import LinkCard from "../components/LinkCard.vue";
import PregledConf from "../components/PregledConfirmation.vue"
import OdmorCard from "../components/OdmorCard.vue"
export default {
  name: 'HomeAdminKlinike',
  components:{
    LinkCard,
    PregledConf,
    OdmorCard

  },
   data: function() { return {
      lekari:require("../assets/lekar.jpg"),
      sale:require("../assets/sale.jpg"),
      tipPregleda:require("../assets/tipPregleda.jpg"),
      dodavanje:require("../assets/dodavanje.jpg"),
      pregledi: [],
      odsustva: [],
    }
  },
  mounted () {
      axios
      .get('api/ulogovan')
      .then(response => {
          this.ulogovan = response.data;
      })
      .catch(function (error) { console.log(error); //router.push("/loginPage"); 
      });

      axios
      .get('adminKlinike/pregled')
      .then(response => {
          this.pregledi = response.data;
      })
      .catch(function (error) { console.log(error); //router.push("/loginPage"); 
      });

      axios
      .get('odmor')
      .then(response => {
          this.odsustva = response.data;
      })
      .catch(function (error) { console.log(error); //router.push("/loginPage"); 
      });


  },
  methods:{
    zatvori: function(id){
      console.log(id);
      console.log(this.odsustva);
      this.odsustva = this.odsustva.filter(odsustvo => odsustvo.odmor.id !== id);
    }
  }
  
  
}
</script>

<style>
</style>
