<template>
    <v-app-bar v-if="$route.path != '/login'"
        color="primary"
        app
        dark
    >
        Klinički centar
        <v-spacer></v-spacer>
        <router-link v-if="ulogovan.tip == 'ADMIN_K'"  to="/izmenaKlinike">
            <v-btn icon><v-icon color="white">mdi-hospital-building</v-icon></v-btn>
        </router-link>
        <router-link to="/izmenaNaloga">
            <v-btn icon><v-icon color="white">mdi-account</v-icon></v-btn>
        </router-link>
        <router-link to="/">
            <v-btn icon><v-icon color="white">mdi-home</v-icon></v-btn>
        </router-link>
        <router-link v-if="!ulogovan"  to="/loginPage">
            <v-btn icon><v-icon color="white">mdi-login</v-icon></v-btn>
        </router-link>
        
        
        <v-btn v-if="ulogovan" @click="logoutFunc" icon><v-icon color="white">mdi-logout</v-icon></v-btn>
        <v-snackbar
            v-model="snackbarSH"
            :timeout="5000"
            :color="snackbarColor"
            :style="'top: 10px'"
            >
            {{snackbarText}}

            <v-btn text @click="zatvori">
                <v-icon>mdi-close</v-icon>
            </v-btn>
        </v-snackbar>
    </v-app-bar>
</template>
<script>
    const axios = require('axios');
    export default {
        name:"Header",
        data:function () {
            return{
            }
        },
        computed:{
            ulogovan(){
                return this.$store.state.ulogovan
            },
            snackbarSH(){
                return this.$store.state.snackbar.showing
            },
            snackbarText(){
                return this.$store.state.snackbar.text
            },
            snackbarColor(){
                return this.$store.state.snackbar.color
            }

        },
        methods:{
            logoutFunc: function() {
                axios
                .get('api/logout')
                .then(() => {
					this.$store.commit("setUlogovan", "");
					console.log(this.$store.state.ulogovan);
                    this.$router.push("/loginPage");
                })
                .catch(function (error) { console.log(error); });
            },
            zatvori(){
                this.$store.state.snackbar.showing = false;
            }
        },
        mounted:function(){
            axios
                .get('api/ulogovan')
                .then(response => {
                    this.ulogovan = response.data;
                    this.$store.commit("setUlogovan", this.ulogovan);
                })
                .catch(error=>{ 
                    console.log(error);
                    // this.$router.push("/loginPage"); 
                });
        }
    }
</script>