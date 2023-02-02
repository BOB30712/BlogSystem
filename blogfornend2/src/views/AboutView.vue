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
    <router-view/>
  </div>
</template>

<script>
export default {
  data () {
    return {
      num: 0,
      obj: {
        aid: 7414,
        inner: {}
      },
      Targets: [
        0
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
    console.log(this.num)
    console.log(this.obj.aid)
    console.log(this.obj.inner.nodata)
    this.num++
  }
}
</script>
