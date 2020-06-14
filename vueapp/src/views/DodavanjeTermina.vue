<template>
    <v-form
    ref="form"
    v-model="valid"
  >
    <v-menu
        ref="menu"
        v-model="menu2"
        :close-on-content-click="false"
        :return-value.sync="date"
        transition="scale-transition"
        offset-y
        min-width="290px"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-text-field
            v-model="formatDate"
            label="Picker in menu"
            prepend-icon="mdi-calendar"
            readonly
            v-bind="attrs"
            v-on="on"
          ></v-text-field>
        </template>
        <v-date-picker v-model="date" no-title scrollable :min="new Date().toISOString()">
          <v-spacer></v-spacer>
          <v-btn text color="primary" @click="menu = false">Cancel</v-btn>
          <v-btn text color="primary" @click="$refs.menu.save(date)">OK</v-btn>
        </v-date-picker>
      </v-menu>

    <v-select
      v-model="vreme"
      :items="vremena"
      label="Vreme"
      dense
      hint ="Termin traje 30 min"
      :rules="rule"
      outlined
      required>
      <template slot="selection" slot-scope="data">
            <!-- HTML that describe how select should render selected items -->
            {{data.item.toLocaleTimeString()}}
        </template>
        <template slot="item" slot-scope="data">
            <!-- HTML that describe how select should render items when the select is open -->
            {{data.item.toLocaleTimeString()}}
        </template>
    </v-select>
    


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
            Naziv: {{ data.item.naziv }} , Cena: {{ data.item.stavkeCenovnika[0].cena }}
        </template>
        <template slot="item" slot-scope="data">
            <!-- HTML that describe how select should render items when the select is open -->
            Naziv: {{ data.item.naziv }} , Cena: {{ data.item.stavkeCenovnika[0].cena }}
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
import moment from "moment";
import router from "../router/index.js"
export default {
    name: 'AddTermin',
    data: function() { return {
      date: new Date().toISOString().substr(0, 10),
      pregled: {
          termin: {pocetak: new Date(), kraj: ''},
          tipPosete: null,
          sala: null,
          lekar: null,
      },

      tipovi: [],
      sale: [],
      lekari: [],
      vreme: '',
      menu2: false,
      vremena: [

      ],
      valid: true,

      rule: [
        v => !!v || 'Obavezno polje'
      ]
    }
    },
    computed:{
      formatDate () {
            let datum = this.date;
            return datum.substring(8,10) +"." +datum.substring(5,7) + "." + datum.substring(0,4) + " " + datum.substring(11,16); 
        },
    },
    mounted(){
        this.generateTimes();
        axios
            .get('tip')
            .then(response => {
                this.tipovi = response.data;
                console.log(response);
            })
            .catch(() => { this.tipovi = [{naziv: 'pera',tip: 'operacija',stavkeCenovnika: {cena: 20}}]; });

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
            
            this.pregled.termin.pocetak = new Date(this.date);
            this.pregled.termin.pocetak = moment(this.pregled.termin.pocetak).add(this.vreme.getMinutes(), 'm').toDate();
            this.pregled.termin.pocetak = moment(this.pregled.termin.pocetak).add(this.vreme.getHours(), 'h').toDate();
            this.pregled.termin.kraj = moment(this.pregled.termin.pocetak).add(30, 'm').toDate();

            axios
            .post('poseta',this.pregled)
            .then(() => {
                this.$store.commit("setSnackbar", {text:"Uspešno ste dodali slobodan termin", color: "success"});
                router.push("/sale");
            })
            .catch((error) =>{ this.$store.commit("setSnackbar", {text:"Termin je zauzet kod lekara ili sale", color: "error"});
            console.log(error); });
        },
        generateTimes: function(){
          let pocetak = new Date("2015-03-25 08:00");
          while(pocetak < new Date("2015-03-25 23:00")){
            this.vremena.push(pocetak);
            pocetak = moment(pocetak).add(30, 'm').toDate();
          }
        }

    },

}

</script>
<style>

</style>