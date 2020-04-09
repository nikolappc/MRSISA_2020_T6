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
                <Lekar v-for="lekar in lekari" v-bind:key="lekar.id" v-bind:lekar="lekar" v-on:del-lekar="deleteLekar" />
                
            </tbody>
            <tr>
                <td align="right" colspan='4'><v-btn :to="{path: 'lekar/add'}" dark medium left class="blue" slot="action">Dodaj lekara</v-btn></td>
            </tr>
        </v-simple-table>
    </div>
</template>

<script>
import Lekar from "./Lekar.vue"
import axios from "axios"
export default {
    data: () => ({
        lekari : null
    }),
    mounted () {
        axios
            .get('lekar')
            .then(response => {
                this.lekari = response.data;
                console.log(response);
            })
            .catch(function (error) { console.log(error); });
    },
    components: {
        Lekar
    },
    methods: {
        deleteLekar: function(id){
            this.lekari = this.lekari.filter(lekar => lekar.id !== id);
        }
    }
}
</script>