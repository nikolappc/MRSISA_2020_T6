<template>
  <div class="home">
    <h1>Hello world!</h1>
    <v-btn :to="{path: 'profilPacijenta'}" dark medium left class="blue" slot="action">Profil pacijenta</v-btn>
    <v-btn :to="{path: 'lekari'}" dark medium left class="blue" slot="action">Lista lekara</v-btn>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: 'Home',
  
}
</script>
