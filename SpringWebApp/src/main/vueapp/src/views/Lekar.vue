<template>
  <tr>
      <td>{{lekar.ime}}</td>
      <td>{{lekar.prezime}}</td>
      <td>{{lekar.adresa}}</td>
      <td>{{lekar.brojTelefona}}</td>
  </tr>
  
</template>

<script>
export default {
    props: ["lekar"]
}
</script>

<style>

</style>