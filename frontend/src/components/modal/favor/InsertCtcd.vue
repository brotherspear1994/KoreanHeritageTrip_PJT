<template>
  <v-card
    max-width="70%"
    class="mx-auto my-5 px-5 py-5"
  >
  <div style="text-align:center; " class="mx-5 my-5">
    <span class="navy--text jua" style="font-size:36px;">선호하는 지역 3가지를 선택해주세요.</span>
  </div>
    <v-container class="pa-1">
      <v-item-group
        v-model="selected"
        multiple
      >
        <v-row>
          <v-col
            v-for="(item, i) in items"
            :key="i"
            cols="12"
            md="4"
          >
            <v-item v-slot="{ active, toggle }">
              <v-img
                :src="require(`@/assets/${item.src}`)"
                max-height="150"
                contain
                class="text-right pa-2"
                @click="toggle"
              >
                <v-btn
                  icon
                  dark
                  x-large
                  color="red"
                >
                  <v-icon>
                    {{ active ? 'mdi-heart' : 'mdi-heart-outline' }}
                  </v-icon>
                </v-btn>
              </v-img>
            </v-item>
            
                <div style="text-align:center;">
                    <span class="yeon" style="font-size:36px;">{{ item.title }}</span>
                </div>
          </v-col>
        </v-row>
      </v-item-group>
    </v-container>
    <v-btn block large class="brown1 navy--text jua my-5" style="font-size: 20px;" @click="insertCtcd">
        다음으로
    </v-btn>
  </v-card>
</template>
<script>
import { ctcds } from "@/api/favor.js";
import { getFavor } from "@/api/favor.js";
import { updateCtcds } from "@/api/favor.js";
import { mapState } from "vuex";

export default {
  name: "InsertCtcd",
  data () {
      return {
          items: [
            {
            src: 'ctcd/gb.jpg',
            title: '경북'
            },
            {
            src: 'ctcd/gyoengnam.gif',
            title: '경남'
            },
            {
            src: 'ctcd/seoul.png',
            title: '서울'
            },
            {
            src: 'ctcd/jeonnam.jpg',
            title: '전남'
            },
            {
            src: 'ctcd/gg.jpg',
            title: '경기'
            },
            {
            src: 'ctcd/chungnam.jpg',
            title: '충남'
            },
            {
            src: 'ctcd/jeonbuk.jpg',
            title: '전북'
            },
            {
            src: 'ctcd/chungbuk.jpg',
            title: '충북'
            },
            {
            src: 'ctcd/gangwon.jpg',
            title: '강원'
            },
            {
            src: 'ctcd/busan.gif',
            title: '부산'
            },
            {
            src: 'ctcd/jeju.jpg',
            title: '제주'
            },
            {
            src: 'ctcd/daegu.jpg',
            title: '대구'
            },
        ],
        selected: [],
        favor: {
            user_no: 0,
            ccba_ctcd_nm1: "",
            ccba_ctcd_nm2: "",
            ccba_ctcd_nm3: "",
        }
    };
  },
  computed: {
    ...mapState(["userInfo", "isLogin"])
  },
  methods:{
    insertCtcd() {
        if (this.selected.length === 3) {
            let select1 = this.selected[0];
            let select2 = this.selected[1];
            let select3 = this.selected[2];
            this.favor.user_no = this.userInfo.user_no;
            this.favor.ccba_ctcd_nm1 = this.items[select1].title;
            this.favor.ccba_ctcd_nm2 = this.items[select2].title;
            this.favor.ccba_ctcd_nm3 = this.items[select3].title;
            getFavor(
              this.userInfo.user_no,
              (response) => {
                if (response.data === "success") {
                    ctcds (
                      this.favor,
                      (response) => {
                          console.log(response);
                      },
                      (error) => {
                          console.log(error);
                      }
                  );
                } else {
                  updateCtcds (
                    this.favor,
                    (response) => {
                        console.log(response);
                    },
                    (error) => {
                        console.log(error);
                    }
                );
                }
              },
              (error) => {
                  console.log(error);
              }
          )
            this.$router.push({ name: "InsertBcode" });
        }
        else {
          alert("선호하는 지역 3가지를 선택해주세요.");
        }
    },
  },
}
</script>

<style>

</style>