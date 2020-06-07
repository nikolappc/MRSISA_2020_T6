<template>
  <div class="profilPacijentaIzmjena">
    <v-container>
      <v-form
        ref="form"
        v-model="valid"
      ><v-text-field
          v-model="ulogovani.ime"
          :rules="imeRules"
          label="Ime"
          required
		></v-text-field>
        <v-text-field
          v-model="ulogovani.prezime"
          :rules="prezimeRules"
          label="Prezime"
          required
        ></v-text-field>
        <v-text-field
          v-model="ulogovani.password"
          :type="'password'"
          :rules="passwordRules"
          label="Lozinka"
          required
        ></v-text-field>
        <v-text-field
          v-model="novaSifraPotvrda"
          :rules="password2Rules"
          label="Potvrda lozinke"
          :type="'password'"
          required
        ></v-text-field>
        <v-text-field
          v-model="ulogovani.brojTelefona"
          :rules="brojTelefonaRules"
          label="Broj Telefona"
        ></v-text-field>
        <v-text-field
          v-model="ulogovani.adresa.adresa"
          :rules="adresaRules"
          label="Adresa"
        ></v-text-field>
        <v-text-field
          v-model="ulogovani.adresa.grad"
          :rules="adresaRules"
          label="Grad"
        ></v-text-field>
        <v-text-field
          v-model="ulogovani.adresa.drzava"
          :rules="adresaRules"
          label="Država"
        ></v-text-field>
        <v-text-field
          v-model="ulogovani.jbo"
          :rules="jboRules"
          :disabled="true"
          label="Jedinstveni broj osiguranika"
        ></v-text-field>
        <v-btn
          :disabled="!valid"
          color="success"
          class="mr-4"
          @click="izmeniProfil"
        >
          Izmeni profil
        </v-btn>

      </v-form>
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
          password: '',
          adresa: {
              adresa: ''
          }
      },
      novaSifraPotvrda: '',
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
      ],
      password2Rules: [
        v => !!v || 'Potvrda lozinke je obavezno polje',
        v => v === this.ulogavani.password || 'Lozinke se ne poklapaju' 
      ],
    }
	},
      
  mounted () {
    this.ulogovani = this.$store.state.ulogovan;
    this.novaSifraPotvrda = this.ulogovani.password;
	if (this.ulogovani=="") {
		router.push("/");
	}
  },
  methods: {
    izmeniProfil: function(event) {
        event.preventDefault();
        axios
        .put('api/'+this.ulogovani.id,this.ulogovani)
        .then(response => {
            this.$store.commit("setUlogovan", response.data);
            this.$store.commit("setSnackbar", {text:"Uspešno ste izmenili profil", color: "success"});
            router.go("/izmenaNaloga");
        })
        .catch(function (error) { console.log(error); });
    }
  },
    
}
</script>


