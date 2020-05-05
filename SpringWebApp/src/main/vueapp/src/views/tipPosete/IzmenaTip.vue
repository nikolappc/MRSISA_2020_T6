<template>
  <v-form
    ref="form"
    v-model="valid"
  >
    <v-text-field
      v-model="tip.naziv"
      :rules="imeRules"
      label="Naziv"
      required
    ></v-text-field>

    <v-select
      v-model="tip.tip"
      :items="['operacija','pregled']"
      label="Tip"
      dense
      outlined
    ></v-select>

    <v-text-field
      v-model="tip.stavkaCenovnika.cena"
      :rules="cenaRules"
      label="Cena"
      required
    ></v-text-field>
    
    <v-btn
      :disabled="!valid"
      color="success"
      class="mr-4"
      @click="izmeniTip"
    >
      Izmeni tip
    </v-btn>

  </v-form>
</template>

<script>
import axios from "axios";
import router from "../../router/index.js"
export default {
    name: 'AddLekar',
    props: ["tip"],
    data: function() { return {
      valid: true,

      imeRules: [
        v => !!v || 'Ime je obavezno polje'
      ],
      cenaRules: [
        v => !!v || 'Obavezno polje',
        v => parseFloat(v) > 0 || "Polje mora biti pozitivan broj"
      ]
    }
    },
    methods: {
        izmeniTip: function(event) {
            event.preventDefault();
            axios
            .put('tip/'+this.tip.id,this.tip)
            .then(() => {
                this.$store.commit("setSnackbar", {text:"Tip je uspešno izmenjen", color: "success"});
                router.go();
            })
            .catch((error) => { console.log(error); 
              this.$store.commit("setSnackbar", {text:"Tip se koristi na pregledu", color: "error"});
              this.$emit("zatvori");
            });
        }

    }
    
}
</script>
