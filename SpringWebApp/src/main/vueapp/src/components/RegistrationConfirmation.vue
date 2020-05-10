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
    </v-card>
</template>
<script>
    const axios = require("axios");
    export default{
        name:"RegConf",
        props:[
            "username",
        ],
        methods:{
            allow:function () {
                this.notifyServer("/api/approveRegistration");
            },
            deny:function () {
                this.notifyServer("/api/denyRegistration");
            },
            notifyServer:function(address){
                axios.get(address+"/"+this.username)
                    .then(res=>{
                        alert(res.data);
                        this.$emit("resolved");
                    })
                    .catch(error=>{
                        console.log(error);
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