<template>
  <div class="container py-5">
    <div class="d-flex flex-row-reverse mb-4">
      <button @click.prevent="addnewadmin" class="btn btn-outline-light border rounded-0" type="button">新增管理員</button>
    </div>
    <table class="table table-light table-hover">
      <thead>
        <tr>
          <th>#</th>
          <th>名稱</th>
          <th>帳號</th>
          <th>建立日期</th>
          <th>最後登入時間</th>
          <th>修改</th>
          <th>刪除</th>
        </tr>
      </thead>
      <tbody>
        <template v-for="(it,index) in adminlist" :key="index">
          <tr>
            <td>{{index+1}}</td>
            <td>{{it.adminname}}</td>
            <td>{{it.adminaccount}}</td>
            <td>{{ this.filters.ToDateFormat(it.regdate) }}</td>
            <td>{{ this.filters.ToDateFormat(it.lastlogin) }}</td>
            <td><button class="btn btn-outline-success" type="button" @click.prevent="opendetail(it)">修改</button></td>
            <td><button class="btn btn-outline-danger" type="button" @click.prevent="deleteAdmin(it.aid)">刪除</button></td>
          </tr>
        </template>
      </tbody>
    </table>
  </div>
  <modal ref="adminmodal" :admin="admindetail" @reset="getAdminList"/>
</template>

<script>
import modal from '@/components/AdminModal.vue'
export default {
  data () {
    return {
      adminlist: [],
      admindetail: {}
    }
  },
  components: {
    modal
  },
  methods: {
    getAdminList () {
      this.axios({
        method: 'get',
        url: 'http://localhost:8080/admin/getall',
        headers: { Authorization: this.filters.getCookie('tocken') }
      })
        .then((res) => {
          this.adminlist = res.data
        })
    },
    deleteAdmin (id) {
      this.axios({
        method: 'delete',
        url: 'http://localhost:8080/admin/delete/' + id
      })
        .then(() => {
          this.getAdminList()
        })
    },
    opendetail (data) {
      this.admindetail = data
      this.$refs.adminmodal.show()
    },
    addnewadmin () {
      this.admindetail = {}
      this.$refs.adminmodal.show()
    }
  },
  mounted () {
    this.getAdminList()
  }
}
</script>
