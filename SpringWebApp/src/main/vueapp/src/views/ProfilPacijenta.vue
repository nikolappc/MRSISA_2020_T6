<template>
  <div class="profilPacijenta">
    <v-container>
      <v-simple-table>
        <thead>
          <th>Moj profil</th>
          <th>{{ ulogovan.email }}</th>
        </thead>
        <tbody>
          <tr>
            <td>Lozinka:</td>
            <td>{{ ulogovan.password }}</td>
          </tr>
          <tr>
            <td>Ime:</td>
            <td>{{ ulogovan.ime }}</td>
          </tr>
          <tr>
            <td>Prezime:</td>
            <td>{{ ulogovan.prezime }}</td>
          </tr>
          <tr>
            <td>Adresa:</td>
            <td>{{ ulogovan.adresa }}</td>
          </tr>
          <tr>
            <td>Telefon:</td>
            <td>{{ ulogovan.brojTelefona }}</td>
          </tr>
          <tr>
            <td>Jedinstveni broj osiguranika:</td>
            <td>{{ ulogovan.jbo }}</td>
          </tr>
        </tbody>
      </v-simple-table>
      <v-btn :to="{path: 'profilPacijentaIzmjena'}" dark medium left class="blue" slot="action">Izmjena profila</v-btn>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios';
import router from "../router/index.js"

export default {
  name: 'ProfilPacijenta',
  data: () => ({
    ulogovan : {},
  }),
  mounted () {
      axios
      .get('api/ulogovan')
      .then(response => {
          this.ulogovan = response.data;
      })
      .catch(function (error) { console.log(error); router.push("/"); });
  }
}
</script>