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
                <Autocomplete :adresa="klinika.adresa"></Autocomplete>
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
    import Autocomplete from "../../components/Autocomplete.vue"
    const axios = require("axios");
    export default{
        name:"IzmenaKlinike",
        data(){
            return {
                tipoviKlinika:[],
                rules:[v=>!!v||"Ovo polje je obavezno!"],
                klinika:{
                    
                }
            }
        },
        components:{
            Autocomplete
        },
        props:[
            "klinikaZaIzmenu"
        ],
        mounted(){
            this.klinika = JSON.parse(JSON.stringify(this.klinikaZaIzmenu));
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
                        // this.$router.go();
                        this.$emit("done");
                    })
                    .catch(error=>{
                        this.$store.commit("setSnackbar", {text:"Izvinjavamo se došlo je do greške.", color: "error"});
                        console.log(error);
                    })
            },
            otkazi:function () {
                // this.$router.go();
                this.$emit("done");
            }
        }

    }
</script>
