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
      <v-card class="main-card">

        <v-row
          justify="center"
          align-content="stretch"
          align="stretch"
        >
          <v-col 
            v-for="(link, i) in linkovi"
            :key="i"
            lg="4"
            md="6"
            sm="12"
            xs="12"
          >
            <LinkCard
              :img="link.img"
              :title="link.title"
            >
            </LinkCard>
          </v-col>
        </v-row>
      </v-card>

    </v-container>
</div>
</template>

<script>
// @ is an alias to /src
// import router from "../router/index.js"
// import axios from 'axios';

import LinkCard from "../components/LinkCard.vue";
export default {
  name: 'HomeMed',
  components:{
    LinkCard
  },
  data:function(){
    return {
      patients:require("../assets/patients.jpg"),
      kalendar:require("../assets/kalendar.jpg"),
      profile:require("../assets/profile.jpg"),
      recepti:require("../assets/recepti.jpg"),
      vacation:require("../assets/vacation.jpg"),
      linkovi:[
        {
          title:"Pregled pacijenata",
          img:require("../assets/patients.jpg")
        },
        {
          title:"Radni kalendar",
          img:require("../assets/kalendar.jpg")
        },
        {
          title:"Zahtevi za odmor ili odsustvo",
          img:require("../assets/vacation.jpg")
        },
        {
          title:"Profil",
          img:require("../assets/profile.jpg")
        },
        {
          title:"Overavanje recepata",
          img:require("../assets/recepti.jpg")
        },
      ],
    }
  },
  computed:{
    ulogovan(){
      return this.$store.state.ulogovan;
    }
  },
  mounted () {
      
  },
  methods: {

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
