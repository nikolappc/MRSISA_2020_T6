<template>
    <v-card>
        <v-container>
            <v-form
                ref="forma"
            >
            <v-text-field
                label="Šifra dijagnoze"
                :rules="rules"
                required
                v-model="dijagnoza.sifraDijagnoze"
            >
            </v-text-field>
            <v-text-field
                label="Naziv dijagnoze"
                :rules="rules"
                required
                v-model="dijagnoza.nazivDijagnoze"
            >
            </v-text-field>
            <v-row>
                <v-spacer>

                </v-spacer>
                <v-btn @click="izmena" color="success">
                    Izmena dijagnoze
                </v-btn>
            </v-row>
            </v-form>
        </v-container>
    </v-card>
</template>

<script>
    const axios = require("axios");
    export default{
        name:"IzmenaDijagnoze",
        data(){
            return {
                rules:[v=>!!v||"Ovo polje je obavezno!"],
            }
        },
        props:[
            "dijagnoza"
        ],
        mounted(){
        },
        methods:{
            izmena(){
                axios.put("/dijagnoza/"+this.dijagnoza.id, this.dijagnoza)
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
