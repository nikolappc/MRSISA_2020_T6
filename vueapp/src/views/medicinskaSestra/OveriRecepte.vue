<template>
    <v-container>
        <display-1>
            Overa recepata
        </display-1>
        <v-card
            outlined="true"
        >
            <v-toolbar
                color="primary"
                flat="true"
            >
                <v-row>
                    <v-col>
                        {{pregled.tip.naziv}}
                    </v-col>
                    <v-col>
                        <v-icon>
                        mdi-doctor
                        </v-icon>
                        {{pregled.lekar}}
                    </v-col>
                </v-row>
            </v-toolbar>
            <v-container>
                <v-row>
                    <v-col
                    v-for="r in pregled.recepti"
                        cols="12"
                        :key="r.id"
                    >
                        <Recepat
                            :recepat="r"
                        >
                        </Recepat>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col
                        cols="4"
                    >
                        <v-btn
                            @click="back"
                            collor="success"
                        >
                            Završi overu
                        </v-btn>
                    </v-col>
                </v-row>
            </v-container>
        </v-card>
    </v-container>
</template>

<script>
    import Recepat from "../../components/Recepat.vue";
    export default{
        components:{
            Recepat
        },
        props:["pregled"],
        methods:{
            back()
            {
                this.$router.go(-1);
            }
        }
    }
</script>

<style>
</style>