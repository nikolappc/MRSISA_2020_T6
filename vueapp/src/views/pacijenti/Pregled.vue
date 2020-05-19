<template>
  <v-container>
      <v-card>
          <v-card-title>
              Pregled pacijenta
              <Timer />
          </v-card-title>
          <v-row class="mx-4">
              <v-col cols="12" md="6">
                    <v-textarea
                    ma
                    outlined
                    v-model="pregled.opis"
                    label="Opis pregleda"
                    ></v-textarea>
              </v-col>
              <v-spacer></v-spacer>
              <v-chip
                class="ma-2"
                color="primary"
                text-color="white"
                label
                >
                <v-avatar left>
                    <v-icon>mdi-clock</v-icon>
                </v-avatar>
                {{Math.floor(time/60)}}:{{time%60}}
              </v-chip>
          </v-row>
          <v-row>
                <v-tabs
                    fixed-tabs
                    background-color="primary"
                >
                    <v-tab>
                    Dijagnoze
                    </v-tab>
                    <v-tab>
                    Lekovi
                    </v-tab>
                    <v-tab-item>
                        <v-card flat>

                        </v-card>
                    </v-tab-item>

                    <v-tab-item>
                        <v-card flat>
                            
                        </v-card>
                    </v-tab-item>
                </v-tabs>
          </v-row>
          <v-row >
              <v-spacer></v-spacer>
              <v-col cols="6">
                  <v-btn class="mx-10"
                    color="primary"
                  >
                      Zakaži pregled
                  </v-btn>
                  <v-btn
                  color="success">
                      Završi pregled
                  </v-btn>
              </v-col>
          </v-row>
      </v-card>

  </v-container>
</template>

<script>
import axios from "axios";
export default {
name: 'Pregled  ',
  data: () => ({
    pregled: {},
    time: 2000,
  }),

  mounted () {
      axios
      .get('poseta/' + this.$route.params.id)
      .then(response => {
          this.pregled = response.data;
          this.time = Math.floor((Date.parse(this.pregled.termin.kraj)- new Date())/1000);
          
      })    
      .catch((err) => { 
        console.log(err);

        });
      this.countDownTimer();
  },
  methods:{
    countDownTimer: function(){
                if(this.time > 0) {
                    setTimeout(() => {
                        this.time -= 1
                        this.countDownTimer()
                    }, 1000)
                }
            }
  }
}

</script>
