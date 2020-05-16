<template>
  <div class="predefinisaniPregledi">
    <v-container>
			<h2 style="text-align:center">Zakazite predefinisani pregled</h2>
      <v-data-table
        :headers="headers"
        :items="pregledi"
        :items-per-page="10"
        class="elevation-1"
        :custom-sort="customSort">
        <template v-slot:item.kraj="{ item }">{{ formatTime(item.kraj)}}</template>
        <template v-slot:item.pocetak="{ item }">{{ formatTime(item.pocetak)}}</template>
		<template v-slot:item.datum="{ item }">{{ formatDate(item.datum)}}</template>
      </v-data-table>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios';
import router from "../router/index.js"
import moment from 'moment';

export default {
  name: 'PredefinisaniPregledi',    
  data: () => ({
    ulogovani : {},
    pregledi : [],
    headers: [
        {
          text: 'Sala', 
          value: 'idSale', 
          sortable: true, 
        },
        {
          text: 'Cena', 
          value: 'cena', 
          sortable: true, 
        },
        {
          text: 'Tip', 
          value: 'nazivTipa', 
          sortable: true, 
        },
        {
          text: 'Ime lekara', 
          value: 'imeLjekara', 
          sortable: true, 
        },
        {
          text: 'Prezime lekara', 
          value: 'prezimeLjekara', 
          sortable: true, 
        },
        {
          text: 'Datum', 
          value: 'datum', 
          sortable: true, 
        },
        {
          text: 'Pocetak', 
          value: 'pocetak', 
          sortable: true, 
        },
        {
          text: 'Kraj', 
          value: 'kraj', 
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
      .get('poseta/predefinisaniPregledi', this.$route.params.idKlinike)
      .then(response => {
          this.pregledi = response.data;
      })
      .catch(function (error) { console.log(error); router.push("/"); });
	},
	methods: {
    customSort: function(items, index, isDesc) {
      items.sort((a, b) => {
		if (index[0]=='pocetak') {
			console.log(index[0]);
			console.log("uslo");
			var d1 = new Date(a['pocetak']);
			var d2 = new Date(b['pocetak']);
			console.log(d1);
			console.log(a[index]);
			if (!isDesc[0]) {
				console.log("desc uslo");
				console.log(d1.getTime());
				return d1.getTime() < d2.getTime() ? -1 : 1;
			} else {
				return d2.getTime() < d1.getTime() ? -1 : 1;
			}
		}
		else {
			console.log(index[0]);
			if (!isDesc[0]) {
				console.log(a[index]);
				return a[index] < b[index] ? -1 : 1;
			} else {
				return b[index] < a[index] ? -1 : 1;
			}   
		}
            
            
				
      });
      return items;
    },
	formatDate(value) {
		return moment(String(value)).format('DD.MM.YYYY.');
	},
	formatTie(value) {
		return moment(String(value)).format('HH:mm');
	},
  },
}
</script>