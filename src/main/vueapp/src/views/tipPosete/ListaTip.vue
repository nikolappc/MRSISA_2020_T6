<template>
    <div>
        <h2>Lista tipova</h2>
        <v-text-field
            v-model="search"
            label="Pretraga"
            append-icon="mdi-magnify"
        ></v-text-field>
        <v-simple-table border="1">
            <thead>
                <th>Naziv</th>
                <th>Tip (Din/h)</th>
                <th>Cena</th>
                <th></th>
            </thead>
            <tbody>
                <Tip v-for="tip in filterTip" v-bind:key="tip.id" v-bind:tip="tip" v-on:del-tip="deleteTip"
                v-on:otvori="otvoriDialog"/>
                
            </tbody>
            <tr>
                <td align="right" colspan='4'><v-btn :to="{path: '/tipoviPoseta/add'}" dark medium left class="blue" slot="action">Dodaj tip</v-btn></td>
            </tr>
        </v-simple-table>
        <v-dialog
            v-model="dialog"
            max-width="500"
            >
                <v-card>
                    <v-card-title class="headline">Izmeni tip</v-card-title>

                        <IzmenaTipa  v-bind:tip="dialogTip" v-on:zatvori="dialog = false"/>

                    
                </v-card>
        </v-dialog>
    </div>
</template>

<script>
import Tip from "./Tip.vue"
import IzmenaTipa from "./IzmenaTip.vue"
import axios from "axios"
export default {
    data: () => ({
        tipovi : [],
        dialog : false,
        dialogTip: null,
        search: '',
    }),
    mounted () {
        axios
            .get('tip')
            .then(response => {
                this.tipovi = response.data;
                console.log(response);
            })
            .catch(() => { this.tipovi = [{naziv: 'pera',tip: 'operacija',stavkaCenovnika: {cena: 20}}]; });
    },
    components: {
        Tip,
        IzmenaTipa
    },
    methods: {
        deleteTip: function(id){
            this.tipovi = this.tipovi.filter(tip => tip.id !== id);
        },
        otvoriDialog: function(id){
            this.dialogTip = {...this.tipovi.filter(tip => tip.id === id)[0]};
            this.dialog = true;
        }

    },
    computed:{
        filterTip: function(){
            return this.tipovi.filter(tip => {
                if(tip.naziv.match(this.search)){
                    return true;
                }
                else if(tip.tip.match(this.search)){
                    return true;
                }
                else if((tip.stavkaCenovnika.cena + "").match(this.search)){
                    return true;
                }
                return false;
            })
        } 
    }
}
</script>