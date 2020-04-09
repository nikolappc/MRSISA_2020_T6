<template>
  <div class="profilPacijenta">
    <h1>Moj profil {{ ulogovan.email }}</h1>
    <h3>Lozinka: {{ ulogovan.lozinka }}</h3>
    <h3>Ime: {{ ulogovan.ime }}</h3>
    <h3>Prezime: {{ ulogovan.prezime }}</h3>
    <h3>Adresa: {{ ulogovan.adresa }}</h3>
    <h3>Grad: {{ ulogovan.grad }}</h3>
    <h3>Drzava: {{ ulogovan.drzava }}</h3>
    <h3>Telefon: {{ ulogovan.telefon }}</h3>
    <h3>Osiguranik: {{ ulogovan.osiguranik }}</h3>
  </div>
</template>

<script>
import axios from 'axios';
export default {
  name: 'ProfilPacijenta',
  data: () => ({
    ulogovan : {},
  }),
  mounted () {
      axios
      .get('api/pacijent/ulogovan')
      .then(response => {
          this.ulogovan = response.data;
      })
      .catch(function (error) { console.log(error); });
  }
}
</script>