<template>
  <tr>
      <td @click="otvoriDialog">{{tip.id}}</td>
      <td @click="otvoriDialog">{{tip.naziv}}</td>
      <td @click="otvoriDialog">{{tip.tip}}</td>
      <td @click="otvoriDialog">{{tip.cena}}</td>
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
                alert("Uspesno ste obrisali tipa");
                this.$emit("del-tip",this.tip.id);
            })
            .catch(function (error) { console.log(error); });
      },
      otvoriDialog: function(){
        this.$emit("otvori",this.tip.id);
      }
    }
}
</script>

<style>

</style>