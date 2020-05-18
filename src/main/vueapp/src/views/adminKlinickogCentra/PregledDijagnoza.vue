<template>
    <div>
        <v-container>
            <v-container>
                <label class="display-1">
                    Pregled dijagnoza
                </label>
            </v-container>
            <v-row
                align="center"
                justify-content="center"
            >
                <v-col v-if="dijagnoze.length!=0"> 
                    <v-card>
                        <v-container>
                            <v-row>
                                <v-col
                                    cols="11"
                                >
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
                                        <tbody v-for="d in dijagnoze" :key="d.sifraDijagnoze" @click="prikaziDialog(d)">
                                            <tr> 
                                                <td>
                                                    {{d.sifraDijagnoze}}
                                                </td>
                                                <td>
                                                    {{d.nazivDijagnoze}}
                                                </td>
                                            </tr>
                                        </tbody>
                                    </v-simple-table>
                                </v-col>
                                <v-col
                                    cols="1"
                                >
                                    <v-simple-table>
                                        <thead>
                                            <tr>
                                                <th>
        
                                                </th>
                                            </tr>
                                        </thead>
                                        <tbody v-for="(d,i) in dijagnoze" :key="i">
                                            <tr>
                                                <td>
                                                    <v-btn icon @click="deleteDijagnoza(d.sifraDijagnoze)">
                                                        <v-icon>mdi-delete</v-icon>
                                                    </v-btn>
                                                </td>
                                            </tr>
                                        </tbody>
        
                                    </v-simple-table>
                                </v-col>
                            </v-row>
                            <v-row
                            align="center"
                            justify-content="center">
                                <v-col
                                    class="ml-auto"
                                    cols="3"
                                >
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
                @done="onIzmenaDone"
                :dijagnozaZaIzmenu="dijagnoza"
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
                dijagnoze:[
                    {
                        sifraDijagnoze:"212",
                        nazivDijagnoze:"Smrt"
                    }

                ],
                dijagnoza:"",
                dialog:false,
            }
        },
        components:{
            IzmenaDijagnoze
        },
        mounted:function () {
            this.refresh();
        },
        methods:{
            refresh:function(){
                axios.get("/dijagnoza")
                .then(res=>{
                    this.dijagnoze = res.data;
                })
                .catch(error=>{
                    this.$store.commit("setSnackbar", {text:"Izvinjavamo se došlo je do greške.", color: "error"});
                    console.log(error);
                });
            },
            deleteDijagnoza:function (sifraDijagnoze) {
                axios.delete("/dijagnoza/"+sifraDijagnoze)
                    .then(res=>{
                        this.$store.commit("setSnackbar", {text:"Uspešno ste obrisali dijagnozu.", color: "success"});
                        this.refresh();
                        console.log(res.data);
                    })
                    .catch(error=>{
                        this.$store.commit("setSnackbar", {text:"Izvinjavamo se došlo je do greške.", color: "error"});
                        console.log(error);
                    })
            },
            onIzmenaDone:function(){
                this.dialog = false;
            },
            prikaziDialog:function(dijagnoza){
                this.dijagnoza = dijagnoza;
                this.dialog = true;
            }
        }
    }
</script>