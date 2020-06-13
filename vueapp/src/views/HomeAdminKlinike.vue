<template>
  <div class="home">
    <v-container>
      <h1>Administrator klinike</h1>
      <v-card class="main-card">
		
        <v-row
          justify="center"
          align-content="stretch"
          align="stretch"
        >
          <v-col
            lg="6"
            md="6"
            sm="6"
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
            md="6"
            sm="6"
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
            md="6"
            sm="6"
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
            md="6"
            sm="6"
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
          <v-col
            lg="6"
            md="6"
            sm="6"
            xs="12"
          >
            <router-link to="/izvestaj">
              <LinkCard
                :img="dodavanje"
                title="Izvestaj poslovanja"
              >
              </LinkCard>
            </router-link>
          </v-col>
        </v-row>
      </v-card>
      <v-container>

        <v-divider>
  
        </v-divider>
      </v-container>
      <v-row>
        <v-col
          lg="4"
          md="4"
          sm="4"
          xs="12"
        >
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
                        <v-icon>
                          mdi-account-tie
                        </v-icon>
                        Zahtevi za pregled
                      </v-card-title>
                    </v-card>
                  </v-col>
                  <v-col
                    cols="12"
                    v-if="pregledi.length == 0"
                  >
                    <v-card>
                      <v-container>
                        Trenutno nema zahteva za pregled.
                      </v-container>
                    </v-card>
                  </v-col>
                  <v-col
                    v-else
                    cols="12"
                    v-for="idPregleda in pregledi"
                    :key="idPregleda"
                  >
                    <PosetaConf :poseta="idPregleda" :pregled="true">
                    </PosetaConf>
                  </v-col>
              </v-container>
            </v-card>
        </v-col>
        <v-col
          lg="4"
          md="4"
          sm="4"
          xs="12"
        >
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
                      <v-icon>
                        mdi-doctor
                      </v-icon>
                        Zahtevi za operaciju
                      </v-card-title>
                    </v-card>
                  </v-col>
                  <v-col
                    cols="12"
                    v-if="operacije.length == 0"
                  >
                    <v-card>
                      <v-container>
                        Trenutno nema zahteva za operaciju.
                      </v-container>
                    </v-card>
                  </v-col>
                  <v-col
                    cols="12"
                    v-for="o in operacije"
                    :key="o"
                    v-else
                  >
                    <PosetaConf :poseta="o" :pregled="false">
                    </PosetaConf>
                  </v-col>
              </v-container>
            </v-card>
        </v-col>
        <v-col
          lg="4"
          md="4"
          sm="4"
          xs="12"
        >
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
                        <v-icon>
                          mdi-calendar
                        </v-icon>
                          Zahtevi za odsustvo
                        </v-card-title>
                      </v-card>
                    </v-col>
                    <v-col
                      cols="12"
                      v-if="odsustva.length == 0"
                    >
                      <v-card>
                        <v-container>
                          Trenutno nema zahteva za odsustvo.
                        </v-container>
                      </v-card>
                    </v-col>
                    <v-col
                      v-else
                      cols="12"
                      v-for="odsustvo in odsustva"
                      :key="odsustvo.odmor.id"
                    >
                      <OdmorCard :zahtev="odsustvo.odmor" :osoba="odsustvo.mo" v-on:zatvori="zatvori">
                      </OdmorCard>
                    </v-col>
                </v-container>
              </v-card>

        </v-col>
      
      </v-row>
    </v-container>

  </div>




</template>
<script>
import axios from 'axios';
//import router from "../router/index.js"
import LinkCard from "../components/LinkCard.vue";
import PosetaConf from "../components/PosetaConfirmation.vue"
import OdmorCard from "../components/OdmorCard.vue"

export default {
  name: 'HomeAdminKlinike',
  components:{
    LinkCard,
    PosetaConf,
    OdmorCard,
  },
   data: function() { return {
      lekari:require("../assets/lekar.jpg"),
      sale:require("../assets/sale.jpg"),
      tipPregleda:require("../assets/tipPregleda.jpg"),
      dodavanje:require("../assets/dodavanje.jpg"),
      pregledi: [],
      operacije: [],
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
      .get('adminKlinike/operacija')
      .then(response => {
          this.operacije = response.data;
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
    },
    
  }
  
  
}
</script>

<style>
</style>
