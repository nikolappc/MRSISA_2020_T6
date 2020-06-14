<template>
  <div class="home">
    <v-container>
      <label class="display-1">
        Medicinska sestra
      </label>
      <br>
      <label class="subtitle-1">

        Dobrodošli, {{ulogovan.ime}}
      </label>
    </v-container>
    <v-container>
      <v-row>
        <v-col
          cols="12"
          lg="8"
        >
          <v-card>
            <RadniKalendar
              :events="events"
              @selected="selektovan"
            >
              <template slot-scope="kalendar">
                <v-menu
                  v-model="kalendar.opened"
                  :close-on-content-click="false"
                  :activator="kalendar.activator"
                  offset-x
                  v-if="selected!=null"
                >
                  <v-card>
                    <v-toolbar color="blue">
                      <v-row>
                        <v-col>
                          {{selected.tip.naziv}}
                        </v-col>
                        <v-col>
                          <v-icon>
                            mdi-doctor
                          </v-icon>
                          {{selected.lekar}}
                        </v-col>
                      </v-row>
                    </v-toolbar>
                    <v-container>
                        {{selected.opis}}
                        <v-row>
                            <v-btn :disabled="selected.recepti.length!=0&&selected.odradjen==true" collor="success" :to="{ name: 'OveriRecepte', params: { pregled: selected }}" class="ml-auto">
                              Overi recepte
                            </v-btn>
                        </v-row>
                    </v-container>
                  </v-card>
                </v-menu>
              </template>
            </RadniKalendar>
          </v-card>
        </v-col>
        <v-col
          cols="12"
          lg="4"
        >
          <v-card class="main-card">
            Linkovi
            <v-row
              justify="center"
              align-content="stretch"
              align="stretch"
            >
              <v-col
                v-for="(link, i) in linkovi"
                :key="i"
                cols="12"
                lg="12"
                md="6"
                sm="6"
              >
                <router-link :to="link.link">
                  <LinkCard
                    :img="link.img"
                    :title="link.title"
                  >
                  </LinkCard>
                </router-link>
              </v-col>
            </v-row>
          </v-card>
        </v-col>
      </v-row>
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
// @ is an alias to /src
// import router from "../router/index.js"
// import axios from 'axios';

import LinkCard from "../components/LinkCard.vue";
import RadniKalendar from "./RadniKalendar.vue";
const axios = require("axios");
export default {
  name: 'HomeMed',
  components:{
    LinkCard,
    RadniKalendar
  },
  data:function(){
    return {
      dialog: false,
      patients:require("../assets/patients.jpg"),
      kalendar:require("../assets/kalendar.jpg"),
      profile:require("../assets/profile.jpg"),
      recepti:require("../assets/recepti.jpg"),
      vacation:require("../assets/vacation.jpg"),
      linkovi:[
        {
          title:"Pregled pacijenata",
          img:require("../assets/patients.jpg"),
          link:"/listaPacijenata"
        },
        {
          title:"Zahtevi za odmor ili odsustvo",
          img:require("../assets/vacation.jpg"),
          link:"/zakaziOdmor"
        },
        {
          title:"Profil",
          img:require("../assets/profile.jpg"),
          link:"/profilPacijenta"
        },
        {
          title:"Overavanje recepata",
          img:require("../assets/recepti.jpg"),
          link:"/overavanjeRecepata"
        },
      ],
      pregledi : [],
      events:[],
      selected:null

    }
  },
  computed:{
    ulogovan(){
      return this.$store.state.ulogovan;
    }
  },
  mounted () {
    if(this.ulogovan.prviPut == true)
      this.dialog = true;

      axios.get("/poseta/pregledi")
        .then(res=>{
            this.pregledi = res.data;
            console.log(res.data);
            for (const p of this.pregledi) {
              let start = new Date(p.termin.pocetak);
              let end = new Date(p.termin.kraj);
              console.log(start);
              console.log(end);
              this.events.push({
                id:p.id,
                name : p.tip.naziv,
                start : this.formatDate(start),
                end : this.formatDate(end),
                color:"blue"
              });
            }
        })
        .catch(error=>{
          console.log(error);
        })
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
    formatDate (a) {
      return `${a.getFullYear()}-${a.getMonth() + 1}-${a.getDate()} ${a.getHours()}:${a.getMinutes()}`;
    },
    selektovan(sel){
      this.selected = this.pregledi.find(p=>p.id==sel.id);
    }
  }
}
</script>

<style>
  .home{
    margin-top: 100px;
  }
  .main-card{
    padding: 20px;
  }
</style>
