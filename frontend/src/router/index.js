import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '@/components/common/Main.vue'
import NoticePage from '@/views/notice/NoticePage.vue'
import Favor from '@/views/favor/Favor.vue'

Vue.use(VueRouter)

const routes = [
  {
    path:'/',
    name: 'Main',
    component: Main,
  },
  {
    path: '/noticepage',
    name: 'NoticePage',
    component: NoticePage,
  },
  {
    path: "/heritage",
    name: "Favor",
    component: Favor,
    children: [
      {
        path: "recommend/:user_no",
        name: "Recommend",
        component: () => import("@/components/modal/favor/Recommend.vue")
      },
      {
        path: "ctcd",
        name: "InsertCtcd",
        component: () => import("@/components/modal/favor/InsertCtcd.vue")
      },
      {
        path: "bcode",
        name: "InsertBcode",
        component: () => import("@/components/modal/favor/InsertBcode.vue")
      },
      {
        path: "ccce",
        name: "InsertCcce",
        component: () => import("@/components/modal/favor/InsertCcce.vue")
      },
    ],
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
