<template>
    <div>
        <v-container>
            <v-row
                align="center"
                justify-content="center"
            >
                <v-col v-if="klinike.length!=0"> 
                    <v-card>
                        <v-container>
                            <v-simple-table>
                                <thead>
                                    <tr>
                                        <th>
                                            Naziv
                                        </th>
                                        <th>
                                            Adresa
                                        </th>
                                        <th>
                                            Specijalizacija
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="k in klinike" :key="k.id"> 
                                        <td>
                                            {{k.naziv}}
                                        </td>
                                        <td>
                                            {{k.adresa}}
                                        </td>
                                        <td>
                                            {{k.tipKlinike.tip}}
                                        </td>
                                    </tr>
                                </tbody>
                            </v-simple-table>
                        </v-container>
                    </v-card>
                </v-col>
                <v-col v-else>
                    <v-card>
                        <v-container>
                            <v-row
                            align="center"
                            justify-content="center">
                                <v-col>
                                    <v-card-title>
                                        Nema registrovanih klinika.    
                                    </v-card-title>
                                </v-col>
                                <v-col>
                                    <router-link to="/regKlinika">
                                        <v-btn color="success">
                                            Registruj kliniku
                                        </v-btn>
                                    </router-link>
                                </v-col>
                            </v-row>
                        </v-container>
                    </v-card>
                </v-col>
            </v-row>
        </v-container>
    </div>
</template>

<script>
    const axios = require('axios');
    export default {
        name:"PregledKlinika",
        data:function () {
            return {
                klinike:[]
            }
        },
        mounted:function () {
            axios.get("/klinika")
                .then(res=>{
                    this.klinike = res.data;
                    console.log(this.klinike);
                })
                .catch(error=>{
                    alert(error);
                });
        }
    }
</script>