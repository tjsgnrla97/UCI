<template>
  <v-app
    app
    class="base"
  >
    <meeting-nav-bar />
    <v-main>
      <v-text-field color="base--text">
        UCI
      </v-text-field>
      <alert-component />
      <router-view />
      <product-list />
    </v-main>
    <footer-view />
  </v-app>
</template>
<script>
export default {
    name: 'MeetingpageLayout'
}
</script>

<style>

</style>