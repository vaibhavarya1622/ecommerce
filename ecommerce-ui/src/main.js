import { createApp } from 'vue'
import swal from 'sweetalert'
import App from './App.vue'
import router from './router'

window.axios = require('axios')
window.Swal = swal
createApp(App).use(router).mount('#app')
