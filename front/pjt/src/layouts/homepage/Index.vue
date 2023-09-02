<template>
  <v-app
    app
    class="base"
  >
    <nav-bar />
    <v-main>
      <v-text-field color="base--text">
        UCI
      </v-text-field>
      <v-spacer />
      <!-- <v-row
        no-gutters
      > -->
      <!-- <v-col
          cols="3"
          class="mt-5"
        >
          <profile-card />
        </v-col> -->
      <!-- <v-col
          cols="9"
        > -->
      <product-list />
      <!-- </v-col>
      </v-row>
       -->
      <alert-component />
    </v-main>
    <footer-view />
  </v-app>
</template>

<script>

  import NavBar from '@/components/NavBar.vue'
  import FooterView from '@/components/FooterView.vue'
  import AlertComponent from '@/components/AlertComponent.vue'
  import ProductList from '@/views/homepage/ProductList.vue'
  // import ProfileCard from '@/components/ProfileCard.vue'

  export default {
    name: 'HomePageLayout',
    components: {
      NavBar,
      FooterView,
      AlertComponent,
      ProductList,
      // ProfileCard

    },

  }
</script>

<style>
      .body {
            background-color: #f7f7ee;
            font-family: 'Nanum Myeongjo', serif;
            
        }
      .nav {
            font-family: 'Playfair Display SC', serif;
            background: rgba(247, 247, 238, 0.5)
        
        }
        .base {
          background-color:  rgba(247, 247, 238, 0.5) !important
      }
      .base--text {
      color: #2E6155 !important;
       -webkit-text-stroke: 1px #5DC7AE;
      }
      .playing--text {
        color:#D73535 ;
        -webkit-text-stroke: 1px #E97F7F;

      }
       #dotted {
      border-style: dotted none;
      background-color:rgba(255, 255, 255, 0.9)
  }
</style>
