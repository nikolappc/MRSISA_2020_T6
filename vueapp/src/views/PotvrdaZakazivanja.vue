<template>
<v-container>
	<v-card-title class="headline" v-if="dialogZahtjev.zakazivanje">Zakazivanje pregleda</v-card-title>
	<div v-if="dialogZahtjev.mozeOcjenjivati">
		<h1 v-if="dialogZahtjev.zakazivanje == false"><strong>{{ this.dialogZahtjev.imeLekara }} {{ this.dialogZahtjev.prezimeLekara }}</strong></h1>
		<v-rating
			v-model="ocjena"
			color="yellow darken-3"
			background-color="grey darken-1"
			empty-icon="$ratingFull"
			half-increments
			hover
			@input="ocijeniLjekara($event)"
		></v-rating>
		<h6>Ocenite lekara!</h6>
	</div>
	<v-form v-if="dialogZahtjev.zakazivanje" ref="form" v-model="valid">
	<v-simple-table>
		<tr><td>Klinika: </td><td><strong>{{ this.dialogZahtjev.nazivKlinike }}</strong></td></tr>
		<tr><td>Adresa: </td><td><strong>{{ this.dialogZahtjev.adresaKlinike }}</strong></td></tr>
		<tr><td>Lekar: </td><td><strong>{{ this.dialogZahtjev.imeLekara }} {{ this.dialogZahtjev.prezimeLekara }}</strong></td></tr>
		<tr><td>Tip pregleda: </td><td><strong>{{ this.dialogZahtjev.nazivTipa }}</strong></td></tr>
		<tr><td>Datum: </td><td><strong>{{ formatDate(this.dialogZahtjev.datum) }}</strong></td></tr>
		<tr><td>Cena: </td><td><strong>{{ this.dialogZahtjev.cenaPregleda }} din</strong></td></tr>
		<tr><td>Termin pregleda: </td><td>
			<v-select
				v-model="terminPocetak"
				:items="vremena"
				:rules="rule"
				outlined
				required
				dense
				return-object
			>
				<template slot="selection" slot-scope="data">
				{{ formatDateStr(data.item) }} 
				</template>
				<template slot="item" slot-scope="data">
				{{ formatDateStr(data.item) }}
				</template>
			</v-select></td></tr>
			
		<tr><td>
			<v-btn
			:disabled="!valid"
			color="success"
			class="mr-4"
			@click="posaljiZahtjev"
			>
				Potvrdi
			</v-btn>
		</td>
		<td>
		<v-btn
		color="error"
		class="mr-4"
		@click="otkazi">
		Otkaži</v-btn>
		</td></tr>
		</v-simple-table>
	</v-form>
</v-container>
</template>

<script>
import axios from "axios";
import router from "../router/index.js"
import moment from 'moment';

export default {
    name: 'PotvrdaZakazivanja',
    props: ["dialogZahtjev"],
	data: () => ({
		valid: false,
		terminPocetak: null,
		rule: [
			v => !!v || 'Obavezno polje'
		]
	}),
	computed: {
		vremena: function(){
			return this.dialogZahtjev.listaVremena;
		},
		ocjena: function(){
			return this.dialogZahtjev.ocjena;
		}
	},
	mounted() {
		this.cijena = this.dialogZahtjev.cenaPregleda;
		this.vremena = this.dialogZahtjev.listaVremena;
	},
    methods: {
		otkazi() {
			this.$emit("zatvori");
		},
        posaljiZahtjev(event) {
            event.preventDefault();
			if (this.terminPocetak == '' || this.terminPocetak == null) {
				this.$store.commit("setSnackbar", {text:"Izaberite vreme pregleda.", color: "info"});
			} else {
				this.$emit("zatvori");
				axios
				.post('poseta/zakaziPregled', {idPredefinisanogTermina: 0, idKlinike: this.dialogZahtjev.idKlinike, idPacijenta: this.dialogZahtjev.idPacijenta, idLekara: this.dialogZahtjev.idLekara, nazivTipa: this.dialogZahtjev.nazivTipa, terminPocetak: new Date(this.terminPocetak)})
				.then(() => {
					this.$store.commit("setSnackbar", {text:"Zahtev za zakazivanje pregleda je poslat!", color: "success"});
					router.push("/");   
				})
				.catch( () => { this.$store.commit("setSnackbar", {text:"Greška prilikom slanja zahteva za zakazivanje pregleda. Moguće je da je pregled u međuvremenu zauzet.", color: "error"}); console.log("greska");
				});
			}
         
        },
		formatDateStr(value) {
			return moment(String(value)).format('HH:mm');
		},
		formatDate(value) {
			return moment(String(value)).format('DD.MM.YYYY.');
		},
		ocijeniLjekara(value) {
			axios
			.post('lekar/ocijeni', {idPacijenta: this.dialogZahtjev.idPacijenta, id: this.dialogZahtjev.idLekara, ocjena: value})
			.then(() => {
				console.log(value);
			});
		},

    }
    
}
</script>
