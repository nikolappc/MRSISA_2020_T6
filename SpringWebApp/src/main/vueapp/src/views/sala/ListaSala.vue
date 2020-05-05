<template>
    <div>
        <h2>Lista sala</h2>
        <v-simple-table border="1">
            <thead>
                <th>ID</th>
                <th>Naziv</th>
                <th></th>
            </thead>
            <tbody>
                <Sala @click="dialog = true" v-for="sala in sale" v-bind:key="sala.id"
                 v-bind:sala="sala.sala" v-on:del-sala="deleteSala" v-bind:termini="sala.posete" />
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
export default {
    data: () => ({
        sale : [],
        dialog : false
    }),
    mounted () {
        axios
            .get('sala/DTO')
            .then(response => {
                this.sale = preurediDatum(response.data);
                console.log(response);
            })
            .catch(() => { this.sale = [{id: '1',naziv: 'Neka klinika'}]; });
    },
    components: {
        Sala
    },
    methods: {
        deleteSala: function(id){
            console.log(this.sale);
            this.sale = this.sale.filter(sala => sala.sala.id !== id);
            console.log(this.sale);
        },
        
    }
}
</script>