<template>
  <managenavbar :level="getlevel"/>
  <div class="position-relative">
    <router-view/>
    <div class="position-absolute top-0 end-0 me-4">
      <i @click.prevent="toArray" class="bi bi-box-seam-fill fs-1 note"></i>
    </div>
      <div class="toast-container position-fixed bottom-0 end-0">
        <template v-for="(it,index) in triggerdata" :key="index">
          <div class="liveToast toast" role="alert" aria-live="assertive" aria-atomic="true">
            <div class="toast-header">
              <strong class="me-auto text-dark">新增一筆{{ it.tablename }}</strong>
              <small>{{this.filters.ToDateFormat(it.time)}}</small>
              <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
            <div class="toast-body text-dark">
              新增內容: {{ it.content }}
            </div>
          </div>
        </template>
      </div>
  </div>
</template>

<script>
import managenavbar from '@/components/ManageNavBar.vue'
import { Toast } from 'bootstrap'
export default {
  data () {
    return {
      countTime: '',
      getlevel: '',
      triggerdata: [],
      triggertoast: []
    }
  },
  components: {
    managenavbar
  },
  methods: {
    getadmin () {
      this.axios({
        method: 'post',
        url: 'http://localhost:8080/admin/get/login',
        headers: { Authorization: this.filters.getCookie('tocken') }
      })
        .then((res) => {
          this.getlevel = res.data.level
          document.cookie = 'level=' + res.data.level
          document.cookie = 'adminname=' + res.data.adminname
          this.getTrigger()
          if (res.data !== null && res.data !== '') {
            alert('歡迎' + res.data.adminname)
            this.countTime = window.setTimeout(
              () => {
                alert('登入超時，請重新登入') // 2*60*1000=120*1000=120000
                this.$router.push('/login')
              }, 120000
            )
          } else {
            document.cookie = 'tocken=null; expires=Mon, 26 Aug 1996 12:00:00 UTC'
            this.$router.push('/managepage')
          }
        })
        .catch((error) => console.log(error))
    },
    getTrigger () {
      this.axios({
        method: 'get',
        url: 'http://localhost:8080/blog/test',
        headers: { Authorization: this.filters.getCookie('tocken') }
      })
        .then((res) => {
          this.triggerdata = res.data
        })
    },
    toArray () {
      this.triggertoast = []
      Array.from(document.querySelectorAll('.liveToast'))
        .forEach((toastNode) => {
          const t = new Toast(toastNode)
          this.triggertoast.push(t)
        })
      this.openToast()
    },
    openToast () {
      this.triggertoast.forEach((toast) => {
        toast.show()
      })
    }
  },
  mounted () {
    if (this.filters.getCookie('tocken') === '') {
      alert('尚未登入，請重新登入')
      this.$router.push('/login')
    } else {
      this.getadmin()
    }
  },
  beforeUnmount () {
    window.clearTimeout(this.countTime)
  }
}
</script>

<style>
.note {
  color: yellow;
}
.note:hover{
  color: red;
}
</style>
