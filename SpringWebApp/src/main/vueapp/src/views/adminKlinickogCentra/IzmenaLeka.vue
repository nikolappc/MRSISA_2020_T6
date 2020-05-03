<template>
    <v-card>
        <v-container>
            <v-form
                ref="forma"
            >
                <v-text-field
                    label="Šifra leka"
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
                    <v-spacer>

                    </v-spacer>
                    <v-btn @click="izmeni" color="success">
                        Izmena leka
                    </v-btn>
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
            }
        },
        props:[
            "lek"
        ],
        mounted(){
        },
        methods:{
            izmena(){
                axios.put("/lek/"+this.lek.id, this.lek)
                    .then(res=>{
                        alert(res.data);
                        this.$router.go();
                    })
                    .catch(error=>{
                        console.log(error);
                    })
            }
        }

    }
</script>
