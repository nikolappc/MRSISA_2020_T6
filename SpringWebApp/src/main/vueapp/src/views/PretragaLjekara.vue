<template>
  <div class="pretragaLjekara">
    <v-container>
			<h2 style="text-align:center">Zakazivanje pregleda</h2>
			<h4 style="text-align:center">Izaberite ljekara i termin pregleda.</h4>
			<h4 style="text-align:center">Datum pregleda: <b>{{ formatDateStr(this.datum) }}</b></h4>
			<h4 style="text-align:center">Tip pregleda: <b>{{ this.nazivTipa }}</b></h4>
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
        :items="ljekari"
        :items-per-page="10"
        class="elevation-1"
        :search="search"
		@click:row="otvoriDialog"
        :custom-sort="customSort">

      </v-data-table>
		<v-dialog
			v-model="dialog"
			max-width="500"
			>
				<v-card>
					<v-card-title class="headline">Zakazivanje pregleda</v-card-title>
					<PotvrdaZakazivanja  v-bind:dialogZahtjev="dialogZahtjev" v-on:zatvori="dialog = false"/>
				</v-card>
		</v-dialog>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios';
import router from "../router/index.js"
import PotvrdaZakazivanja from "./PotvrdaZakazivanja.vue"
import moment from 'moment';

export default {
  name: 'PretragaLjekara',
  props: ["pretraga"],
  data: () => ({
    ulogovani : {},
    dialogZahtjev: null,
    dialog : false,
    search : '',
    //terminPocetak: '',
    datum : "",
    cena : "",
    nazivTipa: "",
    idKlinike: 0,
    ljekari : [],
    headers: [
        {
          text: 'Ime', 
          value: 'ime', 
          sortable: true, 
        },
        {
          text: 'Prezime', 
          value: 'prezime', 
          sortable: true, 
        },
        {
          text: 'Prosečna ocena', 
          value: 'prosjek', 
          sortable: true, 
        },
        /*{
          text: 'Slobodna vremena', 
          value: 'listaVremena', 
          sortable: false, 
        },*/
      ]
  }),
  mounted () {
	this.ulogovani = this.$store.state.ulogovan;
	if (this.ulogovani == "") {
		router.push("/");
	}
	/*this.idKlinike = this.$route.params.id;
	let naziv = this.$route.params.nazivTipa;
	this.nazivTipa = naziv.replace("%20", " ");
	this.datum = this.$route.params.datum;
	this.cena = this.$route.params.cenaPregleda;*/
	this.idKlinike = this.pretraga.idKlinike;
	let naziv = this.pretraga.naziv;
	this.nazivTipa = naziv.replace("%20", " ");
	this.datum = this.pretraga.datum;
	this.cena = this.pretraga.cena;
	axios
	.post('klinika/getSlobodniLekariKlinike', {idKlinike: this.idKlinike, datum: this.datum, nazivTipa: this.nazivTipa})
	.then(response => {
		this.ljekari = response.data;
		for (let i = 0; i < this.ljekari.length; i++) {
			for (let j = 0; j < this.ljekari[i].listaVremena.length; j++) {
				console.log("********");
				console.log(this.ljekari[i].listaVremena[j]);
			}
		}
	})
	.catch(function (error) { console.log(error); router.go(-1); });
	},
	components: {
		PotvrdaZakazivanja
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

	formatDate(date) {
		let month = `${date.getMonth() + 1}`;
		let day = `${date.getDate()}`;
		const year = date.getFullYear();

		if (month.length < 2) month = `0${month}`;
		if (day.length < 2) day = `0${day}`;

		return [year, month, day].join('-');
	},
	formatDateStr(value) {
		//console.log(value);
		//console.log(String(value));
		return moment(String(value)).format('DD.MM.YYYY.');
	},
	otvoriDialog(value) {
		console.log(value.listaVremena);
		/*for (let i = 0; i < value.listaVremena.length; i++) {
			value.listaVremena[i] = value.listaVremena[i].substr(1);
		}
		console.log(value.listaVremena);*/
		this.dialogZahtjev = {idKlinike: this.idKlinike, idPacijenta: this.ulogovani.id, idLekara: value.id, imeLekara: value.ime, prezimeLekara: value.prezime, nazivTipa: this.nazivTipa, listaVremena: value.listaVremena, datum: this.datum, cenaPregleda: this.cena};
		this.dialog = true;
	},
  },
}
</script>