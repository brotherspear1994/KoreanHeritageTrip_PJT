<template>
  <div>
    <NoticeCreate style="margin-top: 3%" />
    <v-card class="mx-auto" width="80%" style="margin-bottom: 10%; margin-top: 2%;">
      <v-toolbar color="navy" dark>
        <v-toolbar-title class="jua" style="font-size:20px"
          >공지사항</v-toolbar-title
        >
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" persistent max-width="300px">
          <template v-slot:activator="{ on, attrs }">
            <v-btn class="" v-bind="attrs" v-on="on" icon>
              <v-icon> mdi-magnify</v-icon>
            </v-btn>
          </template>
          <v-card>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col>
                    <v-text-field
                      label="검색할 공지사항 번호 입력란"
                      v-model="noticeNumber"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions>
                <v-btn color="grey mx-auto" @click="searchNotice" class="jua" style="font-size:16px;">
                  검색
                </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>

      <template>
        <v-expansion-panels focusable>
          <v-expansion-panel v-for="(item, index) in items" :key="index">
            <!-- 제목 -->
            <v-expansion-panel-header class="jua brown1" style="font-size:19px;">{{
              '[' + item.notice_no + '] ' + item.notice_title
            }}
            </v-expansion-panel-header>

            <!-- 내용 -->
            <v-expansion-panel-content>
              <tiptap-vuetify
                v-model="item.notice_content"
                :extensions="extensions"
                >{{ item.notice_content }}
                <!-- <div slot="toolbar"></div> -->
              </tiptap-vuetify>
                <v-btn @click="modifyNotice(index)" v-if="isLogin" class="jua brown2 white--text my-1" style="font-size: 19px; float: right; margin-left: 10px">수정</v-btn>
                <!-- 작성자 -->
                <v-list-item-action-text>
                  <!-- {{ item.user_no }} -->
                </v-list-item-action-text>
                <v-btn @click="deleteNotice(index)" v-if="isLogin" value="index" class="jua grey white--text my-1" style="font-size: 19px; float: right;">삭제</v-btn>
            </v-expansion-panel-content>

          </v-expansion-panel>
        </v-expansion-panels>
        <!-- <v-divider v-if="index < items.length - 1" :key="index"></v-divider> -->
      </template>

      <v-list two-line>
        <v-list-item-group
          v-model="selected"
          multiple
        >
          <!-- pagination -->
          <div class="pagination jua">
            <p style="cursor:pointer">&laquo;</p>
            <div class="paginationsub" v-for="index in 1" :key="index">
              <p style="cursor:pointer">{{ index }}</p>
            </div>
            <p style="cursor:pointer">&raquo;</p>
          </div>
        </v-list-item-group>
      </v-list>
    </v-card>
  </div>
</template>

<script>
import NoticeCreate from '@/components/modal/notice/NoticeCreate';
import {
  listNoticepage,
  modifyNoticepage,
  deleteNoticepage,
} from '@/api/notice.js';

import {
  TiptapVuetify,
  Heading,
  Bold,
  Italic,
  Strike,
  Underline,
  Code,
  Paragraph,
  BulletList,
  OrderedList,
  ListItem,
  Link,
  Blockquote,
  HardBreak,
  HorizontalRule,
  History,
} from 'tiptap-vuetify';
import { mapState } from 'vuex';

export default {
  name: 'NoticePage',
  components: {
    NoticeCreate,
    TiptapVuetify,
  },

  created() {
    listNoticepage((response) => {
      this.items = response.data;
    });
  },

  computed: {
    ...mapState(['userInfo', 'isLogin']),
  },

  data() {
    return {
      notice: {
        notice_no: '',
        user_no: '',
        notice_title: '',
        notice_content: '',
      },
      dialog: false,
      // selected: [2],
      extensions: [
        History,
        Blockquote,
        Link,
        Underline,
        Strike,
        Italic,
        ListItem,
        BulletList,
        OrderedList,
        [
          Heading,
          {
            options: {
              levels: [1, 2, 3],
            },
          },
        ],
        Bold,
        Code,
        HorizontalRule,
        Paragraph,
        HardBreak,
      ],
      // 여기에 공지사항 데이터 불러와서 items에 담기.
      items: [
        {
          notice_content: '',
          notice_no: '',
          notice_title: '',
          user_no: '',
        },
      ],
    };
  },
  methods: {
    searchNotice() {
      this.dialog = false;
    },
    modifyNotice(index) {
      if(this.userInfo.user_no === this.items[index].user_no){
        modifyNoticepage(this.items[index], (response) => {
            this.items = response.data;
          });
          window.location.reload();
      }
      else{
        alert("글 작성자만 수정이 가능합니다.")
      }
    },
    deleteNotice(index) {
      if(this.userInfo.user_no === this.items[index].user_no){
        deleteNoticepage(this.items[index].notice_no, (response) => {
          this.items = response.data;
        });
        window.location.reload();
      }
      else{
        alert("글 작성자만 삭제가 가능합니다.")
      }
    },
  },
};
</script>

<style>
.pagination {
  display: inline-flex;
  padding-left: 15px;
  padding-top: 15px;
}
.paginationsub {
  display: inline-block;
  padding-left: 10px;
  padding-right: 10px;
}

.paginationsub p {
  color: black;
  float: left;
  text-decoration: none;
}
</style>
