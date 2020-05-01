<template>
    <v-app-bar v-if="$route.path != '/login'"
        color="primary"
        app
        dark
    >
        Klinički centar
        {{ulogovan}}
        <v-spacer></v-spacer>

        <router-link to="/">
            <v-btn icon><v-icon color="white">mdi-home</v-icon></v-btn>
        </router-link>
        <router-link v-if="!ulogovan"  to="/loginPage">
            <v-btn icon><v-icon color="white">mdi-login</v-icon></v-btn>
        </router-link>
        <v-btn v-if="ulogovan" @click="logoutFunc" icon><v-icon color="white">mdi-logout</v-icon></v-btn>
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
            }

        },
        methods:{
            logoutFunc: function() {
                axios
                .get('api/logout')
                .then(() => {
                    this.$router.push("/loginPage");
                })
                .catch(function (error) { console.log(error); });
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