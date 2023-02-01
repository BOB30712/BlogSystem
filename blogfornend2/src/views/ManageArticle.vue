<template>
  <div class="container py-5">
    <table class="table table-light table-hover">
      <thead>
        <tr>
          <th>#</th>
          <th style="width:50%">標題</th>
          <th style="width:20%">時間</th>
          <th>檢視文章</th>
          <th class="d-flex justify-content-center">檢視留言</th>
        </tr>
      </thead>
      <tbody v-if="ArticleList.length != 0">
        <template v-for="(it,index) in ArticleList" :key="index">
          <tr>
            <th scope="row">{{index+1}}</th>
            <td>{{ it.title }}</td>
            <td>{{ this.filters.ToDateFormat(it.createtime) }}</td>
            <td><button @click="openbyarticle(it)" type="button" class="btn btn-outline-dark">文章</button></td>
            <td class="text-center"><button @click="openbymessage(it)" type="button" class="btn btn-outline-secondary">留言</button></td>
          </tr>
        </template>
      </tbody>
      <tbody v-else>
        <tr>
          <th scope="row">1</th>
          <td>測試用文章</td>
          <td>2023/1/31 16:07</td>
          <td><button @click="openbyarticle(test)" type="button" class="btn btn-outline-dark">文章</button></td>
          <td class="text-center"><button @click="openbymessage(test.messages)" type="button" class="btn btn-outline-secondary">留言</button></td>
        </tr>
      </tbody>
    </table>
  </div>
  <modal ref="mymodal" :aid="aid" :mid="mid" @getagain="getArticleList"/>
</template>

<script>
import modal from '@/components/ManageModal.vue'
export default {
  components: {
    modal
  },
  data () {
    return {
      input: {},
      aid: 0,
      mid: 0,
      ArticleList: [],
      test: {
        aid: 43,
        title: '測試用資料',
        createtime: '2023-01-31T02:48:25.000+00:00',
        content: '<p>測試用資料1</p><p>測試用資料2</p><p>測試用資料3</p>',
        targets: [
          {
            tid: 1,
            tname: '標籤1'
          },
          {
            tid: 2,
            tname: '標籤2'
          },
          {
            tid: 43,
            tname: '標籤3'
          }
        ],
        messages: [
          {
            mid: 1,
            mname: '匿名',
            mcontent: '測試用留言',
            createtime: '2023-01-31T02:49:38.000+00:00'
          }
        ]
      }
    }
  },
  methods: {
    openbyarticle (Obj) {
      this.aid = Obj.aid
      this.$refs.mymodal.show()
    },
    openbymessage (Obj) {
      this.mid = Obj.aid
      this.$refs.mymodal.show()
    },
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
    }
  },
  mounted () {
    this.getArticleList()
  }
}
</script>
