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
                                    <tr v-for="d in dijagnoze" :key="d.sifraDijagnoze" @click="prikaziDialog(d)"> 
                                        <td>
                                            {{d.sifraDijagnoze}}
                                        </td>
                                        <td>
                                            {{d.nazivDijagnoze}}
                                        </td>
                                        <td>
                                            <td><v-btn icon @click="deleteDijagnoza(d.sifraDijagnoze)">
                                                <v-icon>mdi-delete</v-icon>
                                            </v-btn>
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
        <v-dialog
            v-model="dialog"
        >
            <IzmenaDijagnoze
                :dijagnoza="dijagnoza"
            >

            </IzmenaDijagnoze>


        </v-dialog>

    </div>
</template>

<script>
    const axios = require('axios');
    import IzmenaDijagnoze from "./IzmenaDijagnoze.vue";
    export default {
        name:"PregledDijagnoza",
        data:function () {
            return {
                dijagnoze:[],
                dijagnoza:"",
                dialog:false
            }
        },
        components:{
            IzmenaDijagnoze
        },
        mounted:function () {
            axios.get("/dijagnoza")
                .then(res=>{
                    this.dijagnoze = res.data;
                })
                .catch(error=>{
                    alert(error);
                });
        },
        methods:{
            deleteDijagnoza:function (sifraDijagnoze) {
                axios.delete("/dijagnoza/"+sifraDijagnoze)
                    .then(res=>{
                        alert(res.data)
                    })
                    .catch(error=>{
                        console.log(error);
                        
                    })
            },
            prikaziDialog(dijagnoza){
                this.dijagnoza = dijagnoza;
                this.dialog = true;
            }
        }
    }
</script>