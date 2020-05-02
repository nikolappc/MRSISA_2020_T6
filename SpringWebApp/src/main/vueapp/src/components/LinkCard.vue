<template>
    <v-card 
    min-width="300px" 
    min-height="300px"
    :img="img"
    >
        <v-container>
            <v-card
            color="primary"
            dark
            >
            <v-card-title>
                {{title}}
            </v-card-title>
            </v-card>
        </v-container>
    </v-card>
</template>

<script>
    export default {
        name:"LinkCard",
        props:[
            "img",
            "title",
        ],
    }
</script>