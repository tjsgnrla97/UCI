<template>
  <v-card
    align="center"
    class="card-style"
  >
    <v-system-bar
      color="teal darken-2"
      dark
    >
      <v-spacer />

      <v-icon>mdi-window-minimize</v-icon>

      <v-icon>mdi-window-maximize</v-icon>

      <v-icon>mdi-close</v-icon>
    </v-system-bar>


    <v-container>
      <v-row dense>
        <v-col cols="12">
          <v-card
            color="teal"
            dark
            class="rounded-pill"
          >
            <v-col cols="12">
              <v-card
                color="teal lighten-3"
                dark
                class="rounded-pill"
              >
                <v-card-actions>
                  <!-- <v-btn
                router-link
                to="/login"
                text
                @click="openLoginModal()"
              > -->
                  uci 소개<br>
                  얼굴보며 친해져요
                  우리 화상으로
                  게임하며
                  친해지는
                  화상
                  미팅
                  시스탬
                  <!--
                    <v-icon
                  >
                    mdi-login</v-icon>
                    </v-btn> -->
                </v-card-actions>
              </v-card>
            </v-col>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  name: 'MainDiv',
  data() {

  },
  methods: {
    
    ...mapActions(['openLoginModal','openSignup'])
  },
}
</script>

<style>
    .btn--text {
        text-align: center;
    }
    .card-style {
        opacity:0.6;
    }
</style>