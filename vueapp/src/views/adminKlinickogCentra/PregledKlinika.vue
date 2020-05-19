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
                            <v-row>
                                <v-col
                                    cols="11"
                                >
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
                                        <tbody v-for="k in klinike" :key="k.id" @click="izmena(k)">
                                            <tr> 
                                                <td>
                                                    {{k.naziv}}
                                                </td>
                                                <td>
                                                    {{toStringAdresa(k)}}
                                                </td>
                                                <td>
                                                    {{k.tipKlinike.tip}}
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
                                        <tbody v-for="(k,i) in klinike" :key="i">
                                            <tr>
                                                <td>
                                                    <v-btn icon @click="deleteKlinika(k.id)">
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
                                    cols="12"
                                    lg="4"
                                    class="ml-auto"
                                >
                                    <router-link to="/regKlinika">
                                        <v-btn color="success">
                                            Registrovanje klinika
                                        </v-btn>
                                    </router-link>
                                </v-col>
                                <v-col
                                    cols="12"
                                    lg="4"
                                >
                                    <router-link to="/regTipKlinike">
                                        <v-btn color="success">
                                            Registrovanje tipa klinika
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
            IzmenaKlinike
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