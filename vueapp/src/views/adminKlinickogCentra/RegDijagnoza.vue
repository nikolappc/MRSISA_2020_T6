<template>
    <div>
        <v-container>
            <v-container>
                <label class="display-1">
                    Registrovanje dijagnoza
                </label>
            </v-container>
            <v-row
                align="center"
                justify-content="center"
            >
                <v-col> 
                    <v-form ref="forma">
                        <v-card>
                            <v-container>
                                <v-text-field
                                    label="Šifra dijagnoze"
                                    :rules="rules"
                                    required
                                    v-model="sifraDijagnoze"
                                >
                                </v-text-field>
                                <v-text-field
                                    label="Naziv dijagnoze"
                                    :rules="rules"
                                    required
                                    v-model="nazivDijagnoze"
                                >
                                </v-text-field>
                                <v-row>
                                    <v-col
                                        cols="3"
                                        class="ml-auto"
                                    >
                                        <v-btn @click="registruj" color="success">
                                            Registruj dijagnozu
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
        name:"RegDijagnoza",
        data:function () {
            return {
                rules:[v=>!!v||"Ovo polje je obavezno!"],
                sifraDijagnoze:"",
                nazivDijagnoze:"",
            }
        },
        methods:{
            registruj:function () {
                if(!this.$refs.forma.validate()){
                    return;
                }
                axios.post("/dijagnoza", {nazivDijagnoze:this.nazivDijagnoze, sifraDijagnoze:this.sifraDijagnoze})
                    .then(()=>{
                        this.$store.commit("setSnackbar", {text:"Uspešno registrovana dijagnoza.", color: "success"});
                        this.$router.push("/pregledDijagnoza");
                    })
                    .catch(error=>{
                        this.$store.commit("setSnackbar", {text:"Izvinjavamo se došlo je do greške.", color: "error"});
                        this.$router.push("/pregledDijagnoza");
                        console.log(error);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
                    })
            },
        }
    }
</script>