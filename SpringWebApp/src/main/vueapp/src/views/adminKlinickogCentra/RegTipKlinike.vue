<template>
    <div>
        <v-container>
            <v-container>
                <label class="display-1">
                    Registrovanje tipova klinike
                </label>
            </v-container>
            <v-row
                align="center"
                justify-content="center"
            >
                <v-col> 
                    <v-form ref="forma">
                        <v-card
                        min-width="600px"
                        >                
                            <v-container>
                                <v-text-field
                                    label="Naziv specijalizacije klinike"
                                    :rules="rules"
                                    v-model="tip"
                                    required
                                >
                                </v-text-field>
                                <v-row>
                                    <v-col
                                        cols="3"
                                        class="ml-auto"
                                    >
                                        <v-btn @click="registruj" color="success">
                                            Registruj tip klinike
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
    const axios = require('axios');
    export default {
        name:"RegTipKlinike",
        data:function () {
            return {
                rules:[v=>!!v||"Ovo polje je obavezno!"],
                tip:""
            }
        },
        methods:{
            registruj:function () {
                if(!this.$refs.forma.validate()){
                    return;
                }
                axios.post("/tipKlinike", {tip:this.tip})
                    .then(res=>{
                        console.log(res);
                        this.$store.commit("setSnackbar", {text:"Uspešno registrovan tip klinike.", color: "success"});
                        this.$router.push("/pregledKlinika");
                    })
                    .catch(error=>{
                        this.$store.commit("setSnackbar", {text:"Izvinjavamo se došlo je do greške.", color: "error"});
                        console.log(error);  
                        this.$router.push("/pregledKlinika");
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
                    })
            },
        }
    }
</script>