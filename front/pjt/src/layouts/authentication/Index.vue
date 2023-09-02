<template>
  <v-app>
    <v-app-bar
      app
      color="teal lighten-5"
      dark
    >
      <v-toolbar-title>meeting page</v-toolbar-title>
    </v-app-bar>
    <v-main>
      <v-container
        fluid
      />
      <router-view />
    </v-main>
  </v-app>
</template>


<script>
export default {
    name: 'AuthenticationLayout'
}
</script>

<style>

</style>