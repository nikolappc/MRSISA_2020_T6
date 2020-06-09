<template>
    <v-card
        class="card"
    >
        <v-container>
            <v-simple-table>
                <tbody>
                    <tr>
                        <td>
                            <display-1
                                v-if="pregled"
                            >
                                Zahtev za pregled sa ID: {{poseta.id}}
                            </display-1>
                            <display-1
                                v-else
                            >
                                Zahtev za operaciju sa ID: {{poseta.id}}
                        </display-1>
                        </td>
                        <td>
                            <v-btn color="success" @click="predjiNaDodeluSale">
                                Postavi salu
                            </v-btn>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            Pacijent
                        </td>
                        <td>
                            {{poseta.pacijent.ime+' ' + poseta.pacijent.prezime}}
                        </td>
                    </tr>
                </tbody>
            </v-simple-table>
        </v-container>
    </v-card>
</template>
<script>
    // const axios = require("axios");
    export default{
        name:"PosetaConf",
        props:{
            poseta:Object,
            pregled:Boolean
        },
        methods:{
            predjiNaDodeluSale:function () {
                this.$router.push("/potvrdaPosete/"+this.poseta.id+"/"+this.pregled);
            }
        },
        mounted(){
            console.log(this.poseta);
            
        }
    }
</script>

<style>
.card{
    max-height: 250px;
}    
</style>