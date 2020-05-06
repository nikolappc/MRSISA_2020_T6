<template>
    <v-card>
        <v-container>
            <v-form
                ref="forma"
            >
            <v-text-field
                label="Šifra dijagnoze"
                :rules="rules"
                disabled
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
                <v-col
                    cols="3"
                    class="ml-auto"
                >
                    <v-btn @click="izmena" color="success">
                        Izmena dijagnoze
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
                axios.put("/dijagnoza/"+this.dijagnoza.sifraDijagnoze, this.dijagnoza)
                    .then(res=>{
                        this.$store.commit("setSnackbar", {text:"Uspešno ste izmenili dijagnozu.", color: "success"});

                        console.log(res.data);
                        this.$router.go();
                    })
                    .catch(error=>{
                        console.log(error);
                        this.$store.commit("setSnackbar", {text:"Izvinjavamo se došlo je do greške.", color: "error"});
                        this.$router.go();

                    })
            },
            otkazi:function () {
                this.$router.go();
            }
        }

    }
</script>
