<template>
    <v-card>
        <v-container>
            <v-form
                ref="forma"
            >
                <v-text-field
                    label="Naziv klinike"
                    :rules="rules"
                    required
                    v-model="klinika.naziv"
                >
                    
                </v-text-field>
                <v-select
                    :items="tipoviKlinika"
                    item-text="tip"
                    label="Tip klinike"
                    required
                    :rules="rules"
                    v-model="klinika.tipKlinike"
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
                    v-model="klinika.opis"
                >
                    
                </v-textarea>
                <v-row>
                    <v-col
                        cols="3"
                        class="ml-auto"
                    >
                        <v-btn @click="izmenaKlinike">
                            Izmena klinike
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
        name:"IzmenaKlinike",
        data(){
            return {
                ulica:"",
                broj:"",
                mesto:"",
                tipoviKlinika:[],
                rules:[v=>!!v||"Ovo polje je obavezno!"],
            }
        },
        props:[
            "klinika"
        ],
        mounted(){
            axios.get("/tipKlinike")
                .then(res=>{
                    this.tipoviKlinika = res.data;
                    console.log(this.tipoviKlinika);
                })
                .catch(error=>{
                    console.log(error);
                });
            let data = this.klinika.adresa.split(" ");
            this.grad = data[0];
            this.ulica = data[1];
            this.broj = data[2];
        },
        methods:{
            izmenaKlinike(){
                this.klinika.adresa = this.grad+" "+this.ulica+" "+this.broj;
                axios.put("/klinika/"+this.klinika.id, this.klinika)
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
