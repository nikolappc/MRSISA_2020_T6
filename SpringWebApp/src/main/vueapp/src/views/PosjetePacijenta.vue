<template>
  <div class="posjetePacijenta">
    <v-container>
			<h2>Istorija pregleda i operacija</h2>
			<h4>{{ ulogovan.ime }} {{ ulogovan.prezime }}</h4>
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
      </v-data-table>
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
    ulogovan : {},
    pregledi : [],
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
      })
      .catch(function (error) { console.log(error); router.push("/"); });
	},
	methods: {
    formatDate(value) {
      //console.log(value);
      //console.log(String(value));
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
            //var d1 = moment(this.formatDate(a[index]), 'DD.MM.YYYY. HH:mm');
            //var d2 = moment(this.formatDate(b[index]), 'DD.MM.YYYY. HH:mm');
            if (!isDesc[0]) {
                //return new Date(b[index]) - new Date(a[index]);
                //return d1 < d2 ? -1 : 1;
                console.log("desc uslo");
                console.log(d1.getTime());
                return d1.getTime() < d2.getTime() ? -1 : 1;
            } else {
                //return new Date(a[index]) - new Date(b[index]);
                //return d2 < d1 ? -1 : 1;
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
          } else if (index[0]=='sala.id') {
            console.log(index[0]);
            if (!isDesc[0]) {
              console.log(a[index]);
              return a['sala'].id < b['sala'].id ? -1 : 1;
            } else {
              return b['sala'].id < a['sala'].id ? -1 : 1;
            }
          } else if (index[0]=='tip.tip') {
            console.log(index[0]);
            if (!isDesc[0]) {
              console.log(a[index]);
              return a['tip'].tip < b['tip'].tip ? -1 : 1;
            } else {
              return b['tip'].tip < a['tip'].tip ? -1 : 1;
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
          /*else {
            if(typeof a[index] !== 'undefined'){
              if (!isDesc) {
                return a[index].toLowerCase().localeCompare(b[index].toLowerCase());
              }
              else {
                return b[index].toLowerCase().localeCompare(a[index].toLowerCase());
              }
            }
          }*/
      });
      return items;
    }
  },
}
</script>