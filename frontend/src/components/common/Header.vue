<template>
    <nav class="mb-16">
        <v-toolbar
        color="navy"
        height="80"
      >
        <div class="d-flex align-center">
          <v-toolbar-title color="navy"><span class="white--text east-sea-Dokdo " style="font-size: 56px; cursor: pointer; padding: 10px;" @click="moveToMainPage">한디아나존스</span></v-toolbar-title>
        </div>

        <v-spacer></v-spacer>
          <!-- 로그인이 안돼었을시 보이는 배너, vuetify 회원가입, 로그인 모답 -->
          <span v-if="!isLogin" class="mr-2"><Join/></span>
          <span v-if="!isLogin" class="mr-2"><Login/></span>
          <!-- 로그인 상태일때 보이는 배너 -->
          <span v-if="isLogin" class="mr-2"><MyInfo/></span>
          <span v-if="isLogin" class="mr-2">
            <v-btn text
              @click="logout">
              <span class="white--text jua" style="font-size:19px; padding: 5px; ">로그아웃</span>
            </v-btn>
          </span>
      </v-toolbar>

      <v-img
        :src="require('@/assets/mainImage.jpg')"
        class="md-2"
        height="250"
      />
      <!-- Navbar -->
      <v-toolbar 
      height="65"
      color="grey"
      >
      <!-- 공지사항 버튼 -->
        <v-btn text
          class="ma-1"
          @click="moveToNoticePage"
        >
          <span class="navy--text jua" style="font-size:19px; padding: 5px; ">
            공지사항
          </span>
          <i class="fas fa-volume-down" style="padding-left: 0.5rem"></i>
        </v-btn>

        <span v-if="isLogin" class="mr-2">
        <!-- 선호 정보 입력 -->
        <v-btn text
          class="ma-1"
          @click="moveToFavor"
        >
          <span class="navy--text jua" style="font-size:19px; padding: 5px; ">
          문화재 추천을 위한 선호 정보 입력
          </span>
        </v-btn>
        </span>

        <span v-if="isLogin" class="mr-2">
        <!-- 추천 목록 -->
        <v-btn text
          class="ma-1"
          @click="moveToRecommend"
        >
          <span class="navy--text jua" style="font-size:19px; padding: 5px; ">
          사용자 맞춤 문화재 추천 목록
          </span>
        </v-btn>
        </span>

      </v-toolbar>
    </nav>
</template>

<script>
import Join from '@/components/modal/auth/Join';
import Login from '@/components/modal/auth/Login';
import MyInfo from '@/components/modal/auth/MyInfo';
import { mapState } from "vuex";

export default {
  name: 'Header',
  components: {
    Join,
    Login,
    MyInfo,
  },
  computed: {
    ...mapState(["userInfo", "isLogin"])
  },
  methods: {
    logout: function () {
      this.$store
      .dispatch("LOGOUT")
      .then(() => {
        this.$router.push({ name: "Main" });
      })
      .catch(() => {
        console.log("로그아웃 문제 발생");
      });
    },
    moveToNoticePage: function () {
      this.$router.push({ name: "NoticePage" })
    },
    moveToRecommend() {
      console.log(this.userInfo.user_no)
      this.$router.push({
        name: "Recommend",
        params: { user_no: this.userInfo.user_no }
      });
    },
    moveToFavor() {
      this.$router.push({ name: "InsertCtcd" });
    },
    moveToMainPage: function () {
      this.$router.push({ name: "Main" })
    },
  },
}
</script>

<style>

</style>

