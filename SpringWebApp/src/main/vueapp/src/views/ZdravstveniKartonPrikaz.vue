<template>
  <div class="zdravstveniKartonPrikaz">
    <v-container>
			<h2>Zdravstveni karton</h2>
			<h4>{{ ulogovan.ime }} {{ ulogovan.prezime }} {{ ulogovan.tip }}</h4>
      <v-simple-table>
        <tbody>
          <tr>
            <td>Visina:</td>
            <td>{{ karton.visina }} m</td>
          </tr>
          <tr>
            <td>Tezina:</td>
            <td>{{ karton.tezina }} kg</td>
          </tr>
          <tr>
            <td>Krvna grupa:</td>
            <td>{{ karton.krvnaGrupa }}</td>
          </tr>
        </tbody>
      </v-simple-table>
      <!--<v-simple-table>
        <thead>
					<th>Id pregleda</th>
					<th>Opis</th>
					<th>Sala</th>
					<th>Pocetak</th>
					<th>Kraj</th>
					<th>Tip</th>
          <th>Dijagnoze</th>
          <th>Recepti</th>
          <th>Lekari</th>
        </thead>
        <tbody>
					<tr v-for="p in this.pregledi" v-bind:key="p.id">
							<td>{{ p.id }}</td>
							<td>{{ p.opis }}</td>
							<td>{{ p.sala.id }}</td>
							<td>{{ formatDate(p.termin.pocetak) }}</td>
							<td>{{ formatDate(p.termin.kraj) }}</td>
							<td>{{ p.tip.tip }}</td>
              <td>
                <div v-if="p.hasOwnProperty('dijagnoze')">
                  <ul v-for="d in p.dijagnoze" v-bind:key="d.sifraDijagnoze">
                    <li>{{ d.nazivDijagnoze }}</li>
                  </ul>
                </div>
                <div v-else>
                  -
                </div>
              </td>
              <td>
                <div v-if="p.hasOwnProperty('recepti')">
                  <ul v-for="r in p.recepti" v-bind:key="r.id">
                    <li>{{ r.lek.nazivLeka }} {{ overenRecept(r.overen) }}</li>
                  </ul>
                </div>
                <div v-else>
                  -
                </div>
              </td>
              <td>
                <div v-if="p.hasOwnProperty('lekar')">
                  {{ p.lekar }}
                </div>
                <div v-else>
                  <ul v-for="r in p.lekari" v-bind:key="r">        
                    <li>{{ r }}</li>
                  </ul>
                </div>
              </td>
          </tr>
        </tbody>
      </v-simple-table>-->
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
            -
          </div>
        </template>
        <template v-slot:item.recepti="{ item }">
          <div v-if="item.hasOwnProperty('recepti')">
            <ul v-for="r in item.recepti" v-bind:key="r.id">
              <li>{{ r.lek.nazivLeka }} {{ overenRecept(r.overen) }}</li>
            </ul>
          </div>
          <div v-else>
            -
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
      </v-data-table>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios';
import moment from 'moment';
import router from "../router/index.js"

export default {
  name: 'ZdravstveniKartonPrikaz',
  data: () => ({
    ulogovan : {},
    pregledi : [],
    karton : {},
    headers: [
        {
          text: 'Id',
          sortable: true,
          value: 'id',
        },
        {
          text: 'Opis', 
          value: 'opis', 
          sortable: true, 
        },
        {
          text: 'Sala', 
          value: 'sala.id', 
          sortable: true, 
        },
        {
          text: 'Pocetak', 
          value: 'termin.pocetak', 
          sortable: true, 
        },
        {
          text: 'Kraj', 
          value: 'termin.kraj', 
          sortable: true, 
        },
        {
          text: 'Tip', 
          value: 'tip.tip', 
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
      axios
      .get('api/ulogovan')
      .then(response => {
          this.ulogovan = response.data;
          axios
          .get('api/pacijent/listaPregleda/' + this.ulogovan.id)
          .then(response => {
              this.pregledi = response.data;
          });
          axios
          .get('api/pacijent/karton/' + this.ulogovan.id)
          .then(response => {
              this.karton = response.data;
          });
      })
      .catch(function (error) { console.log(error); router.push("/"); });
	},
	methods: {
    formatDate(value) {
      console.log(value);
      console.log(String(value));
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
          if (index=='termin.pocetak' || index=='termin.kraj') {
            //var d1 = new Date(a[index]);
            //var d2 = new Date(b[index]);
            if (!isDesc) {
                //return new Date(b[index]) - new Date(a[index]);
                //return dates.compare(new Date(a[index]), new Date(b[index]));
                //return d1 < d2;
                return a[index] < b[index];
            } else {
                //return new Date(a[index]) - new Date(b[index]);
                //return dates.compare(new Date(b[index]), new Date(a[index]));
                //return d2 < d1;
                return b[index] < a[index];
            }
          }
          else if (index=='id' || index=='sala.id') {
            if (!isDesc) {
              return a[index] < b[index] ? -1 : 1;
            } else {
              return b[index] < a[index] ? -1 : 1;
            }
          }
          else if (index=='opis') {
            if (!isDesc) {
                return a[index].toLowerCase().localeCompare(b[index].toLowerCase());
              }
              else {
                return b[index].toLowerCase().localeCompare(a[index].toLowerCase());
              }
          }
          else {
            if(typeof a[index] !== 'undefined'){
              if (!isDesc) {
                return a[index].toLowerCase().localeCompare(b[index].toLowerCase());
              }
              else {
                return b[index].toLowerCase().localeCompare(a[index].toLowerCase());
              }
            }
          }
      });
      return items;
    }
  },
}
</script>