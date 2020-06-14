<template>
    <v-card
        outlined="true"
        :color="pozadina"
    >
        <v-row>
            <v-col
                cols="8"
            >
                <v-card
                    flat="true"
                    :color="color"
                >
                    <v-container>
                        <v-text-field
                            v-model="sifra"
                            label="Pretraži"
                        >
                        </v-text-field>
                    </v-container>
                </v-card>
                <v-list
                    rounded="rounded"
                >
                    <v-list-item-group>
                        <v-list-item
                            v-for="element in filtriraniElementi"
                            :key="element[id]"
                            @click="odaberiElement(element)"
                        >
                            <v-row>
                                <v-col>
                                    {{element[id]}}
                                </v-col>
                                <v-col
                                    class="ml-auto"
                                >
                                    {{element[atribut]}}
                                </v-col>
                                <v-col>
                                    <v-icon>
                                        mdi-plus
                                    </v-icon>
                                </v-col>
                            </v-row>
                        </v-list-item>
                    </v-list-item-group>
                </v-list>
            </v-col>
            <v-col
                cols="4"
            >   
                    <v-list
                        rounded="rounded"
                        color="color"
                    >
                        <v-list-item-group>
                            <v-list-item
                                v-for="element in odabraniElementi"
                                :key="element[atribut]"
                                :color="color"
                            >
                                <v-row>
                                    <v-col>
                                        {{element[id]}}
                                    </v-col>
                                    <v-col
                                    >
                                        {{element[atribut]}}
                                    </v-col>
                                    <v-col
                                        class="ml-auto"
                                    >
                                        <v-btn icon @click="ukloniElement(element[id])">
                                            <v-icon>
                                                mdi-delete
                                            </v-icon>
                                        </v-btn>
                                    </v-col>
                                </v-row>
                            </v-list-item>
                        </v-list-item-group>
                    </v-list>
            </v-col>
        </v-row>
    </v-card>
</template>

<script>
    export default {
        name:"Pretrazivac",
        props:["atribut", "color", "id", "elementi", "pozadina", "odabrani"],
        data:function () {
            return{
                odabraniElementi:[],
                sifra:""
            }
        },
        watch:{
            odabrani(val){
                this.odabraniElementi = val;
            }
        },
        computed:{
            filtriraniElementi(){
                let sifra = this.sifra;
                let id = this.id;
                return this.elementi.filter(function (element) {
                    let sifraLeka = element[id]+"";
                    return sifraLeka.includes(sifra);
                });
            }

        },
        mounted:function () {
            console.log(this.odabrani);
            
           
            this.filtriraniElementi = this.elementi;
        },
        methods:{
            odaberiElement(element){
                this.sifra = "";
                let id = this.id;
                if(!this.odabraniElementi.find(function (l) {
                        return l[id] == element[id];
                    })){
                    this.odabraniElementi.push(element);
                }
                this.$emit("odabrani", this.odabraniElementi)
            },
            ukloniElement(sifra){
                console.log(sifra);
                let id = this.id;
                this.odabraniElementi = this.odabraniElementi.filter(function (element) {
                    return element[id]!=sifra;
                })
            }
        }
    }
</script>
