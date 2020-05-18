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
      v-model="lekar.jbo"
      label="Jedinstveni broj osiguranika"
      :rules="rule"
      required
    ></v-text-field>
    <v-btn
      :disabled="!valid"
      color="success"
      class="mr-4"
      @click="izmeniLekara"
    >
      Izmeni lekara
    </v-btn>

  </v-form>
</template>

<script>
import axios from "axios";
import router from "../../router/index.js"
export default {
    name: 'AddLekar',
    props: ["lekar"],
    data: function() { return {
      valid: true,
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
        izmeniLekara: function(event) {
            event.preventDefault();

            axios
            .put('lekar/'+this.lekar.id,this.lekar)
            .then(() => {
                this.$store.commit("setSnackbar", {text:"Lekar je uspešno izmenjen", color: "success"});
                router.go();
                
            })
            .catch( () => { this.$store.commit("setSnackbar", {text:"Lekar ima zakazan termin. Nije moguća izmena", color: "error"});
            this.$emit("zatvori"); });
        }

    }
    
}
</script>
