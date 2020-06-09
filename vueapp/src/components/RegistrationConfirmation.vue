<template>
    <v-card>
        <v-container>
            <v-simple-table>
                <tbody>
                    <tr>
                        <td>
                            <display-1
                            >
                                Zahtev za registraciju
                            </display-1>
                        </td>
                        <td>
                            {{user.email}}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Ime i prezime
                        </td>
                        <td>
                            {{user.ime+' ' + user.prezime}}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <v-btn color="success" @click="allow()">
                                Odobri
                            </v-btn>
                        </td>
                        <td>
                            <v-btn color="error" @click="deny()"> 
                                Odbij
                            </v-btn>
                        </td>
                    </tr>
                </tbody>
            </v-simple-table>
        </v-container>

        <v-overlay
            v-model="overlay"
            absolute="true"
        >
            <v-progress-circular
                :indeterminate="overlay"
                :width="7"
                color="primary"
            >
            </v-progress-circular>
        </v-overlay>
    </v-card>
</template>
<script>
    const axios = require("axios");
    export default{
        name:"RegConf",
        props:[
            "user",
        ],
        data:function () {
            return{
                overlay:false,
            }
        },
        methods:{
            allow:function () {
                this.notifyServer("/api/approveRegistration");
            },
            deny:function () {
                this.notifyServer("/api/denyRegistration");
            },
            notifyServer:function(address){
                this.overlay = true;
                axios.get(address+"/"+this.user.email)
                    .then(res=>{
                        this.$store.commit("setSnackBar", {text:res.data, color:"success"});
                        this.$emit("resolved");
                        this.overlay = false;
                    })
                    .catch(error=>{
                        console.log(error);
                        this.overlay = false;    
                    });
            },
        }
    }
</script>