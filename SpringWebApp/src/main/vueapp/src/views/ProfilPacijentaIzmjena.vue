<template>
  <div class="profilPacijentaIzmjena">
    <v-container>
      <v-card><v-form
        ref="form"
        v-model="valid"
      >
		<v-text-field
          v-model="izmijenjen.ime"
          :rules="imeRules"
          label="Ime"
          required
		></v-text-field>
        <v-text-field
          v-model="izmijenjen.prezime"
          :rules="prezimeRules"
          label="Prezime"
          required
        ></v-text-field>
        <v-text-field
          v-model="novaSifra"
          :rules="passwordRules"
          label="Lozinka"
          required
        ></v-text-field>
        <v-text-field
          v-model="novaSifraPotvrda"
          :rules="password2Rules"
          label="Potvrda lozinke"
          required
        ></v-text-field>
        <v-text-field
          v-model="izmijenjen.brojTelefona"
          :rules="brojTelefonaRules"
          label="Broj Telefona"
        ></v-text-field>
        <v-text-field
          v-model="izmijenjen.adresa.adresa"
          :rules="adresaRules"
          label="Adresa"
        ></v-text-field>
        <v-text-field
          v-model="izmijenjen.adresa.grad"
          :rules="adresaRules"
          label="Grad"
        ></v-text-field>
        <v-text-field
          v-model="izmijenjen.adresa.drzava"
          :rules="adresaRules"
          label="Država"
        ></v-text-field>
        <v-text-field
          v-model="izmijenjen.jbo"
          :rules="jboRules"
          label="Jedinstveni broj osiguranika"
        ></v-text-field>
        <v-btn
          :disabled="!valid"
          color="success"
          class="mr-4"
          @click="izmijeniProfil"
        >
          Izmeni profil
        </v-btn>

      </v-form></v-card>
    </v-container>
  </div>
</template>


<script>
import axios from 'axios';
import router from "../router/index.js"
export default {
  name: 'ProfilPacijentaIzmjena',
  data: function () {
    return {
      ulogovani : {
        id : "",
        email : "",
        password : "",
        ime : "",
        prezime : "",
        adresa : {
			adresa : "",
			grad : "",
			drzava : ""
        },
        brojTelefona : "",
        jbo : ""
      },
      izmijenjen : {},
      novaSifra : "",
      novaSifraPotvrda : "",
      valid: true,

      imeRules: [
        v => !!v || 'Ime je obavezno polje'
      ],
      prezimeRules: [
        v => !!v || 'Prezime je obavezno polje'
      ],
      brojTelefonaRules: [
        v => !!v || 'Broj telefona je obavezno polje'
      ],
      adresaRules: [
        v => !!v || 'Adresa je obavezno polje',
		//v => v.split(", ").length == 3 || 'Neispravan format adrese.'
      ],
      jboRules: [
        v => !!v || 'Jedinstveni broj osiguranika je obavezno polje'
      ],
      passwordRules: [
        v => !!v || 'Lozinka je obavezno polje',
        v => v === this.novaSifraPotvrda || 'Lozinke se ne poklapaju' 
      ],
      password2Rules: [
        v => !!v || 'Potvrda lozinke je obavezno polje',
        v => v === this.novaSifra || 'Lozinke se ne poklapaju' 
      ],
    }
	},
      
  mounted () {
	this.ulogovani = this.$store.state.ulogovan;
	if (this.ulogovani=="") {
		router.push("/");
	} else {
		this.izmijenjen.id = this.ulogovani.id;
		this.izmijenjen.ime = this.ulogovani.ime;
		this.izmijenjen.prezime = this.ulogovani.prezime;
		this.izmijenjen.adresa = this.ulogovani.adresa;
		this.izmijenjen.brojTelefona=this.ulogovani.brojTelefona;
		this.izmijenjen.jbo = this.ulogovani.jbo;
		this.novaSifra = this.ulogovani.password;
		this.novaSifraPotvrda = this.ulogovani.password;
	}
  },
  methods: {
    izmijeniProfil: function(event) {
		this.izmijenjen.password = this.novaSifra;
        event.preventDefault();
        axios
        .post('api/pacijent/izmjena',this.izmijenjen)
        .then(response => {
			this.$store.commit("setUlogovan", response.data);
            router.push("/profilPacijenta");
        })
        .catch(function (error) { console.log(error); });
    }
  },
    
}
</script>


