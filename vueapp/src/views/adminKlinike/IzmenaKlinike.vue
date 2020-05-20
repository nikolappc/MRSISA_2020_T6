<template>
    <v-card>
        <v-container>
            <v-form
                ref="forma"
            >
                <v-text-field
                    label="Naziv klinike"
                    :rules="rules"
                    required
                    v-model="klinika.naziv"
                >
                    
                </v-text-field>
                <v-select
                    :items="tipoviKlinika"
                    item-text="tip"
                    return-object
                    label="Tip klinike"
                    required
                    :rules="rules"
                    v-model="klinika.tipKlinike"
                >

                </v-select>
                <v-row>
                    <v-col>
                        <v-text-field
                            label="Adresa"
                        :rules="rules"
                        required
                        v-model="klinika.adresa.adresa"
                        >
    
                        </v-text-field>
                    </v-col>
                    <v-col>
                        <v-text-field
                        label="Grad"
                        :rules="rules"
                        required
                        v-model="klinika.adresa.grad"
                        >
    
                        </v-text-field>
                    </v-col>
                    <v-col>
                        <v-text-field
                            label="Država"
                            :rules="rules"
                            required
                            v-model="klinika.adresa.drzava"
                        >
    
                        </v-text-field>
                    </v-col>
                </v-row>
                <v-textarea
                    label="Opis"
                    :rules="rules"
                    required
                    v-model="klinika.opis"
                >
                    
                </v-textarea>
                <v-row>
                    <v-col
                        cols="3"
                        class="ml-auto"
                    >
                        <v-btn @click="izmenaKlinike" color="success">
                            Izmena klinike
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
        name:"IzmenaKlinikeAdmin",
        data(){
            return {
                tipoviKlinika:[],
                rules:[v=>!!v||"Ovo polje je obavezno!"],
                klinika:""
            }
        },
        props:[
            "klinikaZaIzmenu"
        ],
        mounted(){
            axios.get("klinika/admin")
                .then(res=>{
                    this.klinika = res.data;
                    console.log(this.klinika);
                })
                .catch(error=>{
                    console.log(error);
                });
            axios.get("/tipKlinike")
                .then(res=>{
                    this.tipoviKlinika = res.data;
                    console.log(this.tipoviKlinika);
                })
                .catch(error=>{
                    console.log(error);
                });
        },
        methods:{
            izmenaKlinike(){
                console.log(this.klinika);
                
                axios.put("/klinika/"+this.klinika.id, this.klinika)
                    .then(res=>{
                        console.log(res.data);
                        
                        this.$store.commit("setSnackbar", {text:"Uspešno ste izmenili kliniku.", color: "success"});
                        this.$router.go();
                    })
                    .catch(error=>{
                        this.$store.commit("setSnackbar", {text:"Izvinjavamo se došlo je do greške.", color: "error"});
                        console.log(error);
                    })
            }
        }

    }
</script>
