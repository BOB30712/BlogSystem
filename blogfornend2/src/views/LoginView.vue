<template>
  <div class="container mt-4">
    <h1 class="text-center fw-bold">登入畫面</h1>
    <div class="mb-3 col-12">
      <label for="accountinput" class="form-label fs-1">帳號</label>
      <input v-model="account" type="text" class="form-control rounded-0 bg-dark text-white border-light" id="accountinput" placeholder="輸入帳號">
    </div>
    <div class="mb-3 col-12">
      <label for="passwordinput" class="form-label fs-1">密碼</label>
      <input v-model="password" type="password" class="form-control rounded-0 bg-dark text-white border-light" id="passwordinput">
    </div>
    <div class="d-flex justify-content-center">
      <button @click.prevent="login" type="button" class="btn btn-outline-light rounded-0 w-100 fs-1 my-5">登入</button>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      account: '',
      password: ''
    }
  },
  methods: {
    login () {
      this.axios({
        method: 'post',
        url: 'http://localhost:8080/admin/login/',
        data: {
          adminaccount: this.account,
          adminpassword: this.password
        }
      })
        .then((res) => {
          console.log(res)
          document.cookie = 'tocken=' + res.data
          this.$router.push('/managepage')
        })
    }
  }
}
</script>
