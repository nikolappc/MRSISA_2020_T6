<template>
    <div>
        <v-container>
            <v-container>
                <label class="display-1">
                    Pregled lekova
                </label>
            </v-container>
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
                                        <th id="sleka">
                                            Šifra leka
                                        </th>
                                        <th id="naziv">
                                            Naziv leka
                                        </th>
                                        <th id="izmena">
                                            Izmena
                                        </th>
                                        <th id="brisanje">
                                            Brisanje
                                        </th>
                                    </tr>
                                </thead>
                                <tbody v-for="l in lekovi" :key="l.sifraLeka">
                                    <tr>
                                        <td>
                                            {{l.sifraLeka}}
                                        </td>
                                        <td>
                                            {{l.nazivLeka}}
                                        </td>
                                        <td>
                                            <v-btn icon @click="izmena(l)">
                                                <v-icon>mdi-pencil</v-icon>
                                            </v-btn>
                                        </td>
                                        <td>
                                            <v-btn v-if="!l.koristen" icon @click="deleteLek(l.sifraLeka)">
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
                :lekZaIzmenu = "lek"
                @done="onIzmenaDone()"
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
            this.refresh();
        },
        methods:{
            refresh:function () {
                axios.get("/lek")
                    .then(res=>{
                        this.lekovi = res.data;
                    })
                    .catch(error=>{
                        this.$store.commit("setSnackbar", {text:"Izvinjavamo se došlo je do greške.", color: "error"});
                        console.log(error);
                    });
            },
            deleteLek:function (sifra) {
                axios.delete("/lek/"+sifra)
                    .then(res=>{
                        this.$store.commit("setSnackbar", {text:"Uspešno ste obrisali lek.", color: "success"});
                        this.refresh();
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
            onIzmenaDone:function () {
                this.dialog = false;
                this.refresh();
            }
        }
    }
</script>