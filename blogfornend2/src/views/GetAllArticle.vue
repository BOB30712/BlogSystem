<template>
  <h1 v-if="$route.params.id == undefined">所有文章資料</h1>
  <h1 v-else>所有具有<span class="text-info fw-bold">#{{tname}}</span>的文章</h1>
  <br><br><br><br>
  <template v-for="(Article,index) in ArticleList" :key="index">
    <div class="card mb-3 rounded-0 bg-dark">
      <div class="row g-0">
        <div class="col-md-4 d-flex justify-content-center" style="height: 200px">
          <img v-if="Article.photo == null" src="#" style="height: 100%;object-fit: contain;" alt="">
          <img v-else :src="'http://localhost:8080/getimg/'+Article.photo.pid+'/' + '圖片'"/>
        </div>
        <div class="col-md-8">
          <div class="card-body d-flex flex-column h-100">
            <h5 class="card-title"><router-link class="text-decoration-none link-info fs-5" :to="{ name: 'readpage/articleread', params: { id: Article.aid} }">{{ Article.title }}</router-link></h5>
            <div class="d-flex">
              <template v-for="target in Article.targets" :key="target.tid">
                <p class="card-text">#{{target.tname}} </p>
              </template>
            </div>
            <p class="card-text mt-auto"><small class="text-muted">{{ getTime(Article.createtime) }}</small></p>
          </div>
        </div>
      </div>
    </div>
  </template>
</template>

<script>
export default {
  data () {
    return {
      tname: '',
      ArticleList: [],
      num: 5,
      img: 'http://localhost:8080/getimg/'
    }
  },
  watch: {
    '$route.params.id': function (value) {
      console.log('newtid is', value)
      this.getArticleList(value)
      this.tname = this.$route.params.tname
    }
  },
  methods: {
    getArticleList (tid) {
      if (tid == null) {
        console.log('qwer')
        this.axios({
          method: 'get',
          url: 'http://localhost:8080/Article/getAll'
        })
          .then((response) => {
            console.log(response)
            this.ArticleList = response.data
            // this.imghref = 'http://localhost:8080/getimg/' + response.data.photo.pid + '/' + 123
          })
          .catch((error) => console.log(error))
      } else {
        this.axios({
          method: 'get',
          url: 'http://localhost:8080/Article/getlist/' + tid
        })
          .then((response) => {
            console.log(response)
            this.ArticleList = response.data
          })
          .catch((error) => console.log(error))
      }
    },
    getTime (String) {
      const d = new Date(String)
      return d.getFullYear() + '/' + (d.getMonth() + 1) + '/' + d.getDate()
    }
  },
  mounted () {
    if (this.$route.params.id === undefined) {
      this.getArticleList()
    } else {
      this.getArticleList(this.$route.params.id)
    }
  }
}
</script>
