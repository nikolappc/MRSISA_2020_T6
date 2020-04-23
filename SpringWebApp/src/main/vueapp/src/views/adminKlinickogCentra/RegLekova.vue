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
                                    label="Šifra leka"
                                    :rules="rules"
                                    type="number"
                                    required
                                    v-model="sifraLeka"
                                >
                                </v-text-field>
                                <v-text-field
                                    label="Naziv leka"
                                    :rules="rules"
                                    required
                                    v-model="nazivLeka"
                                >
                                </v-text-field>
                                <v-textarea
                                    label="Opis leka"
                                    :rules="rules"
                                    required
                                    v-model="opisLeka"
                                >
                                </v-textarea>
                                <v-row>
                                    <v-spacer>
            
                                    </v-spacer>
                                    <v-btn @click="registruj" color="success">
                                        Registruj lek
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
    /*
     * Funkcija koja proverava da li se string sastoji samo od brojeva i slova
     */
    function isAlphaNumeric(str) {
        var code, i, len;

        for (i = 0, len = str.length; i < len; i++) {
            code = str.charCodeAt(i);
            if (!(code > 47 && code < 58) && // numeric (0-9)
                !(code > 64 && code < 91) && // upper alpha (A-Z)
                !(code > 96 && code < 123)) { // lower alpha (a-z)
            return false;
            }
        }
        return true;
    }

    const axios = require('axios');
    export default {
        name:"RegLekova",
        data:function () {
            return {
                rules:[v=>!!v||"Ovo polje je obavezno!"],
                sifraRules:[
                    v=>!!v||"Ovo polje je obavezno!"
                    , v=>v.length!=12||"Šifra mora biti dužine 12 karaktera."
                    , v=>v.toUpperCase()!=v||"Šifra mora sadržati samo velika slova."
                    , v=>!isAlphaNumeric(v)||"Šifra mora sadržati samo brojeve i slova."
                ],
                sifraLeka:"",
                nazivLeka:"",
                opisLeka:""
            }
        },
        methods:{
            registruj:function () {
                if(!this.$refs.forma.validate()){
                    return;
                }
                axios.post("/lek", {nazivLeka:this.nazivLeka, opisLeka:this.opisLeka, sifraLeka:this.sifraLeka})
                    .then(()=>{
                        alert("Uspesno registrovan lek.");
                    })
                    .catch(error=>{
                        alert(error);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
                    })
            },
        }
    }
</script>