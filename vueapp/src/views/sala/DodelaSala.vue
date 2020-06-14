<template>
    <v-container>
        <v-card
            outlined="true"
        >
            <v-container>

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
        
                    
                <v-card
                    v-if="isPregled"
                >
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
                </v-card>
                <v-container
                    v-else
                >
                    <template
                        v-if="lekari.length!=0"
                    >                        
                        <v-card
                        >
                            <v-list
                            >
                                <v-subheader>Odabrani lekari</v-subheader>
                                <v-list-item-group>
                                    <v-list-item
                                        v-for="lekar in lekari"
                                        :key="lekar.id"
                                    >
                                        <v-checkbox
                                            v-model="pregled.lekari"
                                            :label="lekar.ime+' '+lekar.prezime"
                                        >   
                                        </v-checkbox>
                                    </v-list-item>
                                </v-list-item-group>
                            </v-list>
                        </v-card>
                    </template>
                    <v-card
                        color="error"
                        v-else
                    >
                        <v-container>
                            Nema slobodnih lekara u ovom terminu.<br>
                            Odaberite drugi termin.
                        </v-container>
                    </v-card>
                </v-container>
                <v-vontainer>
                    <v-row>
                        <v-col
                            class="ml-auto"
                        >
                            <v-btn color="success" @click="done">Završi odabir</v-btn>
                        </v-col>
                    </v-row>
                </v-vontainer>
                <v-dialog
                    v-model="dialog"
                    max-width="500"
                    >
                        <v-card>
                            <v-card-title class="headline">Izmeni salu</v-card-title>
                                <IzmenaSale  v-bind:sala="dialogSala" v-on:zatvori="dialog = false"/>
                        </v-card>
                </v-dialog>
            </v-container>
        </v-card>
        <v-overlay
            v-model="overlay"
            absolute="true"
        >
            <v-progress-circular
                :indeterminate="overlay"
                :width="7"
                color="primary"
            >
            </v-progress-circular>
        </v-overlay>
    </v-container>
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
        overlay: false,
        dialog : false,
        dialogSala: null,
        search: '',
        pregled: {},
        lekari: [],
        pocetak: null,
        kraj: null,
    }),
    props:{
        isPregled:Boolean,
        id:Number
    },
    watch:{
        pocetak:function () {
            
        },
        kraj:function () {
            
        }
    },
    mounted () {

        console.log(this.isPregled);
        console.log(typeof this.isPregled);
        console.log(this.id);
        console.log(this.address);
        
        this.isPregled = (this.isPregled=="true");

        if(!this.isPregled){
            this.pregled.lekari = [];
            delete this.pregled.lekar;
        }
        console.log(this.address);

        axios
        .get('sala/DTO')
        .then(response => {
            this.sale = preurediDatum(response.data);
            console.log(response);
        })
        .catch(error => { 
            console.log(error);
        });
        if(this.id !== undefined){
            axios
                .get(this.address+ this.id)
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
                                if(this.isPregled){
                                    for(var id in this.lekari){
                                        if(this.lekari[id].id == this.pregled.lekar.id){
                                            this.lekari[id] = this.pregled.lekar;
                                            break;
                                        }
                                    }
                                }else{
                                    for(var id in this.lekari){
                                        if(this.lekari[id].id == this.pregled.lekari[0].id){
                                            this.lekari[id] = this.pregled.lekari[0];
                                            break;
                                        }
                                    }
                                }
                                console.log(response);
                            })
                            .catch(error => { 
                                console.log(error);
                                this.$store.commit("setSnackbar", {text:"U sistemu ne postoji nijedan doktor.", color: "error"});
                                this.$router.push("/");
                            });
                })
                .catch(error => { 
                    console.log(error);
                    this.$store.commit("setSnackbar", {text:"Izvinjavamo se došlo je do greške.", color: "error"});
                 });
        }
    },
    components: {
        Sala,
        IzmenaSale
    },
    computed:{
        address(){
            if(this.isPregled===true){
                return "adminKlinike/pregled/"
            }
            else{
                return "adminKlinike/operacija/"
            }
        },
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
        done(){
            this.$router.push("/");
        },
        otvoriDialog: function(id){
            if(this.isPregled&&this.pregled.lekar == null){
                this.dialogSala = {...this.sale.filter(sala => sala.sala.id === id)[0].sala };
                this.dialog = true;
            }
            else{
                var sala = this.sale.find(sala => sala.sala.id == id);
                console.log(sala);
                this.pregled.sala = sala.sala;

                this.pregled.termin.pocetak = this.pocetak;
                this.pregled.termin.kraj = this.kraj;
                this.overlay = true;
                axios
                    .put(this.address+ this.id,this.pregled)
                    .then((response) => {
                        console.log(response);
                        this.$store.commit("setSnackbar", {text:"Termin je uspesno zakazan", color: "success"});
                        this.$router.push("/homeAdminKlinike");
                        this.overlay = false;
                    })
                    .catch((err) => { 
                        console.log(err);
                        this.$store.commit("setSnackbar", {text:"Lekar nije dostupan u tom delu dana", color: "error"});
                        this.overlay = false;
                    });
            }
        }
    }
}
</script>