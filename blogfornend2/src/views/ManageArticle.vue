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
      <tbody>
        <tr>
          <th scope="row">1</th>
          <td>測試用文章</td>
          <td>2023/1/31 16:07</td>
          <td><button @click="openbyarticle" type="button" class="btn btn-outline-dark">文章</button></td>
          <td class="text-center"><button @click="openbymessage" type="button" class="btn btn-outline-secondary">留言</button></td>
        </tr>
      </tbody>
    </table>
  </div>
  <modal ref="mymodal" :data="input"/>
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
      test: {
        aid: 43,
        title: '測試用資料',
        createtime: '2023-01-31T02:48:25.000+00:00',
        content: '測試用資料',
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
    openbyarticle () {
      this.input = this.test
      this.$refs.mymodal.show()
    },
    openbymessage () {
      this.input = this.test.messages
      this.$refs.mymodal.show()
    }
  }
}
</script>
