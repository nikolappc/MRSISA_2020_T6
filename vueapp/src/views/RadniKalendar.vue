<template>
    <v-card>
        <v-container>
            <display-1>
                Radni kalendar
            </display-1>
            <v-btn outlined class="mr-4" color="grey darken-2" @click="setToday">
                Danas
            </v-btn>
            <v-btn
                small
                @click="$refs.calendar.prev()"
            >
                <v-icon dark>mdi-chevron-left</v-icon>
            </v-btn>
            <v-btn
                small
                @click="$refs.calendar.next()"
            >
                <v-icon dark>mdi-chevron-right</v-icon>
            </v-btn>
        </v-container>
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
        
        <slot :selected="selected" :activator="activator" :opened="opened">
        </slot>
        <v-calendar
            ref="calendar"
            v-model="calendar"
            :type="type"
            :events="events"
            @click:event="selectedEvent"
            @change="updateRange"
            :event-color="getEventColor"
        >
        </v-calendar>
           
        
        
    </v-card>
</template>

<script>
    export default{
        name:"RadniKalendar",
        data:function(){
            return{
                type:"month",
                calendar:"",            
                selected:null,
                activator:null,
                opened:false,
            }
        },
        props:[
            "events"
        ],
        mounted(){

        },
        methods:{ 
            setToday () {
                this.calendar = this.today
            },
            updateRange ({ start, end }) {
                this.start = start
                this.end = end
            },
            selektovanEvent(e){
                let nativeEvent = e.nativeEvent;
                let event = e.event;
                const open = () => {
                    this.selected = event
                    this.activator = nativeEvent.target
                    setTimeout(() => this.opened = true, 10)
                }

                if (this.opened) {
                    this.opened = false
                    setTimeout(open, 10)
                } else {
                    open()
                }

                nativeEvent.stopPropagation()
            },
            
            setType(type){
                this.type = type;
            },
            selectedEvent({nativeEvent, event}){
                console.log(nativeEvent);
                console.log(event);
                this.selektovanEvent({nativeEvent:nativeEvent, event:event});
                this.$emit("selected", this.selected);
            },
            getEventColor (event) {
                return event.color
            },
        }

    }
</script>