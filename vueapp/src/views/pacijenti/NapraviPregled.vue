<template>
  <v-container>
    <display-1>
      Zakazivanje operacije/pregleda
    </display-1>
    <v-card
      outlined="true"
    >
      <v-container>
        <v-form
          ref="form"
          v-model="valid"
          >
      
          <v-datetime-picker 
            label="Početak termina"
            v-model="pregled.termin.pocetak"
            dateFormat= 'dd.MM.yyyy'
            prepend-icon="mdi-calendar"

            >
              <template slot="dateIcon">
                  <v-icon>mdi-calendar</v-icon>
              </template>
      
              <template slot="timeIcon">
                  <v-icon>mdi-clock-outline</v-icon>
              </template>
          </v-datetime-picker>
      
      
          <v-datetime-picker 
            label="Kraj termina"
            v-model="pregled.termin.kraj"
            dateFormat= 'dd.MM.yyyy'
            prepend-icon="mdi-calendar"
            >
              <template slot="dateIcon">
                  <v-icon>mdi-calendar</v-icon>
              </template>
      
              <template slot="timeIcon">
                  <v-icon>mdi-clock-outline</v-icon>
              </template>
          </v-datetime-picker>
      
      
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
      
      
          <v-select
            v-model="tipPregleda"
            :items= '["Pregled", "Operacija"]'
            label="Pregled/Operacija"
            outlined
            dense
            :rules="rule"
            required
          >
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
      </v-container>
    </v-card>
  </v-container>
</template>

<script>
import axios from "axios";
export default {
    name: 'NapraviPregled',
    props: ["lekar","pacijent"],
    data: function() { return {
      pregled: {
          termin: {pocetak: '', kraj: ''},
          tipPosete: null,
          sala: null,
          pacijentId: null
      },
      tipPregleda: "Pregled",
      tipovi: [],

      valid: true,

      rule: [
        v => !!v || 'Obavezno polje'
      ]
    }
    },
    mounted() {
      this.pregled.pacijentId = this.pacijent.id; 
        axios
            .get('tip')
            .then(response => {
                this.tipovi = response.data;
                console.log(response);
            })
            .catch((response) => { console.log(response); });
            
    },
    methods: {
        dodajTermin: function(event) {
            event.preventDefault();
            this.pregled.pacijent = this.pacijent;
            if(this.tipPregleda == "Operacija"){
                this.pregled.lekari = [this.lekar];
                axios
                    .post('poseta/operacija',this.pregled)
                    .then(() => {
                        this.$store.commit("setSnackbar", {text:"Uspešno ste poslali zahtev za operaciju", color: "success"});
                        this.$emit("zatvori", true);
                    })
                    .catch(function (error) { console.log(error); });
            }
            else{
                this.pregled.lekar = this.lekar;
                axios
                    .post('poseta/pregled',this.pregled)
                    .then(() => {
                        this.$store.commit("setSnackbar", {text:"Uspešno ste poslali zahtev za pregled", color: "success"});
                        this.$emit("zatvori", false);
                    })
                    .catch(function (error) { console.log(error); });
            }
            
        },

    }
}
</script>

<style>

</style>