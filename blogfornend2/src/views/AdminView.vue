<template>
  <h1>這是管理員列表</h1>
</template>

<script>
export default {
  data () {
    return {
      adminlist: []
    }
  },
  methods: {
    getAdminList () {
      this.axios({
        method: 'get',
        url: 'http://localhost:8080/admin/getall',
        headers: { Authorization: this.filters.getCookie('tocken') }
      })
        .then((res) => {
          console.log(res)
        })
    }
  },
  mounted () {
    this.getAdminList()
  }
}
</script>
