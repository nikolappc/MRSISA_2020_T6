<template>
    <v-container>
        <label class="display-1">
            Istorija pregleda
        </label>
        <v-card
            outlined="true"
        >
            <v-container>

                <v-list
                    color="blue"
                >
                    <v-list-item-group>
                        <v-list-item
                            v-for="poseta in pregledi"
                            :key = "poseta.id"
                        >
                            <v-row>
                                <v-col>
                                    <v-icon>
                                        mdi-doctor
                                    </v-icon>
                                </v-col>
                                <v-col>
                                    {{poseta.id}}
                                </v-col>
                                <v-col
                                >
                                    {{poseta.tip.naziv}}
                                </v-col>
                                <v-col
                                >
                                    {{poseta.tip.opis}}
                                </v-col>
                                <v-col>
                                    <v-chip 
                                        v-if="poseta.odradjen"
                                        color="success"
                                    >
                                        Odrađen
                                    </v-chip
                                    >
                                    <v-chip
                                        v-else-if="checkStart(poseta)"
                                        color="warning"
                                    >
                                        U toku
                                    </v-chip>
                                    <v-chip
                                        v-else
                                        color="error"
                                    >
                                        Nije odrađen
                                    </v-chip>
                                </v-col>
                                <v-col>
                                    <v-btn color="success" :disabled="poseta.recepti.length==0||!poseta.odradjen" collor="success" :to="{ name: 'OveriRecepte', params: { pregled: poseta }}" class="ml-auto">
                                        Overi recepte
                                    </v-btn>
                                </v-col>
                            </v-row>
                        </v-list-item>
                    </v-list-item-group>
                </v-list>
            </v-container>
        </v-card>
    </v-container>
</template>



<script>
    import axios from "axios";
    export default {
    name: 'IstorijaPoseta',
      data: () => ({
        pregledi: [],
      }),
      mounted () {
           
            axios
                .get('poseta/pregledi')
                .then((res) => {
                    console.log(res.data);
                    this.pregledi = res.data;
                    this.pregledi.forEach(element => {
                        element.start = new Date(element.termin.pocetak);
                        element.end = new Date(element.termin.kraj);
                    });
                    this.pregledi.sort((a, b)=>a.start < b.start);
                })
                .catch(error => { 
                    console.log(error);
                    this.$store.commit("setSnackbar", {text:"Whoops", color: "error"});
                });
      },
      methods:{
        checkStart: function(pregled){
            if(pregled.odradjen){
                return false;
            }
            return this.checkDate(pregled);
        },
        checkDate(pregled){
            let trenutno = new Date(Date.now() + 5 * 60000);
            if(pregled.start <= trenutno && trenutno<=pregled.end){
                return true;
            }
            return false;
        },
        izracunajTrajanje(poset){
            return (poset.end - poset.start)/1000/60 +" min";
        },       
        formatirajDatum (a) {
            return `${a.getFullYear()}-${a.getMonth() + 1}-${a.getDate()} ${a.getHours()}:${a.getMinutes()}`;
        },
    }
}
    
</script>

<style>
.mini{
    height: 200px;
}
</style>