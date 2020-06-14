<template>
    <v-card>
        <v-row>
            <v-col
                cols="12"
            >
            </v-col>
                <v-container>
                    <v-card-title>
                        Osoba: {{osoba.ime}} {{osoba.prezime}}
                    </v-card-title>
                    <v-card-subtitle>
                        Vreme: {{formatirajDatum(zahtev.pocetak)}} - {{formatirajDatum(zahtev.kraj)}}
                    </v-card-subtitle>
                </v-container>
        
        </v-row>
        <v-row>
            <v-text-field
            label = "Razlog"
            v-model="razlog"
            dense
        >

        </v-text-field>
        </v-row>
        <v-row
            justify="center"
            align="center"
        >
            <v-col class="ma-4"
                justify="center"
                align="center"
            >
                <v-btn color="success" @click="prihvati">
                    Prihvati
                </v-btn>
            </v-col>
            <v-col  class="ma-4"
                justify="center"
                align="center">
                <v-btn color="error" @click="odbi">
                    Odbi
                </v-btn>
            </v-col>
        </v-row>
    </v-card>
</template>
<script>
import axios from 'axios';
export default{
    name:"PregledConf",
    props:[
        "osoba",
        "zahtev",
        "razlog"
    ],
    methods:{
        formatirajDatum: function(datum){
            return datum.substring(8,10) +"." +datum.substring(5,7) + "." + datum.substring(0,4) + " " + datum.substring(11,16); 
        },
        prihvati:function () {
            this.zahtev.odobren = true;
            axios
                .put('odmor/'+this.zahtev.id,{odmor: this.zahtev,razlog: this.razlog})
                .then(response => {
                    console.log(response.data)
                    if(!response.data.odobren){
                       this.$store.commit("setSnackbar", {text:"Zahtev nije odobren jer lekar ima posetu u tom terminu", color: "error"});
                    }
                    else{
                        this.$store.commit("setSnackbar", {text:"Zahtev je odobren", color: "error"});
                    }
                    this.$emit("zatvori",this.zahtev.id);
                })
                .catch(function (error) { console.log(error); 
                });
        },
        odbi: function(){
            axios
                .put('odmor/'+this.zahtev.id,this.zahtev)
                .then(response => {
                    console.log(response.data)
                    this.$emit("zatvori",this.zahtev.id);
                })
                .catch(function (error) { console.log(error); //router.push("/loginPage"); 
                });
        }
    }
}
</script>