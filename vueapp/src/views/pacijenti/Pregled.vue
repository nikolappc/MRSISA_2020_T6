<template>
  <v-container>
      <v-card>
          <v-card-title>
              Pregled pacijenta
              <Timer />
          </v-card-title>
          <v-row class="mx-4">
              <v-col cols="12" md="6">
                    <v-textarea
                    ma
                    outlined
                    v-model="pregled.opis"
                    label="Opis pregleda"
                    ></v-textarea>
              </v-col>
              <v-spacer></v-spacer>
              <v-chip
                class="ma-2"
                color="primary"
                text-color="white"
                label
                >
                <v-avatar left>
                    <v-icon>mdi-clock</v-icon>
                </v-avatar>
                {{Math.floor(time/60)}}:{{time%60}}
              </v-chip>
          </v-row>
          <v-row>
              <v-container class="pa-5">
                  <v-tabs
                      background-color="primary"
                  >
                      <v-tab>
                        Dijagnoze
                      </v-tab>
                      <v-tab>
                        Lekovi
                      </v-tab>
                      <v-tab-item>
                          <v-container>
                              <v-card flat>
                                  <Pretrazivac
                                      @odabrani="promenaDijagnoza"
                                      id="sifraDijagnoze"
                                      atribut="nazivDijagnoze"
                                      color="dijagnoza"
                                      :elementi="dijagnoze"
                                  >
                                  </Pretrazivac>
                              </v-card>
                          </v-container>
                      </v-tab-item>
  
                      <v-tab-item>
                          <v-container>
                              <v-card flat>
                                  <Pretrazivac
                                      @odabrani="promenaLekova"
                                      id="sifraLeka"
                                      atribut="nazivLeka"
                                      color="lek"
                                      :elementi="lekovi"
                                  >
                                  </Pretrazivac>
                              </v-card>
                          </v-container>
                      </v-tab-item>
                  </v-tabs>
              </v-container>
          </v-row>
          <v-row >
              <v-spacer></v-spacer>
              <v-col cols="6">
                  <v-btn class="mx-10"
                    color="primary"
                    @click="dialog = true"
                  >
                      Zakaži pregled
                  </v-btn>
                  <v-btn
                  @click="end"
                  color="success">
                      Završi pregled
                  </v-btn>
              </v-col>
          </v-row>
      </v-card>
      
      <v-dialog
            v-model="dialog"
            max-width="500"
            >
                <v-card>
                    <v-card-title class="headline mx-4">Zakaži pregled/operaciju</v-card-title>

                        <NapraviPregled v-bind:pacijent="pacijent" v-bind:lekar="lekar" v-on:zatvori="dialog = false"/>

                    
                </v-card>
      </v-dialog>


  </v-container>
</template>

<script>
import axios from "axios";
import Pretrazivac from "../../components/Pretrazivac.vue";
import NapraviPregled from "./NapraviPregled.vue";
export default {
name: 'Pregled  ',
  data: () => ({
    pregled: {},
    lekovi:[],
    dijagnoze:[],
    time: 2000,
    dialog: false,
    lekar: {},
    pacijent: {}
  }),
  components:{
    Pretrazivac,
    NapraviPregled
  },
  mounted () {
        this.lekar = this.$store.state.ulogovan;
        
        axios.get("/lek")
            .then(res=>{
                this.lekovi = res.data;
            }).catch(error=>{
                console.log(error);
                
            });
        axios.get("/dijagnoza")
            .then(res=>{
                this.dijagnoze = res.data;
            }).catch(error=>{
                console.log(error);
                
            });
        axios
            .get('poseta/' + this.$route.params.id)
            .then(response => {
                this.pregled = response.data;
                this.time = Math.floor((Date.parse(this.pregled.termin.kraj)- new Date())/1000);


                axios.get("/poseta/pacijent/"+this.pregled.id)
                    .then(res=>{
                        this.pacijent = res.data;
                    }).catch(error=>{
                        console.log(error);
                        
                    });
                
            })    
            .catch((err) => { 
                console.log(err);

            });
        
        this.countDownTimer();
  },
  methods:{
    
    countDownTimer: function(){
            if(this.time > 0) {
                setTimeout(() => {
                    this.time -= 1
                    this.countDownTimer()
                }, 1000)
            }
        },
    promenaLekova(lekovi){
        this.lekovi = lekovi;
    },
    promenaDijagnoza(dijagnoze){
        this.dijagnoze = dijagnoze;
    },
    end(){
        let recepti = [];
        for (const lek of this.lekovi) {
            recepti.push({
                overen:false,
                lek:lek
            });
        }
        this.pregled.recepti = recepti;
        this.pregled.lekovi = this.lekovi;
        axios.put("/poseta/pregled/"+this.pregled.id, this.pregled)
            .then(res=>{
                console.log(res);
                this.$router.push("/");
            })
            .catch(error=>{
                console.log(error);
                this.$store.commit("setSnackbar", {text:"Izvinjavamo se došlo je do greške.", color: "error"});
            })
    }

  }
}

</script>
