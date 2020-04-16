<template>
  <v-row>
    <v-col>
      <v-sheet height="400">
        <v-calendar
          ref="calendar"
          :now="today"
          :value="today"
          :events="events"
          color="primary"
          type="week"
        ></v-calendar>
      </v-sheet>
    </v-col>
  </v-row>
</template>


<script>
  export default {
    data: () => ({
      today: '2019-01-08',
    }),
    props: ["events"],
    mounted () {
        this.$refs.calendar.scrollToTime('08:00');
        var today = new Date();
        var dd = String(today.getDate()).padStart(2, '0');
        var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
        var yyyy = today.getFullYear();

        this.today = yyyy + '-' + mm + '-' + dd;
    },
  }
</script>