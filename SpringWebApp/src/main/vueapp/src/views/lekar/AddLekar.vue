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
      v-model="lekar.adresa"
      label="Adresa"
      :rules="rule"
      required
    ></v-text-field>
    <v-text-field
      v-model="grad"
      label="Grad"
      :rules="rule"
      required
    ></v-text-field>
    <v-text-field
      v-model="drzava"
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
          id: 0,
          password: '',
          brojTelefona: ""
          },
      valid: true,
      grad: '',
      drzava: '',


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
            
            

            axios
            .post('lekar',this.lekar)
            .then(() => {
                router.push("/lekari");
            })
            .catch(function (error) { console.log(error); });
        },

    }
    
}
</script>
