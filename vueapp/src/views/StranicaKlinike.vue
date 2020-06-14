<template>
  <div class="stranicaKlinike" style="width:100%;">
    <v-container style="width:100%;"><v-card style="width:100%;">
    <v-card-title>{{ this.nazivKlinike }}</v-card-title>
			<v-card-subtitle><p>{{ this.adresaKlinike.adresa }}, {{ this.adresaKlinike.grad }}, {{ this.adresaKlinike.drzava }}</p>
			<p>{{ this.opis }}</p></v-card-subtitle>
			
			
			<v-form ref="form" v-model="valid">
			<v-card-text>
			<div v-if="mozeOcjenjivati">
				<v-rating
					v-model="ocjena"
					color="yellow darken-3"
					background-color="grey darken-1"
					empty-icon="$ratingFull"
					half-increments
					hover
					@input="ocijeniKliniku($event)"
				></v-rating>
			</div>
			
			
	<v-simple-table>
	
	<tr>
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
			</td>
			<td>
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

				</tr></v-simple-table>
			</v-card-text>
			
			<v-card-actions>
				<router-link :to="'/predefinisaniPregledi/' + this.idKlinike" style="text-decoration:none;"><v-btn dark medium left class="blue">Predefinisani pregledi</v-btn></router-link>
			<v-spacer></v-spacer>
			<v-btn
			color="info"
			class="mr-4"
			@click="ponisti"
			>
				Poništi pretragu
			</v-btn>
			<v-btn
			:disabled="!valid"
			color="success"
			class="mr-4"
			@click="pretrazi"
			>
				Pretraži
			</v-btn></v-card-actions>
			</v-form></v-card>

		
	<v-card-title>
		<v-spacer></v-spacer>
		<v-text-field
		v-model="search"
		append-icon="mdi-magnify"
		label="Filter"
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
  name: 'StranicaKlinike',
  data: () => ({
    ulogovani : {},
    ocjena:null,
    pretrazeno : false,
    sviLjekari : [],
    mozeOcjenjivati: true,
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
	opis : '',
	vremena: [],
	cijena: null,
	mozeOcjenjivatiL: false,
	ocjenaL: null,
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
	this.idKlinike = this.$route.params.idKlinike;
	axios
	.get('klinika/' + this.idKlinike)
	.then(response => {
	var klin = response.data;
	this.nazivKlinike = klin.naziv;
	this.adresaKlinike = klin.adresa;
	this.opis = klin.opis;
	})
	.catch(function (error) { console.log(error); router.push("/"); });
	axios
	.get('tip/tipoviPregleda')
	.then(response => {
	this.tipovi = response.data;
	})
	.catch(function (error) { console.log(error); router.push("/"); });
	axios
	.get('klinika/getSviLekariKlinike/' + this.$route.params.idKlinike)
	.then(response => {
		this.ljekari = response.data;
		for (let ll of this.ljekari) {
			this.sviLjekari.push(ll);
		}
		this.pretrazeno = false;
	})
	.catch(function (error) { console.log(error); router.go(-1); });
	axios
	.get('klinika/pacijentPosjetio/' + this.ulogovani.id + '/' + this.$route.params.idKlinike)
	.then(response => {
	var getOcena = response.data;
	this.mozeOcjenjivati = getOcena.mozeOcjenjivati;
	this.ocjena = getOcena.ocjena; //ako vrati 0 zvjezdice staviti rando, nema ocjene
	})
	.catch(function (error) { console.log(error); router.push("/"); });
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
	formatDate1(value) {
		return moment(String(value)).format('DD.MM.YYYY.');
	},
	formatDateStr(value) {
		return moment(String(value)).format('DD.MM.YYYY.');
	},
	otvoriDialog(value) {
		let adr = this.adresaKlinike.adresa + ", " + this.adresaKlinike.grad + ", " + this.adresaKlinike.drzava;

		console.log(value.id);
		axios
		.get('lekar/pacijentPosjetio/' + this.ulogovani.id + '/' + value.id)
		.then(response => {
		var getOcena = response.data;
		this.mozeOcjenjivatiL = getOcena.mozeOcjenjivati;
		this.ocjenaL = getOcena.ocjena;
		
		if (this.pretrazeno == true) {
			axios
			.get('lekar/vratiVremenaCijenu/' + value.id + '/' + this.nazivTipa + '/' + this.formatDate1(this.datum))
			.then(response => {
			var podaci = response.data;
			this.cijena = podaci.cijenaTipaOpciono;
			this.vremena = podaci.listaVremena;
			
			this.dialogZahtjev = {zakazivanje: this.pretrazeno, idKlinike: this.idKlinike, nazivKlinike: this.nazivKlinike, adresaKlinike: adr, idPacijenta: this.ulogovani.id, idLekara: value.id, imeLekara: value.ime, prezimeLekara: value.prezime, nazivTipa: this.nazivTipa, listaVremena: this.vremena, datum: this.datum, cenaPregleda: this.cijena, mozeOcjenjivati: this.mozeOcjenjivatiL, ocjena: this.ocjenaL};
			this.dialog = true;
		
			})
			.catch(function (error) { console.log(error); router.push("/"); });
		} else {
			this.dialogZahtjev = {zakazivanje: this.pretrazeno, idKlinike: this.idKlinike, nazivKlinike: this.nazivKlinike, adresaKlinike: adr, idPacijenta: this.ulogovani.id, idLekara: value.id, imeLekara: value.ime, prezimeLekara: value.prezime, nazivTipa: this.nazivTipa, listaVremena: this.vremena, datum: this.datum, cenaPregleda: this.cijena, mozeOcjenjivati: this.mozeOcjenjivatiL, ocjena: this.ocjenaL};
			this.dialog = true;
		}
		
		})
		.catch(function (error) { console.log(error); router.push("/"); });
		
		
		

		
	},
	pretrazi() {
		this.pretrazeno = true;
		this.nazivTipa = this.tip.naziv;
		this.ljekari = [];
		for (let l of this.sviLjekari) {
			console.log(l.id);
			axios
			.post('lekar/provjeriDaLiJeLjekarSlobodan', {idLekara: l.id, idKlinike: this.idKlinike, datum: this.datum, nazivTipa: this.nazivTipa})
			.then(response => {
				if (response.data == true) {
					this.ljekari.push(l);
				}
			})
			.catch(function (error) { console.log(error); router.go(-1); });
		}
		console.log(this.ljekari);
		if (this.ljekari != []) {
			this.cena = this.ljekari[0].cijenaTipaOpciono;
		}
	},
	ponisti() {
		this.pretrazeno = false;
		this.ljekari = this.sviLjekari;
	},
	ocijeniKliniku(value) {
		axios
		.post('klinika/ocijeni', {idPacijenta: this.ulogovani.id, id: this.idKlinike, ocjena: value})
		.then(() => {
			console.log(value);
		});
	}
  },
}
</script>