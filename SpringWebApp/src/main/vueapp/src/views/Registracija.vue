<template>
  <div class="registracija">
    <v-container>
      <v-card outlined>
        <v-container>
          <v-form
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
            <tr><td><v-text-field
              v-model="reg.adresa"
              :rules="Rules"
              label="Adresa prebivalista"
              required
            ></v-text-field></td>
            <td><v-text-field
              v-model="reg.grad"
              :rules="Rules"
              label="Grad"
              required
            ></v-text-field></td>
			<td><v-text-field
              v-model="reg.drzava"
              :rules="Rules"
              label="Država"
              required
            ></v-text-field></td></tr>
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

          </v-form>
        </v-container>
      </v-card>
    </v-container>
  </div>
</template>


<script>
import axios from 'axios';
import router from "../router/index.js"
export default {
  name: 'Registracija',
  data: function () {
    return {
      ulogovan : {},
      reg : {},
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
    posaljiZahtjev: function(event) {
		this.reg.password = this.novaSifra;
        event.preventDefault();
        if (this.novaSifra == this.novaSifraPotvrda) {
			axios
			.post('api/registracija',this.reg)
			.then(() => {
			this.$store.commit("setSnackbar", {text:"Poslali ste zahtev za registraciju. Biće Vam poslat email na adresu \n" + this.reg.email + ", gde možete verifikovati Vaš nalog.", color: "success"});
				//alert("Poslali ste zahtev za registraciju. Biće Vam poslat email na adresu \n" + this.reg.email + ", gde možete verifikovati Vaš nalog.")
				router.push("/");
			})
			.catch(error => { 
				console.log(error); 
				if (error.response) {
					//alert(error.response.data);
					this.$store.commit("setSnackbar", {text:error.response.data, color: "error"});
				}
			});
		} else {
			this.$store.commit("setSnackbar", {text:"Lozinke se ne poklapaju.", color: "error"});
		}
        
    },
  },
  watch: {
    novaSifra: function() {
      this.$nextTick(() => {
        //this.$refs.form.validate();
        this.novaSifraPotvrda += "";
      });
    },
    novaSifraPotvrda: function() {
      this.$nextTick(() => {
        //this.$refs.form.validate();
        this.novaSifra += "";
      });
    }
  },
    
}
</script>


