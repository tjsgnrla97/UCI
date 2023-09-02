<template>
  <v-card
    class="profile_card"
    max-width="300"
  >
    <v-card-text>
      <v-row align="center">
        <v-list-item-title 
          align="center"
          class="text-h5"
        >
          <!-- {{ newCredentials.username }}  -->
          user
        </v-list-item-title>

        <div class="mx-auto text-center">
          <!-- <v-avatar
                  color="brown"
                >
                  <span class="white--text text-h5">{{ newCredentials.username }}</span>
                </v-avatar> -->
          <v-avatar
            color="grey darken-3"
            size="70"
            class="my-3"
          >
            <v-img
              class="elevation-6"
              alt=""
              src="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
            />
          </v-avatar>

          <h3 bold>
            {{ newCredentials.username }}님
          </h3>
          <h2 class="text-caption mt-1">
            {{ newCredentials.email }}
          </h2>
              
          <v-spacer />
        </div>

        <!-- <v-btn
          class="rounded-pill"
          color="red lighten-2"
          dark
          large
        >
          로그아웃
        </v-btn> -->
      </v-row>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
    data() {
        return {
          newCredentials: {
            email: JSON.parse(localStorage.newCredentials).email,
            username: JSON.parse(localStorage.newCredentials).username,
            department: JSON.parse(localStorage.newCredentials).department,
            position: JSON.parse(localStorage.newCredentials).position,
            },
         }
    },
    methods:{

    }
}
</script>

<style>
  .profile_card {
    background-color: rgba(255, 255, 255, 0.5) !important;
    margin: auto;
  }
</style>