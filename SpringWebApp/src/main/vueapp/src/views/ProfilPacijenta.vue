<template>
  <div class="profilPacijenta">
    <v-simple-table>
      <thead>
        <th>Moj profil</th>
        <th>{{ ulogovan.email }}</th>
      </thead>
      <tbody>
        <tr>
          <td>Korisnicko ime:</td>
          <td>{{ ulogovan.username }}</td>
        </tr>
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