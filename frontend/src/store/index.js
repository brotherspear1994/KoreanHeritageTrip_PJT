import Vue from 'vue';
import Vuex from 'vuex';
import jwt_decode from "jwt-decode";
import { findById } from "@/api/user.js"

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isLogin: false, // 로그인 여부
    userInfo: null  
  },
  getters: {
    getUserInfo(state){
      return state.userInfo;
    }
  },
  mutations: {
    setIsLogined(state, isLogin) {
      state.isLogin = isLogin;
    },
    setUserInfo(state, userInfo) {
      state.isLogin = true;
      state.userInfo = userInfo;
    },
    logout(state) {
      state.isLogin = false;
      state.userInfo = null;
    }
  },
  actions: {
      async GET_MEMBER_INFO({ commit }, token) {
        let decode = jwt_decode(token);

      await findById(
        decode.user_id,
        (response) => {
          if (response.data.message === "success") {
            commit("setUserInfo", response.data.userInfo);
            // router.push("/");
            // router.go(router.currentRoute);
          } else {
            console.log("유저 정보 없음!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    LOGOUT({ commit }) {
      commit("logout");
      localStorage.removeItem("access-token");
      // axios.defaults.headers.common["auth-token"] = undefined;
    }
  },
  modules: {
  }
});
