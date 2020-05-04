<template>
  <div class="home">
    <v-container>
      <h1 style="text-align:center">{{ ulogovani.email }}</h1>
       <v-card class="main-card">

        <v-row
          justify="center"
          align-content="stretch"
          align="stretch"
        >
          <v-col
            lg="4"
            md="12"
            sm="12"
            xs="12"
          >
          <router-link to="/profilPacijenta">
            <LinkCard
              :img="profile"
              title="Moj profil"
            >
            </LinkCard>
          </router-link>
          </v-col>
          <v-col
            lg="4"
            md="12"
            sm="12"
            xs="12"
          >
          <router-link to="/zdravstveniKartonPrikaz">
            <LinkCard
              :img="medicalrecord"
              title="Zdravstveni karton"
            >
            </LinkCard>
          </router-link>
          </v-col>
          <v-col
            lg="4"
            md="12"
            sm="12"
            xs="12"
          >
          <router-link to="/posjetePacijenta">
            <LinkCard
              :img="appointments"
              title="Istorija pregleda i operacija"
            >
            </LinkCard>
          </router-link>
          </v-col>
          <v-col
            lg="4"
            md="12"
            sm="12"
            xs="12"
          >
          <router-link to="/klinikeZaPacijenta">
            <LinkCard
              :img="klinike"
              title="Lista klinika"
            >
            </LinkCard>
          </router-link>
          </v-col>
          
          
        </v-row>
      </v-card>
    </v-container>
  </div>
</template>

<script>
// @ is an alias to /src
//     import axios from 'axios';
 import router from "../router/index.js"
import LinkCard from "../components/LinkCard.vue";

export default {
  name: 'HomePacijent',
   data: () => ({
	ulogovani : {},
	klinike:require("../assets/klinike.jpg"),
	profile:require("../assets/profile.png"),
	appointments:require("../assets/appointments.jpg"),
	medicalrecord:require("../assets/medicalrecord.jpg"),
  }),
  components:{
    LinkCard
  },
  mounted () {
	this.ulogovani = this.$store.state.ulogovan;
	if (this.ulogovani == "") {
		router.push("/");
	}
  },
	methods: {

  },
  
}
</script>

<style>
</style>
