import Vue from 'vue'
import DatetimePicker from 'vuetify-datetime-picker' 
Vue.use(DatetimePicker)
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';

Vue.config.productionTip = false

 


new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app');

