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
                            <v-row>
                                <v-col
                                    cols="11"
                                >
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
                                        <tbody v-for="l in lekovi" :key="l.sifraLeka" @click="izmena(l)">
                                            <tr>
                                                <td>
                                                    {{l.sifraLeka}}
                                                </td>
                                                <td>
                                                    {{l.nazivLeka}}
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
                                        <tbody v-for="(l,i) in lekovi" :key="i">
                                            <tr>
                                                <td>
                                                    <v-btn v-if="!l.koristen" icon @click="deleteLek(l.sifraLeka)">
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
                                <v-spacer>
                                </v-spacer>
                                <v-col
                                cols="3"
                                class="ml-auto"
                                >

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
        <v-dialog
            v-model="dialog"
        >
            <IzmenaLeka
                :lek = "lek"
            >
            </IzmenaLeka>
        </v-dialog>
    </div>
</template>

<script>
    const axios = require('axios');
    import IzmenaLeka from "./IzmenaLeka.vue";
    export default {
        name:"PregledLekova",
        data:function () {
            return {
                lekovi:[],
                dialog:false,
                lek:""
            }
        },
        components:{
            IzmenaLeka
        },
        mounted:function () {
            axios.get("/lek")
                .then(res=>{
                    this.lekovi = res.data;
                })
                .catch(error=>{
                    alert(error);
                });
        },
        methods:{
            deleteLek:function (sifra) {
                axios.delete("/lek/"+sifra)
                    .then(res=>{
                        this.$store.commit("setSnackbar", {text:"Uspešno ste obrisali lek.", color: "success"});
                        this.$router.go();
                        console.log(res.data);
                    })
                    .catch(error=>{
                        console.log(error);
                        this.$store.commit("setSnackbar", {text:"Izvinjavamo se došlo je do greške.", color: "error"});
                    })
            },
            izmena:function(lek){
                this.lek = lek;
                this.dialog = true;
            },
        }
    }
</script>