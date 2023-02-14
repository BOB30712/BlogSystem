<template>
  <div class="about">
    <h1>This is an about page</h1>
    <template v-for="(i,index) in TargetList" :key="index">
      <div class="form-check me-2">
        <input v-model="Targets" class="form-check-input" type="checkbox" :value="i.tid" :id="i.tid">
        <label class="form-check-label" :for="i.tname">
          {{ i.tname }}
        </label>
      </div>
    </template>
    <button @click="enter">按鈕</button>
    <p v-if="image==''">今天天氣真好</p>
    <p v-else>但是我忘記帶雨傘出門</p>
    <button @click="image=1">chamge</button>
    <button v-if="image==''" @click="image=1">123</button>
    <button v-else @click="image=1">456</button>
    <router-view/>
  </div>
</template>

<script>
export default {
  data () {
    return {
      num: 0,
      image: '',
      obj: {
        aid: 7414,
        inner: {}
      },
      Targets: [
        0,
        2,
        3
      ],
      TargetList: [
        {
          tid: 0,
          tname: '第一標籤'
        },
        {
          tid: 1,
          tname: '第二標籤'
        },
        {
          tid: 2,
          tname: '第三標籤'
        },
        {
          tid: 3,
          tname: '第四標籤'
        }
      ]
    }
  },
  methods: {
    enter () {
      // console.log(this.TargetList)
      console.log(this.Targets)
    }
  },
  mounted () {
    // console.log(this.num)5*60*1000
    // console.log(this.obj.aid)
    // console.log(this.obj.inner.nodata)
    console.log('image', this.image.num)
    this.num++
    window.setInterval(
      () => {
        console.log('每五秒要做的事')
      }, 5000
    )
  }
}
</script>
