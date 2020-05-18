<template>
    <div>
        <h2>Lista sala</h2>
        <v-text-field
            v-model="search"
            label="Pretraga"
            append-icon="mdi-magnify"
        ></v-text-field>
        <v-simple-table border="1">
            <thead>
                <th>ID</th>
                <th>Naziv</th>
                <th></th>
            </thead>
            <tbody>
                <Sala @click="dialog = true" v-for="sala in filterSale" v-bind:key="sala.id"
                 v-bind:sala="sala.sala" v-on:del-sala="deleteSala" v-bind:termini="sala.posete"
                 v-on:otvori="otvoriDialog" />
                <v-dialog
                v-model="dialog"
                max-width="290"
                >
                
                </v-dialog>
            </tbody>
            <tr>
                <td align="right" colspan='4'><v-btn :to="{path: 'sala/add'}" dark medium left class="blue" slot="action">Dodaj salu</v-btn></td>
            </tr>
        </v-simple-table>


        <v-select
        v-if="pregled.lekar != null"
        v-model="pregled.lekar"
        :items="lekari"
        label="Lekar"
        dense
        outlined
        return-object
        >
            <template slot="selection" slot-scope="data">
                <!-- HTML that describe how select should render selected items -->
                {{ data.item.ime }} {{ data.item.prezime }}
            </template>
            <template slot="item" slot-scope="data">
                <!-- HTML that describe how select should render items when the select is open -->
                {{ data.item.ime }} {{ data.item.prezime }}
            </template>
        </v-select>


        <v-dialog
            v-model="dialog"
            max-width="500"
            >
                <v-card>
                    <v-card-title class="headline">Izmeni salu</v-card-title>
                        <IzmenaSale  v-bind:sala="dialogSala" v-on:zatvori="dialog = false"/>
                </v-card>
        </v-dialog>
    </div>
</template>




<script>

    function preurediDatum(sale){
        for (let sala of sale){
            for(let termin of sala.posete){
                termin.start = formatirajDatum(termin.start);
                termin.end = formatirajDatum(termin.end);
        }
    }
    return sale;
    }
    function formatirajDatum(datum){
        return datum.substring(0, 10) + " " + datum.substring(11,16); 
    }

import Sala from "./Sala.vue"
import axios from "axios"
import IzmenaSale from "./IzmenaSale.vue"
export default {
    data: () => ({
        sale : [],
        dialog : false,
        dialogSala: null,
        search: '',
        pregled: {lekar: null},
        lekari: [],
        pocetak: null,
        kraj: null,
    }),
    mounted () {

        let pregledID = this.$route.params.id;
        
        axios
        .get('sala/DTO')
        .then(response => {
            this.sale = preurediDatum(response.data);
            console.log(response);
        })
        .catch(() => { this.sale = [{posete: [], sala: {id: '1',naziv: 'Neka klinika'}}]; });

        if(pregledID !== undefined){
            axios
                .get('admin/zahtevi/'+ pregledID)
                .then(response => {
                    this.pregled = response.data;
                    console.log(response);


                    axios
                        .get('lekar')
                        .then(response => {
                            this.lekari = response.data;
                            for(var id in this.lekari){
                                if(this.lekari[id].id == this.pregled.lekar.id){
                                    this.lekari[id] = this.pregled.lekar;
                                    break;
                                }
                            }
                            console.log(response);
                        })
                        .catch(() => { this.lekari = [{ime: 'pera',prezime: ''}]; });


                })
                .catch(() => { this.sale = [{posete: [], sala: {id: '1',naziv: 'Neka klinika'}}]; });

            
        }
        
    },
    components: {
        Sala,
        IzmenaSale
    },
    computed:{
        filterSale: function(){
            return this.sale.filter(sala => {
                if(sala.sala.naziv.match(this.search)){
                    return true;
                }
                else if(sala.sala.id == this.search){
                    return true;
                }
                return false;
            })
        } 
    },
    methods: {
        deleteSala: function(id){
            this.sale = this.sale.filter(sala => sala.sala.id !== id);
        },
        otvoriDialog: function(id){
            if(this.pregled.lekar == null){
                this.dialogSala = {...this.sale.filter(sala => sala.sala.id === id)[0].sala };
                this.dialog = true;
            }
            else{
                var sala = this.sale.find(sala => sala.sala.id == id);
                console.log(sala);
                this.pregled.sala = sala.sala;

                let pregledID = this.$route.params.id;
                axios
                .put('admin/zahtevi/'+ pregledID,this.pregled)
                .then((response) => {
                    console.log(response);
                    this.$store.commit("setSnackbar", {text:"Termin je uspesno zakazan", color: "success"});
                    this.$router.go("/sale");
                })
                .catch((err) => { 
                    console.log(err);
                    this.$store.commit("setSnackbar", {text:"Termin je zauzet", color: "error"});
                
                });
            }
        }
        
    }
}
</script>