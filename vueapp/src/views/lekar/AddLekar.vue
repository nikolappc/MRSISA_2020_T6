<template>
<v-container>

  <v-form
    ref="form"
    v-model="valid"
    
  >
    <v-text-field
      v-model="lekar.ime"
      :rules="rule"
      label="Ime"
      required
    ></v-text-field>

    <v-text-field
      v-model="lekar.email"
      :rules="emailRules"
      label="E-mail"
      required
    ></v-text-field>

    <v-text-field
      v-model="lekar.prezime"
      :rules="rule"
      label="Prezime"
      required
    ></v-text-field>
    <v-text-field
      :type="'password'"
      v-model="lekar.password"
      :rules="rule"
      label="Password"
      required
    ></v-text-field>
    <v-text-field
      v-model="lekar.brojTelefona"
      label="Broj Telefona"
      :rules="rule"
      required
    ></v-text-field>
    <AutocompleteAdresa v-bind:adresa="lekar.adresa"/>
    <v-text-field
      v-model="lekar.jbo"
      label="Jedinstveni broj osiguranika"
      :rules="rule"
      required
    ></v-text-field>
    <v-select
      v-model="radnoVreme"
      :items="smena"
      label="Smena"
      outlined
      dense
      :rules="rule"
      required
      return-object
    >

        <template slot="selection" slot-scope="data">
            Smena: {{formatirajDatum(data.item.pocetak)}} - {{formatirajDatum(data.item.kraj)}}
        </template>
        <template slot="item" slot-scope="data">
            Smena: {{formatirajDatum(data.item.pocetak)}} - {{formatirajDatum(data.item.kraj)}}
        </template>
    </v-select>


    <v-select
      v-model="odabraniTipovi"
      :items="tipovi"
      label="Specijalizacija lekara"
      chips
      outlined
      dense
      :rules="rule2"
      multiple
      required
      return-object
      
    >
        <template slot="selection" slot-scope="data">
            {{data.item.naziv}}
        </template>
        <template slot="item" slot-scope="data">
            Tip: {{data.item.naziv}}
        </template>
    </v-select>

  
    <v-btn
      :disabled="!valid"
      color="success"
      class="mr-4"
      @click="dodajLekara"
    >
      Dodaj lekara
    </v-btn>

  </v-form>
</v-container>
</template>

<script>
import axios from "axios";
import moment from "moment"
import router from "../../router/index.js"
import AutocompleteAdresa from "../../components/Autocomplete.vue";
export default {
    components:{
      AutocompleteAdresa
    },
    name: 'AddLekar',
    data: function() { return {
        smena: [{ pocetak: new Date("1000-01-01 07:00"),kraj: new Date("1000-01-01 15:00")},
          { pocetak: new Date("1000-01-01 15:00"),kraj: new Date("1000-01-01 23:00")}
        ],
        odabraniTipovi: [],
        adresa: "",
        lekar: { 
            ime: '', 
            prezime: '',
            email: '',
            jbo: '',
            id: null,
            adresa: {adresa: '', grad: '', drzava: ''},
            password: '',
            brojTelefona: "",
            radnoVreme: [],
            tipoviPoseta: [],
            tip: "LEKAR",
            },
        valid: true,
        tipovi: [],
        radnoVreme: "",
        rule: [
          v => !!v || 'Obavezno polje'
        ],
        rule2: [
          v => v.length != 0 || 'Obavezno polje'
        ],
        rule3: [
          v => console.log(v) || "Mora imati polje"
        ],
        emailRules: [
          v => !!v || 'E-mail je obavezan',
          v => /.+@.+\..+/.test(v) || 'E-mail mora biti u formi pera@domen.com',
        ],
        passwordRules: [
          v => !!v || 'Password je obavezno polje'
        ]

      }
    },

    mounted () {

      axios
            .get('tip')
            .then(response => {
                this.tipovi = response.data;
                console.log(response);
            })
            .catch((err) => { console.log(err) });
    },
    methods: {
        formatirajDatum: function(datum){
            
          return moment(datum).format('HH:mm'); 
        },
        dodajLekara: function(event) {
            event.preventDefault();
            this.lekar.radnoVreme = [];
            this.lekar.radnoVreme.push(this.radnoVreme);
            console.log(this.lekar);

            axios
            .post('lekar',{lekar: this.lekar, tipovi: this.odabraniTipovi})
            .then(() => {
                this.$store.commit("setSnackbar", {text:"Lekar je uspešno dodat", color: "success"});
                router.push("/lekari");
            })
            .catch(function (error) { console.log(error); });
        },
        

    }
    
}
</script>
