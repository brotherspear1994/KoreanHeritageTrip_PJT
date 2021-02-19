<template>

  <v-card
    max-width="70%"
    class="mx-auto my-5 px-5 py-5"
  >
  <div style="text-align:center; " class="mx-5 my-5">
    <span class="navy--text jua" style="font-size:36px;">선호하는 카테고리 3가지를 선택해주세요.</span>
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
                :src="`https://cdn.vuetifyjs.com/images/${item.src}`"
                height="150"
                class="text-right pa-2"
                @click="toggle"
              >
                <v-btn
                  icon
                  dark
                  x-large
                >
                  <v-icon>
                    {{ active ? 'mdi-heart' : 'mdi-heart-outline' }}
                  </v-icon>
                </v-btn>
                <div style="text-align:center;">
                    <span class="yeon" style="font-size:36px;">{{ item.title }}</span>
                </div>
              </v-img>
            </v-item>
          </v-col>
        </v-row>
      </v-item-group>
    </v-container>
  <v-btn block large class="brown1 navy--text jua my-5" style="font-size: 20px;" @click="insertBcode">
    다음으로
  </v-btn>
  </v-card>
</template>
<script>
import { bcodes } from "@/api/favor.js";
import { mapState } from "vuex";

export default {
  name: "InsertBcode",
  data () {
      return {
          items: [
            {
            src: 'backgrounds/bg.jpg',
            title: '종교신앙'
            },
            {
            src: 'backgrounds/md.jpg',
            title: '주거생활'
            },
            {
            src: 'backgrounds/bg-2.jpg',
            title: '불교조각'
            },
            {
            src: 'backgrounds/md2.jpg',
            title: '전적류'
            },
            {
            src: 'backgrounds/bg.jpg',
            title: '천연기념물'
            },
            {
            src: 'backgrounds/md.jpg',
            title: '무덤'
            },
            {
            src: 'backgrounds/bg-2.jpg',
            title: '불교회화'
            },
            {
            src: 'backgrounds/md2.jpg',
            title: '정치국방'
            },
            {
            src: 'backgrounds/bg.jpg',
            title: '교육문화'
            },
            {
            src: 'backgrounds/md.jpg',
            title: '서각류'
            },
            {
            src: 'backgrounds/bg-2.jpg',
            title: '인물사건'
            },
            {
            src: 'backgrounds/md2.jpg',
            title: '생활공예'
            },
            {
            src: 'backgrounds/md2.jpg',
            title: '일반회화'
            },
            {
            src: 'backgrounds/bg.jpg',
            title: '문서류'
            },
            {
            src: 'backgrounds/md.jpg',
            title: '불교공예'
            },
            {
            src: 'backgrounds/bg-2.jpg',
            title: '과학기술'
            },
            {
            src: 'backgrounds/md2.jpg',
            title: '명승'
            },
            {
            src: 'backgrounds/md2.jpg',
            title: '교통통신'
            },
        ],
        selected: [],
        favor: {
            user_no: 0,
            bcode_name1: "",
            bcode_name2: "",
            bcode_name3: "",
        }
    };
  },
  computed: {
    ...mapState(["userInfo", "isLogin"])
  },
  methods:{
    insertBcode() {
        if (this.selected.length === 3) {
            let select1 = this.selected[0];
            let select2 = this.selected[1];
            let select3 = this.selected[2];
            this.favor.user_no = this.userInfo.user_no;
            this.favor.bcode_name1 = this.items[select1].title;
            this.favor.bcode_name2 = this.items[select2].title;
            this.favor.bcode_name3 = this.items[select3].title;
            bcodes (
                this.favor,
                (response) => {
                    console.log(response);
                },
                (error) => {
                    console.log(error);
                }
            );
            this.$router.push({ name: "InsertCcce" });
        }
        else {
          alert("선호하는 카테고리 3가지를 선택해주세요.")
        }
    },
  },
}
</script>

<style>

</style>