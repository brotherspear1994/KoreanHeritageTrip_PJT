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
          <span class="white--text jua" style="font-size:19px; padding: 5px;">{{ userInfo.user_name }}님의 마이페이지</span>
          </v-btn>
        </template>
        <template v-slot:default="dialog">
          <v-card>
            <v-toolbar
              color="navy"
              class="mb-4"
              dark
            ><span class="white--text jua" style="font-size:24px;">회원 정보</span>
            <v-spacer/>
              <v-btn
                text
                @click="dialog.value = false"
              >
              <span class="brown1--text jua" style="font-size:14px; padding: 10px; ">x</span>
              </v-btn>
            </v-toolbar>
            <v-card-text>
                <v-row class="mx-0">
                    <v-col cols="3" class="black--text jua" style="font-size: 18px;">
                    아이디
                    </v-col>
                    <v-text-field
                    readonly
                    v-model="userid"
                    cols="7">
                    </v-text-field>
                </v-row>
                <v-row class="mx-0">
                    <v-col cols="3" class="black--text jua" style="font-size: 18px;">
                    이름
                    </v-col>
                    <v-text-field v-model="username" cols="7">
                    {{ userInfo.user_name }}
                    </v-text-field>
                </v-row>
                <v-row class="mx-0">
                    <v-col cols="3" class="black--text jua" style="font-size: 18px;">
                    비밀번호
                    </v-col>
                    <v-text-field 
                    type="password"
                    v-model="userpwd"
                    cols="7">
                    {{ userInfo.user_password }}
                    </v-text-field>
                </v-row>
                <v-row>
                  <v-col cols="3" class="black--text jua" style="font-size: 18px;">
                    비밀번호 확인
                    </v-col>
                    <v-text-field
                    type="password"
                    v-model="passcon"
                    cols="6"
                    ></v-text-field>
                </v-row>
                <v-row class="mx-0">
                    <v-col cols="3" class="black--text jua" style="font-size: 18px;">
                    연락처
                    </v-col>
                    <v-text-field v-model="userphone" cols="7">
                    {{ userInfo.user_phone }}
                    </v-text-field>
                </v-row>
            </v-card-text>
            <v-card-actions class="justify-end">
              <v-btn
                color="grey"
                @click="deleteUser"
                class="mx-2 my-2"
              ><span class="white--text jua mx-5" style="font-size:18px; padding: 10px; ">회원 탈퇴</span>
              </v-btn>
            <v-btn
                color="brown2"
                @click="modifyUser"
                class="mx-2 my-2"
              ><span class="jua mx-5" style="font-size:18px; padding: 10px; ">정보 수정</span>
              </v-btn>
            </v-card-actions>
          </v-card>
        </template>
      </v-dialog>
    </v-col>
 </v-row>
  
</template>

<script>
import { findById, modifyInfo, deleteInfo, confirmPwd } from "@/api/user.js";
import { deleteFavor, getFavor } from "@/api/favor.js";
import { mapState } from "vuex";

export default {
    name: 'myInfo',
    data(){
      return {
        user: {},
        userid: null,
        username: null,
        userpwd: null,
        userphone: null,
        passcon: '',
      };
    },
    computed: {
        ...mapState(["userInfo", "isLogin"])
    },
    created() {
        this.userid = this.userInfo.user_id;
        this.username = this.userInfo.user_name;
        this.userphone = this.userInfo.user_phone;
        findById(
          this.userInfo.user_id,
          (response) => {
            if (response.data.message === "success") {
                console.log('회원 정보 확인');
            } else {
              console.log('회원 정보 확인 불가');
            }
          },
          (error) => {
            console.log(error);
          }
        );
    },
    methods: {
        modifyUser(){
            this.userInfo.user_id = this.userid;
            this.userInfo.user_name = this.username;
            this.userInfo.user_password = this.userpwd;
            this.userInfo.user_phone = this.userphone;
            if(this.userInfo.user_password === null){
              alert("기존 비밀번호를 입력해주세요.");
            }
            else{
              if (this.passcon === this.userInfo.user_password) {
                modifyInfo(
                    this.userInfo,
                    (response) => {
                        if (response.data === "success") {
                            console.log('성공');
                            alert("수정이 완료되었습니다.")
                        } else {
                        console.log('실패');
                        }
                    },
                    (error) => {
                        console.log(error);
                })
              }
              else{
                alert("비밀번호가 일치하지 않습니다. 다시 입력해 주세요.")
              }
            }
        },
        deleteUser(){
          this.userInfo.user_id = this.userid;
          this.userInfo.user_name = this.username;
          this.userInfo.user_password = this.userpwd;
          this.userInfo.user_phone = this.userphone;
          confirmPwd(
            this.userInfo,
            (response) => {
              if (response.data === "success") {
                  getFavor(
                    this.userInfo.user_no,
                    (response) => {
                      if (response.data === "success") {
                          console.log('기존 정보 없음');
                          deleteInfo(
                            this.userInfo.user_no,
                            (response) => {
                                if (response.data === "success") {
                                    alert("탈퇴 처리가 완료되었습니다.");
                                    this.$store
                                        .dispatch("LOGOUT")
                                        .then(() => {
                                          this.$router.push({ name: "Main" });
                                        })
                                        .catch(() => {
                                          console.log("로그아웃 문제 발생");
                                        });
                                }
                            },
                            (error) => {
                                console.log(error);
                        })
                      } else {
                      deleteFavor(
                          this.userInfo.user_no,
                          (response) => {
                              if (response.data === "success") {
                                  console.log('추천 정보 삭제');
                                  deleteInfo(
                                    this.userInfo.user_no,
                                    (response) => {
                                        if (response.data === "success") {
                                            alert("탈퇴 처리가 완료되었습니다.");
                                            this.$store
                                                .dispatch("LOGOUT")
                                                .then(() => {
                                                  this.$router.push({ name: "Main" });
                                                })
                                                .catch(() => {
                                                  console.log("로그아웃 문제 발생");
                                                });
                                        }
                                    },
                                    (error) => {
                                        console.log(error);
                                })
                              }
                          },
                          (error) => {
                              console.log(error);
                        })
                      }
                    },
              (error) => {
                  console.log(error);
              })
              }
            },
            (error) => {
                console.log(error);
                alert("비밀번호를 다시 입력해주세요.");
            }
          )
        },
    },
}
</script>

<style>

</style>