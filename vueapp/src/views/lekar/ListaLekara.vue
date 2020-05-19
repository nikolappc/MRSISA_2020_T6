<template>
    <div>
        <h2>Lista lekara</h2>
        <v-text-field
            v-model="search"
            label="Pretraga"
            append-icon="mdi-magnify"
        ></v-text-field>
        <v-simple-table border="1">
            <thead>
                <th>Ime</th>
                <th>Prezime</th>
                <th>Adresa</th>
                <th>Broj Telefona</th>
                <th></th>
            </thead>
            <tbody>
                <Lekar v-for="lekar in filterLekari" v-bind:key="lekar.id" v-bind:lekar="lekar" v-on:del-lekar="deleteLekar"
                v-on:otvori="otvoriDialog"/>
                
            </tbody>
            <tr>
                <td align="right" colspan='4'><v-btn :to="{path: 'lekar/add'}" dark medium left class="blue" slot="action">Dodaj lekara</v-btn></td>
            </tr>
        </v-simple-table>
        <v-dialog
            v-model="dialog"
            max-width="500"
            >
                <v-card>
                    <v-card-title class="headline">Izmeni lekara</v-card-title>

                        <IzmenaLekara  v-bind:lekar="dialogLekar" v-on:zatvori="dialog = false"/>

                    
                </v-card>
        </v-dialog>
    </div>
</template>

<script>
import Lekar from "./Lekar.vue"
import IzmenaLekara from "./IzmenaLekar.vue"
import axios from "axios"
export default {
    data: () => ({
        lekari : [],
        dialog : false,
        dialogLekar: null,
        search: ''
    }),
    mounted () {
        axios
            .get('lekar')
            .then(response => {
                this.lekari = response.data;
                console.log(response);
            })
            .catch(() => { this.lekari = [{ime: 'pera',prezime: ''}]; });
    },
    components: {
        Lekar,
        IzmenaLekara
    },
    methods: {
        
        deleteLekar: function(id){
            this.lekari = this.lekari.filter(lekar => lekar.id !== id);
        },
        otvoriDialog: function(id){
            this.dialogLekar = {...this.lekari.filter(lekar => lekar.id === id)[0]};
            this.dialog = true;
        }

    },
    computed:{
        filterLekari: function(){
            return this.lekari.filter(lekar => {
                if(lekar.ime.match(this.search)){
                    return true;
                }
                else if(lekar.prezime == this.search){
                    return true;
                }
                return false;
            })
        } 
    },
}
</script>