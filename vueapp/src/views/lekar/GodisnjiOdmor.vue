<template>
  <v-form
    ref="form"
    v-model="valid"
    >
    
    <h2 class="mx-4">Godišnji odmor ili odsustvo</h2>
    <v-menu
        v-model="menu1"
        :close-on-content-click="false"
        transition="scale-transition"
        offset-y
        max-width="290px"
        min-width="290px"
    >
        <template v-slot:activator="{ on }">
        <v-text-field
            v-model="formatStart"
            label="Početak odmora"
            :rules="rule"
            prepend-icon="mdi-calendar"
            v-on="on"
        ></v-text-field>
        </template>
        <v-date-picker v-model="zahtev.pocetak" no-title @input="menu1 = false"></v-date-picker>
    </v-menu>

    <v-menu
          v-model="menu2"
          :close-on-content-click="false"
          transition="scale-transition"
          offset-y
          
          max-width="290px"
          min-width="290px"
    >
        <template v-slot:activator="{ on }">
        <v-text-field
            v-model="formatEnd"
            label="Kraj odmora"
            persistent-hint
            :rules="rule"
            prepend-icon="mdi-calendar"
            readonly
            v-on="on"
        ></v-text-field>
        </template>
        <v-date-picker v-model="zahtev.kraj" no-title @input="menu2 = false"></v-date-picker>
    </v-menu>


    <v-btn
      :disabled="!valid"
      color="success"
      class="mr-4"
      @click="posaljiZahtev"
    >
      Pošalji zahtev
    </v-btn>

  </v-form>
</template>

<script>
import axios from "axios";
export default {
    name: 'Odsustvo',
    data: function() { return {
      menu1: false,
      menu2: false,
      valid: true,
      zahtev: {
          pocetak: null,
          kraj: null
      },

      rule: [
        v => !!v || 'Obavezno polje'
      ]
    }
    },
    computed: {
        formatStart () {
            return this.formatDate(this.zahtev.pocetak);
        },
        formatEnd () {
            return this.formatDate(this.zahtev.kraj);
        }
    },
    
    methods: {
        formatDate(date){
            if(!date){
                return "";
            }
            const [year, month, day] = date.split('-')
            return `${day}.${month}.${year}`
        },

        posaljiZahtev: function(event) {
            event.preventDefault();
            axios
                .post('/odmor',this.zahtev)
                .then(() => {
                    this.$store.commit("setSnackbar", {text:"Uspešno ste poslali zahtev", color: "success"});
                })
                .catch(function (error) { console.log(error); });

        },

    }
}
</script>

<style>

</style>