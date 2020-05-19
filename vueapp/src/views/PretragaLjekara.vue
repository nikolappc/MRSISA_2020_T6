<template>
  <div class="pretragaLjekara">
    <v-container>
			<v-card><div v-if="nijeIzabran">
			<h2 style="text-align:center">{{ this.nazivKlinike }}</h2>
			<h4 style="text-align:center">{{ this.adresaKlinike.adresa }}, {{ this.adresaKlinike.grad }}, {{ this.adresaKlinike.drzava }}</h4>
			</div><div v-else>
			<h2 style="text-align:center">Zakazivanje pregleda</h2>
			<h4 style="text-align:center">Izaberite ljekara i termin pregleda.</h4>
			</div></v-card>
			
			<router-link :to="'/predefinisaniPregledi/' + this.idKlinike" style="text-decoration:none;"><v-btn dark medium left class="blue">Predefinisani pregledi</v-btn></router-link>
			<!--<v-btn :to="{path: 'predefinsianiPregledi'}" dark medium left class="blue" slot="action">Predefinisani pregledi</v-btn>-->
			
			<div v-if="nijeIzabran">
			<v-card><v-form ref="form" v-model="valid">
	<v-simple-table>
	
	<tr><td>
	<v-menu
		v-model="fromDateMenu"
		:close-on-content-click="false"
		:nudge-right="40"
		lazy
		transition="scale-transition"
		offset-y
		full-width
		max-width="290px"
		min-width="290px"
	>
	<template v-slot:activator="{ on }">
		<v-text-field
		label="*Datum"
		readonly
		:value="fromDateDisp"
		v-on="on"
	></v-text-field>
	</template>
	<v-date-picker
		locale="en-in"
		:min="minDate"
		v-model="datum"
		dateFormat= 'dd.MM.yyyy'
		:rules="rule"
		no-title
		@input="fromDateMenu = false"
	></v-date-picker>
	</v-menu>
	</td>
				
				<!--<v-date-picker 
				label="*Datum"
				v-model="datum"
				dateFormat= 'dd.MM.yyyy'
				required
				:rules="rule"
				:min="minDate"
				>
				<template slot="dateIcon">
					<v-icon>mdi-calendar</v-icon>	
				</template>
				</v-date-picker>-->
				
				<td>
				<v-select
				v-model="tip"
				:items="tipovi"
				label="*Tip pregleda"
				dense
				:rules="rule"
				required
				return-object
			>
				<template slot="selection" slot-scope="data">
				{{ data.item.naziv }} 
				</template>
				<template slot="item" slot-scope="data">
				{{ data.item.naziv }}
				</template>
			</v-select>
			</td></tr>
			<v-btn
			:disabled="!valid"
			color="success"
			class="mr-4"
			@click="pretrazi"
			>
				Pretraži
			</v-btn>
			</v-simple-table>
			</v-form></v-card>
			</div>
			<div v-else>
				<v-card><h4 style="text-align:center">Datum pregleda: <b>{{ formatDateStr(this.datum) }}</b></h4>
				<h4 style="text-align:center">Tip pregleda: <b>{{ this.nazivTipa }}</b></h4></v-card>
			</div>
		
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
    adresaKlinike : null,
    nazivKlinike : '',
    nijeIzabran : false,
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
	if (this.pretraga.naziv == "") {
		this.nijeIzabran = true;
		console.log(this.pretraga.naziv);
		console.log(this.nijeIzabran);
		axios
		.get('tip/tipoviPregleda')
		.then(response => {
		this.tipovi = response.data;
		})
		.catch(function (error) { console.log(error); router.push("/"); });
	} else {
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
	}
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
	// format date, apply validations, etc. Example below.
	// return this.fromDateVal ? this.formatDate(this.fromDateVal) : "";
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
		let adr = this.adresaKlinike.adresa + ", " + this.adresaKlinike.grad + ", " + this.adresaKlinike.drzava;
		this.dialogZahtjev = {idKlinike: this.idKlinike, nazivKlinike: this.nazivKlinike, adresaKlinike: adr, idPacijenta: this.ulogovani.id, idLekara: value.id, imeLekara: value.ime, prezimeLekara: value.prezime, nazivTipa: this.nazivTipa, listaVremena: value.listaVremena, datum: this.datum, cenaPregleda: this.cena};
		this.dialog = true;
	},
	pretrazi() {
		this.nazivTipa = this.tip.naziv;
		//this.datum
		//this.cena = 0;
		axios
		.post('klinika/getSlobodniLekariKlinike', {idKlinike: this.idKlinike, datum: this.datum, nazivTipa: this.nazivTipa})
		.then(response => {
			this.ljekari = response.data;
			this.cena = this.ljekari[0].cijenaTipaOpciono;
			for (let i = 0; i < this.ljekari.length; i++) {
				for (let j = 0; j < this.ljekari[i].listaVremena.length; j++) {
					console.log("----------");
					console.log(this.ljekari[i].listaVremena[j]);
				}
			}
		})
		.catch(function (error) { console.log(error); router.go(-1); });
		//		<!--prepend-icon="event"-->
	},
  },
}
</script>