<template >
  <v-container class="blue lighten-1 fill-height"
  fluid
  >
    <v-row justify="center">
      <v-col cols="6">
        <v-card
          outlined
        >

          <v-form 
            ref="form"
            v-model="valid"
          >
            <v-container>
              
              <v-text-field
                  v-model="username"
                  :rules="usernameRules"
                  label="Korisnicko ime"
                  required
                ></v-text-field>
    
                <v-text-field
                  :type="'password'"
                  v-model="password"
                  :rules="passwordRules"
                  label="Lozinka"
                  required
                ></v-text-field>
                <v-btn
                  :disabled="!valid"
                  color="success"
                  class="mr-4"
                  @click="login"
                >
                  Login
                </v-btn>
                <v-btn small depressed :to="{path: 'registracija'}" slot="action">Registruj se</v-btn>
            </v-container>
          </v-form>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
import router from "../router/index.js"
export default {
  data: function(){ return{
    valid: false,
        username: '',
        password: '',
        passwordRules: [
        v => !!v || 'Password je obavezno polje'],
        usernameRules: [v => !!v || 'Username je obavezno polje'],
    }
    },
    mounted(){
		if (this.$store.state.ulogovan != {} && this.$store.state.ulogovan != ""  && this.$store.state.ulogovan != null && this.$store.state.ulogovan != undefined) {
			router.push("/");
		}
    },
    methods: {
        login: function() {
            axios
            .post('api/login',{ username: this.username, password: this.password})
            .then(res => {
              console.log(res.data);
              this.$store.commit("setUlogovan", res.data);
              router.push("/");
            })
            .catch( () => { this.$store.commit("setSnackbar", {text:"Neispravno korisničko ime ili lozinka, pokusajte ponovo.", color: "error"}); });
        }
    }
}
</script>

<style scoped>
  .v-card{
    padding: 20px;
  }
</style>