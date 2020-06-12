<template>
<v-container>
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
      :rules="imeRules"
      outlined
      required
    ></v-select>

    <v-text-field
      v-model="tip.stavkeCenovnika[0].cena"
      :rules="cenaRules"
      label="Cena"
      required
    ></v-text-field>
    <v-btn
      :disabled="!valid"
      color="success"
      class="mr-4"
      @click="dodajTip"
    >
      Dodaj tip
    </v-btn>

  </v-form>
  </v-container>
</template>

<script>
import axios from "axios";
import router from "../../router/index.js"
export default {
    name: 'AddLekar',
    data: function() { return {
      tip: { 
          naziv: '', 
          stavkeCenovnika: [{cena: 0}],
          tip: '',
          id: 0,
          },
      valid: true,

      imeRules: [
        v => !!v || 'Obavezno polje'
      ],
      cenaRules: [
        v => !!v || 'Obavezno polje',
        v => parseFloat(v) > 0 || "Polje mora biti pozitivan broj"
      ]
      
    }
    },
    methods: {
        dodajTip: function(event) {
            event.preventDefault();
            
            

            axios
            .post('tip',this.tip)
            .then(() => {
                this.$store.commit("setSnackbar", {text:"Tip je uspešno dodat", color: "success"});
                router.push("/tipoviPoseta");
            })
            .catch(function (error) { console.log(error); });
        },

    }
    
}
</script>
