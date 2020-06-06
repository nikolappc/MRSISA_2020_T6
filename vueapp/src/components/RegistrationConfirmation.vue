<template>
    <v-card>
        <v-row>
            <v-col
                cols="12"
            >
            </v-col>
                <v-container>
                    <v-card-title>
                        {{username}}
                    </v-card-title>
                </v-container>
            <v-col
                cols="12"
            >
                <v-row
                    justify="end"
                >
                    <v-col
                        lg="12"
                        cols="6"
                        class="ml-auto"
                    >
                        <v-row
                            justify="center"
                            align="center"
                        >
                            <v-col
                                cols="6"
                            >
                                <v-row        
                                    justify="center"
                                    align="center"
                                >
                                    <v-btn color="success" @click="allow()">
                                        Odobri
                                    </v-btn>
                                </v-row>
                            </v-col>
                            <v-col
                                cols="6"
                            >
                                <v-row
                                    justify="center"
                                    align="center"
                                >
                                    <v-btn color="error" @click="deny()"> 
                                        Odbij
                                    </v-btn>
                                </v-row>
                            </v-col>
                        </v-row>
                    </v-col>
                </v-row>
            </v-col>
        </v-row>
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
            "username",
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
                axios.get(address+"/"+this.username)
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
<style>
random{
    color:"#ffffff";
}

</style>