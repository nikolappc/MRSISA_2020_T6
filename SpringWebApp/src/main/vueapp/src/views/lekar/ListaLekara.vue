<template>
    <div>
        <h2>Lista lekara</h2>
        <v-simple-table border="1">
            <thead>
                <th>Ime</th>
                <th>Prezime</th>
                <th>Adresa</th>
                <th>Broj Telefona</th>
                <th></th>
            </thead>
            <tbody>
                <Lekar v-for="lekar in lekari" v-bind:key="lekar.id" v-bind:lekar="lekar" v-on:del-lekar="deleteLekar"
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

                        <IzmenaLekara  v-bind:lekar="dialogLekar" />

                    
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
        lekari : null,
        dialog : false,
        dialogLekar: null
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
        },
        promeniLekara: function(id){
            id;
        },

    }
}
</script>