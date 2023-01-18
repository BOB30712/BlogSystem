<template>
  <div class="container">
    <div class="mb-3 col-12">
      <label for="formGroupExampleInput" class="form-label fs-1">標題</label>
      <input v-model="title" type="text" class="form-control" id="formGroupExampleInput" placeholder="寫下標題">
    </div>
    <div class="mb-3">
      <label for="formFile" class="form-label fs-1">上傳圖片</label>
      <input class="form-control" type="file" id="formFile" @change="test">
    </div>
    <h2>設定標籤</h2>
    <div class="d-flex flex-wrap">
      <template v-for="(i,index) in target" :key="index">
        <div class="form-check me-2">
          <input v-model="selecttarget" class="form-check-input" type="checkbox" :value="i.name" :id="i.name">
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
      <button @click.prevent="print" type="button" class="btn btn-outline-light w-100 fs-1 fw-bold my-5">送出</button>
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
      newTarget: '',
      title: '',
      selecttarget: [],
      target: [
        {
          name: '生活',
          select: false
        },
        {
          name: '政治',
          select: false
        },
        {
          name: '知識',
          select: true
        }
      ]
    }
  },
  methods: {
    test (event) {
      this.imgfile = event.target.files[0]
      console.log(event.target.files[0])
    },
    addtarget () {
      if (this.newTarget!=='') {
        const object = {
          name: this.newTarget,
          select: false
        }
        this.target.push(object)
        this.newTarget = ''
      }
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
      console.log(this.selecttarget)
      console.log(this.title)
      console.log(this.editorData)
      this.selecttarget = []
    }
  }
}
</script>

<style  scoped lang="scss">
@import url('https://fonts.googleapis.com/css2?family=DotGothic16&display=swap');
.ArticleContent{
  font-family: 'DotGothic16', sans-serif;
}
</style>
