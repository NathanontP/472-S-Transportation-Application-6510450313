import './assets/main.css'

import { createApp } from 'vue'
import store from './stores/store'

import App from './App.vue'
import router from './router'
export const apiBaseUrl = import.meta.env.VITE_API_BASE_URL
const app = createApp(App)

app.use(store)
app.use(router)

app.mount('#app')
