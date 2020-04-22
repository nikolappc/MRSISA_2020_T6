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
                                    required
                                    v-model="sifraLeka"
                                >
                                <v-text-field
                                    label="Naziv leka"
                                    :rules="rules"
                                    required
                                    v-model="nazivLeka"
                                >
                                <v-textarea
                                    label="Opis leka"
                                    :rules="rules"
                                    required
                                >
                                </v-textarea>
                                <v-row>
                                    <v-spacer>
            
                                    </v-spacer>
                                    <v-btn @click="registruj">
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
    const axios = require('axios');
    export default {
        name:"RegLekova",
        data:function () {
            return {
                rules:[v=>!!v||"Ovo polje je obavezno!"],
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