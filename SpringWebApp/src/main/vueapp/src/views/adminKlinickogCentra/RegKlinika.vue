<template>
    <div>
        <v-container>
            <v-row
                align="center"
                justify-content="center"
            >
                <v-col>
                    <v-form ref="forma">
                        <v-card>
                            <v-container>
                                <v-text-field
                                    label="Naziv klinike"
                                    :rules="rules"
                                >
                                    
                                </v-text-field>
                                <v-select
                                    :items="tipovi"
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
                tipKlinike:""
            }
        },
        mounted:function () {
            axios.get
        },
        methods:{
            registruj:function () {
                if(!this.$refs.forma.validate()){
                    return;
                }
                axios.post("/klinika", {naziv:this.naziv, adresa:this.grad+" "+this.ulica+" "+this.broj, opis:this.opis, tipKlinike:this.tipKlinike})
                    .then(res=>{
                        alert("Uspesno registrovana klinika.");
                    })
                    .catch(error=>{
                        alert(error);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
                    })
            },
        }
    }
</script>