<template>
  <tr>
      <td @click="otvoriDialog">{{lekar.ime}}</td>
      <td @click="otvoriDialog">{{lekar.prezime}}</td>
      <td>
        <Map v-bind:adresaMape="lekar.adresa"/>
      </td>
      <td @click="otvoriDialog">{{lekar.brojTelefona}}</td>
      <td><v-btn icon @click="deleteLekar"><v-icon>mdi-delete</v-icon></v-btn></td>
  </tr>
  
</template>

<script>
import axios from "axios";
import Map from "../../components/Map.vue"
export default {
    props: ["lekar"],
    data() {
      return {
      };
    },
    components:{
      Map
    },
    methods: {
      deleteLekar: function(){
        axios
            .delete('lekar/'+this.lekar.id)
            .then(() => {
                this.$store.commit("setSnackbar", {text:"Uspešno ste obrisali lekara", color: "success"});
                this.$emit("del-lekar",this.lekar.id);
            })
            .catch(() => { 
              this.$store.commit("setSnackbar", {text:"Lekar ima zakazan pregled. Nije moguće brisanje", color: "error"});
            });
      },
      otvoriDialog: function(){
        this.$emit("otvori",this.lekar.id);
      }
    }
}
</script>

<style>
</style>