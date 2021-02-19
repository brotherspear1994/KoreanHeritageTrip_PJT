import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify';
import VueGlide from 'vue-glide-js'
import 'vue-glide-js/dist/vue-glide.css'
import store from './store'

Vue.use(VueGlide)
Vue.config.productionTip = false

new Vue({
  router,
  vuetify,
  store,
  async beforeCreate() {
    let token = localStorage.getItem("access-token");
    if (store.state.userInfo == null && token) {
      await store.dispatch("GET_MEMBER_INFO", token);
    }
  },
  render: h => h(App)
}).$mount('#app')
