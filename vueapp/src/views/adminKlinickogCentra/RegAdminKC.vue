<template>
    <div>
        <v-container>
            <v-container>
                <label class="display-1">
                    Registrovanje administratora kliničkog centra
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
                                    type="password"
                                    v-model="admin.password"
                                >
                                </v-text-field>
                                <v-text-field
                                    label="Ponovite šifru"
                                    :rules="[notEmptyRule, passwordRule]"
                                    type="password"
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
                                <Autocomplete
                                    :adresa="admin.adresa"
                                >

                                </Autocomplete>
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
    import Autocomplete from "../../components/Autocomplete.vue"

    const axios = require("axios");
    export default {
        name:"RegAdminKC",
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
                    adresa:{}
                }
            }
        },
        components:{
            Autocomplete
        },
        methods:{
            registruj(){
                if(!this.$refs.forma.validate()){
                    return;
                }
                axios.post("/adminKC/", this.admin)
                .then(()=>{
                        this.$store.commit("setSnackbar",{text:"Uspešno ste registrovali administratora kliničkog centra.", color: "success"});
                        this.$router.push("/pregledAdminaKC");
                    }
                )
                .catch(error=>{
                    this.$store.commit("setSnackbar", {text:error, color:"error"})
                });
            }
        },
        mounted(){
            axios.get("/adminKC")
                .then(res=>{
                    this.klinike = res.data;
                })
                .catch(error=>{
                    console.log(error);
                    this.$store.commit("setSnackBar", {text:"Izvinjavamo se došlo je do greške.", color:"error"});
                    this.$router.push("/");
                })
        },

    }
</script>