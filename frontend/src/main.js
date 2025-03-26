import './assets/main.css'

import { createApp } from 'vue'
import store from './stores/store'

import App from './App.vue'
import router from './router'
export const apiBaseUrl = "http://10.4.11.244:18080"
const app = createApp(App)

app.use(store)
app.use(router)

app.mount('#app')
