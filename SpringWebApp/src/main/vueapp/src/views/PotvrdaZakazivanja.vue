<template>
<v-container>
	<v-form ref="form" v-model="valid">
	<v-simple-table>
		<tr><td>Lekar: </td><td><b>{{ this.dialogZahtjev.imeLekara }} {{ this.dialogZahtjev.prezimeLekara }}</b></td></tr>
		<tr><td>Tip pregleda: </td><td><b>{{ this.dialogZahtjev.nazivTipa }}</b></td></tr>
		<tr><td>Datum: </td><td><b>{{ formatDate(this.dialogZahtjev.datum) }}</b></td></tr>
		<tr><td>Cena: </td><td><b>{{ this.dialogZahtjev.cenaPregleda }} din</b></td></tr>
		<tr><td>Termin pregleda: </td><td>
			<v-select
				v-model="terminPocetak"
				:items="dialogZahtjev.listaVremena"
				:rules="rule"
				outlined
				required
				return-object
			>
				<template slot="selection" slot-scope="data">
				<!-- HTML that describe how select should render selected items -->
				{{ formatDateStr(data.item) }} 
				</template>
				<template slot="item" slot-scope="data">
				<!-- HTML that describe how select should render items when the select is open -->
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
		terminPocetak: null,
		vremena: [],
		rule: [
			v => !!v || 'Obavezno polje'
		]
	}),
	/*mounted () {
		for (let i = 0; i < this.dialogZahtjev.listaVremena.length; i++) {
			this.vremena[i] = new Date(this.dialogZahtjev.listaVremena[i]);
			console.log(this.vremena[i]);
		}
	},*/
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
				.post('poseta/zakaziPregled', {idKlinike: this.dialogZahtjev.idKlinike, idPacijenta: this.dialogZahtjev.idPacijenta, idLekara: this.dialogZahtjev.idLekara, nazivTipa: this.dialogZahtjev.nazivTipa, terminPocetak: new Date(this.terminPocetak)})
				.then(() => {
					this.$store.commit("setSnackbar", {text:"Zahtev za zakazivanje pregleda je poslat!", color: "success"});
					router.push("/");   
				})
				.catch( () => { this.$store.commit("setSnackbar", {text:"Greska", color: "error"});
				});
			}
         
        },
		formatDateStr(value) {
			console.log(value);
			//console.log(String(value));
			console.log(moment(String(value)).format('DD.MM.YYYY. HH:mm'));
			return moment(String(value)).format('HH:mm');
		},
		formatDate(value) {
			return moment(String(value)).format('DD.MM.YYYY.');
		},

    }
    
}
</script>
