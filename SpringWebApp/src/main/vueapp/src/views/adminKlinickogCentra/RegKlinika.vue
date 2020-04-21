<template>
    <div>
        <v-container>
            <v-row
                align="center"
                justify-content="center"
            >
                <v-col v-if="tipoviKlinika.length!=0"> 
                    <v-form ref="forma">
                        <v-card>
                            <v-container>
                                <v-text-field
                                    label="Naziv klinike"
                                    :rules="rules"
                                >
                                    
                                </v-text-field>
                                <v-select
                                    :items="tipoviKlinika"
                                    item-text="tip"
                                    item-value="id"
                                    label="Tip klinike"
                                >

                                </v-select>
                                <v-row>
                                    <v-col>
                                        <v-text-field
                                            label="Mesto"
                                        :rules="rules"
                                        >
                    
                                        </v-text-field>
                                    </v-col>
                                    <v-col>
                                        <v-text-field
                                        label="Ulica"
                                        :rules="rules"
                                        >
                    
                                        </v-text-field>
                                    </v-col>
                                    <v-col>
                                        <v-text-field
                                            label="Broj"
                                            :rules="rules"
                                        >
                    
                                        </v-text-field>
                                    </v-col>
                                </v-row>
                                <v-textarea
                                    label="Opis"
                                    :rules="rules"
                                >
                                    
                                </v-textarea>
                                <v-row>
                                    <v-spacer>
            
                                    </v-spacer>
                                    <v-btn @click="registruj">
                                        Registruj kliniku
                                    </v-btn>
                                </v-row>
                            </v-container>
                        </v-card>
                    </v-form>
                </v-col>
                <v-col v-else>
                    <v-card>
                        <v-container>
                            <v-row
                            align="center"
                            justify-content="center">
                                <v-col>
                                    <v-card-title>
                                        Ne moze se registrovati klinika jer nema nijedne registrovane specijalizacije klinike.
                                    </v-card-title>
                                </v-col>
                                <v-col>
                                    <router-link to="/regTipKlinike">
                                        <v-btn color="success">
                                            Registruj tip klinike
                                        </v-btn>
                                    </router-link>
                                </v-col>
                            </v-row>
                        </v-container>
                    </v-card>
                </v-col>
            </v-row>
        </v-container>
    </div>
</template>

<script>
    const axios = require('axios');
    export default {
        name:"RegKlinika",
        data:function () {
            return {
                rules:[v=>!!v||"Ovo polje je obavezno!"],
                naziv:"",
                ulica:"",
                broj:"",
                grad:"",
                opis:"",
                tipKlinike:"",
                tipoviKlinika:[{id:"dadsad",tip:"WOW"},{id:"da",tip:"LEL"}],
            }
        },
        mounted:function () {
            axios.get("/tipKlinike")
                .then(res=>{
                    this.tipoviKlinika = res.data;
                })
                .catch(error=>{
                    alert(error);
                });
        },
        methods:{
            registruj:function () {
                if(!this.$refs.forma.validate()){
                    return;
                }
                axios.post("/klinika", {naziv:this.naziv, adresa:this.grad+" "+this.ulica+" "+this.broj, opis:this.opis, tipKlinike:this.tipKlinike})
                    .then(()=>{
                        alert("Uspesno registrovana klinika.");
                    })
                    .catch(error=>{
                        alert(error);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
                    })
            },
        }
    }
</script>