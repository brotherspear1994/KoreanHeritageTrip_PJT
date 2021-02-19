<template>
<v-main>
  <v-container class="my-16">
    <v-row class="text-center">
      <v-col cols="12">
        <Kakaomap/>
      </v-col>
    </v-row>
  </v-container>
</v-main>
</template>

<!-- Main.vue -->
<script>
import Kakaomap from '@/components/modal/map/Kakaomap';

  export default {
    name: 'Main',
    components: {
      Kakaomap
    }
  }
</script>


<style>

</style>