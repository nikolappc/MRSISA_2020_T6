<template>
  <v-container>
    <display-1>
      Lista pacijenata klinike
    </display-1>
    <v-card>
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
        :items="pacijenti"
        :search="search"
        @click:row="posaljiNaPacijenta"
      ></v-data-table>
    </v-card>
  </v-container>
</template>

<script>
import axios from "axios"
import router from "../../router/index.js"
export default {
    name:"ListaPacijenata",
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
            .get('api/pacijent/klinike')
            .then(response => {
                this.pacijenti = response.data;
                for (const p of this.pacijenti) {
                  p.adresa= this.toStringAdresa(p.adresa);
                }
                console.log(response);
            })
            .catch(error=> {
              console.log(error);
              this.$store.commit("setSnackBar", {text:"Izvinjavamo se došlo je do greške.", color:"error"});
              this.$router.push("/");
            });
    },
    methods: {
        posaljiNaPacijenta: function(value){
            router.push("/pacijent/"+value.id);
        },
        toStringAdresa:function(adresa){
            return adresa.adresa+ ", " + adresa.grad + ", " + adresa.drzava;
        },
    }
}
</script>