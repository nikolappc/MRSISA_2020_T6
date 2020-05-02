<template>
    <v-card>
        <v-container>
            <v-row>
                <v-col
                    cols="12"
                >
                </v-col>
                    <v-container>
                        <v-card-title>
                            {{username}}
                        </v-card-title>
                    </v-container>
                <v-col
                    cols="12"
                >
                    <v-row
                        justify="end"
                    >
                        <v-col>
                            <v-row>
                                <v-col>
                                    <v-btn color="success">
                                        Odobri
                                    </v-btn>
                                </v-col>
                                <v-col>
                                    <v-btn color="error"> 
                                        Odbij
                                    </v-btn>
                                </v-col>
                            </v-row>
                        </v-col>

                    </v-row>
                </v-col>
            </v-row>
        </v-container>
    </v-card>
</template>
<script>
    export default{
        name:"RegConf",
        props:[
            "username",
            "email",

        ]
    }
</script>