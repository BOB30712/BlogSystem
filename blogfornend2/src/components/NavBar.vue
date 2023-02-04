<template>
<nav class="navbar navbar-expand-lg navbar-dark">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">文章瀏覽</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav">
        <li class="nav-item">
          <router-link class="nav-link" to="/readpage/articlelist">所有文章列表</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/managepage/managearticle">進入後臺</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/readpage/blogarchive">網誌存檔</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/about">about</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/success">success</router-link>
        </li>
      </ul>
    </div>
  </div>
</nav>
</template>
<style lang="scss">
.nav-item:hover{
  background-color: rgb(131, 129, 129);
}
</style>
