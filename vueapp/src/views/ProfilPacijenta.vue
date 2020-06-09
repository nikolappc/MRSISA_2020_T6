<template>
  <div class="profilPacijenta" style="width:100%;">
    <v-container style="width:100%;">
      <v-card style="width:100%;"><v-card-text><v-simple-table>
        <thead>
          <th id="profil">Moj profil</th>
          <th id="email">{{ ulogovani.email }}</th>
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
            <td>{{ toStringAdresa(ulogovani) }}</td>
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
      </v-simple-table></v-card-text>
      <v-card-actions><v-btn :to="{path: 'profilPacijentaIzmjena'}" dark medium left class="blue" slot="action">Izmena profila</v-btn></v-card-actions></v-card>
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
  methods:{
    toStringAdresa:function(p){
        return p.adresa.adresa+ ", " + p.adresa.grad + ", " + p.adresa.drzava;
    },
  },
  mounted () {
    this.ulogovani = this.$store.state.ulogovan;
    if (this.ulogovani=="") {
      router.push("/");
    }
  }
}
</script>