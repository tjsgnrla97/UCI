<template>
  <v-app>
    <v-main
      class="basic"
    >
      <main-view />
      <account-view />
      <alert-component />
    </v-main>
  </v-app>
</template>

<script>
import MainView from '@/views/mainpage/MainView.vue'
import AccountView from '@/views/authentication/AccountView.vue'
import AlertComponent from '@/components/AlertComponent.vue'

export default {
    name: 'MainPageLayout',
    components: {
        MainView,
        AccountView,
        AlertComponent
    }
}
</script>

<style>
  .btn1 {
    left:30%;
    top: 60%;
    background: linear-gradient(pink, rgb(205, 22, 83));
  }
  .intro {
    background-color: rgb(255, 219, 169);
  }
  .basic {
    background-color: rgba(247, 247, 238, 1) !important;
  }
</style>