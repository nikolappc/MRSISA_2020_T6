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
                    <Kalendar />
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
    props: ["sala"],
    methods: {
      deleteSala: function(){
        axios
            .delete('sala/'+this.sala.id)
            .then(() => {
                alert("Uspesno ste obrisali salu");
                this.$emit("del-sala",this.sala.id);
            })
            .catch(function (error) { console.log(error); });
      }
    },
    components: {
        Kalendar
    }
}
</script>

<style>

</style>