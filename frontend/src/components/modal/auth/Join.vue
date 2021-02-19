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
          ><span class="white--text jua" style="font-size:19px; padding: 5px; ">회원가입</span></v-btn>
        </template>
        <template v-slot:default="dialog">
          <v-card>
            <v-toolbar
            class="mb-4"
              color="navy"
              dark
            ><span class="white--text jua" style="font-size:24px;">회원가입</span>
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
            <v-btn
                color="grey"
                @click="confirmUserid"
                style="float: right;"
              ><span class="white--text jua" style="font-size:17px;">중복 확인</span>
              </v-btn>
              <v-text-field 
              v-model="user.user_id"
              outlined
              suffix="@gmail.com"
            label="이메일"
          ></v-text-field>
          <v-text-field
          type="password"
          outlined
          :counter="10"
          v-model="user.user_password"
            label="비밀번호"
          ></v-text-field>
          <v-text-field
          type="password"
          outlined
          :counter="10"
          v-model="passcon"
            label="비밀번호확인"
          ></v-text-field>
           <v-text-field
          v-model="user.user_name"
          outlined
            label="이름"
          ></v-text-field>
            
          <v-text-field
          v-model="user.user_phone"
          outlined
            label="연락처"
          ></v-text-field>
            </v-card-text>
            <v-card-actions class="justify-end">
              <v-btn
                color="brown2"
                @click="create"
                class="mx-2 my-2"
              ><span class="navy--text jua mx-5" style="font-size:20px; padding: 10px; ">가입하기</span></v-btn>
            </v-card-actions>
          </v-card>
        </template>
      </v-dialog>
    </v-col>
  </v-row>
  
</template>


<script>
import { join } from "@/api/user.js";
import { confirmId } from "@/api/user.js";

export default {
    name: 'join',
    data(){
      return {
        user:{
          user_no: 0,
          user_id: "",
          user_password: "", 
          user_name: "",
          user_phone: "",
        },
        passcon: '',
        idcon: '',
      }
    },
    methods:{
      create() {
        if (this.passcon === this.user.user_password) {
          if(this.idcon === true){
            join(
                this.user, 
              (response)=>{
                console.log("회원가입성공", response)
                this.user.user_id = "",
                this.user.user_password = "",
                this.passcon = "",
                this.user.user_name = "",
                this.user.user_phone = ""
                alert("가입하신 아이디로 이메일을 발송했습니다.\n첨부된 링크를 클릭한 후 로그인 해주세요.");
              },
              (error)=>{
                console.log(error);
              })
          }
          else{
            alert("아이디 중복 확인을 해주세요.")
          }
        } else {
          alert("비밀번호가 일치하지 않습니다. 다시 입력해 주세요.")
        }
      },
      confirmUserid(){
        confirmId(
         this.user.user_id,
          (response) => {
            console.log(this.user.user_id);
            console.log(response.data);
            if (response.data === "success") {
              console.log('성공');
              alert('사용가능한 아이디입니다.');
              this.idcon = true;
            } else {
              console.log('실패');
              alert('이미 사용하고 있는 아이디입니다.');
            }
          },
          (error) => {
            console.log(error);
          }
      )},
    },
}
</script>


<style>

</style>