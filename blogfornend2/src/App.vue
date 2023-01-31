<template>
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
</style>
