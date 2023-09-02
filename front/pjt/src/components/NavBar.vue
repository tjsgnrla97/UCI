<template>
  <div>
    <v-app-bar
      absolute
      color="#f7f7ee"
      elevate-on-scroll
      scroll-target="#scrolling-techniques-7"
    >
      <v-avatar
        class="mt-2"
        size="80"
        tile
      >
        <img
          src="@/assets/navbar_logo.png"
          router-link
          to="/home"
        >
      </v-avatar>
      <v-spacer />
      <!-- <my-profile-view /> -->
      <v-btn
        class="mx-2"
        fab
        dark
        small
        color="#f7f7ee"
      >
        <v-icon color="grey darken-2">
          mdi-home
        </v-icon>
      </v-btn>

      <v-btn        
        text
        color="grey darken-2"
        router-link
        :to="{ name:'MyProfileView' }"
        @click="openMyprofile"
      >
        프로필
        <v-icon>mdi-account</v-icon>
      </v-btn>
      <v-btn
        text
        color="teal darken-1"
        @click="logout"
      >
        로그아웃
        <v-icon>mdi-login</v-icon>
      </v-btn>
    </v-app-bar>
    <router-view />
    <!-- <v-navigation-drawer
      v-model="drawer"
      app
      :src="require('@/assets/image/Frame.png')"
    > 
      <template
        #img="props"
      >
        <v-img
          :gradient="gradient"
          v-bind="props"
        />
      </template>
      <v-list>
        <v-list-item class="px-2">
          <v-list-item-avatar>
            <v-img src="https://randomuser.me/api/portraits/women/85.jpg" />
          </v-list-item-avatar>
        </v-list-item>

        <v-list-item link>
          <v-list-item-content>
            <v-list-item-title class="text-h6">
              ssafykim
            </v-list-item-title>
            <v-list-item-subtitle>ssafykim@gmail.com</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-list>
      
      <v-list
        dense
        nav
      >
        <v-list-item
          v-for="item in items"
          :key="item.title"
          link
          :to="item.to"
          active-class="teal lighten-4"
          class="py-1"
        >
          <v-list-icon>
            <v-icon color="teal darken-4">
              {{ item.icon }}
            </v-icon>
          </v-list-icon>
          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer> -->
  </div>
</template>

<script>
import { mapActions, mapState } from 'vuex'
// import MyProfileView from '@/views/homepage/MyProfileView.vue'

export default {
  name: 'NavBar',
  components: {
    // MyProfileView
  },
  data() {
      return {
        drawer: false,
        gradient: 'rgba(247, 247, 238, 0.8), rgba(247, 247, 238, 0.5)',
        items: [
          { title: ' my profile', icon: 'mdi-account', to: '/myprofile'},
          { title: ' room create', icon: 'mdi-controller-classic', to: '/roomcreate'},
          { title: ' community', icon: 'mdi-account-multiple', to: '/community'},
        ]
      }},
  computed: {
    ...mapState(['currentUser']),
  },    
  methods: {
    ...mapActions(['openMyprofile','openSignup','logout'])
  },
  
  }
  
</script>

<style>
        .nav {
            font-family: 'Playfair Display SC', serif;
            font-family: 'Nanum Myeongjo', serif;
            background: rgba(247, 247, 238, 0.5);
        
        }
</style>