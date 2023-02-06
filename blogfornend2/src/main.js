import { createApp } from 'vue'
import CKEditor from '@ckeditor/ckeditor5-vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-icons/font/bootstrap-icons.css'
import 'bootstrap'
import './css/test.css'
import { ToDateFormat, MonthString } from './method/fiters'

const app = createApp(App)
app.config.globalProperties.axios = axios
app.config.globalProperties.filters = {
  ToDateFormat,
  MonthString
}
app.use(CKEditor)
app.use(router)
app.mount('#app')
