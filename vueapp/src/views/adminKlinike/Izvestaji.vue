<template>
<div>
  <h3>
        Ocena klinike: {{izvestaj.prosekKlinike}}
  </h3>
  <v-data-table
      :headers="headers"
      :items="izvestaj.oceneLekara"
      hide-default-footer
    >
      <template v-slot:top>
        <v-toolbar flat color="white">
          <v-toolbar-title>Ocene lekara </v-toolbar-title>
        </v-toolbar>
      </template>

      <template v-slot:item.lekar="{ item }">
        {{item.lekar.ime}} {{item.lekar.prezime}}
      </template>
    </v-data-table>
    <div>
      <h2>Graph</h2>
      <v-select
        width="200"
        v-model="opcija"
        :items="opcije"
        label="ChartType"
        outlined
        dense
        @change="dovuciPodatke"
      >
      </v-select>
      <GChart
        type="ColumnChart"
        :data="chartData"
      /> 
    </div>
    <div>
      <h2>Troskovi</h2>
      <v-datetime-picker 
          label="Početak"
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
      label="Kraj"
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
      Troskovi: {{troskovi}}
      <v-spacer></v-spacer>
      <v-btn color="success" @click="nadjiTroskove">
          Nadji troskove
      </v-btn>
      
    </div>
</div>
</template>

<script>
import { GChart } from "vue-google-charts";
import axios from "axios"
export default {
    data: () => ({
      headers: [
        {
          text: 'Lekar',
          align: 'start',
          value: 'lekar',
        },
        { text: 'Ocena', value: 'ocena' }
      ],
      opcija: "day",
      opcije: [
        "day","month","year"
      ],
      izvestaj: {},
      chartData: [
        ["day","Number"],
        ["22.23.123",2]
      ],
      pocetak: "",
      kraj: "",
      troskovi: 0
    }),
    methods: {
      nadjiTroskove: function(){
        if(this.pocetak == ""){
          this.$store.commit("setSnackbar", {text:"Niste uneli pocetak", color: "error"});
          return;
        }
        if(this.kraj == ""){
          this.$store.commit("setSnackbar", {text:"Niste uneli kraj", color: "error"});
          return;
        }
        if(new Date(this.pocetak) > new Date(this.kraj)){
          this.$store.commit("setSnackbar", {text:"Krajnji datum mora biti veci od pocetnog", color: "error"});
          return;
        }

        axios
            .post('adminKlinike/troskovi', {pocetak: new Date(this.pocetak), kraj: new Date(this.kraj)})
            .then(response => {
                this.troskovi = response.data.trosak;
                console.log(response);
            })
            .catch((erro) => { 
              console.log(erro);
            });

      },
      dovuciPodatke: function(tip){
        console.log(tip);
        axios
            .get('adminKlinike/izvestaj/'+tip)
            .then(response => {
                this.chartData = this.preraspodeli(response.data,tip);
                console.log(response);
            })
            .catch(() => { 
              let a = {"podaci":[{"podatak":"2020-03-20 00:00:00.0","vrednost":3},{"podatak":"2020-05-21 00:00:00.0","vrednost":3},{"podatak":"2020-06-08 00:00:00.0","vrednost":1},{"podatak":"2020-06-10 00:00:00.0","vrednost":1},{"podatak":"2020-06-25 00:00:00.0","vrednost":2}]};
              this.chartData = this.preraspodeli(a,"day"); console.log(this.chartData);
            });
      },
      preraspodeli: function(data,tip){

        let matrix = [[tip,"Number"]];
        for(let podatak of data.podaci){
          matrix.push([this.formatirajDatum(podatak.podatak),podatak.vrednost]);
        }
        console.log(matrix);
        return matrix;
      },
      formatirajDatum: function(datum){
          if(this.opcija == "day")
            return datum.substring(8,10) +"." +datum.substring(5,7) + "." + datum.substring(0,4);
          else if(this.opcija == "month")
            return datum.substring(5,7) + "." + datum.substring(0,4);
          else
            return datum.substring(0,4);
        },
    },
    mounted(){
        axios
            .get('adminKlinike/izvestaj')
            .then(response => {
                this.izvestaj = response.data;
                console.log(response);
            })
            .catch(() => {});
        axios
            .get('adminKlinike/izvestaj/day')
            .then(response => {
                this.chartData = this.preraspodeli(response.data,"Day");
                console.log(response);
            })
            .catch(() => { 
              let a = {"podaci":[{"podatak":"2020-03-20 00:00:00.0","vrednost":3},{"podatak":"2020-05-21 00:00:00.0","vrednost":3},{"podatak":"2020-06-08 00:00:00.0","vrednost":1},{"podatak":"2020-06-10 00:00:00.0","vrednost":1},{"podatak":"2020-06-25 00:00:00.0","vrednost":2}]};
              this.chartData = this.preraspodeli(a,"Day"); console.log(this.chartData);
            });
    },
    components:{
      GChart
    }
}
</script>

<style>

</style>