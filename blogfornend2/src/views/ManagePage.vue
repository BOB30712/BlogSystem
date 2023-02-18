<template>
  <managenavbar :level="getlevel"/>
  <router-view/>
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
    }
  },
  mounted () {
    if (this.filters.getCookie('tocken') === '') {
      alert('請重新登入')
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
