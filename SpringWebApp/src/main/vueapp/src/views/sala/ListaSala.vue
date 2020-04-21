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
                <Sala @click="dialog = true" v-for="sala in sale" v-bind:key="sala.id" v-bind:sala="sala" v-on:del-sala="deleteSala" />
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
import Sala from "./Sala.vue"
import axios from "axios"
export default {
    data: () => ({
        sale : null,
        dialog : false
    }),
    mounted () {
        axios
            .get('sala')
            .then(response => {
                this.sale = response.data;
                console.log(response);
            })
            .catch(() => { this.sale = [{id: '1',naziv: 'Neka klinika'}]; });
    },
    components: {
        Sala
    },
    methods: {
        deleteSala: function(id){
            this.sale = this.sale.filter(sala => sala.id !== id);
        }
    }
}
</script>