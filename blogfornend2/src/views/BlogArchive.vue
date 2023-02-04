<template>
  <p><i class="bi bi-caret-right-fill"></i></p>
  <p><i class="bi bi-caret-down-fill"></i></p>
  <template v-for="(it,index) in currentyearlist" :key="index">
    <h1>{{ it.year }}</h1>
    <template v-for="(i,index) in it.monlist" :key="index">
      <h3>{{ i }}</h3>
    </template>
  </template>
  <h1>網誌存檔</h1>
  <ul class="list-unstyled">
    <li>This is a list.</li>
    <li>However, this style only applies to immediate child elements.</li>
    <li>
      <div class="d-flex">
        <i class="bi bi-caret-right-fill text-white me-2" data-bs-toggle="collapse" data-bs-target="#collapsetest" aria-expanded="false" aria-controls="collapsetest"></i>
        <router-link class="nav-link" to="/">2023</router-link>
      </div>
      <ul class="list-unstyled collapse ms-3" id="collapsetest">
        <li><i class="bi bi-caret-right-fill text-white me-2"></i>一月</li>
        <li><i class="bi bi-caret-right-fill text-white me-2"></i>二月</li>
        <li><i class="bi bi-caret-right-fill text-white me-2"></i>三月</li>
      </ul>
    </li>
  </ul>
</template>

<script>
export default {
  data () {
    return {
      startyear: 2023,
      currentyearlist: []
    }
  },
  methods: {
    getyearlist (endyear) {
      const range = endyear - this.startyear
      const yearlist = []
      for (let i = 0; i <= range; i++) {
        const obj = {
          year: this.startyear + i
        }
        yearlist.push(obj)
      }
      return yearlist
    },
    getmonthlist (endyear) {
      this.currentyearlist.forEach(
        (element) => {
          if (element.year !== endyear) {
            element.monlist = [
              '一月',
              '二月',
              '三月',
              '四月',
              '五月',
              '六月',
              '七月',
              '八月',
              '九月',
              '十月',
              '十二月'
            ]
          }
        }
      )
    }
  },
  mounted () {
    const d = new Date()
    console.log(d)
    console.log(d.getFullYear())
    console.log(d.getMonth() + 1)
    console.log(this.getyearlist(2024))
    console.log(this.getyearlist(d.getFullYear()))
    this.currentyearlist = this.getyearlist(2033)
    this.getmonthlist(2033)
  }
}
</script>
