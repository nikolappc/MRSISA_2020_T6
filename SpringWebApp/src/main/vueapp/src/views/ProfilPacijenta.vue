<template>
  <div class="profilPacijenta">
    <v-container>
      <v-simple-table>
        <thead>
          <th>Moj profil</th>
          <th>{{ ulogovani.email }}</th>
        </thead>
        <tbody>
          <tr>
            <td>Lozinka:</td>
            <td>{{ ulogovani.password }}</td>
          </tr>
          <tr>
            <td>Ime:</td>
            <td>{{ ulogovani.ime }}</td>
          </tr>
          <tr>
            <td>Prezime:</td>
            <td>{{ ulogovani.prezime }}</td>
          </tr>
          <tr>
            <td>Adresa:</td>
            <td>{{ ulogovani.adresa }}</td>
          </tr>
          <tr>
            <td>Telefon:</td>
            <td>{{ ulogovani.brojTelefona }}</td>
          </tr>
          <tr>
            <td>Jedinstveni broj osiguranika:</td>
            <td>{{ ulogovani.jbo }}</td>
          </tr>
        </tbody>
      </v-simple-table>
      <v-btn :to="{path: 'profilPacijentaIzmjena'}" dark medium left class="blue" slot="action">Izmjena profila</v-btn>
    </v-container>
  </div>
</template>

<script>
import router from "../router/index.js"

export default {
  name: 'ProfilPacijenta',
  data: () => ({
    ulogovani : {},
  }),
  mounted () {
	this.ulogovani = this.$store.state.ulogovan;
	if (this.ulogovani=="") {
		router.push("/");
	} 
	
	/*axios
      .get('api/ulogovan')
      .then(response => {
          this.ulogovan = response.data;
      })
      .catch(function (error) { console.log(error); router.push("/"); });*/
  }
}
</script>