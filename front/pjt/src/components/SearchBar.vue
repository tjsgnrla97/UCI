
  <!-- <v-btn
    width="200"
  >
    <v-text-field
      hide-details
    />
    <v-btn icon>
      <v-icon color="teal">
        mdi-magnify
      </v-icon>
    </v-btn>
  </v-btn> -->
<template>
  <div>
    <input
      v-model="input"
      type="text"
      placeholder="Search fruits..."
    >
    <div
      v-for="fruit in filteredList()"
      :key="fruit"
      class="item fruit"
    >
      <p>{{ fruit }}</p>
    </div>
    <div
      v-if="input&&!filteredList().length" 
      class="item error"
    >
      <p>No results found!</p>
    </div>
  </div>
</template>


<script>
export default {

}
</script>

<style>

</style>