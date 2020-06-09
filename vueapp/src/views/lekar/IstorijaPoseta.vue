<template>
    <v-container>
        Istorija pregleda
        <v-card
            outlined="true"
        >
            <v-container>
                
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
                .get('lekar/poseta/'+this.$store.state.ulogovan.id)
                .then((res) => {
                    this.pregledi = res.data;
                })
                .catch(error => { 
                console.log(error);
                
                this.$store.commit("setSnackbar", {text:"Whoops", color: "error"});
                });
      },
      methods:{
        promenaDijagnoza(dijagnoze){
            this.dijagnoze = dijagnoze;
        },
        izmeni(){
            axios.put("/poseta/pregled/"+this.pregled.id, this.pregled)
                .then(res=>{
                    console.log(res);
                    this.$store.commit("setSnackbar", {text:"Pregled je uspešno izmenjen.", color: "success"});
                    this.$router.push("/");
                })
                .catch(error=>{
                    console.log(error);
                    this.$store.commit("setSnackbar", {text:"Izvinjavamo se došlo je do greške.", color: "error"});
                })
        }
    
      }
    }
    
    </script>