<template>
  <div class="zakazaniPregledi" style="width:100%;">
    <v-container style="width:100%;">
			<v-card style="width:100%;"><v-card-title>Zakazani pregledi</v-card-title>
      <v-data-table
        :headers="headers"
        :items="pregledi"
        show-expand
        :items-per-page="10"
        class="elevation-1">
        <template v-slot:item.cena="{ item }">{{ item.cena }} din</template>
        <template v-slot:item.data-table-expand="{ item }"><v-btn @click="otkazi(item)">Otkaži</v-btn></template>
        <!--<template><v-btn @click="zakazi(row.item)">ZakaÅ¾i</v-btn></template>-->
      </v-data-table></v-card>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios';
import router from "../router/index.js"

export default {
  name: 'ZakazaniPregledi',    
  data: () => ({
    ulogovani : {},
    pregledi : [],
    headers: [
        {
          text: 'Tip', 
          value: 'tip', 
        },
        {
          text: 'Datum', 
          value: 'datum', 
        },
        {
          text: 'Pocetak', 
          value: 'pocetak', 
        },
        {
          text: 'Kraj', 
          value: 'kraj', 
        },
        {
          text: 'Lekar', 
          value: 'lekar', 
        },
        {
          text: 'Sala', 
          value: 'sala', 
        },
        {
          text: 'Opis', 
          value: 'opis', 
        },
        {
          text: 'Cena', 
          value: 'cena', 
        },
        {
          text: 'Klinika', 
          value: 'klinika', 
        },
        {
          text: 'Otkazivanje', 
          value: 'data-table-expand',
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
	.get('poseta/zakazani/' + this.ulogovani.id)
	.then(response => {
		this.pregledi = response.data;
	})
	.catch(function (error) { console.log(error); router.push("/"); });
	},
	methods: {

	otkazi(item) {
		console.log(item);
		axios
		.post('poseta/otkazi/' + item.id)
		.then(() => {
			this.$store.commit("setSnackbar", {text: "Uspesno otkazano!", color: "success"});
			router.push("/");   
		})
		.catch( () => { this.$store.commit("setSnackbar", {text: "Ne mozete otkazati pregled koji pocinje u naredna 24 sata.", color: "error"})});
	},
  },
}
</script>