<template>
  <tr>
      <td @click="otvoriDialog">{{lekar.ime}}</td>
      <td @click="otvoriDialog">{{lekar.prezime}}</td>
      <td>
        <div class="mapouter">
          <div class="gmap_canvas">
            <iframe width="205" height="288" id="gmap_canvas" :src= napraviAdresu(this.lekar.adresa) frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
          </div>
        </div>
      </td>
      <td @click="otvoriDialog">{{lekar.brojTelefona}}</td>
      <td><v-btn icon @click="deleteLekar"><v-icon>mdi-delete</v-icon></v-btn></td>
  </tr>
  
</template>

<script>
import axios from "axios";
export default {
    props: ["lekar"],
    data() {
      return {
        center: { lat: -3.350235, lng: 111.995865 },
        mapTypeId: "terrain",
        markers: [
          { position: { lat: -0.48585, lng: 117.1466 } },
          { position: { lat: -6.9127778, lng: 107.6205556 } }
        ]
      };
    },
    methods: {
      napraviAdresu: function(adresa){
        adresa;
        return "https://maps.google.com/maps?q=" + encodeURI(adresa.adresa + ", "  + adresa.grad + ", "  +  adresa.drzava) + "&t=&z=9&ie=UTF8&iwloc=&output=embed"; 
      },
      toStringAdresa:function(p){
        return p.adresa.adresa+ ", " + p.adresa.grad + ", " + p.adresa.drzava;
      },
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
.mapouter{position:relative;text-align:right;height:200px;width:200px;}.gmap_canvas {overflow:hidden;background:none!important;height:200px;width:200px;}
</style>