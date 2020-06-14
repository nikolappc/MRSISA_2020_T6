<template>
  <div class="pretragaLjekara" style="width:100%;">
    <v-container style="width:100%;"><v-card style="width:100%;">

			<v-card-title>Zakazivanje pregleda</v-card-title>
			<p></p>
			<v-card-subtitle><p>Datum pregleda: <strong>{{ formatDateStr(this.datum) }}</strong></p>
			<p>Tip pregleda: <strong>{{ this.nazivTipa }}</strong></p>
			<p>Izaberite lekara i termin pregleda.</p></v-card-subtitle>			
		
	<v-card-text>
		<v-spacer></v-spacer>
		<v-text-field
		v-model="search"
		append-icon="mdi-magnify"
		label="Filter"
		single-line
		hide-details
		></v-text-field>
	<!--</v-card-title>-->
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
					<PotvrdaZakazivanja  v-bind:dialogZahtjev="dialogZahtjev" v-on:zatvori="dialog = false"/>
				</v-card>
		</v-dialog>
	</v-card-text>
	<v-card-actions>
		<router-link :to="'/predefinisaniPregledi/' + this.idKlinike" style="text-decoration:none;"><v-btn dark medium left class="blue">Predefinisani pregledi</v-btn></router-link>
    </v-card-actions></v-card></v-container>
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
    mozeOcjenjivati: false,
    ocjena: null,
    adresaKlinike : null,
    nazivKlinike : '',
    dialogZahtjev: null,
    dialog : false,
    search : '',
    datum : "",
    cena : "",
    nazivTipa: "",
    tip: null,
    idKlinike: 0,
    ljekari : [],
    tipovi:[],
    fromDateMenu : false,
	rule: [
		v => !!v || 'Obavezno polje',
	],
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
      ]
  }),
  mounted () {
	this.ulogovani = this.$store.state.ulogovan;
	if (this.ulogovani == "") {
		router.push("/");
	}
	if (this.pretraga == undefined || this.pretraga == null) {
		router.push("/");
	}
	this.idKlinike = this.pretraga.idKlinike;
	console.log(this.idKlinike);
	axios
	.get('klinika/' + this.idKlinike)
	.then(response => {
	this.nazivKlinike = response.data.naziv;
	this.adresaKlinike = response.data.adresa;
	})
	.catch(function (error) { console.log(error); router.push("/"); });
	let naziv = this.pretraga.naziv;
	this.nazivTipa = naziv.replace("%20", " ");
	this.datum = this.pretraga.datum;
	this.cena = this.pretraga.cena;
	axios
	.post('klinika/getSlobodniLekariKlinike', {idKlinike: this.idKlinike, datum: this.datum, nazivTipa: this.nazivTipa})
	.then(response => {
		this.ljekari = response.data;
	})
	.catch(function (error) { console.log(error); router.go(-1); });
	},
	components: {
		PotvrdaZakazivanja
	},
	computed: {
	minDate() {
		const today = new Date();
		return this.formatDate(today);
	},	
	fromDateDisp() {
		return this.datum;
	}
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
		return moment(String(value)).format('DD.MM.YYYY.');
	},
	otvoriDialog(value) {
		let adr = this.adresaKlinike.adresa + ", " + this.adresaKlinike.grad + ", " + this.adresaKlinike.drzava;
		
		axios
		.get('lekar/pacijentPosjetio/' + this.ulogovani.id + '/' + value.id)
		.then(response => {
		var getOcena = response.data;
		this.mozeOcjenjivati = getOcena.mozeOcjenjivati;
		this.ocjena = getOcena.ocjena;
		
		this.dialogZahtjev = {zakazivanje: true, idKlinike: this.idKlinike, nazivKlinike: this.nazivKlinike, adresaKlinike: adr, idPacijenta: this.ulogovani.id, idLekara: value.id, imeLekara: value.ime, prezimeLekara: value.prezime, nazivTipa: this.nazivTipa, listaVremena: value.listaVremena, datum: this.datum, cenaPregleda: this.cena, mozeOcjenjivati: this.mozeOcjenjivati, ocjena: this.ocjena};
		this.dialog = true;
		
		})
		.catch(function (error) { console.log(error); router.push("/"); });
		
		
	},
	pretrazi() {
		this.nazivTipa = this.tip.naziv;
		axios
		.post('klinika/getSlobodniLekariKlinike', {idKlinike: this.idKlinike, datum: this.datum, nazivTipa: this.nazivTipa})
		.then(response => {
			this.ljekari = response.data;
			if (this.ljekari != []) {
				this.cena = this.ljekari[0].cijenaTipaOpciono;
			}
			
		})
		.catch(function (error) { console.log(error); router.go(-1); });
	},
  },
}
</script>