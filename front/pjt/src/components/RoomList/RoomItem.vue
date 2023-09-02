<template>
  <v-card
    class="b"
    outlined
  >
    <v-list-item
      three-line
    >
      <v-list-item-content>
        <div
          class="
          text-overline
          mb-4
          rounded-pill"
          style="background-color:rgba(247, 244, 229, 1.0)"
          rounded
        >
          #{{ thisRoomDB.seq }} room
        </div>

        <v-list-item-title
          class="text-h5 mb-1"
        >
          {{ thisRoomDB.title }}
        </v-list-item-title>
        <v-list-item-subtitle>
          {{ thisRoomDB.description }}
        </v-list-item-subtitle>
      </v-list-item-content>

      <v-list-item-avatar
        tile
        size="80"
        color="grey lighten-4"
      >
        <v-img
          src="@/assets/logo_transparent.png"
        />
      </v-list-item-avatar>
    </v-list-item>
    <v-divider
      class="divider1"
    />
    <v-row>
      <v-col
        cols="6"
      >
        <v-card-title
          class="base--text"
          v-if="!thisRoomDB.active"
        >
          waiting
        </v-card-title>
        <v-card-title
          class="playing--text"
          v-if="thisRoomDB.active">
          playing
        </v-card-title>        
      </v-col>
      <v-col
        cols="6"
      >
        <v-chip
          class="ma-2"
          color="teal"
          outlined
        >
          <v-icon left>
            mdi-account-outline
          </v-icon>
          {{ roomInfo.connections.content.length }}/6
        </v-chip>
      </v-col>
    </v-row>
    <v-divider 
      class="divider1"
    />
    <v-card-actions>
      <v-btn
        v-if="!thisRoomDB.active"
        outlined
        rounded
        text
        color="teal"
        @click="[removeProductList(),resignHost()]"
      >
        입장하기
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import { mapActions,mapGetters } from "vuex"

export default {
  name: 'RoomItem',
  props: {
    roomInfo: Object,
  },
  computed: {
    ...mapGetters(['getRoomsData']),
    mySessionId () {
      return this.roomInfo.sessionId
    },
    thisRoomDB () {
        let rooms = this.getRoomsData.data
        const data = rooms.filter(e => e.title === this.roomInfo.sessionId)
      return data[0]
    },
  },
  methods: {
    ...mapActions(["resignHost"]),
    removeProductList () {
      this.$emit('remove-product-list',this.mySessionId)
    },
  }
}
</script>

<style>
  .divider1 {
    margin-top: 0.5px;
    margin-bottom: 0.5px;
  }
</style>