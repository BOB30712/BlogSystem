<template>
  <div class="container">
    <div class="mb-3 col-12">
      <label for="formGroupExampleInput" class="form-label fs-1">標題</label>
      <input v-model="title" type="text" class="form-control" id="formGroupExampleInput" placeholder="寫下標題">
    </div>
    <div class="mb-3">
      <label for="formFile" class="form-label fs-1">上傳圖片</label>
      <input class="form-control" type="file" id="formFile" @change="addfile">
    </div>
    <img v-if="imgurl!=''" style="width: 100%; height: 400px;object-fit: contain;" :src="imgurl" alt="">
    <h2>設定標籤</h2>
    <div class="d-flex flex-wrap">
      <template v-for="(i,index) in target" :key="index">
        <div class="form-check me-2">
          <input v-model="selecttarget" class="form-check-input" type="checkbox" :value="i.id" :id="i.name">
          <label class="form-check-label" :for="i.name">
            {{ i.name }}
          </label>
        </div>
      </template>
    </div>
    <div class="input-group mb-3">
      <input type="text" v-model="newTarget" class="form-control" placeholder="新增標籤" aria-describedby="addtarget">
      <button  @click.prevent="addtarget"  class="btn btn-outline-light" type="button" id="addtarget">新增</button>
    </div>

    <h2>內容</h2>
    <div class="ArticleContent">
      <ckeditor :editor="editor" v-model="editorData" :config="editorConfig"></ckeditor>
    </div>
    <div class="d-flex justify-content-center">
      <button @click.prevent="addFileToBackend" type="button" class="btn btn-outline-light w-100 fs-1 fw-bold my-5">送出</button>
    </div>
  </div>
</template>

<script>
/* eslint-disable */
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
export default {
  data(){
    return {
      editor: ClassicEditor,
      editorData: '<p>Content of the editor.</p>',
      editorConfig: {},
      imgfile: {},
      imgurl:'',
      newTarget: '',
      title: '',
      selecttarget: [],
      target: [],
      pid: null
    }
  },
  methods: {
    addfile (event) {
      if(event.target.files[0]!=undefined){
        this.imgfile = event.target.files[0]  
        const reader =  new FileReader();
        reader.readAsDataURL(this.imgfile);
        reader.onloadend = (e) => { //function(e) e =>
          this.imgurl=e.target.result;
        }
      }else{
        this.imgurl = ''
      }
    },
    addFileToBackend () {
      const data = new FormData();
      data.append('image', this.imgfile);
      data.append('name', this.imgfile.name);

      this.axios.post('http://localhost:8080/File/add', data, {
      headers: {
          'accept': 'application/json',
          'Accept-Language': 'en-US,en;q=0.8',
          'Access-Control-Allow-Origin': '*',
          'Content-Type': `multipart/form-data; boundary=${data._boundary}`,
      }
      })
      .then((response) => {
          console.log(response)
          this.pid=response.data.pid
          this.addarticle()
      }).catch((error) => {
          console.log(error)
      });
    },
    getalltarget () {
      this.axios({
        method: 'get',
        url: 'http://localhost:8080/Target/get/all'
      })
      .then((response) => {
        console.log(response)
        this.target = []
        response.data.forEach(
          (element) => {
            this.target.push({
              name: element.tname,
              id: element.tid
            })
          }
        )
      })
      .catch((error) => console.log(error))
    },
    addtarget () {
      if (this.newTarget!=='') {
        const object = {
          name: this.newTarget
        }
        this.target.push(object)
        this.axios({
          method: 'put',
          url: 'http://localhost:8080/Target/add/'+`${this.newTarget}`
        })
        .then((response) => {
          console.log(response)
          this.getalltarget()
        })
        .catch((error) => console.log(error))
        this.newTarget = ''
      }
    },
    addarticle () {
      this.$router.push({ name: 'about'})
      this.axios({
        method: 'post',
        url: 'http://localhost:8080/Article/add/',
        data: {
          title: this.title,
          content: this.editorData,
          pid: this.pid,
          targets: this.selecttarget
        }
      })
      .then((response) => {
        console.log(response)
        this.$router.push('/success')
      })
      .catch((error) => console.log(error))
    },
    print () {
      /*
      this.target.forEach(
        (element) => {
          if (element.select === true) {
            this.selecttarget.push(element.name)
          }
        }
      )
      */
      console.log(this.imgfile)
      console.log(this.imgfile.name)
      console.log(this.selecttarget)
      console.log(this.title)
      console.log(this.editorData)
      this.selecttarget = []
    }
  },
  mounted () {
    this.getalltarget()
  }
}
</script>

<style  scoped lang="scss">
@import url('https://fonts.googleapis.com/css2?family=DotGothic16&display=swap');
.ArticleContent{
  font-family: 'DotGothic16', sans-serif;
}
</style>
