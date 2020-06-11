<template>
  <div class="profilPacijenta">
    <v-container>
      <v-card><v-simple-table>
        <thead>
          <th id="email">Email adresa</th>
          <th id="remail">{{ pacijent.email }}</th>
        </thead>
        <tbody>
          <tr>
            <td>Lozinka:</td>
            <td>{{ pacijent.password }}</td>
          </tr>
          <tr>
            <td>Ime:</td>
            <td>{{ pacijent.ime }}</td>
          </tr>
          <tr>
            <td>Prezime:</td>
            <td>{{ pacijent.prezime }}</td>
          </tr>
          <tr>
            <td>Adresa:</td>
            <td>{{ toStringAdresa(pacijent)}}</td>
          </tr>
          <tr>
            <td>Telefon:</td>
            <td>{{ pacijent.brojTelefona }}</td>
          </tr>
          <tr>
            <td>Jedinstveni broj osiguranika:</td>
            <td>{{ pacijent.jbo }}</td>
          </tr>
        </tbody>
      </v-simple-table>
      </v-card>
      <div>
        <v-container>
          <v-card><h2 style="text-align:center">Istorija pregleda i operacija</h2>
          <h4>{{ pacijent.ime }} {{ pacijent.prezime }}</h4></v-card>
          <v-data-table
            :headers="headers"
            :items="pregledi"
            :items-per-page="10"
            class="elevation-1">
            <template v-slot:item.termin.pocetak="{ item }">{{ formatDate(item.termin.pocetak)}}</template>
            <template v-slot:item.termin.kraj="{ item }">{{ formatDate(item.termin.kraj)}}</template>
            <template v-slot:item.actions="{ item }">
              <v-btn
                :disabled="checkStart(item)"
                color="success"
                class="mr-4"
                @click="pocniPregled(item.id,item.tipPregleda)"
              >
                Započni pregled
              </v-btn>
              
            </template>
          </v-data-table>
        </v-container>
      </div>
    </v-container>
  </div>
</template>

<script>

import moment from 'moment';
import axios from 'axios';  
export default {
  name: 'RNG',
  data: () => ({
    pacijent : {},
    pregledi: [],
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
          value: 'sala.naziv', 
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
          text: '',
          value: 'actions',
          sortable: false
        }
      ]
  }),
  methods:{
    toStringAdresa:function(p){
        return p.adresa.adresa+ ", " + p.adresa.grad + ", " + p.adresa.drzava;
      },
    checkStart: function(pregled){
        let trenutno = new Date(Date.now() + 5 * 60000);
        if(new Date(pregled.termin.pocetak) <= trenutno){
            return false;
        }
        return true;
    },
    pocniPregled: function(idPregleda,tipPregleda){
        console.log(this.$store.state.ulogovan);
        let operacija = tipPregleda == "operacija" ? "operacija/" : "";

        axios
          .post('lekar/pocetak/' + operacija + idPregleda + "/", this.$store.state.ulogovan)
          .then(response => {
              console.log(response.data);
              var provera = response.data;
              if(provera.zapocni){
                let path = tipPregleda == "operacija" ? "/operacija/" : "/pregled/";
                this.$router.push(path+ idPregleda);
              }
              else{
                
              this.$store.commit("setSnackbar", {text:"Nemate pravo da zapocnete pregled", color: "error"});
              }
          })    
          .catch((err) => { 
            this.$store.commit("setSnackbar", {text:"Započinjanje pregleda nije dostupno", color: "error"});
            console.log(err);
          });
    },
    
    
    formatDate(value) {
			return moment(String(value)).format('DD.MM.YYYY. HH:mm');
		},
  },
  mounted () {
    axios
      .get('api/pacijent/' + this.$route.params.id)
      .then(response => {
          this.pacijent = response.data;
          this.pacijent.adresa = this.toStringAdresa(this.pacijent);
      })    
      .catch((err) => { 
        console.log(err);
        this.pacijent = {"id":2,"ime":"Hermione","prezime":"Granger","password":"hermione12345","brojTelefona":"065/123-111","jbo":"0101","email":"hermione@gmail.com","tip":"PACIJENT","adresa":{"id":2,"drzava":"Bugarska","grad":"Vidin","adresa":"Svetozara Markovskog 44"},"allowed":true,"responded":true,"zdravstveniKarton":{"id":1,"visina":1.6,"tezina":60.0,"krvnaGrupa":"A+","dijagnoza":[{"sifraDijagnoze":1,"nazivDijagnoze":"Covid-19"}],"hibernateLazyInitializer":{},"iteratorDijagnoza":[{"sifraDijagnoze":1,"nazivDijagnoze":"Covid-19"}]}};
        });
    
    axios
      .get('poseta/predstojeciPregledi/' + this.$route.params.id)
      .then(response => {
          this.pregledi = response.data;
      })    
      .catch((err) => { console.log(err);
        this.pregledi = [{"id":5,"opis":"Bad dream","termin":
        {"id":5,"pocetak":"2020-05-20T08:00:00.000+0000","kraj":"2020-05-20T09:00:00.000+0000"},"tip":{"id":1,"tip":"pregled","naziv":"Super specijalni pregled"},"sala":{"id":1,"naziv":"sala 1"}},
        {"id":6,"opis":"Krvarenje desni","termin":{"id":6,"pocetak":"2020-05-19T17:18:37.692+0000","kraj":"2020-05-19T17:00:37.692+0000"},"tip":{"id":1,"tip":"pregled","naziv":"Super specijalni pregled"},"sala":{"id":2,"naziv":"operaciona"}}];
      });
      

    
  }
}
</script>