<template>
    <div>
        <v-container>
            <v-container>
                <label class="display-1">
                    Pregled klinika
                </label>
            </v-container>
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
                                        <th id="naziv">
                                            Naziv
                                        </th>
                                        <th id="adresa">
                                            Adresa
                                        </th>
                                        <th id="spec">
                                            Specijalizacija
                                        </th>
                                        <th id="izmena">
                                            Izmena
                                        </th>
                                        <th id="Brisanje">
                                            Brisanje
                                        </th>
                                    </tr>
                                </thead>
                                <tbody v-for="k in klinike" :key="k.id" >
                                    <tr> 
                                        <td>
                                            {{k.naziv}}
                                        </td>
                                        <td>
                                            <Map :adresaMape="k.adresa"></Map>
                                        </td>
                                        <td>
                                            {{k.tipKlinike.tip}}
                                        </td>
                                        <td>
                                            <v-btn icon @click="izmena(k)">
                                                <v-icon>mdi-pencil</v-icon>
                                            </v-btn>
                                        </td>
                                        <td>
                                            <v-btn icon @click="deleteKlinika(k.id)">
                                                <v-icon>mdi-delete</v-icon>
                                            </v-btn>
                                        </td>
                                    </tr>
                                </tbody>
                            </v-simple-table>
                            <v-row
                            align="center"
                            justify-content="center">
                                <v-col
                                    cols="12"
                                    lg="3"
                                    class="ml-auto"
                                >
                                    <v-btn to="/regKlinika" color="success">
                                        Registrovanje klinika
                                    </v-btn>
                                </v-col>
                                <v-col
                                    cols="12"
                                    lg="3"
                                >
                                    <v-btn to="/regTipKlinike" color="success">
                                        Registrovanje tipa klinika
                                    </v-btn>
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
                                        Nema registrovanih klinika.    
                                    </v-card-title>
                                </v-col>
                                <v-col>
                                    <router-link to="/regKlinika">
                                        <v-btn color="success">
                                            Registrovanje klinika
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
            <IzmenaKlinike
                @done="onIzmenaDone()"
                :klinikaZaIzmenu="klinika"
            >
            </IzmenaKlinike>
        </v-dialog>

    </div>
</template>

<script>
    import Map from "../../components/Map.vue"
    const axios = require('axios');
    import IzmenaKlinike from "./IzmenaKlinike.vue";
    export default {
        name:"PregledKlinika",
        data:function () {
            return {
                klinike:[
                ],
                klinika:"",
                dialog:false,
            }
        },
        components:{
            IzmenaKlinike,
            Map
        },  
        mounted:function () {
            this.refresh();
        },
        methods:{
            toStringAdresa:function(k){
                return k.adresa.adresa+ ", " + k.adresa.grad + ", " + k.adresa.drzava;
            },
            refresh:function () {
                axios.get("/klinika")
                    .then(res=>{
                        this.klinike = res.data;
                        console.log(this.klinike);
                    })
                    .catch(error=>{
                        this.$store.commit("setSnackbar", {text:"Izvinjavamo se došlo je do greške.", color: "error"});
                        console.log(error);
                    });
            },
            izmena(klinika){
                this.klinika = klinika;
                this.dialog = true;
            },
            onIzmenaDone:function () {
                this.dialog = false;
                this.refresh();
            },
            deleteKlinika:function (id) {
                axios.delete("/klinika/"+id)
                    .then(res=>{
                        this.$store.commit("setSnackbar", {text:"Uspešno ste obrisali kliniku.", color: "success"});
                        this.refresh();
                        console.log(res.data);
                    })
                    .catch(error=>{
                        this.$store.commit("setSnackbar", {text:"Izvinjavamo se došlo je do greške.", color: "error"});
                        console.log(error);
                    })
            }
        }
    }
</script>