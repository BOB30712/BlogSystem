<template>
  <nav>
    <router-link to="/">Home</router-link> |
    <router-link to="/about">About</router-link> |
    <router-link to="/success">success</router-link> |
    <router-link to="/readpage/articlelist">所有文章列表</router-link> |
    <router-link to="/article">撰寫文章</router-link> |
  </nav>
  <div style="background-color: black;">
    <router-view/>
  </div>
</template>

<style lang="scss">
@import url('https://fonts.googleapis.com/css2?family=DotGothic16&display=swap');
#app {
  font-family: 'DotGothic16', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: white;
}
.ArticleContent .ck-editor__editable {
  min-height: 400px;
}
.ArticleContent .ck.ck-editor__main>.ck-editor__editable {
    background: #000;
    border-radius: 0;
}
.ArticleContent .ck-focused {
  border: 2px solid white !important;
}
nav {
  padding: 30px;
  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
