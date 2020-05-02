<template>
  <div class="registracija">
    <v-container>
      <v-card outlined>
        <v-container>
          <v-form
            ref="form"
            v-model="valid"
          >
            <v-text-field
              v-model="reg.email"
              :rules="emailRules"
              label="E-mail"
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
              v-model="reg.ime"
              :rules="Rules"
              label="Ime"
              required
            ></v-text-field>
            <v-text-field
              v-model="reg.prezime"
              :rules="Rules"
              label="Prezime"
              required
            ></v-text-field>
            <v-text-field
              v-model="reg.adresa"
              :rules="Rules"
              label="Adresa prebivalista"
              required
            ></v-text-field>
            <v-text-field
              v-model="reg.grad"
              :rules="Rules"
              label="Grad"
              required
            ></v-text-field>
            <v-text-field
              v-model="reg.drzava"
              :rules="Rules"
              label="Drzava"
              required
            ></v-text-field>
            <v-text-field
              v-model="reg.brojTelefona"
              :rules="Rules"
              label="Broj Telefona"
            ></v-text-field>
            <v-text-field
              v-model="reg.jbo"
              :rules="Rules"
              label="Jedinstveni broj osiguranika"
            ></v-text-field>
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
        v => v === this.novaSifraPotvrda || 'Lozinke se ne poklapaju'
      ],
      password2Rules: [
        v => !!v || 'Obavezno polje',
        v => v === this.novaSifra || 'Lozinke se ne poklapaju'
      ],
    }
	},
      
  mounted () {
      axios
      .get('api/ulogovan')
      .then(response => {
          this.ulogovan = response.data;
          router.push("/");
      })
      .catch(function (error) { console.log(error);  });   
  },
  methods: {
    posaljiZahtjev: function(event) {
		this.reg.password = this.novaSifra;
        event.preventDefault();
        axios
        .post('api/registracija',this.reg)
        .then(() => {
            alert("Poslali ste zahtjev za registraciju. Bice Vam poslat email na adresu \n" + this.reg.email + ", gdje mozete verifikovati Vas nalog.")
            router.push("/");
        })
        .catch(function (error) { 
            console.log(error); 
            if (error.response) {
                alert(error.response.data);
            }
        });
    },
  },
  watch: {
    novaSifra: function() {
      this.$nextTick(() => {
        this.$refs.form.validate();
      });
    },
    novaSifraPotvrda: function() {
      this.$nextTick(() => {
        this.$refs.form.validate();
      });
    }
  },
    
}
</script>


