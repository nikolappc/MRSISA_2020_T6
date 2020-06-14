<template>
  <div class="zdravstveniKartonPrikaz" style="width:100%;">
    <v-container style="width:100%;">
			<v-card style="width:100%;"><v-card-title>Zdravstveni karton</v-card-title>
			<v-card-subtitle>{{ karton.email }}, {{ karton.ime }} {{ karton.prezime }}</v-card-subtitle>
      <v-simple-table>
        <tbody>
          <tr>
            <td>Visina:</td>
            <td>{{ karton.visina }} m</td>
          </tr>
          <tr>
            <td>Težina:</td>
            <td>{{ karton.tezina }} kg</td>
          </tr>
          <tr>
            <td>Krvna grupa:</td>
            <td>{{ karton.krvnaGrupa }}</td>
          </tr>
        </tbody>
      </v-simple-table></v-card>
      
    </v-container>
  </div>
</template>

<script>
import axios from 'axios';
import router from "../router/index.js"

export default {
  name: 'ZdravstveniKartonPrikaz',
  data: () => ({
    ulogovani : {},
    karton : {},
  }),
  
  mounted () {
	this.ulogovani = this.$store.state.ulogovan;
	if (this.ulogovani == "") {
    router.push("/");
  }
  
  axios
      .post('api/pacijent/provera/' + this.$route.params.id)
      .then(response => {
        console.log(response);
        if(!response.data.provera){
          this.$store.commit("setSnackbar", {text:"Nemate pristup kartonu.", color: "error"});
          router.push("/listaPacijenata");
        }
      })
      .catch(
        err => {
          console.log(err);
          
        }
      );  

	axios
      .get('api/pacijent/karton/' + this.$route.params.id)
      .then(response => {
          this.karton = response.data;
      })
		.catch(() => { this.$store.commit("setSnackbar", {text:"Pacijent još uvek nema svoj zdravstveni karton.", color: "info"}); router.go(-1); });
},
	
}
</script>