<template>
    <v-card>
    <v-card-title>
      Lista pacijenata
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
      :items="pacijenti"
      :search="search"
      @click:row="posaljiNaPacijenta"
    ></v-data-table>
  </v-card>
</template>

<script>
import axios from "axios"
import router from "../../router/index.js"
export default {
    data: () => ({
        pacijenti : [],
        search : '',
        headers: [
            { text: 'Ime', value: 'ime'},
            { text: 'Prezime', value: 'prezime'},
            { text: 'JBO', value: 'jbo'},
            { text: 'Adresa', value: 'adresa', filterable: false},
            { text: 'Broj telefona', value: 'brojTelefona', filterable: false},
        ]
    }),
    mounted () {
        axios
            .get('api/pacijent')
            .then(response => {
                this.pacijenti = response.data;
                console.log(response);
            })
            .catch(() => { this.pacijenti = [{ime: 'pera',prezime: ''}]; });
    },
    methods: {
        posaljiNaPacijenta: function(value){
            router.push("/zdravstveniKartonPrikaz/"+value.id);
        }
    }
}
</script>