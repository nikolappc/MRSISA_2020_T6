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
                <Kalendar v-bind:events="termini"/>
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
import Kalendar from "./sala/Kalendar.vue";
  function preurediDatum(termini){
        for(let termin of termini){
            termin.start = formatirajDatum(termin.start);
            termin.end = formatirajDatum(termin.end);
        }
        return termini;
  }
  function formatirajDatum(datum){
      return datum.substring(0, 10) + " " + datum.substring(11,16); 
  }
import LinkCard from "../components/LinkCard.vue";

export default {

  


  name: 'HomeLekar',
  components:{
    LinkCard,
    Kalendar
  },
   data: () => ({
      termini: {},
      pacijent:require("../assets/pacijent.jpg"),
      pregled:require("../assets/pregled.jpg")
  }),
  mounted () {
    axios
            .get('lekar/pregledi/'+this.$store.state.ulogovan.id)
            .then((res) => {
                this.termini = preurediDatum(res.data);
            })
            .catch(() => { this.$store.commit("setSnackbar", {text:"Whops", color: "error"});

            });
  },
  methods: {
    
  }
  
}
</script>

<style>
</style>
