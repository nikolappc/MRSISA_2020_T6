<template>
  <tr>
      <td @click="otvoriDialog">{{tip.naziv}}</td>
      <td @click="otvoriDialog">{{tip.tip}}</td>
      <td @click="otvoriDialog">{{tip.stavkaCenovnika.cena}}</td>
      <td><v-btn icon @click="deleteTip"><v-icon>mdi-delete</v-icon></v-btn></td>
  </tr>
  
</template>

<script>
import axios from "axios";
export default {
    props: ["tip"],
    methods: {
      deleteTip: function(){
        axios
            .delete('tip/'+this.tip.id)
            .then(() => {
                this.$store.commit("setSnackbar", {text:"Uspešno ste obrisali tipa", color: "success"});
                this.$emit("del-tip",this.tip.id);
            })
            .catch((error) => { console.log(error); 
               this.$store.commit("setSnackbar", {text:"Tip se koristi na poseti", color: "error"});
            });
      },
      otvoriDialog: function(){
        this.$emit("otvori",this.tip.id);
      }
    }
}
</script>

<style>

</style>