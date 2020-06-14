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
                            
                            <v-menu
                            
                            :close-on-content-click = "false"
                            v-model="menu"
                            >
                                <template v-slot:activator="{ on, attrs }">
                                    <v-btn color="error"
                                    v-bind="attrs"
                                    v-on="on"
                                    
                                    > 
                                        Odbij
                                    </v-btn>
                                
                                </template>
                                <v-card>
                                    <v-card
                                        color="error"
                                    >
                                        Razlog odbijanja
                                    </v-card>
                                    <div class="pa-5">

                                        <v-textarea
                                            label="Razlog"
                                            :rules="[v=>!!v||'Ovo polje je obavezno']"
                                            required
                                            v-model="opis"
                                        ></v-textarea>
                                        <v-btn color="warning" @click="deny">Potvrdi</v-btn>
                                    </div>
                                </v-card>
                            </v-menu>
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
                opis:"",
                menu:false
            }
        },
        methods:{
            allow:function () {
                this.overlay = true;
                axios.get("/api/approveRegistration/"+this.user.email)
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
            deny:function () {
                this.menu = false;
                this.overlay = true;
                axios.post("/api/denyRegistration/"+this.user.email, {razlog:this.opis})
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