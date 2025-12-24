// src/main.js
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './css/global.css'
//导入代码颜色高亮工具
import highlightCodeAndShowLineNumbers from './utils/highlight.js'
//数据模拟
// import './mockjs/index.js'

Vue.config.productionTip = false
Vue.use(ElementUI);

Vue.directive('highlight', {
  inserted(el, binding) {
    highlightCodeAndShowLineNumbers(el, binding)
  },
  update(el, binding) {
    highlightCodeAndShowLineNumbers(el, binding)
  },
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
