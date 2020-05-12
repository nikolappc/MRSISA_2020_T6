<template>
  <div class="klinikeZaPacijenta">
    <v-container>
			<h2 style="text-align:center">Lista klinika u sistemu</h2>
      <v-data-table
        :headers="headers"
        :items="klinike"
        :items-per-page="10"
        class="elevation-1"
        :custom-sort="customSort">
      </v-data-table>
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
          sortable: false, 
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
    }
  },
}
</script>