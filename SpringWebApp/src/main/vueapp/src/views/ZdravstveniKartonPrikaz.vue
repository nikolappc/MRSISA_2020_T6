<template>
  <div class="zdravstveniKartonPrikaz">
    <v-container>
			<h2>Zdravstveni karton</h2>
			<h4>{{ ulogovan.ime }} {{ ulogovan.prezime }}</h4>
      <v-simple-table>
        <tbody>
          <tr>
            <td>Visina:</td>
            <td>{{ karton.visina }} m</td>
          </tr>
          <tr>
            <td>Tezina:</td>
            <td>{{ karton.tezina }} kg</td>
          </tr>
          <tr>
            <td>Krvna grupa:</td>
            <td>{{ karton.krvnaGrupa }}</td>
        </tbody>
      </v-simple-table>
      
    </v-container>
  </div>
</template>

<script>
import axios from 'axios';
import moment from 'moment';
import router from "../router/index.js"

export default {
  name: 'ZdravstveniKartonPrikaz',
  data: () => ({
    ulogovan : {},
    karton : {},
  }),
  mounted () {
      axios
      .get('api/ulogovan')
      .then(response => {
          this.ulogovan = response.data;
          axios
          .get('api/pacijent/karton/' + this.ulogovan.id)
          .then(response => {
              this.karton = response.data;
          });
      })
      .catch(function (error) { console.log(error); router.push("/"); });
	},
	
}
</script>