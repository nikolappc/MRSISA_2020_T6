<template>
    <v-card 
    min-width="200px" 
    min-height="200px"
    :img="img"
    >
            <v-card
            color="primary"
            class="mt-5"
            dark
            :flat="true"
            >
            <v-card-title>
                {{title}}
            </v-card-title>
            </v-card>
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