<template>
    <div>
        <v-container>
            <v-row
                align="center"
                justify-content="center"
            >
                <v-col>
                    <v-form ref="forma">
                        <v-card>
                            <v-container>
                                <v-text-field
                                    label="Naziv klinike"
                                >
            
                                </v-text-field>
                                <v-row>
                                    <v-col>
                                        <v-text-field
                                            label="Mesto"
                                        >
                    
                                        </v-text-field>
                                    </v-col>
                                    <v-col>
                                        <v-text-field
                                        label="Ulica"
                                        >
                    
                                        </v-text-field>
                                    </v-col>
                                    <v-col>
                                        <v-text-field
                                            label="Broj"
                                        >
                    
                                        </v-text-field>
                                    </v-col>
                                </v-row>
                                <v-textarea
                                    label="Opis"
                                >
                                    
                                </v-textarea>
                                <v-row>
                                    <v-spacer>
            
                                    </v-spacer>
                                    <v-btn>
                                        Registruj kliniku
                                    </v-btn>
                                </v-row>
                            </v-container>
                        </v-card>
                    </v-form>
                </v-col>
            </v-row>
        </v-container>
    </div>
</template>

<script>
    export default {
        name:"RegKlinika",
        data:function () {
            return {

            }
        },
        methods:{

        }
    }
</script>