<template>
  <div class="klinikeZaPacijenta" style="width:100%;">
    <v-container grid-list-md style="width:100%;">
    <v-card style="width:100%;">
			<v-card-title>Klinike</v-card-title>
	<v-card-text>
	<v-card-title>
		
		<v-spacer></v-spacer>
		<v-text-field
		v-model="search"
		append-icon="mdi-magnify"
		label="Pretraga"
		single-line
		hide-details
		></v-text-field>
	</v-card-title>
      <v-data-table
        :headers="headers"
        :search="search"
        :items="klinike"
        :items-per-page="10"
        class="elevation-1"
        @click:row="posaljiNaStranicuKlinike"
        :custom-sort="customSort">
      </v-data-table>
	</v-card-text></v-card>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios';
import router from "../router/index.js"

export default {
  name: 'KlinikeZaPacijenta',
  data: () => ({
    ulogovani : {},
    klinike : [],
    search : '',
    headers: [
        {
          text: 'Ime', 
          value: 'naziv', 
          sortable: true, 
        },
        {
          text: 'Tip', 
          value: 'tip', 
          sortable: true, 
        },
        {
          text: 'Opis', 
          value: 'opis', 
          sortable: true, 
        },
        {
          text: 'Adresa', 
          value: 'adresa.adresa', 
          sortable: true, 
        },
        {
          text: 'Grad', 
          value: 'adresa.grad', 
          sortable: true, 
        },
        {
          text: 'Država', 
          value: 'adresa.drzava', 
          sortable: true, 
        },
      ]
  }),
  mounted () {
	this.ulogovani = this.$store.state.ulogovan;
	if (this.ulogovani == "") {
		router.push("/");
	}
      axios
      .get('klinika/klinikeZaPacijenta')
      .then(response => {
          this.klinike = response.data;
      })
      .catch(function (error) { console.log(error); router.push("/"); });
	},
	methods: {
    customSort: function(items, index, isDesc) {
      items.sort((a, b) => {
				console.log(index[0]);
				if (!isDesc[0]) {
					console.log(a[index]);
					return a[index] < b[index] ? -1 : 1;
				} else {
					return b[index] < a[index] ? -1 : 1;
				}   
      });
      return items;
    },
	posaljiNaStranicuKlinike(value) {
		router.push("/stranicaKlinike/" + value.id);
	}, 
  },
}
</script>