<template>
  <v-main
    class="basic"
  >
    <v-row
      no-gutters
    >
      <v-col
        cols="6"
      >
        <v-img
          src="@/assets/main_pg.png"
        />
        <button
          id="account-part2"
          @click="show=!show"
        />
        <button
          id="account-part"
          @click="openSignup"
        />
      </v-col>
      <!-- <main-div /> -->
      <!-- <v-container
          class="rounded-pill my-8"
        >
          <v-card
            color="rgb(255, 255, 255, 0.3)"
          >
            <v-card-text>
              <p class="text-h5 text--primary">
                함께 모여 게임하는 즐거움<br>
                UCI
              </p>
              <div class="text--primary">
                어색한 첫 만남 직장, 모임 등<br>
                함께 화상으로 게임하면서 친해지는 화상 미팅 시스템<br>
                UCI와 함께 아이스브레이킹 게임하며 친해져봅시다.
              </div>
            </v-card-text>
          </v-card>
        </v-container> -->
      <v-col
        dense
        cols="6"
      >
        <v-img
          src="@/assets/027.png"
        >
          <v-row>
            <v-col>
              <transition>
                <div v-show="show">
                  <v-carousel
                    hide-delimiters
                    class="mx-auto mt-10"
                    height="400"
                  >
                    <v-carousel-item
                      v-for="(item,i) in items"
                      :key="i"
                      align="center"
                      :src="item.src"
                      class="mx-auto"
                    />
                  </v-carousel>
                  <!-- <v-btn
                    router-link
                    :to="{ name: 'AccountView' }"
                    dark
                    plain
                    class="btn2"
                    @click="openSignup"
                  >
                    로그인 / 회원가입
                  </v-btn> -->
                </div>
              </transition>
            </v-col>
          </v-row>
        </v-img>
      </v-col>
    </v-row>
  </v-main>
</template>

<script>
// import MainDiv from '@/components/MainDiv.vue'
import { mapActions } from 'vuex'
export default {
    el: '#app',
    name: 'MainView',
    components: {
        // MainDiv,

    },
    data: function() {
      return {
        show: false,
        items: [
          {
            src: require('@/assets/009.png'),
          },
          {
            src: require('@/assets/010.png'),
          },
          {
            src: require('@/assets/011.png'),
          },
        ],
        cycle: false,
        slides: [
              'First',
              'Second',
              'Third',
              'Fourth',
              'Fifth',
            ],
        };
      },
    methods: {
       ...mapActions(['openLoginModal','openSignup'])
    }
}
</script>

<style>
  .btn2 {
    margin-left: 300px !important;
    margin-top: 300px !important;
  }
  .btn1 {
    margin-left: 100px !important;
    margin-top: 0px !important;
    background: linear-gradient(pink, rgb(205, 22, 83));
  }
  .intro {
    background-color: rgb(255, 219, 169);
  }
  .basic {
    background-color: rgba(247, 247, 238, 1) !important;
  }
  .v-enter-active, .v-leave-active {
    transition: opacity 1s;
  }
  .v-enter, .v-leave-to {
    opacity: 0;
  }
  #account-part {
    position: absolute;
    top: 85%;
    left: 16.5%;
    width: 17%;
    height: 6%;
    z-index: 1;
  }
  #account-part2 {
    position: absolute;
    top: 80%;
    left: 16.5%;
    width: 17%;
    height: 6%;
    z-index: 1;
  }

</style>