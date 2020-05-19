<template>
    <v-card
        outlined="true"
        >
        
        <v-toolbar
            :color="recept.overen?'success':'warning'"
            flat="true"
        >
            <v-row>
                <v-col>
                  {{recept.lek.nazivLeka}}<br>
                  {{recept.lek.sifraLeka}}
                </v-col>
              </v-row>
        </v-toolbar> 
        {{recept.lek.opisLeka}}
        <v-row>
            <v-col
                class="ml-auto"
                cols="3"
            >
                <v-switch
                    v-model="recept.overen"
                    :label="recept.overen?'Poništi overu':'Overi recept'"
                    @change="overen()"
                >
                </v-switch>
            </v-col>
        </v-row>
    </v-card>
</template>

<script>
    const axios = require('axios');
    export default {
        name:"Recepat",
        data:function(){
            return {
                recept:null
            }
        },
        props:["recepat"],
        mounted(){
            this.recept = this.recepat;
        },
        methods:{
            overen(){
                axios.put("/recept/"+this.recepat.id, this.recept)
                    .then(res=>{
                        console.log(res);
                        console.log("Uspesno");
                    }
                    )
                    .catch(error=>{
                        console.log(error);
                        this.$store.commit("setSnackbar", {text:"Izvinjavamo se došlo je do greške.", color: "error"});
                    })
            }
        }
    }
</script>

<style>

</style>