<template>
  <div class="home">
    <v-container>
      <v-container>
        <label class="display-1">
          Lekar
        </label>
        <br>
        <label class="subtitle-1">
  
          Dobrodošli, {{ulogovan.ime}}
        </label>
      </v-container>
      <v-card class="main-card">
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
                      <v-icon v-if="kalendar.selected.tip=='operacija'">
                          mdi-box-cutter
                      </v-icon>
                      <v-icon
                        v-else
                      >
                          mdi-doctor
                      </v-icon>
                      {{kalendar.selected.name}}:{{kalendar.selected.tip}}
                    </v-col>
                  </v-row>
                </v-toolbar>
                <v-container>
                    <v-simple-table>
                        <tbody>
                            <tr> 
                                <td>
                                  Vreme početka                                        
                                </td>
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
                          <tr> 
                            <td>
                              Pacijent
                            </td>
                            <td>
                              {{kalendar.selected.pacijent}}
                            </td>
                        </tr>
                        
                        </tbody>
                    </v-simple-table>
                    <v-card
                      outlined="true"
                    >
                      {{kalendar.selected.opis}}
                    </v-card>
                    
                    <v-btn
                      v-if="kalendar.selected.odradjen"
                      @click="toIzmena(kalendar.selected.id)"
                      :disabled="!kalendar.selected.odradjen"
                    >
                      Izmena pregleda
                    </v-btn>
                    <v-btn
                      v-if="checkStart(kalendar.selected)"
                      color="success"
                      class="mr-4"
                      @click="pocniPregled(kalendar.selected.id,kalendar.selected.tip)"
                    >
                      Započni pregled
                    </v-btn>
                    <v-btn
                      v-if="checkCancel(kalendar.selected)"
                      color="error"
                      class="mr-4"
                      :disabled="kalendar.selected.odradjen"
                      @click="otkaziPregled(kalendar.selected.id,kalendar.selected.tip)"
                    >
                      Otkaži pregled
                    </v-btn>
                </v-container>
              </v-card>
            </v-menu>
          </template>
        </RadniKalendar>
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
                :img="zakazi"
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
            <router-link to="/istorijaPoseta">
              <LinkCard
                :img="istorija"
                title="Istorija poseta"
              >
              </LinkCard>
            </router-link>
          </v-col>
        </v-row>
      </v-card>
      <v-dialog
      v-model="dialog"
      max-width="290"
      >
      <v-card>
        <v-text-field
          label="Nova sifra"
          v-model="ulogovan.password"
        >
        </v-text-field>
        <v-btn @click="promeniSifru">
          Promeni sifru
        </v-btn>
      </v-card>
      </v-dialog>
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
      dialog: false,
      termini: {},
      events:[],
      pacijent:require("../assets/pacijent.jpg"),
      pregled:require("../assets/pregled.jpg"),
      istorija:require("../assets/lekar.jpg"),
      zakazi:require("../assets/zakazani.jpg"),
  }),
  computed:{
    ulogovan(){
      return this.$store.state.ulogovan;
    }
  },
  mounted () {
    if(this.ulogovan.prviPut == true)
      this.dialog = true;
    
    axios
            .get('lekar/poseta/'+this.$store.state.ulogovan.id)
            .then((res) => {
              console.log(res.data);
              
                this.preurediEvent(res.data);
            })
            .catch(error => { 
              console.log(error);
              
              this.$store.commit("setSnackbar", {text:"Whoops", color: "error"});
            });
  },
  methods: {
    promeniSifru: function(){
      this.ulogovan.prviPut = false;
      axios
        .put('api/'+this.ulogovan.id,this.ulogovan)
        .then(response => {
            this.$store.commit("setUlogovan", response.data);
            this.$store.commit("setSnackbar", {text:"Uspešno ste izmenili profil", color: "success"});
            this.dialog = false;
        })
        .catch(function (error) { console.log(error); });
    },
    checkStart: function(pregled){
        if(pregled.odradjen){
            return false;
        }
        return this.checkDate(pregled);
      },
    checkDate(pregled){
      let trenutno = new Date(Date.now() + 5 * 60000);
      if(pregled.startDate <= trenutno&&trenutno<=pregled.endDate){
          return true;
      }
      return false;
    },
    otkaziPregled(id,tip){
      axios
        .delete('poseta/otkazi/'+id+"/"+tip)
        .then((response) => {
            console.log(response);
            this.$store.commit("setSnackbar", {text:"Uspesno ste otkazali posetu", color: "success"});
            this.$router.go();
        })
        .catch(function (error) { console.log(error); });
    },
    checkCancel(pregled){
      let trenutno = new Date(Date.now() + 60 * 60000);//60 minuta od sad
      if(trenutno <= pregled.startDate){
          return true;
      }
      return false;
    },
    toIzmena(id){
      this.$router.push('/izmenaPregleda/' + id);
    },
    pocniPregled(id,tip){
      if(tip=="pregled")
        this.$router.push('/pregled/' + id);
      else{
        this.$router.push('/operacija/' + id);
      }
    },
    preurediEvent(termini){
        for(let termin of termini){
            let event = {}; 
            event.id = termin.id;
            event.startDate = new Date(termin.termin.pocetak);
            event.endDate = new Date(termin.termin.kraj);
            event.start = this.formatirajDatum(event.startDate);
            event.end = this.formatirajDatum(event.endDate);
            event.color = termin.tipPregleda == "pregled"?"blue":"lek";
            event.tip = termin.tipPregleda;
            event.opis = termin.opis;
            event.name = termin.tip.naziv;
            event.odradjen = termin.odradjen;
            event.pacijent = termin.pacijent.ime+" "+termin.pacijent.prezime;
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
