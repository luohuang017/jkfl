import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './store'
import '@/assets/css/global.css'
import Vant from 'vant'
import 'vant/lib/index.css';
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
// import 'element-plus/theme-chalk/index.css';
import locale from 'element-plus/lib/locale/lang/zh-cn'

// createApp(App).use(store).use(router).use(ElementPlus,{locale}).mount('#app')
import * as ElIconModules from '@element-plus/icons'
const app = createApp(App)
app.use(store).use(router).use(Vant).use(ElementPlus,{locale}).mount('#app')

for(let iconName in ElIconModules){
    app.component(iconName,ElIconModules[iconName])
}
