<template>
  <div class="zdravstveniKartonPrikaz">
    <v-container>
			<v-card><h2 style="text-align:center">Zdravstveni karton</h2>
			<h4 style="text-align:center">{{ karton.email }}, {{ karton.ime }} {{ karton.prezime }}</h4></v-card>
      <v-card><v-simple-table>
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
      .get('api/pacijent/karton/' + this.$route.params.id)
      .then(response => {
          this.karton = response.data;
      })
		.catch(() => { this.$store.commit("setSnackbar", {text:"Pacijent još uvek nema svoj zdravstveni karton.", color: "info"}); router.go(-1); });
},
	
}
</script>