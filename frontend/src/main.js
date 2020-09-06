import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import vueMoment from 'vue-moment'
import Carousel3d from 'vue-carousel-3d';

Vue.use(Carousel3d);
Vue.use(vueMoment)
Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
