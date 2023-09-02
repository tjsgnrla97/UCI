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
        >
      </v-avatar>
      <v-spacer />
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
        id="buttonLeaveSession"
        class="mx-2"
        color="error"
        large
        @click="videoLeave"
      >
        <v-icon>mdi-power</v-icon>
        방 나가기
      </v-btn>    
      <div />
    </v-app-bar>
    <v-divider />
  </div>
</template>

<script>


export default {
  name: 'MeetingNavBar',
  methods: {
    videoLeave () {
			this.$emit('leave-button')
		},
    roomDetail() {
      this.$emit('room-detail')
    }
  },
  
  }
  
</script>

<style>
    .nav {
        font-family: 'Playfair Display SC', serif;
        font-family: 'Nanum Myeongjo', serif;
        background: rgba(247, 247, 238, 0.5);
    
    }
    .nav_btn {
      font-size:  25px;
      margin: 0 2px;
    }
</style>