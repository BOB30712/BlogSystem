<template>
<nav class="navbar navbar-expand-lg navbar-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">後臺管理</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item">
          <router-link class="nav-link" to="/managepage/article">撰寫文章</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/managepage/managearticle">管理文章</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/managepage/adminview">管理管理員</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" @click="logout" to="/login">登出</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/readpage/articlelist">離開</router-link>
        </li>
      </ul>
    </div>
  </div>
</nav>
</template>

<script>
export default {
  methods: {
    logout () {
      window.clearTimeout()
      document.cookie = 'tocken=null; expires=Mon, 26 Aug 1996 12:00:00 UTC'
      this.$router.push('/managepage')
    }
  }
}
</script>

<style lang="scss">
.nav-item:hover{
  background-color: rgb(131, 129, 129);
}
</style>
