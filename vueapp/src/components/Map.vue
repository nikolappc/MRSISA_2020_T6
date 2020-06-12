<template>
  <div class="mapouter">
          <div class="gmap_canvas">
            <iframe width="150" height="150" id="gmap_canvas" :src= napraviAdresu(adresaMape) frameborder="0" scrolling="no" marginheight="0" marginwidth="0"></iframe>
          </div>
        </div>
</template>

<script>
export default {
    props:["adresaMape"],
    methods: {
      napraviAdresu: function(adresa){
        return "https://maps.google.com/maps?q=" + encodeURI(adresa.adresa + ", "  + adresa.grad + ", "  +  adresa.drzava) + "&t=&z=9&ie=UTF8&iwloc=&output=embed"; 
      },
    }
}
</script>

<style>
.mapouter{position:relative;text-align:right;height:150px;width:150px;}.gmap_canvas {overflow:hidden;background:none!important;height:200px;width:200px;}

</style>