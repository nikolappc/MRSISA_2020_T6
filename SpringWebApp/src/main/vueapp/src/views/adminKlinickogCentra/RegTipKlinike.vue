<template>
    <div>
        <v-container>
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
                                >
                                </v-text-field>
                                <v-row>
                                    <v-spacer>
            
                                    </v-spacer>
                                    <v-btn @click="registruj" color="success">
                                        Registruj tip klinike
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
                        alert("Uspesno registrovana specijalizacija klinike.");
                    })
                    .catch(error=>{
                        alert(error);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
                    })
            },
        }
    }
</script>