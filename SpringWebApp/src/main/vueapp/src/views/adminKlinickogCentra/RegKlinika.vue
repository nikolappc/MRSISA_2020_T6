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
                                    required
                                    v-model="naziv"
                                >
                                    
                                </v-text-field>
                                <v-select
                                    :items="tipoviKlinika"
                                    item-text="tip"
                                    item-value="id"
                                    label="Tip klinike"
                                    required
                                    :rules="rules"
                                    v-model="tipKlinike"
                                >

                                </v-select>
                                <v-row>
                                    <v-col>
                                        <v-text-field
                                            label="Mesto"
                                        :rules="rules"
                                        required
                                        v-model="grad"
                                        >
                    
                                        </v-text-field>
                                    </v-col>
                                    <v-col>
                                        <v-text-field
                                        label="Ulica"
                                        :rules="rules"
                                        required
                                        v-model="ulica"
                                        >
                    
                                        </v-text-field>
                                    </v-col>
                                    <v-col>
                                        <v-text-field
                                            label="Broj"
                                            :rules="rules"
                                            required
                                            type="number"
                                            v-model="broj"
                                        >
                    
                                        </v-text-field>
                                    </v-col>
                                </v-row>
                                <v-textarea
                                    label="Opis"
                                    :rules="rules"
                                    required
                                    v-model="opis"
                                >
                                    
                                </v-textarea>
                                <v-row>
                                    <v-col
                                        cols="3"
                                        calss="ml-auto"
                                    >
                                        <v-btn @click="registruj" color="success">
                                            Registruj kliniku
                                        </v-btn>
                                    </v-col>
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
                tipoviKlinika:[],
            }
        },
        mounted:function () {
            axios.get("/tipKlinike")
                .then(res=>{
                    this.tipoviKlinika = res.data;
                    console.log(this.tipoviKlinika);
                    
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
                axios.get("/tipKlinike/"+this.tipKlinike)
                    .then(res=>{

                        axios.post("/klinika", {naziv:this.naziv, adresa:this.grad+" "+this.ulica+" "+this.broj, opis:this.opis, tipKlinike:res.data})
                            .then(()=>{
                                this.$store.commit("setSnackbar", {text:"Uspešno registrovana klinika.", color: "success"});
                                this.$router.push("/pregledKlinika");
                            })
                            .catch(error=>{
                                this.$store.commit("setSnackbar", {text:"Izvinjavamo se došlo je do greške.", color: "error"});
                                this.$router.push("/pregledKlinika");
                                console.log(error);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
                            })
                    });
            },
        }
    }
</script>