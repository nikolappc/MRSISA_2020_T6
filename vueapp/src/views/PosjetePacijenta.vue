<template>
  <div class="posjetePacijenta" style="width:100%;">
    <v-container style="width:100%;"><v-card style="width:100%;">
		<v-card-title>Istorija pregleda i operacija</v-card-title>
		<v-card-subtitle>{{ ulogovani.ime }} {{ ulogovani.prezime }}</v-card-subtitle>
		<v-card-text>
      <v-data-table
        :headers="headers"
        :items="pregledi"
        :items-per-page="10"
        class="elevation-1"
        :custom-sort="customSort">
        <template v-slot:item.termin.pocetak="{ item }">{{ formatDate(item.termin.pocetak)}}</template>
        <template v-slot:item.termin.kraj="{ item }">{{ formatDate(item.termin.kraj)}}</template>
        <template v-slot:item.dijagnoze="{ item }">
          <div v-if="item.hasOwnProperty('dijagnoze')">
            <ul v-for="d in item.dijagnoze" v-bind:key="d.sifraDijagnoze">
              <li>{{ d.nazivDijagnoze }}</li>
            </ul>
          </div>
          <div v-else>
            
          </div>
        </template>
        <template v-slot:item.recepti="{ item }">
          <div v-if="item.hasOwnProperty('recepti')">
            <ul v-for="r in item.recepti" v-bind:key="r.id">
              <li>{{ r.lek.nazivLeka }} {{ overenRecept(r.overen) }}</li>
            </ul>
          </div>
          <div v-else>
            
          </div>
        </template>
        <template v-slot:item.lekari="{ item }">
          <div v-if="item.hasOwnProperty('lekar')">
              {{ item.lekar }}
          </div>
          <div v-else>
            <ul v-for="r in item.lekari" v-bind:key="r">     
              <li>{{ r }}</li>
            </ul>
          </div>
        </template>
      </v-data-table></v-card-text></v-card>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios';
import moment from 'moment';
import router from "../router/index.js"

export default {
  name: 'PosjetePacijenta',
  data: () => ({
    ulogovani : {},
    pregledi : [],
    headers: [
		{
			text: 'Tip', 
			value: 'tip.naziv', 
			sortable: true, 
		},
		{
          text: 'Opis', 
          value: 'opis', 
          sortable: true, 
        },
        {
          text: 'Sala', 
          value: 'sala.naziv', 
          sortable: true, 
        },
        {
          text: 'Početak', 
          value: 'termin.pocetak', 
          sortable: true, 
        },
        {
          text: 'Kraj', 
          value: 'termin.kraj', 
          sortable: true, 
        },
        {
          text: 'Dijagnoze', 
          value: 'dijagnoze', 
          sortable: false, 
        },
        {
          text: 'Recepti', 
          value: 'recepti', 
          sortable: false, 
        },
        {
          text: 'Lekari', 
          value: 'lekari', 
          sortable: false, 
        },
      ]
  }),
  mounted () {
	this.ulogovani = this.$store.state.ulogovan;
	if (this.ulogovani=="") {
		router.push("/");
	} else {
		axios
		.get('api/pacijent/listaPregleda/' + this.ulogovani.id)
		.then(response => {
			this.pregledi = response.data;
			console.log(this.pregledi);
		});
	}
	},
	methods: {
    formatDate(value) {
			return moment(String(value)).format('DD.MM.YYYY. HH:mm');
		},
    overenRecept(value) {
      if (value == true) {
        return "(overen)";
      } else {
        return "(nije overen)";
      }
    },
    customSort: function(items, index, isDesc) {
      items.sort((a, b) => {
          if (index[0]=='termin.pocetak') {
            console.log(index[0]);
            console.log("uslo");
            var d1 = new Date(a['termin'].pocetak);
            var d2 = new Date(b['termin'].pocetak);
            console.log(d1);
            console.log(a[index]);
            if (!isDesc[0]) {
                console.log("desc uslo");
                console.log(d1.getTime());
                return d1.getTime() < d2.getTime() ? -1 : 1;
            } else {
                return d2.getTime() < d1.getTime() ? -1 : 1;
            }
          } else if (index[0]=='termin.kraj') {
            console.log(index[0]);
            var dd1 = new Date(a['termin'].kraj);
            var dd2 = new Date(b['termin'].kraj);
            console.log(dd1);
            console.log(a[index]);
            if (!isDesc[0]) {
                console.log(dd1.getTime());
                return dd1.getTime() < dd2.getTime() ? -1 : 1;
            } else {
                return dd2.getTime() < dd1.getTime() ? -1 : 1;
            }
          } else if (index[0]=='tip.naziv') {
            console.log(index[0]);
            if (!isDesc[0]) {
              console.log(a[index]);
              return a['tip'].tip < b['tip'].naziv ? -1 : 1;
            } else {
              return b['tip'].tip < a['tip'].naziv ? -1 : 1;
            }
          }
          else {
            console.log(index[0]);
            if (!isDesc[0]) {
              console.log(a[index]);
              return a[index] < b[index] ? -1 : 1;
            } else {
              return b[index] < a[index] ? -1 : 1;
            }
          }
      });
      return items;
    }
  },
}
</script>