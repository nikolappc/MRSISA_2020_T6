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
        search: ''
    }),
    mounted () {
        axios
            .get('sala/DTO')
            .then(response => {
                this.sale = preurediDatum(response.data);
                console.log(response);
            })
            .catch(() => { this.sale = [{posete: [], sala: {id: '1',naziv: 'Neka klinika'}}]; });
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
            this.dialogSala = {...this.sale.filter(sala => sala.sala.id === id)[0].sala };
            this.dialog = true;
        }
        
    }
}
</script>