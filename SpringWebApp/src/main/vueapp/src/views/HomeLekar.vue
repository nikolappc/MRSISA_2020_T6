<template>
  <div class="home">
    <v-container>
      <h1>Lekar</h1>
      <v-col
      >
        <v-container>
          <v-card class="main-card">
    
            <v-row
              justify="center"
              align-content="stretch"
              align="stretch"
            >
              <v-col
                lg="6"
                md="12"
                sm="12"
                xs="12"
              >
              <router-link to="/listaPacijenata">
                <LinkCard
                  :img="pacijent"
                  title="Lista pacijenata"
                >
                </LinkCard>
              </router-link>
              </v-col>
              
            </v-row>
          </v-card>
        </v-container>
      </v-col>
    </v-container>
  </div>
</template>

<script>
import LinkCard from "../components/LinkCard.vue";

export default {
  name: 'HomeLekar',
  components:{
    LinkCard
  },
   data: () => ({
      pacijent:require("../assets/pacijent.jpg")
  }),
  mounted () {
  },
  methods: {
    
  }
  
}
</script>

<style>
</style>
