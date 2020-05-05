<template>
    <v-form
    ref="form"
    v-model="valid"
  >

    <v-datetime-picker 
      label="Početak termina"
      v-model="pregled.termin.pocetak"
      dateFormat= 'dd.MM.yyyy'
      >
        <template slot="dateIcon">
            <v-icon>mdi-calendar</v-icon>
        </template>

        <template slot="timeIcon">
            <v-icon>mdi-clock-outline</v-icon>
        </template>
    </v-datetime-picker>


    <v-datetime-picker 
      label="Kraj termina"
      v-model="pregled.termin.kraj"
      dateFormat= 'dd.MM.yyyy'
      >
        <template slot="dateIcon">
            <v-icon>mdi-calendar</v-icon>
        </template>

        <template slot="timeIcon">
            <v-icon>mdi-clock-outline</v-icon>
        </template>
    </v-datetime-picker>


    <v-select
      v-model="pregled.lekar"
      :items="lekari"
      label="Lekar"
      dense
      :rules="rule"
      outlined
      required
      return-object
    >
        <template slot="selection" slot-scope="data">
            <!-- HTML that describe how select should render selected items -->
            {{ data.item.ime }} {{ data.item.prezime }}
        </template>
        <template slot="item" slot-scope="data">
            <!-- HTML that describe how select should render items when the select is open -->
            {{ data.item.ime }} {{ data.item.prezime }}
        </template>
    </v-select>


    <v-select
      v-model="pregled.sala"
      :items="sale"
      label="Sala"
      outlined
      dense
      :rules="rule"
      required
      item-text="naziv"
      return-object
    >
    </v-select>

    <v-select
      v-model="pregled.tipPosete"
      :items="tipovi"
      label="Tip"
      outlined
      dense
      :rules="rule"
      required
      return-object
    >

    <template slot="selection" slot-scope="data">
            <!-- HTML that describe how select should render selected items -->
            Naziv: {{ data.item.naziv }} , Cena: {{ data.item.stavkaCenovnika.cena }}
        </template>
        <template slot="item" slot-scope="data">
            <!-- HTML that describe how select should render items when the select is open -->
            Naziv: {{ data.item.naziv }} , Cena: {{ data.item.stavkaCenovnika.cena }}
        </template>
    </v-select>

    <v-btn
      :disabled="!valid"
      color="success"
      class="mr-4"
      @click="dodajTermin"
    >
      Dodaj termin
    </v-btn>

  </v-form>
</template>

<script>
import axios from "axios";
import router from "../router/index.js"
export default {
    name: 'AddTermin',
    data: function() { return {
      pregled: {
          termin: {pocetak: '', kraj: ''},
          tipPosete: null,
          sala: null,
          lekar: null,
          },

      tipovi: [],
      sale: [],
      lekari: [],

      valid: true,

      rule: [
        v => !!v || 'Obavezno polje'
      ]
    }
    },
    mounted(){
        axios
            .get('tip')
            .then(response => {
                this.tipovi = response.data;
                console.log(response);
            })
            .catch(() => { this.tipovi = [{naziv: 'pera',tip: 'operacija',stavkaCenovnika: {cena: 20}}]; });

        axios
            .get('sala')
            .then(response => {
                this.sale = response.data;
                console.log(response);
            })
            .catch(() => { this.sale = [{id: '1',naziv: 'Neka klinika'}]; });

        axios
            .get('lekar')
            .then(response => {
                this.lekari = response.data;
                console.log(response);
            })
            .catch(() => { this.lekari = [{ime: 'pera',prezime: 'neko'}]; });
    },
    methods: {
        dodajTermin: function(event) {
            event.preventDefault();
            axios
            .post('poseta',this.pregled)
            .then(() => {
                this.$store.commit("setSnackbar", {text:"Uspešno ste dodali slobodan termin", color: "success"});
                router.push("/sale");
            })
            .catch(function (error) { console.log(error); });
        },

    },

}

</script>
<style>

</style>