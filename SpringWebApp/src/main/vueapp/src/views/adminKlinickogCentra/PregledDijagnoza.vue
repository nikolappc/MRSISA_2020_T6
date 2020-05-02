<template>
    <div>
        <v-container>
            <v-row
                align="center"
                justify-content="center"
            >
                <v-col v-if="dijagnoze.length!=0"> 
                    <v-card>
                        <v-container>
                            <v-simple-table>
                                <thead>
                                    <tr>
                                        <th>
                                            Šifra dijagnoze
                                        </th>
                                        <th>
                                            Naziv dijagnoze
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="d in dijagnoze" :key="d.sifraDijagnoze">
                                        <td>
                                            {{d.sifraDijagnoze}}
                                        </td>
                                        <td>
                                            {{d.nazivDijagnoze}}
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
                                    <router-link to="/regDijagnoza">
                                        <v-btn color="success">
                                            Registrovanje dijagnoza
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
                                        Nema registrovanih dijagnoza.    
                                    </v-card-title>
                                </v-col>
                                <v-col>
                                    <router-link to="/regDijagnoza">
                                        <v-btn color="success">
                                            Registrovanje dijagnoza
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
        name:"PregledDijagnoza",
        data:function () {
            return {
                dijagnoze:[]
            }
        },
        mounted:function () {
            axios.get("/dijagnoza")
                .then(res=>{
                    this.dijagnoze = res.data;
                })
                .catch(error=>{
                    alert(error);
                });
        }
    }
</script>