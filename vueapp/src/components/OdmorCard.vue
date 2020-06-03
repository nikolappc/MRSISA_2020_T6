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
        <v-row
            justify="center"
            align="center"
        >
            <v-col
                justify="center"
                align="center"
            >
                <v-btn color="success" @click="prihvati">
                    Prihvati
                </v-btn>
            </v-col>
            <v-col
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
        "zahtev"
    ],
    methods:{
        formatirajDatum: function(datum){
            return datum.substring(8,10) +"." +datum.substring(5,7) + "." + datum.substring(0,4) + " " + datum.substring(11,16); 
        },
        prihvati:function () {
            this.zahtev.odobren = true;
            axios
                .put('odmor/'+this.zahtev.id,this.zahtev)
                .then(response => {
                    console.log(response.data)
                    this.$emit("zatvori",this.zahtev.id);
                })
                .catch(function (error) { console.log(error); //router.push("/loginPage"); 
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
<style>
random{
    color:"#ffffff";
}

</style>