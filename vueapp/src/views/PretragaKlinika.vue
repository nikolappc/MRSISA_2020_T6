<template>
  <div class="pretragaKlinika" style="width:100%;">
    <v-container grid-list-md style="width:100%;"><v-card style="width:100%;">
    <v-form ref="form" v-model="valid">
			<v-card-title>Zakazivanje pregleda</v-card-title>
			<v-card-subtitle>Izaberite tip pregleda i datum kada želite da zakažete pregled.</v-card-subtitle>
		<v-card-text>
		<v-simple-table>
			<tr>
			<td colspan="3" valign="middle"><v-select
				v-model="tip"
				:items="tipovi"
				label="*Tip pregleda"
				:rules="rule"
				outlined
				dense
				return-object
			>
				<template slot="selection" slot-scope="data">
				{{ data.item.naziv }} 
				</template>
				<template slot="item" slot-scope="data">
				{{ data.item.naziv }}
				</template>
            </v-select></td>
            <td colspan="3">
			
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
		:rules="rule"
		dense
		:value="fromDateDisp"
		v-on="on"
		prepend-inner-icon="mdi-calendar-range"
		outlined
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
            </tr>
            <tr><td colspan="2"><v-text-field
              v-model="grad"
              label="Grad"
              append-icon='mdi-map-marker'
              outlined
            ></v-text-field></td>
            <td colspan="2"><v-text-field
              v-model="drzava"
              label="Država"
              append-icon='mdi-map-marker'
              outlined
            ></v-text-field></td>
            <td colspan="2"><v-text-field
              v-model="ocjena"
              label="Minimalna ocena"
              prepend-inner-icon="mdi-file-chart"
              outlined
            ></v-text-field></td></tr></v-simple-table></v-card-text>
			<v-card-actions><v-spacer></v-spacer><v-btn
			:disabled="!valid"
			color="success"
			class="mr-4"
			@click="pretraziKlinike"
			>
				Pretraži klinike
			</v-btn></v-card-actions>
		</v-form></v-card>
	<v-card-title>
		Filtriraj po ceni:
		<v-text-field
		v-model="od"
		label="Od "
		single-line
		hide-details
		></v-text-field>
		<v-text-field
		v-model="doo"
		label="Do"
		single-line
		hide-details
		></v-text-field>
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
        :items="klinikeFilter"
        :items-per-page="10"
        class="elevation-1"
        :search="search"
		@click:row="posaljiNaListuLjekara"
        :custom-sort="customSort">
        <template v-slot:item.cenaPregleda="{ item }">{{ item.cenaPregleda }} din</template>
      </v-data-table>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios';
import router from "../router/index.js"

export default {
  name: 'PretragaKlinika',
  data: () => ({
	valid: false,
    ulogovani : {},
    search : '',
    datum : '',
    fromDateMenu : false,
    tip: null,
    grad: "",
    drzava: "",
    ocjena: '',
    ocjenaRules: [
		v => (v <= 5) && (v >= 0) || 'Ocena mora biti u opsegu od 0 do 5.',
	],
	rule: [
		v => !!v || 'Obavezno polje',
	],
	od: "",
	doo: "",
    tipovi: [],
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
          sortable: true, 
        },
        {
          text: 'Prosečna ocena', 
          value: 'prosjek', 
          sortable: true, 
        },
        {
          text: 'Cena izabranog pregleda', 
          value: 'cenaPregleda', 
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
	.get('tip/tipoviPregleda')
	.then(response => {
		this.tipovi = response.data;
	})
	.catch(function (error) { console.log(error); router.push("/"); });
	},
	computed: {
		klinikeFilter() {
			console.log(this.od);
			console.log(this.doo);
			let proveraOd = this.od;
			if (this.od == ""  || isNaN(this.od)) { proveraOd = 0; }
			let proveraDo = this.doo;
			if (this.doo == "" || isNaN(this.doo)) { proveraDo = 9999999; }
			console.log(Number(proveraOd));
			console.log(Number(proveraDo));
			return this.klinike.filter(klinika => (Number(klinika.cenaPregleda) >= Number(proveraOd)) && (Number(klinika.cenaPregleda) <= Number(proveraDo)));
		},
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
	posaljiNaListuLjekara(value) {
		router.push({name:'PretragaLjekara', params: {pretraga: {idKlinike: value.id, cena: value.cenaPregleda, naziv: this.tip.naziv, datum: this.datum}}});
	}, 
	formatDate(date) {
		let month = `${date.getMonth() + 1}`;
		let day = `${date.getDate()}`;
		const year = date.getFullYear();

		if (month.length < 2) month = `0${month}`;
		if (day.length < 2) day = `0${day}`;

		return [year, month, day].join('-');
	},
	pretraziKlinike () {
		console.log(this.datum);
		if (this.datum != '' && this.tip != null) {
			let oc = 0;
			if ((this.ocjena != '') && (typeof this.ocjena == 'number')) {
				oc = this.ocjena;
			}
			axios		
			.post('klinika/pretragaKlinikaZakazivanje', { datum: this.datum, nazivTipa: this.tip.naziv, grad: this.grad, drzava: this.drzava, ocjena: oc})
			.then(response => {
				this.klinike = response.data;
				for (let i = 0; i < this.klinike.length; i++){
					console.log(this.klinike[i].cenaPregleda);
				}
			})
			.catch(function (error) { console.log(error); router.push("/"); });
		}
		
	}
  },
}
</script>