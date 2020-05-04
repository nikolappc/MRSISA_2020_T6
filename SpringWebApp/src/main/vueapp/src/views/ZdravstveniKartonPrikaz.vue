<template>
  <div class="zdravstveniKartonPrikaz">
    <v-container>
			<h2 style="text-align:center">Zdravstveni karton</h2>
			<h4 style="text-align:center">{{ ulogovani.ime }} {{ ulogovani.prezime }}</h4>
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
      </v-simple-table>
      
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
      .get('api/pacijent/karton/' + this.ulogovani.id)
      .then(response => {
          this.karton = response.data;
      })
      .catch(function (error) { console.log(error); router.push("/"); alert("Još uvek nemate svoj zdravstveni karton.");});
	},
	
}
</script>