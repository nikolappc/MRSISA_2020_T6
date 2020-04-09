<template>
  <v-form
    ref="form"
    v-model="valid"
  >
    <v-text-field
      v-model="lekar.ime"
      :rules="imeRules"
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
      :rules="prezimeRules"
      label="Prezime"
      required
    ></v-text-field>
    <v-text-field
      v-model="lekar.username"
      :rules="imeRules"
      label="Username"
      required
    ></v-text-field>
    <v-text-field
      :type="'password'"
      v-model="lekar.password"
      :rules="passwordRules"
      label="Password"
      required
    ></v-text-field>
    <v-text-field
      v-model="lekar.brojTelefona"
      label="Broj Telefona"
    ></v-text-field>
    <v-text-field
      v-model="lekar.adresa"
      label="Adresa"
    ></v-text-field>
    <v-text-field
      v-model="lekar.jbo"
      label="Jedinstveni broj osiguranika"
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
          username: '',
          password: ''
          },
      valid: true,

      imeRules: [
        v => !!v || 'Ime je obavezno polje'
      ],
      emailRules: [
        v => !!v || 'E-mail je obavezan',
        //v => /.+@.+\..+/.test(v) || 'Nije dobra forma',
      ],
      prezimeRules: [
        v => !!v || 'Prezime je obavezno polje'
      ],
      passwordRules: [
        v => !!v || 'Password je obavezno polje'
      ],
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
