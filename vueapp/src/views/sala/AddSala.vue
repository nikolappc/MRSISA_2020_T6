<template>
  <v-form
    ref="form"
    v-model="valid"
  >
    <v-text-field
      v-model="sala.naziv"
      :rules="nazivRules"
      label="Naziv"
      required
    ></v-text-field>

    
    <v-btn
      :disabled="!valid"
      color="success"
      class="mr-4"
      @click="dodajSalu"
    >
      Dodaj salu
    </v-btn>

  </v-form>
</template>

<script>
import axios from "axios";
import router from "../../router/index.js"
export default {
    name: 'AddSalu',
    data: function() { return {
      sala: { 
          id: null,
          naziv: '',
          },
      valid: true,

      nazivRules: [
        v => !!v || 'Naziv je obavezno polje'
      ]
    }
    },
    methods: {
        dodajSalu: function(event) {
            event.preventDefault();
            axios
            .post('sala',this.sala)
            .then(() => {
                this.$store.commit("setSnackbar", {text:"Sala je uspešno dodata", color: "success"});
                router.push("/sale");
            })
            .catch(function (error) { console.log(error); });
        },

    }
    
}
</script>
