<template>
    <div>
        <h2>Lista sala</h2>
        <v-text-field
            v-model="search"
            label="Pretraga"
            append-icon="mdi-magnify"
        ></v-text-field>

        <v-datetime-picker 
        label="Početak slobodnog termina"
        v-model="pocetak"
        dateFormat= 'dd.MM.yyyy'
        >
            <template slot="dateIcon">
                <v-icon>mdi-calendar</v-icon>
            </template>

            <template slot="timeIcon">
                <v-icon>mdi-clock-outline</v-icon>
            </template>
        </v-datetime-picker>


        <v-datetime-picker 
        label="Kraj slobodnog termina"
        v-model="kraj"
        dateFormat= 'dd.MM.yyyy'
        >
            <template slot="dateIcon">
                <v-icon>mdi-calendar</v-icon>
            </template>

            <template slot="timeIcon">
                <v-icon>mdi-clock-outline</v-icon>
            </template>
        </v-datetime-picker>


        <v-simple-table border="1"
        >
            <thead>
                <th id="id">ID</th>
                <th id="naziv">Naziv</th>
                <th id=""></th>
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
        .catch(() => { this.sale =  preurediDatum(
            [
                {"sala":{"id":1,"naziv":"sala 1"},
                    "posete":[{"start":"2020-03-20T07:00:00.000+0000","end":"2020-03-20T08:00:00.000+0000","name":"Sirius Black","details":"korona test"},{"start":"2020-03-20T08:00:00.000+0000","end":"2020-03-20T09:00:00.000+0000","name":"Sirius Black","details":"terapija"},{"start":"2020-03-20T09:00:00.000+0000","end":"2020-03-20T10:00:00.000+0000","name":"Sirius Black","details":"previjanje"}]},
            
                {"sala":{"id":2,"naziv":"operaciona"},
                    "posete":[{"start":"2020-03-20T07:00:00.000+0000","end":"2020-03-20T09:00:00.000+0000","name":"Operacija","details":"slijepo crijevo"},{"start":"2020-03-20T07:00:00.000+0000","end":"2020-03-20T09:00:00.000+0000","name":"Operacija","details":"slijepo crijevo"}]}]
        )
        
        });

        if(pregledID !== undefined){
            axios
                .get('admin/zahtevi/'+ pregledID)
                .then(response => {
                    this.pregled = response.data;
                    console.log(response);
                    //this.pregled.termin.pocetak = formatirajDatum(this.pregled.termin.pocetak);
                    this.pocetak = new Date(this.pregled.termin.pocetak);
                    //this.pregled.termin.kraj = formatirajDatum(this.pregled.termin.kraj);
                    this.kraj = new Date(this.pregled.termin.kraj);
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
                let uslov1 = false;
                if(sala.sala.naziv.match(this.search)){
                    uslov1 = true;
                }
                else if(sala.sala.id == this.search){
                    uslov1 = true;
                }

                let uslov2 = false;
                if(this.pocetak != null && this.kraj != null){
                    if(sala.posete.filter( poseta => {
                        return !(new Date(poseta.start) > new Date(this.kraj)
                        || new Date(poseta.end) < new Date(this.pocetak))
                        ;
                    }).length == 0){
                        uslov2 = true;
                    }
                }
                else
                    uslov2 = true;


                return uslov1 && uslov2;
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

                this.pregled.termin.pocetak = this.pocetak;
                this.pregled.termin.kraj = this.kraj;
                let pregledID = this.$route.params.id;
                axios
                .put('admin/zahtevi/'+ pregledID,this.pregled)
                .then((response) => {
                    console.log(response);
                    this.$store.commit("setSnackbar", {text:"Termin je uspesno zakazan", color: "success"});
                    this.$router.push("/homeAdminKlinike");
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