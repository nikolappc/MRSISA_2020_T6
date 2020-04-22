<template>
  <div class="zdravstveniKartonPrikaz">
    <v-container>
			<h2>Zdravstveni karton</h2>
			<h4>{{ ulogovan.ime }} {{ ulogovan.prezime }}</h4>
      <v-simple-table>
        <thead>
					<th>Id pregleda</th>
					<th>Opis</th>
					<th>Sala</th>
					<th>Pocetak</th>
					<th>Kraj</th>
					<th>Tip</th>
        </thead>
        <tbody>
					<tr v-for="p in this.pregledi" v-bind:key="p.id">
							<td>{{ p.id }}</td>
							<td>{{ p.opis }}</td>
							<td>{{ p.sala.id }}</td>
							<td>{{ formatDate(p.termin.pocetak) }}</td>
							<td>{{ formatDate(p.termin.kraj) }}</td>
							<td>{{ p.tip.tip }}</td>
          </tr>
        </tbody>
      </v-simple-table>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios';
import moment from 'moment';
export default {
  name: 'ProfilPacijenta',
  data: () => ({
    ulogovan : {},
    pregledi : [],
  }),
  mounted () {
      axios
      .get('api/pacijent/ulogovan')
      .then(response => {
          this.ulogovan = response.data;
          axios
          .get('api/pacijent/listaPregleda/' + this.ulogovan.id)
          .then(response => {
              this.pregledi = response.data;
          })
      })
      .catch(function (error) { console.log(error); });
	},
	methods: {
    formatDate(value) {
			return moment(String(value)).format('YYYY MM DD HH:mm');
		}
  },
}
</script>