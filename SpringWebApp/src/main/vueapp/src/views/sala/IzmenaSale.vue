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
      @click="izmeniSalu"
    >
      Izmeni salu
    </v-btn>

  </v-form>
</template>

<script>
import axios from "axios";
import router from "../../router/index.js"
export default {
    name: 'IzmenaSale',
    props: ["sala"],
    data: function() { return {
      valid: true,
      nazivRules: [
        v => !!v || 'Naziv je obavezno polje'
      ]
    }
    },
    methods: {
        izmeniSalu: function(event) {
            event.preventDefault();
            axios
            .put('sala/'+this.sala.id,this.sala)
            .then(() => {
                this.$store.commit("setSnackbar", {text:"Sala je uspešno izmenjena", color: "success"});
                router.go();
                
            })
            .catch( () => { this.$store.commit("setSnackbar", {text:"Sala ima zauzet termin", color: "error"}); 
            this.$emit("zatvori");});
        }

    }
    
}
</script>
