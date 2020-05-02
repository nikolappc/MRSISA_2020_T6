<template >
  <v-container class="blue lighten-1"
  fluid
  height="100%">
  <v-row justify="center">
    <v-col cols="6">
      <v-card
        outlined
      >

      <v-form 
        ref="form"
        v-model="valid"
      >
      <v-text-field
          v-model="username"
          :rules="usernameRules"
          label="Username"
          required
        ></v-text-field>

        <v-text-field
          :type="'password'"
          v-model="password"
          :rules="passwordRules"
          label="Password"
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
      </v-form>
      </v-card>
    </v-col>
  </v-row>
  <v-row>
    <v-col>
      <router-link :to="{path: 'registracija'}" slot="action">Registruj se</router-link>
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
    methods: {
        login: function() {
            axios
            .post('api/login',{ username: this.username, password: this.password})
            .then(() => {
                router.push("/");
            })
            .catch(function () { alert("Prijava nije uspjela. Pokusajte ponovo ili kreirajte nalog!"); router.push("/"); });
        }
    }
}
</script>

<style scoped>
  .v-card{
    padding: 20px;
  }
</style>