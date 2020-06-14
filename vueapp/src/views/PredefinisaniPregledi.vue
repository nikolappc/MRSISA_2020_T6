<template>
  <div class="predefinisaniPregledi" style="width:100%;">
    <v-container style="width:100%;"><v-card style="width:100%;">
			<v-card-title>Zakažite predefinisani pregled</v-card-title>
		<v-card-text>
      <v-data-table
        :headers="headers"
        :items="pregledi"
        show-expand
        :items-per-page="10"
        class="elevation-1"
        :custom-sort="customSort">
        <template v-slot:item.kraj="{ item }">{{ formatTime(item.kraj)}}</template>
        <template v-slot:item.pocetak="{ item }">{{ formatTime(item.pocetak)}}</template>
        <template v-slot:item.datum="{ item }">{{ formatDate(item.datum)}}</template>
        <template v-slot:item.cena="{ item }">{{ item.cena }} din</template>
        <template v-slot:item.data-table-expand="{ item }"><v-btn @click="zakazi(item)">Zakaži</v-btn></template>
      </v-data-table></v-card-text></v-card>
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
          text: 'Početak', 
          value: 'pocetak', 
          sortable: true, 
        },
        {
          text: 'Kraj', 
          value: 'kraj', 
          sortable: true, 
        },
        {
          text: 'Zakazivanje', 
          value: 'data-table-expand',
          sortable: false,
        },
      ]
  }),
  mounted () {
	console.log("uslo");
	this.ulogovani = this.$store.state.ulogovan;
	if (this.ulogovani == "") {
		router.push("/");
	}
	axios
	.get('poseta/getPredefinisaniPregledi/' + this.$route.params.idKlinike)
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
	formatTime(value) {
		return moment(String(value)).format('HH:mm');
	},
	zakazi(item) {
		console.log(item);
		axios
		.post('poseta/zakaziPregled', {idPredefinisanogTermina: item.id, idKlinike: this.$route.params.idKlinike, idPacijenta: this.ulogovani.id, idLekara: null, nazivTipa: null, terminPocetak: null})
		.then(() => {
			this.$store.commit("setSnackbar", {text:"Uspešno ste poslali zahtev za zakazivanje pregleda!", color: "success"});
				router.push("/");   
			})
		.catch( () => { this.$store.commit("setSnackbar", {text:"Greška prilikom zakazivanja unapred definisanog pregleda.", color: "error"}); console.log("greska"); router.push("/");  });
	},
  },
}
</script>