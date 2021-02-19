<template>
  <v-row justify="">
    <v-col cols="auto">
      <v-dialog
        transition="dialog-bottom-transition"
        max-width="600"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn text
            v-bind="attrs"
            v-on="on"
          >
          <span class="white--text jua" style="font-size:19px; padding: 5px;">로그인</span>
          </v-btn>
        </template>
        <template v-slot:default="dialog">
          <v-card>
            <v-toolbar
              color="navy"
              class="mb-4"
              dark
            ><span class="white--text jua" style="font-size:24px;">로그인</span>
            <v-spacer/>
              <v-btn
                text
                @click="dialog.value = false"
              >
              <span class="white--text jua" style="font-size:14px; padding: 10px; ">x</span>
              </v-btn>
              </v-toolbar>
            <v-card-text>
              <br>
               <v-text-field
            label="이메일"
            outlined
            v-model="user.user_id"
          ></v-text-field>
          <v-text-field
            type="password"
            outlined
            label="비밀번호"
            v-model="user.user_password"
          ></v-text-field>
            </v-card-text>
            <v-card-actions class="justify-end">
              <FindId/>
              
              <FindPwd/>
            </v-card-actions>
            <v-card-actions class="justify-end">
              <v-btn
                color="brown2"
                @click="confirm"
                class="mx-2 my-2"
              ><span class="navy--text jua mx-5" style="font-size:20px; padding: 10px; ">로그인</span></v-btn>
            </v-card-actions>
          </v-card>
        </template>
      </v-dialog>
    </v-col>
 </v-row>
  
</template>

<script>
import { login } from "@/api/user.js";
import FindPwd from '@/components/modal/auth/FindPwd';
import FindId from '@/components/modal/auth/FindId';

export default {
    name: 'login',
    data(){
      return {
        user: {
          user_id: null,
          user_password: null
        },
        isLoginError: false
      };
    },
    components:{
      FindPwd,
      FindId,
    },
    computed:{},
    methods:{
      confirm() {
        localStorage.setItem("access-token", "");
        login(
          this.user,
          (response) => {
            console.log("최종로그인확인", response.data.message)
            if (response.data.message === "success") {
              let token = response.data["access-token"];
              this.$store.commit("setIsLogined", true);
              localStorage.setItem("access-token", token);
              this.$store.dispatch("GET_MEMBER_INFO", token);
              // this.$router.push({ name: "Main" });
              // 페이지 리렌더링
              location.reload();
            } else {
              console.log('로그인 실패')
              this.isLoginError = true;
            }
          },
          (error) => {
            console.log(error);
            alert("아이디 또는 비밀번호가 일치하지 않습니다.");
          }
        );
      }
    },

}
</script>

<style>

</style>