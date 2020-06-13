<template>
  <v-data-table
   :headers="headers"
   :items="pregledi"
  >

  </v-data-table>
</template>

<script>
export default {
    data:() =>{
        headers: [
        {
          text: 'Datum',
          sortable: true,
          value: 'termin',
        },
        {
          text: 'Lekar', 
          value: 'lekar', 
          sortable: true, 
        },
        {
          text: 'Sala', 
          value: 'sala.naziv', 
          sortable: true, 
        },
        {
          text: 'Tip', 
          value: 'tip', 
          sortable: true, 
        }
        
      ]
    }
}
</script>

<style>

</style>