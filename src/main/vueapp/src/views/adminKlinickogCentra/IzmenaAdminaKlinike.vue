<template>
    <v-card>
        <v-container>
            <v-form
                ref="forma"
            >
                <v-text-field
                    label="Ime"
                    required
                    v-model="admin.ime"
                >
                </v-text-field>
                <v-text-field
                    label="Prezime"
                    required
                    v-model="admin.prezime"
                >
                </v-text-field>
                <v-text-field
                    label="Šifra"
                    :rules="[notEmptyRule]"
                    type="password"
                    required
                    v-model="admin.password"
                >
                </v-text-field>
                <v-text-field
                    label="Ponovite šifru"
                    type="password"
                    :rules="[passwordRule]"
                    required
                >
                </v-text-field>
                <v-text-field
                    label="Broj telefona"
                    required
                    v-model="admin.brojTelefona"
                >
                </v-text-field>
                <v-text-field
                    label="JBO"
                    type="number"
                    required
                    v-model="admin.jbo"
                >
                </v-text-field>
                <v-text-field
                    label="E-mail"
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
                            required
                            v-model="admin.adresa.drzava"
                        >
                        </v-text-field>
                    </v-col>
                </v-row>
                <v-select
                    :items="klinike"
                    item-text="ime"
                    item-value="naziv"
                    label="Klinika"
                    v-model="admin.klinika"
                >
                </v-select>
            
            <v-row>
                <v-col
                    cols="3"
                    class="ml-auto"
                >
                    <v-btn @click="izmena" color="success">
                        Izmena
                    </v-btn>
                </v-col>
                <v-col
                    cols="3"
                >
                    <v-btn @click="otkazi" color="warning">
                        Otkaži
                    </v-btn>
                </v-col>
            </v-row>
            </v-form>
        </v-container>
    </v-card>
</template>

<script>
    const axios = require("axios");
    export default{
        name:"IzmenaAdminaKlinike",
        data(){
            return {
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
        props:[
            "adminZaIzmenu"
        ],
        mounted(){
            this.admin = this.adminZaIzmenu;
            axios.get("/klinika")
                .then(res=>{
                    this.klinike = res.data;
                })
                .catch(error=>{
                    console.log(error);
                    this.$store.commit("setSnackbar", {text:error, color:"error"});
                    this.$router.push("/");
                });
        },
        methods:{
            izmena(){
                axios.put("/adminKlinike/"+this.adminZaIzmenu.id, this.admin)
                    .then(res=>{
                        this.$store.commit("setSnackbar", {text:"Uspešno ste izmenili administratora klinike.", color: "success"});

                        console.log(res.data);
                        this.$emit("done");
                    })
                    .catch(error=>{
                        console.log(error);
                        this.$store.commit("setSnackBar", {text:"Izvinjavamo se došlo je do greške.", color:"error"});
                        this.$emit("done");
                    })
            },
            otkazi:function () {
                this.$emit("done");
            }
        }

    }
</script>
