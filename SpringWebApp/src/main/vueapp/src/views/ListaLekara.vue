<template>
    <table border = '1'>
        <thead>
            <th>Ime</th>
            <th>Prezime</th>
            <th>Adresa</th>
            <th>Broj Telefona</th>
        </thead>
        <tbody>
            <Lekar v-for="lekar in lekari" v-bind:key="lekar.id" v-bind:lekar="lekar"  />
        </tbody>
    </table>
</template>

<script>
import Lekar from "./Lekar.vue"
import axios from "axios"
export default {
    data: () => ({
        lekari : null
    }),
    mounted () {
        axios
            .get('lekar')
            .then(response => {
                this.lekari = response.data;
                console.log(response);
            })
            .catch(function (error) { console.log(error); });
    },
    components: {
        Lekar
    }
}
</script>