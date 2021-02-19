<template>
  

    <v-col cols="3">
      <v-dialog
        transition="dialog-bottom-transition"
        max-width="600"
      >
        <template v-slot:activator="{ on, attrs }">
          <v-btn
           style="margin:0px; padding:0px;"
            color="grey"
            v-bind="attrs"
            v-on="on"
          ><span class="white--text jua" style="font-size:18px; padding: 10px; ">아이디 찾기</span>
          </v-btn>
        </template>
        <template v-slot:default="dialog">
          <v-card>
            <v-toolbar
            class="mb-4"
              color="navy"
              dark
            ><span class="white--text jua " style="font-size:24px;">아이디 찾기</span>
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
                @click="findId"
                class="mx-2 my-2"
              ><span class="navy--text jua mx-5" style="font-size:18px; padding: 10px; ">확인</span>
              </v-btn>
            </v-card-actions>
          </v-card>
        </template>
      </v-dialog>
    </v-col>
  
  
</template>

<script>
import { findUserId } from "@/api/user.js";

export default {
    name: 'FindId',
    data(){
      return {
        user:{
          user_name: null,
          user_phone: null,
        },

      }
    },
    methods:{
      findId(){
        findUserId(
          this.user,
          (response) => {
              console.log(response.data);
              alert(this.user.user_name + "님의 아이디는 " + response.data + "입니다.");
          },
          (error) => {
              console.log(error);
        })
      }
    },
}
</script>

<style>

</style>