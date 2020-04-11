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
                <Lekar @click="dialog = true" v-for="lekar in lekari" v-bind:key="lekar.id" v-bind:lekar="lekar" v-on:del-lekar="deleteLekar" />
                <v-dialog
                v-model="dialog"
                max-width="290"
                >
                <v-card>
                    <v-card-title class="headline">Use Google's location service?</v-card-title>

                    <v-card-text>
                    Let Google help apps determine location. This means sending anonymous location data to Google, even when no apps are running.
                    </v-card-text>

                    <v-card-actions>
                    <v-spacer></v-spacer>

                    <v-btn
                        color="green darken-1"
                        text
                        @click="dialog = false"
                    >
                        Disagree
                    </v-btn>

                        <v-btn
                            color="green darken-1"
                            @click="dialog = false"
                        >
                            Save
                        </v-btn>
                    </v-card-actions>
                </v-card>
                </v-dialog>
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
        lekari : null,
        dialog : false
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
        Lekar
    },
    methods: {
        deleteLekar: function(id){
            this.lekari = this.lekari.filter(lekar => lekar.id !== id);
        }
    }
}
</script>