<template>
    <div>
        <v-container>
            <v-container>
                <label class="display-1">
                    Pregled administratora kliničkog centra
                </label>
            </v-container>
            <v-row
                align="center"
                justify-content="center"
            >
                <v-col v-if="admini.length!=0"> 
                    <v-card>
                        <v-container>
                            <v-row>
                                <v-col
                                    cols="11"
                                >
                                    <v-simple-table>     
                                         <thead>
                                             <tr>
                                                 <th
                                                    id="ime"
                                                 >
                                                     Ime
                                                 </th>
                                                 <th
                                                    id="prezime"
                                                 >
                                                     Prezime
                                                 </th>
                                                 <th
                                                    id="br"
                                                 >
                                                     Broj telefona
                                                 </th>
                                                 <th
                                                    id="jbo"
                                                 >
                                                     JBO
                                                 </th>
                                                 <th
                                                    id="email"
                                                 >
                                                     E-mail
                                                 </th>
                                                 <th
                                                    id="adresa"
                                                 >
                                                     Adresa
                                                 </th>
                                             </tr>
                                         </thead>
                                         <tbody @click="izmena(a)" v-for="a in admini" :key="a.id">
                                             <tr> 
                                                 <td>
                                                     {{a.ime}}
                                                 </td>
                                                 <td>
                                                     {{a.prezime}}
                                                 </td>
                                                 <td>
                                                     {{a.brojTelefona}}
                                                 </td>
                                                 <td>
                                                     {{a.jbo}}
                                                 </td>
                                                 <td>
                                                     {{a.email}}
                                                 </td>
                                                 <td>
                                                     {{toStringAdresa(a.adresa)}}
                                                 </td>
                                             </tr>
                                         </tbody>
                                     </v-simple-table>
                                </v-col>
                                <v-col
                                    cols="1"
                                    v-if="this.admini.length>1"
                                >
                                    <v-simple-table>
                                        <thead>
                                            <tr>
                                                <th id="">
                                                </th>
                                            </tr>
                                        </thead>
                                        <tbody v-for="a in admini" :key="a.id">
                                            <tr>
                                                <td>
                                                    <v-btn icon @click="deleteAdmin(a.id)">
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
                                    lg="6"
                                    class="ml-auto"
                                >
                                    <router-link to="/regAdminKC">
                                        <v-btn color="success">
                                            Registrovanje administratora kliničkog centra
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
                                    <display-1>
                                        Nema registrovanih administratora kliničkog centra.    
                                    </display-1>
                                </v-col>
                                <v-col>
                                    <router-link to="/regAdminKC">
                                        <v-btn color="success">
                                            Registrovanje administratora kliničkog centra
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
            <IzmenaAdminaKlinike
                @done="onIzmenaDone()"
                :adminZaIzmenu="admin"
            >
            </IzmenaAdminaKlinike>
        </v-dialog>
    </div>
</template>
<script>
    const axios = require('axios');
    import IzmenaAdminaKlinike from "./IzmenaAdminaKlinike.vue";
    export default {
        name:"PregledAdminaKC",
        components:{
            IzmenaAdminaKlinike
        },
        data:function () {
            return {
                admini:[
                ],
                admin:"",
                dialog:false,
            }
        },
        mounted:function () {
            this.refresh();
        },
        methods:{
            toStringAdresa:function(adresa){
                return adresa.adresa+ ", " + adresa.grad + ", " + adresa.drzava;
            },
            refresh:function () {
                axios.get("/adminKC")
                    .then(res=>{
                        this.admini = res.data;
                        console.log(this.admini);
                    })
                    .catch(error=>{
                        this.$store.commit("setSnackbar", {text:"Izvinjavamo se došlo je do greške.", color: "error"});
                        console.log(error);
                    });
            },
            izmena(admin){
                this.admin = admin;
                this.dialog = true;
            },
            onIzmenaDone:function () {
                this.dialog = false;
                this.refresh();
            },
            deleteAdmin:function (id) {
                axios.delete("/adminKC/"+id)
                    .then(res=>{
                        this.$store.commit("setSnackbar", {text:"Uspešno ste obrisali administratora kliničkog centra.", color: "success"});
                        this.refresh();
                        console.log(res.data);
                    })
                    .catch(error=>{
                        console.log(error);
                    })
            }
        }
    }
</script>

<style>

</style>