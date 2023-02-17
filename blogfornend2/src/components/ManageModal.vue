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
              <img v-if="imgurl!=''" style="width: 100%; height: 400px;object-fit: contain;" :src="imgurl" alt=""/>
              <p class="fs-3">內容</p>
              <div>
                <ckeditor :editor="editor" v-model="Article.content" :config="editorConfig"></ckeditor>
              </div>
            </form>
          </div>
          <div class="modal-footer">
              <button @click.prevent="deletearticle(Article.aid)" type="button" class="btn btn-danger px-5">刪除</button>
              <button v-if="this.imgfile.name==undefined" @click.prevent="updatearticle" type="button" class="btn btn-dark px-5">儲存</button>
              <button v-else @click.prevent="addFileToBackend" type="button" class="btn btn-dark px-5">儲存(包含圖片)</button>
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
      currenttid: [],
      pid: '',
      imgfile: '',
      imgurl: ''
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
      this.currenttid = []
      this.Article = {}
      this.getmessage(this.mid)
    }
  },
  methods: {
    show () {
      this.modal.show()
    },
    hide () {
      this.modal.hide()
    },
    datasort (data) {
      if (data.length > 1) {
        data.sort((a, b) => {
          return b.createtime < a.createtime ? 1 : -1
        })
      }
      return data
    },
    addfile (event) {
      if (event.target.files[0] !== undefined) {
        this.imgfile = event.target.files[0]
        const reader = new FileReader()
        reader.readAsDataURL(this.imgfile)
        reader.onloadend = (e) => { // function(e) e =>
          this.imgurl = e.target.result
        }
      } else {
        this.imgfile = ''
        this.imgurl = ''
      }
    },
    addFileToBackend () {
      const data = new FormData()
      data.append('image', this.imgfile)
      data.append('name', this.imgfile.name) // 如果imgfile為空值，呼叫imgfile.name導致回報錯誤

      this.axios.post('http://localhost:8080/File/add', data, {
        headers: {
          accept: 'application/json',
          'Accept-Language': 'en-US,en;q=0.8',
          'Access-Control-Allow-Origin': '*',
          'Content-Type': `multipart/form-data; boundary=${data._boundary}`
        }
      })
        .then((response) => {
          console.log(response)
          this.pid = response.data.pid
          this.updatearticle()
        }).catch((error) => {
          console.log(error)
        })
    },
    getarticle (id) {
      this.currenttid = []
      this.axios({
        method: 'get',
        url: 'http://localhost:8080/Article/get/' + id
      })
        .then((res) => {
          console.log(res)
          this.Article = res.data
          this.isObject = true
          this.Article.targets.forEach(element => {
            this.currenttid.push(element.tid) // 因為沒有歸零所以造成重複將數值加入
          })
          if (res.data.photo.pid !== undefined) {
            this.imgurl = 'http://localhost:8080/getimg/' + res.data.photo.pid + '/' + '圖片'
          } else {
            this.imgurl = ''
          }
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
      this.Article.currenttid = this.currenttid // 20230214造成無法在沒有圖片時修改文章
      this.Article.pid = this.pid // 20230214造成無法在沒有圖片時修改文章
      this.axios({
        method: 'post',
        url: 'http://localhost:8080/Article/update/',
        data: this.Article
      })
        .then(() => {
          this.hide()
          this.$emit('getagain')
        })
    },
    addtarget () {
      if (this.newTarget !== '') {
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
    },
    deletearticle (id) {
      this.axios({
        method: 'delete',
        url: 'http://localhost:8080/article/delete/' + id
      })
        .then(() => {
          this.modal.hide()
          this.$emit('getagain')
        })
    }
  },
  mounted () {
    this.modal = new Modal(document.getElementById('myModal'))
  }
}
</script>
