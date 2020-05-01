<template>
    <v-app-bar v-if="$route.path != '/login'"
      app
      color="primary"
      dark
      :img="header"
    >
      Klinički centar
      <v-spacer></v-spacer>

      <v-btn icon @click="homeFunc"><v-icon color="white">mdi-home</v-icon></v-btn>
      <router-link v-if="ulogovan=={}"  to="/login"><v-btn icon><v-icon color="white">mdi-account</v-icon></v-btn></router-link>
      <router-link v-if="ulogovan=={}"  to="/login"><v-btn icon><v-icon color="white">mdi-account</v-icon></v-btn></router-link>
      <v-btn @click="logoutFunc" icon><v-icon color="white">mdi-logout</v-icon></v-btn>
    </v-app-bar>
</template>
<script>
    const axios = require('axios');
    export default {
        name:"Header",
        data:function () {
            return {
                ulogovan : {}
            };
        },
        methods:{
            logoutFunc: function() {
                axios
                .get('api/logout')
                .then(() => {
                    this.$router.push("/loginPage");
                })
                .catch(function (error) { console.log(error); });
            },
            homeFunc:function () {
                if (this.ulogovan.tip == "PACIJENT") {
                    this.$router.push("/homePacijent");
                } else if (this.ulogovan.tip == "LEKAR") {
                    this.$router.push("/homeLekar");
                }
                else if (this.ulogovan.tip == "SESTRA") {
                    this.$router.push("/homeMed");
                }
                else if (this.ulogovan.tip == "ADMIN_K") {
                    this.$router.push("/homeAdminKlinike");
                }
                else {
                    this.$router.push("/homeAdminKC");
                }
            }
        },
        mounted:function(){
            axios
                .get('api/ulogovan')
                .then(response => {
                    this.ulogovan = response.data;
                })
                .catch(function (error) { console.log(error); this.$router.push("/loginPage"); });
        }
    }
</script>