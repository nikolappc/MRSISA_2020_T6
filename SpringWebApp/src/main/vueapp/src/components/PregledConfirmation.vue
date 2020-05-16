<template>
    <v-card>
        <v-row>
            <v-col
                cols="12"
            >
            </v-col>
                <v-container>
                    <v-card-title>
                        Zahtev za pregled sa ID: {{idPregleda}}
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
                                    <v-btn color="success" @click="predjiNaDodeluSale">
                                        Postavi salu
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
    // const axios = require("axios");
    export default{
        name:"PregledConf",
        props:[
            "idPregleda",
        ],
        methods:{
            predjiNaDodeluSale:function () {
                this.$router.push("/sale/"+this.idPregleda);
            }
        }
    }
</script>
<style>
random{
    color:"#ffffff";
}

</style>