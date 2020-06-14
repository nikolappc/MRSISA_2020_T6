<template>
    <v-container>
        <v-card>
            <v-card-title>
                Izmena pregleda
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
            </v-row>
            <Pretrazivac
                @odabrani="promenaDijagnoza"
                id="sifraDijagnoze"
                atribut="nazivDijagnoze"
                color="dijagnoza"
                :elementi="dijagnoze"
                pozadina="karton"
                :odabrani="pregled.dijagnoze"
                >
            </Pretrazivac>
            <v-row >
                <v-spacer></v-spacer>
                <v-col cols="4">
                    <v-btn
                    @click="izmeni"
                    color="success">
                        Izmeni opis pregleda
                    </v-btn>
                </v-col>
            </v-row>
        </v-card>
    </v-container>
  </template>
  
  <script>
  import axios from "axios";
  import Pretrazivac from "../../components/Pretrazivac.vue";
  export default {
  name: 'IzmenaPregleda',
    data: () => ({
      pregled: {},
      dijagnoze:[],
      dialog: false,
      lekar: {},
      pacijent: {},
      zakazaoNovi:false
    }),
    components:{
      Pretrazivac,
    },
    mounted () {
          this.lekar = this.$store.state.ulogovan;
         
          axios.get("/dijagnoza")
              .then(res=>{
                  this.dijagnoze = res.data;
              }).catch(error=>{
                  console.log(error);
                  
              });
          axios
              .get('/poseta/' + this.$route.params.id)
              .then(response => {
                  this.pregled = response.data;
                  console.log(this.pregled);
              })    
              .catch((err) => { 
                  console.log(err);
  
              });
    },
    methods:{
      promenaDijagnoza(dijagnoze){
          this.pregled.dijagnoze = dijagnoze;
      },
      izmeni(){
          axios.put("/poseta/pregled/"+this.pregled.id, this.pregled)
              .then(res=>{
                  console.log(res);
                  this.$store.commit("setSnackbar", {text:"Pregled je uspešno izmenjen.", color: "success"});
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
  