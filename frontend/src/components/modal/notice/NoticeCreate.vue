<template>
  <v-row justify="center">
    <v-dialog
      v-model="dialog"
      hide-overlay
      transition="dialog-top-transition"
      width="70%"
    >
      <template v-slot:activator="{ on, attrs }" v-if="isLogin">
        <v-btn x-large class="brown2 jua" style="font-size:18px; padding: 10px; " v-bind="attrs" v-on="on">
          글 작성
        </v-btn>
      </template>
      <!-- 공지사항 작성창 시작-->
      <v-card>
        <v-toolbar dark color="navy">
          <v-toolbar-title
            class="jua"
            style="font-size:20px; padding: 8.5px 0px 5px 0px;"
          >
            공지사항 작성
          </v-toolbar-title>
          <v-spacer></v-spacer>
          <v-btn icon dark @click="dialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>

        <!-- tiptap 적용 시작-->
        <div>
          <!-- Use the component in the right place of the template -->
          <v-text-field
            height="50px"
            class="font-weight-medium px-5"
            type="text"
            v-model="title"
            placeholder="제목을 작성하세요."
          ></v-text-field>
          <tiptap-vuetify
            v-model="content"
            :extensions="extensions"
            placeholder="내용을 작성하세요."
          />
          <div>
          <v-btn text @click="write" class="jua brown2 my-2 mx-5" style="font-size:17px;">
              저장
            </v-btn>
          </div>
        </div>
        
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script>
import Vue from 'vue';
import Vuetify from 'vuetify';
// import plugin
import { TiptapVuetifyPlugin } from 'tiptap-vuetify';
// don't forget to import CSS styles
import 'tiptap-vuetify/dist/main.css';
// Vuetify's CSS styles
import 'vuetify/dist/vuetify.min.css';
// Set up FontAwesome
import { library as faLibrary } from '@fortawesome/fontawesome-svg-core';
import { faHome, faSearch, faPlus } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import FileSelector from '@/components/FileSelector';
faLibrary.add(faHome, faSearch, faPlus);
Vue.component('font-awesome-icon', FontAwesomeIcon);
// Vuetify Object (as described in the Vuetify 2 documentation)
const vuetify = new Vuetify();
// use Vuetify's plugin
Vue.use(Vuetify);
// use this package's plugin
Vue.use(TiptapVuetifyPlugin, {
  // the next line is important! You need to provide the Vuetify Object to this place.
  vuetify, // same as "vuetify: vuetify"
  // optional, default to 'md' (default vuetify icons before v2.0.0)
  iconsGroup: 'fa',
});
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
  Image,
} from 'tiptap-vuetify';

import { writeNoticepage } from '@/api/notice.js';
import { mapState } from 'vuex';

export default {
  // specify TiptapVuetify component in "components"

  components: { TiptapVuetify },

  computed: {
    ...mapState(['userInfo', 'isLogin']),
  },

  data() {
    return {
      notice: {
        notice_no: 0,
        user_no: '',
        // notice_create_date: '',
        // notice_modify_date: '',
        notice_title: '',
        notice_content: '',
      },
      dialog: false,
      notifications: false,
      sound: true,
      widgets: false,
      // declare extensions you want to use
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
        [
          Image,
          {
            options: {
              imageSources: [
                { component: FileSelector, name: 'File Selector' },
              ],
            },
          },
        ],
      ],
      title: "",
      // starting editor's content
      content: "",
    };
  },
  methods: {
    write() {
      this.user_no = this.userInfo.user_no;
      if(this.title == ''){
        alert("제목을 입력하세요");  
      }
      else{
        if(this.content == ''){
          alert("내용을 입력하세요");
        }
        else{
          if(this.userInfo.user_no === 1){ // 관리자 계정
            writeNoticepage(
              this.notice,
              (this.notice.user_no = this.user_no),
              (this.notice.notice_title = this.title),
              (this.notice.notice_content = this.content),
              (response) => {
                console.log('공지사항 작성', response);
                this.notice_content = '';
              },
              (error) => {
                console.log(error);
              }
            );
            this.dialog = false;
            window.location.reload();
          }
          else{
            alert("글 작성은 관리자만 가능합니다.")
          }
        }
      }
    },
  },
};
</script>

<style></style>
