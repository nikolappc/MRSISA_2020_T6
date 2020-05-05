<template>
  <tr>
      <td>{{sala.id}}</td>
      <td>{{sala.naziv}}</td>
      <td><v-btn @click="dialog = true">Kalendar</v-btn></td>
      <v-dialog
                v-model="dialog"
                max-width="600"
                >
                <v-card>
                    <Kalendar v-bind:events="termini"/>
                </v-card>
                
      </v-dialog>
      <td><v-btn icon @click="deleteSala"><v-icon>mdi-delete</v-icon></v-btn></td>
  </tr>
  
</template>

<script>
import axios from "axios";
import Kalendar from "./Kalendar.vue";
export default {
    data: () => ({
        dialog : false
    }),
    props: ["sala","termini"],
    methods: {
      deleteSala: function(){
        axios
            .delete('sala/'+this.sala.id)
            .then(() => {
                this.$store.commit("setSnackbar", {text:"Uspešno ste obrisali salu", color: "success"});
                this.$emit("del-sala",this.sala.id);
            })
            .catch(() => { this.$store.commit("setSnackbar", {text:"Sala se već koristi u pregledu", color: "error"});});
      }
    },
    components: {
        Kalendar
    }
}
</script>

<style>

</style>