<template>
  <v-overlay
    :value="is_show"
    z-index="100005"
  >
    <!-- <v-alert
      dense
      prominent
      :type="alert_data.type"
      color="teal lighten-2"
    >
      <v-row>
        <v-col cols="12">
          {{ alert_data.title }}
        </v-col>
        <v-col
          cols="12"
          v-text="change(alert_data.text)"
        />
      </v-row>
      <v-row>
        <v-col
          cols="12"
          align="end"
        >
          <v-btn
            outlined
            @click="close"
          >
            확인
          </v-btn>
        </v-col>
      </v-row>
    </v-alert> -->
    <v-card
      :type="alert_data.type"
      class="mx-auto"
      width="300"
      color="rgba(255, 255, 255, 0.8)"
    >
      <div
        align="center"
      >
        <div v-if="alert_data.type === 'success'">
          <v-icon
            class="my-3"
            large          
            color="orange darken-2"
          >
            mdi-check-circle-outline
          </v-icon>
        </div>
        <div v-if=" alert_data.type === 'error'">
          <v-icon
            class="my-3"
            large          
            color="red"
          >
            mdi-alert
          </v-icon>
        </div>
      </div>
      <v-card-text>
        <div
          align="center"
          class="text-h6 text--primary"
        >
          {{ alert_data.text }}<br>
        </div>
      </v-card-text>
      <v-row>
        <v-col
          cols="12"
          align="end"
        >
          <div
            v-if="alert_data.type === 'success'"
          >
            <v-btn
              text
              color="orange darken-2"
              @click="close"
            >
              확인
            </v-btn>
          </div>
          <div
            v-if="alert_data.type === 'error'"
          >
            <v-btn
              text
              color="red"
              @click="close"
            >
              확인
            </v-btn>
          </div>
        </v-col>
      </v-row>
    </v-card>
  </v-overlay>
</template>

<script>
export default {

  computed: {
    is_show() {
      return this.$store.getters['GET_ALERT'];
    },
    alert_data() {
      return this.$store.getters['GET_ALERT_DATA'];
    }
  },
  methods: {
    change(v) {
      return String(v).replace(/(?:\r\n|\r|\n)/g,"</br>");
    },
    close() {
      this.$store.dispatch('updateAlert', {alert: false, type: 'error', text: ''});
    }
  }
}
</script>

<style>

</style>