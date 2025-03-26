import './assets/main.css'

import { createApp } from 'vue'
import store from './stores/store'

import App from './App.vue'
import router from './router'
export const apiBaseUrl = 'https://api-g1-472.jgogo01.in.th'
const app = createApp(App)

app.use(store)
app.use(router)

app.mount('#app')
