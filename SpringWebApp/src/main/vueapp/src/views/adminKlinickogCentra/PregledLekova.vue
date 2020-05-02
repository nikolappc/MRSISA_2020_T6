<template>
    <div>
        <v-container>
            <v-row
                align="center"
                justify-content="center"
            >
                <v-col v-if="lekovi.length!=0"> 
                    <v-card>
                        <v-container>
                            <v-simple-table>
                                <thead>
                                    <tr>
                                        <th>
                                            Šifra leka
                                        </th>
                                        <th>
                                            Naziv leka
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="l in lekovi" :key="l.sifraLeka">
                                        <td>
                                            {{l.sifraLeka}}
                                        </td>
                                        <td>
                                            {{l.nazivLeka}}
                                        </td>
                                    </tr>
                                </tbody>
                            </v-simple-table>
                            <v-row
                            align="center"
                            justify-content="center">
                                <v-spacer>
                                </v-spacer>
                                <v-col>
                                    <router-link to="/regLekova">
                                        <v-btn color="success">
                                            Registrovanje leka
                                        </v-btn>
                                    </router-link>
                                </v-col>
                            </v-row>
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
                                        Nema registrovanih lekova.    
                                    </v-card-title>
                                </v-col>
                                <v-col>
                                    <router-link to="/regLekova">
                                        <v-btn color="success">
                                            Registrovanje leka
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
        name:"PregledLekova",
        data:function () {
            return {
                lekovi:[]
            }
        },
        mounted:function () {
            axios.get("/lek")
                .then(res=>{
                    this.lekovi = res.data;
                })
                .catch(error=>{
                    alert(error);
                });
        }
    }
</script>