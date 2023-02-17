<template>
  <!-- Modal -->
  <div class="modal fade" id="adminModal" tabindex="-1" aria-labelledby="adminModalLabel" aria-hidden="true">
    <div class="modal-dialog text-dark">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="adminModalLabel">管理人員詳細資料</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <div class="input-group mb-3">
            <span class="input-group-text bg-dark text-light border rounded-0 border-dark" id="admin-id">管理員ID</span>
            <input type="text" v-model="adminmodal.adminname" class="form-control border rounded-0 border-dark" aria-describedby="admin-id">
          </div>
          <div class="input-group mb-3">
            <span class="input-group-text bg-dark text-light border rounded-0 border-dark" id="admin-level">階級</span>
            <select v-model="adminmodal.level" class="form-select border rounded-0 border-dark" aria-describedby="admin-level">
              <option value="大總管">大總管</option>
              <option value="版務">版務</option>
              <option value="韭菜">韭菜</option>
            </select>
          </div>
          <div class="input-group mb-3">
            <span class="input-group-text bg-dark text-light border rounded-0 border-dark" id="admin-account">帳號</span>
            <input type="text" v-model="adminmodal.adminaccount" class="form-control border rounded-0 border-dark" aria-describedby="admin-account">
          </div>
          <div class="input-group mb-3">
            <span class="input-group-text bg-dark text-light border rounded-0 border-dark" id="admin-password">密碼</span>
            <input type="text" v-model="adminmodal.adminpassword" class="form-control border rounded-0 border-dark" aria-describedby="admin-password">
          </div>
          <div v-if="admin.regdate!=null">
            <div class="input-group mb-3">
              <span class="input-group-text bg-dark text-light border rounded-0 border-dark" id="admin-create">建立時間</span>
              <input type="date" v-model="cdate" class="form-control border rounded-0 border-dark" aria-describedby="admin-create">
            </div>
            <div class="input-group mb-3">
              <span class="input-group-text bg-dark text-light border rounded-0 border-dark" id="admin-last">最後一次登入時間</span>
              <input type="date" v-model="ldate" class="form-control border rounded-0 border-dark" aria-describedby="admin-last">
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button v-if="admin.regdate==null" type="button" class="w-100 btn btn-outline-success">新增</button>
          <button v-else type="button" class="w-100 btn btn-outline-success">儲存</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Modal } from 'bootstrap'
export default {
  data () {
    return {
      modal: '',
      adminmodal: {},
      cdate: '',
      ldate: ''
    }
  },
  props: {
    admin: {
      type: Object
    }
  },
  watch: {
    admin () {
      console.log('檢視詳細資料', this.admin)
      this.adminmodal = this.admin
      this.cdate = this.filters.ToDateFormat2(this.admin.regdate)
      this.ldate = this.filters.ToDateFormat2(this.admin.lastlogin)
    }
  },
  methods: {
    show () {
      this.modal.show()
    },
    hide () {
      this.modal.hide()
    }
  },
  mounted () {
    this.modal = new Modal(document.getElementById('adminModal'))
  }
}
</script>
