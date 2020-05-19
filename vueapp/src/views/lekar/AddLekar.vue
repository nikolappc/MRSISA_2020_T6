<template>
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
      :rules="rule"
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
    <v-text-field
      v-model="lekar.adresa.adresa"
      label="Adresa"
      :rules="rule"
      required
    ></v-text-field>
    <v-text-field
      v-model="lekar.adresa.grad"
      label="Grad"
      :rules="rule"
      required
    ></v-text-field>
    <v-text-field
      v-model="lekar.adresa.drzava"
      label="Država"
      :rules="rule"
      required
    ></v-text-field>
    <v-text-field
      v-model="lekar.jbo"
      label="Jedinstveni broj osiguranika"
      :rules="rule"
      required
    ></v-text-field>
    <v-menu
        ref="menu"
        v-model="menu"
        :close-on-content-click="false"
        :nudge-right="40"
        :return-value.sync="pocetak"
        transition="scale-transition"
        offset-y
        max-width="290px"
        min-width="290px"
        
      >
        <template v-slot:activator="{ on }">
          <v-text-field
            :rules="rule"
            v-model="pocetak"
            label="Početak radnog vremena"
            prepend-icon="mdi-clock"
            readonly
            v-on="on"
          ></v-text-field>
        </template>
        <v-time-picker
        format="24hr"
        
          v-if="menu"
          v-model="pocetak"
          full-width
          @click:minute="$refs.menu.save(pocetak)"
        ></v-time-picker>
      </v-menu>
    <v-menu
        ref="menu2"
        v-model="menu2"
        :close-on-content-click="false"
        :nudge-right="40"
        :return-value.sync="kraj"
        transition="scale-transition"
        offset-y
        max-width="290px"
        min-width="290px"
      >
        <template v-slot:activator="{ on }">
          <v-text-field
          :rules="rule"
            v-model="kraj"
            label="Kraj radnog vremena"
            prepend-icon="mdi-clock"
            readonly
            v-on="on"
          ></v-text-field>
        </template>
        <v-time-picker
          format="24hr"
          v-if="menu2"
          v-model="kraj"
          full-width
          @click:minute="$refs.menu2.save(kraj)"
        ></v-time-picker>
      </v-menu>
    <v-btn
      :disabled="!valid"
      color="success"
      class="mr-4"
      @click="dodajLekara"
    >
      Dodaj lekara
    </v-btn>

  </v-form>
</template>

<script>
import axios from "axios";
import router from "../../router/index.js"
export default {
    name: 'AddLekar',
    data: function() { return {
      lekar: { 
          ime: '', 
          prezime: '',
          email: '',
          jbo: '',
          id: null,
          adresa: {adresa: '', grad: '', drzava: ''},
          password: '',
          brojTelefona: "",
          radnoVreme: [{pocetak: '', kraj:''}]
          },
      valid: true,
      pocetak: '',
      kraj: '',
      menu: false,
      menu2: false,
      rule: [
        v => !!v || 'Obavezno polje'
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
    methods: {
        dodajLekara: function(event) {
            event.preventDefault();
            
            this.lekar.radnoVreme[0].pocetak =  new Date("0000-01-1 " + this.pocetak);
            this.lekar.radnoVreme[0].kraj =  new Date("0000-01-1 " + this.kraj);
            axios
            .post('lekar',this.lekar)
            .then(() => {
                this.$store.commit("setSnackbar", {text:"Lekar je uspešno dodat", color: "success"});
                router.push("/lekari");
            })
            .catch(function (error) { console.log(error); });
        },
        compareTime(time1, time2) {

            return time1>time2; // true if time1 is later
        }

    }
    
}
</script>
