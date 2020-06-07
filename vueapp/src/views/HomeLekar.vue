<template>
  <div class="home">
    <v-container>
      <h1>Lekar</h1>
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
              <router-link to="/listaPacijenata">
                <LinkCard
                  :img="pacijent"
                  title="Lista pacijenata"
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
              <router-link to="/zakaziPregled">
                <LinkCard
                  :img="pregled"
                  title="Zakaži pregled"
                >
                </LinkCard>
              </router-link>
              </v-col>
              </v-row>
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
              <router-link to="/zakaziOdmor">
                <LinkCard
                  :img="odmor"
                  title="Zakaži godišnji odmor"
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
                <RadniKalendar
                  :events="events"
                >
                  <template slot-scope="kalendar">
                    <v-menu
                      v-model="kalendar.opened"
                      :close-on-content-click="false"
                      :activator="kalendar.activator"
                      offset-x
                      v-if="kalendar.selected!=null"
                    >
                      <v-card>
                        <v-toolbar :color="kalendar.selected.color">
                          <v-row>
                            <v-col>
                              {{kalendar.selected.name}}:{{kalendar.selected.tip}}
                            </v-col>
                          </v-row>
                        </v-toolbar>
                        <v-container>
                            <v-simple-table>
                                <tbody>
                                    <tr> 
                                        <td>
                                          Vreme početka                                        </td>
                                        <td>
                                          {{kalendar.selected.start}}
                                        </td>
                                    </tr>
                                    <tr> 
                                      <td>
                                        Trajanje
                                      </td>
                                      <td>
                                        {{(kalendar.selected.endDate - kalendar.selected.startDate)/1000/60}} min
                                      </td>
                                  </tr>
                                </tbody>
                            </v-simple-table>
                            <v-card
                              outlined="true"
                            >
                              {{kalendar.selected.opis}}
                            </v-card>
                        </v-container>
                      </v-card>
                    </v-menu>
                  </template>
                </RadniKalendar>
              </v-col>
            </v-row>
          </v-card>
        </v-container>
      </v-col>
    </v-container>
  </div>
</template>

<script>
import axios from "axios";
import RadniKalendar from "./RadniKalendar.vue";

// import Kalendar from "./sala/Kalendar.vue";
 
import LinkCard from "../components/LinkCard.vue";

export default {
  name: 'HomeLekar',
  components:{
    LinkCard,
    RadniKalendar
  },
   data: () => ({
      termini: {},
      events:[],
      pacijent:require("../assets/pacijent.jpg"),
      pregled:require("../assets/pregled.jpg"),
  }),
  mounted () {
    axios
            .get('lekar/poseta/'+this.$store.state.ulogovan.id)
            .then((res) => {
                this.preurediEvent(res.data);
            })
            .catch(error => { 
              console.log(error);
              
              this.$store.commit("setSnackbar", {text:"Whoops", color: "error"});
            });
  },
  methods: {
    preurediEvent(termini){
        for(let termin of termini){
            let event = {}; 
            event.startDate = new Date(termin.termin.pocetak);
            event.endDate = new Date(termin.termin.kraj);
            event.start = this.formatirajDatum(event.startDate);
            event.end = this.formatirajDatum(event.endDate);
            event.color = termin.kategorija == "pregled"?"blue":"lek";
            event.tip = termin.kategorija;
            event.opis = termin.opis;
            event.name = termin.tip.naziv;
            console.log(event);
            this.events.push(event);
        }
    },
    formatirajDatum (a) {
      return `${a.getFullYear()}-${a.getMonth() + 1}-${a.getDate()} ${a.getHours()}:${a.getMinutes()}`;
    }
  }
}

</script>

<style>
</style>
