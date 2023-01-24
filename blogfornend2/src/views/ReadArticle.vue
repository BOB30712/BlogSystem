<template>
  <h1>這是一個ReadArticle頁面</h1>
  <h1>目前的id: {{id}}</h1>
  <br><br><br><br><br><br>
  <p class="mb-0 header d-flex"><span class="fw-bold title mb-0 d-block px-2 me-2">標題</span>{{Article.title}}</p>
  <p class="mb-0 header d-flex"><span class="fw-bold title mb-0 d-block px-2 me-2">標籤</span>
    <template v-for="(it,index) in Article.targets" :key="index">
      {{ it.tname }} <span v-if="index+1!=Article.targets.length">/</span>
    </template>
  </p>
  <p class="mb-0 header d-flex"><span class="fw-bold title d-block px-2 me-2">時間</span>{{createDate}}</p>
  <p>內文:</p>
  <div v-html="Article.content"></div>
  <p>圖片:</p>
  <img :src="imghref"/>
</template>

<style>
.header{
  background-color: royalblue;
  color:white;
}
.header .title{
  background-color: white;
  color:royalblue;
}
</style>

<script>
export default {
  data () {
    return {
      id: '',
      createDate: '',
      imghref: '#',
      Article: {
        title: '20230119第5次測試',
        targets: [
          {
            tid: '4',
            tname: '窩不知道'
          },
          {
            tid: '5',
            tname: '怎麼會這樣'
          }
        ],
        date: '2023-01-19T06:58:56.000+00:00',
        content: '<p>修改service的新增方法</p><p>第5次測試</p>'
      }
    }
  },
  methods: {
    getArticle () {
      this.axios({
        method: 'get',
        url: 'http://localhost:8080/Article/get/' + this.id
      })
        .then((response) => {
          console.log(response)
          this.Article = response.data
          this.imghref = 'http://localhost:8080/getimg/' + response.data.photo.pid + '/' + 123
        })
        .catch((error) => console.log(error))
    },
    getcreateDate () {
      // const d = new Date(this.Article.date).toLocaleDateString()
      const d = new Date(this.Article.date)
      this.createDate = d.getFullYear() + '/' + (d.getMonth() + 1) + '/' + d.getDate() + '  ' + d.getHours() + ':' + d.getMinutes()
    }
  },
  mounted () {
    console.log('Param', this.$route.params.id)
    this.id = this.$route.params.id
    // this.getArticle()
    this.getcreateDate()
  }
}
</script>
