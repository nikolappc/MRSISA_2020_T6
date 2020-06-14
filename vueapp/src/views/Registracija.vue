<template>
  <div class="registracija" style="width:100%;">
    <v-container style="width:100%;">
      <v-card outlined style="width:100%;">
        <v-container>
          <v-card><v-form
            ref="form"
            v-model="valid"
          >
			<v-simple-table>
            <tr><td colspan="3"><v-text-field
              v-model="reg.email"
              :rules="emailRules"
              label="E-mail"
              required
            ></v-text-field></td></tr>
            <tr><td colspan="3"><v-text-field
              v-model="novaSifra"
              :rules="passwordRules"
              label="Lozinka"
              required
            ></v-text-field></td></tr>
            <tr><td colspan="3"><v-text-field
              v-model="novaSifraPotvrda"
              :rules="password2Rules"
              label="Potvrda lozinke"
              required
            ></v-text-field></td></tr>
            <tr><td colspan="3"><v-text-field
              v-model="reg.ime"
              :rules="Rules"
              label="Ime"
              required
            ></v-text-field></td></tr>
            <tr><td colspan="3"><v-text-field
              v-model="reg.prezime"
              :rules="Rules"
              label="Prezime"
              required
            ></v-text-field></td></tr>
            <tr><td colspan="3">
              <AutocompleteAdresa v-bind:adresa="reg.adresa"/>
            </td></tr>
            <tr><td colspan="3"><v-text-field
              v-model="reg.brojTelefona"
              :rules="Rules"
              label="Broj telefona"
            ></v-text-field></td></tr>
            <tr><td colspan="3"><v-text-field
              v-model="reg.jbo"
              :rules="Rules"
              label="Jedinstveni broj osiguranika"
            ></v-text-field></td></tr>
            <v-btn
              :disabled="!valid"
              color="success"
              class="mr-4"
              @click="posaljiZahtjev"
            >
              Posalji zahtjev za registraciju
            </v-btn>
            </v-simple-table>
          </v-form></v-card>
        </v-container>
      </v-card>
    </v-container>
  </div>
</template>


<script>
import axios from 'axios';
import router from "../router/index.js";
import AutocompleteAdresa from "../components/Autocomplete.vue";
export default {
  components:{
    AutocompleteAdresa
  },
  name: 'Registracija',
  data: function () {
    return {
      ulogovan : {},
      reg : {
        adresa:{
          grad:"",
          adresa:"",
          drzava:""
        }
      },
      novaSifra : "",
      novaSifraPotvrda : "",
      valid: true,

      emailRules: [
        v => !!v || 'Obavezno polje',
        v => /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(v) || 'E- mail adresa nije validna'
      ],
      Rules: [
        v => !!v || 'Obavezno polje'
      ],
      passwordRules: [
        v => !!v || 'Obavezno polje',
      ],
      password2Rules: [
        v => !!v || 'Obavezno polje',
        v => v === this.novaSifra || 'Lozinke se ne poklapaju'
      ],
    }
	},
      
  mounted () {
  console.log(this.$store.state.ulogovan);
	if (this.$store.state.ulogovan != {} && this.$store.state.ulogovan != ""  && this.$store.state.ulogovan != null && this.$store.state.ulogovan != undefined) {
          router.push("/");
	}
  },
  computed:{
    ulogovani(){
      return this.$store.state.ulogovan;
    }
  },
  methods: {
    getAddressData(formattedData) {
          try{
            console.log(formattedData);
            this.reg.adresa.adresa = formattedData.name;
            if(formattedData.locality != undefined && formattedData != null)
              this.reg.adresa.grad = formattedData.locality;
            else
              this.reg.adresa.grad = formattedData.administrative_area_level_1;
            this.reg.adresa.drzava = formattedData.country;

          }
          catch{
            this.reg.adresa = {adresa: '', grad: '', drzava: ''};
          }
        },
    posaljiZahtjev: function(event) {
		this.reg.password = this.novaSifra;
        event.preventDefault();
        if (this.novaSifra == this.novaSifraPotvrda) {
			axios
			.post('api/registracija',this.reg)
			.then(() => {
			this.$store.commit("setSnackbar", {text:"Poslali ste zahtev za registraciju. Bice Vam poslat email na adresu \n" + this.reg.email + ", gde mozete verifikovati VaÅ¡ nalog.", color: "success"});
				router.push("/");
			})
			.catch(error => { 
				console.log(error); 
				if (error.response) {
					this.$store.commit("setSnackbar", {text: "Greska", color: "error"});
				}
			});
		} else {
			this.$store.commit("setSnackbar", {text:"Lozinke se ne poklapaju.", color: "error"});
		}
        
    },
  },

    
}
</script>


