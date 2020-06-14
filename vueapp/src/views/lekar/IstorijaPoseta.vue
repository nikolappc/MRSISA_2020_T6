<template>
    <v-container>
        <label class="display-1">
            Istorija poseta
        </label>
        <v-card
            outlined="true"
        >
            <v-container>
                <v-expansion-panels accordion>
                    <v-expansion-panel
                        v-for="poseta in pregledi"
                        :key = "poseta.id"
                    >
                        <v-expansion-panel-header
                            :color="poseta.tip.tip == 'pregled'?'blue':'lek'"
                        >
                            <template v-if="poseta.tip.tip=='operacija'">
                                <v-icon >
                                    mdi-box-cutter
                                </v-icon>
                                Operacija
                            </template>
                            <template v-else>
                                <v-icon>
                                    mdi-doctor
                                </v-icon>
                                Pregled
                            </template>
                            <v-spacer>

                            </v-spacer>
                            {{formatirajDatum(poseta.start)}}
                            <v-spacer>

                            </v-spacer>
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
                        </v-expansion-panel-header>
                        <v-expansion-panel-content>
                            <v-simple-table>
                                <tbody>
                                    <tr>
                                        <td>
                                            Pacijent
                                        </td>
                                        <td>
                                            {{poseta.pacijent.ime+' '+poseta.pacijent.prezime}}
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            Trajanje
                                        </td>
                                        <td>
                                            {{izracunajTrajanje(poseta)}}
                                        </td>
                                    </tr>
                                </tbody>
                            </v-simple-table>
                            <v-card
                                outlined="true"
                            >
                                {{poseta.opis}}
                            </v-card>
                            <v-btn
                                v-if="poseta.tip.tip=='pregled'&&poseta.odradjen"
                                :to="'/izmenaPregleda/'+poseta.id"
                                :disabled="!poseta.odradjen"
                            >
                                Izmena pregleda
                            </v-btn>
                            <v-btn
                                v-if="checkStart(poseta)"
                                color="success"
                                class="mr-4"
                                @click="pocniPregled(poseta.id)"
                            >
                                Započni
                            </v-btn>
                        </v-expansion-panel-content>
                    </v-expansion-panel>
                </v-expansion-panels>
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
        lekar:{}
      }),
      mounted () {
            this.lekar = this.$store.state.ulogovan;
           
            axios.get("/dijagnoza")
                .then(res=>{
                    this.dijagnoze = res.data;
                 
                }).catch(error=>{
                    console.log(error);
                });
            axios
                .get('lekar/poseta/'+this.lekar.id)
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
        pocniPregled: function(idPregleda){
            const error = (err)=>{
                this.$store.commit("setSnackbar", {text:"Započinjanje pregleda nije dostupno", color: "error"});
                console.log(err);
            }
            console.log(this.$store.state.ulogovan);
            axios
            .post('lekar/pocetak/' + idPregleda, this.$store.state.ulogovan)
            .then(response => {
                console.log(response.data);
                var provera = response.data;
                if(provera.zapocni){
                    this.$router.push("/pregled/"+ idPregleda);
                }else{
                    error(provera);
                }
            })    
            .catch(err =>error(err));
    },
    }
}
    
</script>

<style>
.mini{
    height: 200px;
}
</style>