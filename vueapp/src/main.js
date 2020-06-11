import Vue from 'vue'
import DatetimePicker from 'vuetify-datetime-picker' 
Vue.use(DatetimePicker)
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import Vuetify from 'vuetify';
import VuetifyGoogleAutocomplete from 'vuetify-google-autocomplete';


Vue.config.productionTip = false
Vue.use(Vuetify);
Vue.use(VuetifyGoogleAutocomplete, {
	apiKey: "AIzaSyBboMsdHVaATrFJ83jShy0Heoang0Yr_bs",
});


new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app');

