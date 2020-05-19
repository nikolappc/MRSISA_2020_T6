<template>
    <v-card>
        <v-container>
            <v-form
                ref="forma"
            >
                <v-text-field
                    label="Šifra leka"
                    disabled
                    :rules="rules"
                    type="number"
                    required
                    v-model="lek.sifraLeka"
                >
                </v-text-field>
                <v-text-field
                    label="Naziv leka"
                    :rules="rules"
                    required
                    v-model="lek.nazivLeka"
                >
                </v-text-field>
                <v-textarea
                    label="Opis leka"
                    :rules="rules"
                    required
                    v-model="lek.opisLeka"
                >
                </v-textarea>
                <v-row>
                    <v-col
                        cols="3"
                        class="ml-auto"
                    >
                        <v-btn @click="izmena" color="success">
                            Izmena leka
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
        name:"IzmenaLeka",
        data(){
            return {
                rules:[v=>!!v||"Ovo polje je obavezno!"],
                lek:"",
            }
        },
        props:[
            "lekZaIzmenu"
        ],
        mounted(){
            this.lek = this.lekZaIzmenu;
        },
        methods:{
            izmena(){
                axios.put("/lek/"+this.lek.sifraLeka, this.lek)
                    .then(res=>{
                        this.$store.commit("setSnackbar", {text:"Uspešno ste izmenili lek.", color: "success"});
                        console.log(res.data);
                        this.$emit("done");
                    })
                    .catch(error=>{
                        this.$store.commit("setSnackbar", {text:"Izvinjavamo se došlo je do greške.", color: "error"});
                        console.log(error);
                    })
            },
            otkazi:function () {
                this.$emit("done");
            }
        }

    }
</script>
