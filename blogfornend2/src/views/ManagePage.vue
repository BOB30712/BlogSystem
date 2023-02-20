<template>
  <managenavbar :level="getlevel"/>
  <div class="position-relative">
    <router-view/>
    <div class="position-absolute top-0 end-0 me-4">
      <i class="bi bi-box-seam-fill fs-1 note"></i>
    </div>
  </div>
</template>

<script>
import managenavbar from '@/components/ManageNavBar.vue'
export default {
  data () {
    return {
      countTime: '',
      getlevel: ''
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
          console.log(res)
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
