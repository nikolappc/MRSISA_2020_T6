<template>
    <div>
        <v-container>
            <v-container>
                <label class="display-1">
                    Registrovanje administratora klinike
                </label>
            </v-container>
            <v-row
                align="center"
                justify-content="center"
            >
                <v-col> 
                    <v-form ref="forma">
                        <v-card>
                            <v-container>
                                <v-text-field
                                    label="Ime"
                                    :rules="[notEmptyRule]"
                                    required
                                    v-model="admin.ime"
                                >
                                </v-text-field>
                                <v-text-field
                                    label="Prezime"
                                    :rules="[notEmptyRule]"
                                    required
                                    v-model="admin.prezime"
                                >
                                </v-text-field>
                                <v-text-field
                                    label="Šifra"
                                    :rules="[notEmptyRule]"
                                    required
                                    v-model="admin.password"
                                    type="password"
                                >
                                </v-text-field>
                                <v-text-field
                                    type="password"
                                    label="Ponovite šifru"
                                    :rules="[notEmptyRule, passwordRule]"
                                    required
                                >
                                </v-text-field>
                                <v-text-field
                                    label="Broj telefona"
                                    :rules="[notEmptyRule]"
                                    required
                                    v-model="admin.brojTelefona"
                                >
                                </v-text-field>
                                <v-text-field
                                    label="JBO"
                                    :rules="[notEmptyRule]"
                                    type="number"
                                    required
                                    v-model="admin.jbo"
                                >
                                </v-text-field>
                                <v-text-field
                                    label="E-mail"
                                    :rules="[notEmptyRule]"
                                    required
                                    v-model="admin.email"
                                >
                                </v-text-field>
                                <v-row>
                                    <v-col
                                        cols="4"
                                    >
                                        <v-text-field
                                            label="Adresa"
                                            :rules="[notEmptyRule]"
                                            required
                                            v-model="admin.adresa.adresa"
                                        >
                                        </v-text-field>
                                    </v-col>
                                    <v-col
                                        cols="4"
                                    >
                                        <v-text-field
                                            label="Grad"
                                            :rules="[notEmptyRule]"
                                            required
                                            v-model="admin.adresa.grad"
                                        >
                                        </v-text-field>
                                    </v-col>
                                    <v-col
                                        cols="4"
                                    >
                                        <v-text-field
                                            label="Država"
                                            :rules="[notEmptyRule]"
                                            required
                                            v-model="admin.adresa.drzava"
                                        >
                                        </v-text-field>
                                    </v-col>
                                </v-row>
                                <v-select
                                    :items="klinike"
                                    item-text="naziv"
                                    item-value="id"
                                    label="Klinika"
                                    v-model="admin.klinika"
                                >
                                </v-select>
                                <v-row>
                                    <v-col
                                        cols="3"
                                        class="ml-auto"
                                    >
                                        <v-btn @click="registruj" color="success">
                                            Registruj
                                        </v-btn>
                                    </v-col>
                                </v-row>
                            </v-container>
                        </v-card>
                    </v-form>
                </v-col>
            </v-row>
        </v-container>
    </div>
</template>


<script>
    const axios = require("axios");
    export default {
        name:"RegAdminKlinike",
        data:function () {
            return {
                notEmptyRule:v=>!!v||"Ovo polje je obavezno.",
                passwordRule:v=>this.admin.password==v||"Šifre se ne poklapaju.",
                klinike:[],
                admin:{
                    ime:"",
                    prezime:"",
                    password:"",
                    brojTelefona:"",
                    jbo:"",
                    email:"",
                    adresa:{
                        grad:"",
                        drzava:"",
                        adresa:""
                    },
                    klinika:""
                }
            }
        },
        methods:{
            registruj(){
                if(!this.$refs.forma.validate()){
                    return;
                }
                axios.post("/adminKlinike/", this.admin)
                .then(()=>{
                        this.$store.commit("setSnackbar",{text:"Uspešno ste registrovali administratora klinike.", color: "success"});
                        this.$router.push("/pregledAdminaKlinika");
                    }
                )
                .catch(error=>{
                    console.log(error);
                });
            }
        },
        mounted(){
            axios.get("/klinika")
                .then(res=>{
                    this.klinike = res.data;
                })
                .catch(error=>{
                    console.log(error);
                    this.$store.commit("setSnackBar", {text:"Izvinjavamo se došlo je do greške.", color:"error"});
                    this.$router.push("/");
                });
        },

    }
</script>