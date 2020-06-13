<template>
  <vuetify-google-autocomplete
    id='map'
    v-model="adresaText"
    append-icon='mdi-map-marker'
    v-bind:disable='true'
    placeholder="Adresa"
    v-on:no-results-found="clearAdres"
    v-on:placechanged="getAddressData"
    :rules="rule"
    ref="map"
  >
  </vuetify-google-autocomplete>
</template>

<script>
export default {
    props: ['adresa'],
    data: function(){ return {
        rule: [
          v => !!v || 'Obavezno polje'
        ],
    }
    },
    methods: {
        getAddressData(formattedData) {
          try{
            console.log(formattedData);
            this.adresa.adresa = formattedData.name;
            if(formattedData.locality != undefined && formattedData != null)
              this.adresa.grad = formattedData.locality;
            else
              this.adresa.grad = formattedData.administrative_area_level_1;
            this.adresa.drzava = formattedData.country;

          }
          catch{
            this.adresa.adresa = '';
            this.adresa.grad = '';
            this.adresa.drzava = '';
            this.$refs.map.clear();
          }
        },
        clearAdres(){
          this.$refs.map.clear();
          this.adresa.adresa = '';
          this.adresa.grad = '';
          this.adresa.drzava = '';
        }
    },
    computed:{
      adresaText(){
        if(this.adresa.adresa == "")
          return '';
        return this.adresa.adresa + ", " + this.adresa.grad  + ", " + this.adresa.drzava;
      }
    }
}
</script>

<style>

</style>