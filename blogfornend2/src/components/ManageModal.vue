<template>
  <!-- Modal -->
  <div class="modal fade text-dark" id="myModal" tabindex="-1" aria-labelledby="ModalLabe" aria-hidden="true">
    <div class="modal-dialog modal-lg">
      <template v-if="isObject">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="ModalLabe">檢視<span class="text-primary">{{ Article.title }}</span>文章內容</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form>
              <div class="mb-3">
                <label for="title" class="form-label fs-3">標題</label>
                <input v-model="Article.title" type="text" class="form-control border border-dark" id="title">
              </div>
              <p class="mb-0 fs-3">修改標籤</p>
              <div class="d-flex flex-wrap">
                <template v-for="(i,index) in TargetList" :key="index">
                  <div class="form-check me-2">
                    <input v-model="currenttid" class="form-check-input border" type="checkbox" :value="i.tid" :id="i.tid">
                    <label class="form-check-label" :for="i.tname">
                      {{ i.tname }}
                    </label>
                  </div>
                </template>
              </div>
              <div class="input-group mb-3">
                <input type="text" v-model="newTarget" class="form-control border border-dark" placeholder="新增標籤" aria-describedby="addtarget">
                <button  @click.prevent="addtarget"  class="btn btn-outline-dark" type="button" id="addtarget">新增</button>
              </div>
              <div class="mb-3">
                <label for="formFile" class="form-label fs-3">更改圖片</label>
                <input class="form-control border border-dark" type="file" id="formFile" @change="addfile">
              </div>
              <p class="fs-3">內容</p>
              <div>
                <ckeditor :editor="editor" v-model="Article.content" :config="editorConfig"></ckeditor>
              </div>
            </form>
          </div>
          <div class="modal-footer">
              <button type="button" class="btn btn-danger px-5">刪除</button>
              <button @click.prevent="updatearticle" type="button" class="btn btn-dark px-5">儲存</button>
          </div>
        </div>
      </template>
      <template v-else>
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="ModalLabe">檢視<span class="text-primary">{{ Article.title }}</span>留言</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <table class="table">
              <thead>
                <tr class="table-dark">
                  <th scope="col">#</th>
                  <th scope="col">匿名</th>
                  <th scope="col">內容</th>
                  <th scope="col">時間</th>
                  <th scope="col">刪除</th>
                </tr>
              </thead>
              <tbody>
                <template v-for="(it,index) in datasort(Article)" :key="index">
                <tr>
                  <th scope="row">{{ index+1 }}</th>
                  <td>{{ it.mname }}</td>
                  <td>{{ it.mcontent }}</td>
                  <td>{{ this.filters.ToDateFormat(it.createtime) }}</td>
                  <td><button @click.prevent="deletemessage(it.mid)" type="button" class="btn btn-outline-danger">刪</button></td>
                </tr>
                </template>
              </tbody>
            </table>
          </div>
          <div class="modal-footer">
            <button type="button" class="w-100 btn btn-primary">關閉</button>
          </div>
        </div>
      </template>
    </div>
  </div>
</template>

<script>
import ClassicEditor from '@ckeditor/ckeditor5-build-classic'
import { Modal } from 'bootstrap'
export default {
  data () {
    return {
      editor: ClassicEditor,
      editorConfig: {},
      modal: {},
      newTarget: '',
      isObject: true,
      Article: {},
      TargetList: [],
      currenttid: []
    }
  },
  props: {
    mid: {
      type: Number
    },
    aid: {
      type: Number
    }
  },
  watch: {
    aid () {
      this.getarticle(this.aid)
    },
    mid () {
      this.getmessage(this.mid)
    }
  },
  methods: {
    show () {
      this.modal.show()
    },
    datasort (data) {
      if (data.length > 1) {
        data.sort((a, b) => {
          return b.createtime < a.createtime ? 1 : -1
        })
      }
      return data
    },
    getarticle (id) {
      this.axios({
        method: 'get',
        url: 'http://localhost:8080/Article/get/' + id
      })
        .then((res) => {
          console.log(res)
          this.Article = res.data
          this.isObject = true
          this.Article.targets.forEach(element => {
            this.currenttid.push(element.tid)
          })
          this.gettarget()
        })
    },
    gettarget () {
      this.axios({
        method: 'get',
        url: 'http://localhost:8080/Target/get/all'
      })
        .then((res) => {
          console.log(res)
          this.TargetList = res.data
        })
    },
    getmessage (id) {
      this.axios({
        method: 'get',
        url: 'http://localhost:8080/message/getbyaid/' + id
      })
        .then((res) => {
          this.Article = res.data
          this.isObject = false
        })
    },
    updatearticle () {
      this.axios({
        method: 'post',
        url: 'http://localhost:8080/Article/update/',
        data: this.Article
      })
        .then(() => {
          this.modal.hide()
          this.$emit('getagain')
        })
    },
    addtarget () {
      if (this.newTarget !== '') {
        const object = {
          name: this.newTarget
        }
        this.target.push(object)
        this.axios({
          method: 'put',
          url: 'http://localhost:8080/Target/add/' + `${this.newTarget}`
        })
          .then((response) => {
            console.log(response)
            this.gettarget()
          })
          .catch((error) => console.log(error))
        this.newTarget = ''
      }
    },
    deletemessage (id) {
      this.axios({
        method: 'delete',
        url: 'http://localhost:8080/message/delete/' + id
      })
        .then(() => {
          this.getmessage(this.mid)
        })
    }
  },
  mounted () {
    this.modal = new Modal(document.getElementById('myModal'))
  }
}
</script>
