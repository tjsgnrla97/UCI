<template>
  <div v-if="streamManager">
    <ov-video :stream-manager="streamManager" />
    <div id="client-name">
      <p>{{ clientData }}</p>
    </div>
  </div>
</template>

<script>
import OvVideo from './OvVideo';

export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	props: {
		streamManager: Object,
	},

	computed: {
		clientData () {
			return JSON.parse(this.streamManager.stream.connection.data).clientData;	 
		},
	},
};
</script>
<style>
	/* #client-name {
		position: relative;
		top: -30px;
		left: 270px;
	}
	#client-name p{
		display: inline-block;
		background-color: bisque;
		width: auto;
	} */

</style>
