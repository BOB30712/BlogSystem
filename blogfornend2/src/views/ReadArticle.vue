<template>
  <p class="mb-0 header d-flex"><span class="fw-bold title mb-0 d-block px-2 me-2">標題</span>{{Article.title}}</p>
  <p class="mb-0 header d-flex"><span class="fw-bold title mb-0 d-block px-2 me-2">標籤</span>
    <template v-for="(it,index) in Article.targets" :key="index">
      {{ it.tname }} <span v-if="index+1!=Article.targets.length">/</span>
    </template>
  </p>
  <p class="mb-0 header d-flex"><span class="fw-bold title d-block px-2 me-2">時間</span>{{createDate}}</p>
  <div class="container mt-3">
    <p>內文:</p>
    <div v-html="Article.content"></div>
    <p>圖片:</p>
    <div class="col-md-4 d-flex justify-content-start" style="height: 200px">
      <img :src="imghref" style="height: 100%;object-fit: contain;"/>
    </div>
    <p class="text-warning fs-4">留言:</p>
    <div class="text-center">
      <p class="text-warning fs-4" v-if="nomessage">尚未有留言</p>
    </div>
    <template v-for="(it,index) in Article.messages" :key="index">
      <div class="d-flex text-warning">
        <p>{{ it.mname }}:</p>
        <p v-if="it.replyid!=null" class="flex-grow-1">(回覆第{{getfloor(it.replyid)}}樓 {{ getname(it.replyid) }}){{ it.mcontent }}</p>
        <p v-else class="flex-grow-1">{{ it.mcontent }}</p>
        <p>{{ getmessagedate(it.createtime) }}</p>
      </div>
    </template>
  </div>
  <div class="mt-4 pb-5 container">
    <form class="row g-3">
      <div class="col-3">
        <label for="messagename" class="visually-hidden">暱稱</label>
        <input v-model="mname" type="text" class="form-control rounded-0" id="messagename" placeholder="輸入暱稱">
      </div>
      <div class="col">
        <label for="messagecontext" class="visually-hidden">留言內容</label>
        <input v-model="mcontent" type="text" class="form-control rounded-0" id="messagecontext" placeholder="輸入留言內容">
      </div>
      <div class="col-2">
        <button @click.prevent="addmessage" type="button" class="btn btn-outline-light mb-3 rounded-0 w-100">送出</button>
      </div>
    </form>
  </div>
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
      },
      mname: '',
      mcontent: '',
      nomessage: true
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
          this.imghref = 'http://localhost:8080/getimg/' + response.data.photo.pid + '/' + '圖片'
          if (this.Article.messages.length < 1) {
            this.nomessage = true
          } else {
            this.nomessage = false
            this.Article.messages.sort((a, b) => {
              return b.createtime < a.createtime ? 1 : -1
            })
          }
        })
        .catch((error) => console.log(error))
    },
    getcreateDate () {
      // const d = new Date(this.Article.date).toLocaleDateString()
      const d = new Date(this.Article.date)
      this.createDate = d.getFullYear() + '/' + (d.getMonth() + 1) + '/' + d.getDate() + '  ' + d.getHours() + ':' + d.getMinutes()
    },
    getmessagedate (date) {
      const d = new Date(date)
      return (d.getMonth() + 1) + '/' + d.getDate() + '  ' + d.getHours() + ':' + d.getMinutes()
    },
    addmessage () {
      this.axios({
        method: 'post',
        url: 'http://localhost:8080/message/add/',
        data: {
          mname: this.mname,
          mcontent: this.mcontent,
          aid: this.Article.aid
        }
      })
        .then((response) => {
          console.log(response)
          this.getArticle()
        })
        .catch((error) => console.log(error))
    },
    RiseSort (array) {
      let arr = []
      arr = array
      arr.sort(function (a, b) {
        return b.createtime < a.createtime ? 1 : -1
      })
      return array
    },
    getfloor (target) {
      let ans = ''
      let num = 1
      this.Article.messages.forEach(element => {
        if (element.mid === target) {
          ans = num
        }
        num++
      })
      return ans
    },
    getname (target) {
      let ans = ''
      this.Article.messages.forEach(element => {
        if (element.mid === target) {
          ans = element.mname
        }
      })
      return ans
    }
  },
  mounted () {
    this.id = this.$route.params.id
    this.getArticle()
    this.getcreateDate()
  }
}
</script>
