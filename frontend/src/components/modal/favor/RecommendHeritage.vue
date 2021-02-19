<template>
  <v-card
    class="mx-1"
    max-width="500"
  >
    <v-img
      :src="`${image}`"
      contain
      max-height="500px"
    ></v-img>
    <v-card-title class="east-sea-Dokdo" style="font-size: 36px;">
      {{ heritage.ccba_mnm }}
    </v-card-title>
    
    <v-spacer/>
    <v-card-subtitle class="jua" style="font-size: 20px;">
      {{ heritage.ccba_lcad }}
    </v-card-subtitle>

    <v-card-actions class="brown1">

      <v-spacer></v-spacer>

      <v-btn
        style="font-weight: bold;"
        text
      >
        더보기
      </v-btn>
      <v-btn
        icon
        @click="show = !show"
      >
        <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
      </v-btn>
    </v-card-actions>

    <v-expand-transition>
      <div v-show="show">
        <v-divider></v-divider>

        <v-row>
          <v-col v-for="(img, idx) in images"
          :key="idx">
          <v-img
            :src="`${img.url}`"
            contain
            max-height="600px"
          ></v-img>
          <div style="font-size:14px; text-align:center; font-weight:bold;"> {{ idx+1 }}. {{ img.desc }}</div>
          </v-col>
        </v-row>
        
        <br>
        <v-card-text class="brown1">
          <div class="jua" style="font-size:20px;">
          상세 설명
          </div>
          <br>
          {{ heritage.content }}
        </v-card-text>
      </div>
    </v-expand-transition>
  </v-card>
</template>

<script>
import { mapState } from "vuex";
import { getImages } from "@/api/favor.js";

export default {
  name: "RecommendHeritage",
  data: () => ({
    show: false,
    image: "",
    images: [],
  }),
  props: {
      heritage: {
          type: [Array, Object],
      }
  },
  computed: {
    ...mapState(["userInfo", "isLogin"])
  },
  methods: {
  },
  created() {
    getImages (
      this.heritage.ccba_asno,
      this.heritage.ccba_ctcd,
      this.heritage.ccba_kdcd,
      (response) => {
        this.images = response.data;
        this.image = response.data[0].url;
      },
      (error) => {
        console.log(error);
      }
    );
  },
}
</script>

<style>

</style>