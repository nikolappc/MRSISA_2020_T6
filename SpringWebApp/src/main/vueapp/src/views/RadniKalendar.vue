<template>
    <div>
        Radni kalendar
            <v-tabs>
              <v-tab @click="setType('month')">
                Mesečni prikaz
              </v-tab>
              <v-tab @click="setType('week')">
                Nedeljni prikaz
              </v-tab>
              <v-tab @click="setType('day')">
                Dnevni prikaz
              </v-tab>
            </v-tabs>
            <v-btn
                fab
                small
                absolute
                left
                @click="$refs.calendar.prev()"
            >
                <v-icon dark>mdi-chevron-left</v-icon>
            </v-btn>
            <v-btn
                fab
                small
                absolute
                right
                @click="$refs.calendar.next()"
            >
                <v-icon dark>mdi-chevron-right</v-icon>
            </v-btn>
            <v-calendar
              ref="calendar"
              v-model="calendar"
              :type="type"
              :events="events"
              @click:event="selectedEvent"
            >
            </v-calendar>
    </div>
</template>

<script>
    export default{
        name:"RadniKalendar",
        data:function(){
            return{
                type:"month",
                calendar:"",
            }
        },
        props:[
            "events"
        ],
        mounted(){

        },
        methods:{ 
            setType(type){
                this.type = type;
            },
            selectedEvent({nativeEvent, event}){
                console.log(nativeEvent);
                console.log(event);
                this.$emit("selected", {nativeEvent:nativeEvent, event:event});
            }
        }

    }
</script>