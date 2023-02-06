<template>
  <h1>網誌存檔</h1>
  <template v-for="(it,index) in currentyearlist" :key="index">
    <div class="d-flex">
      <h1>
        <i class="bi bi-caret-right-fill text-white" data-bs-toggle="collapse" :data-bs-target="'#year'+it.year" aria-expanded="false" :aria-controls="'year'+it.year"></i>
        {{ it.year }}
      </h1>
    </div>
    <ul class="list-unstyled collapse ms-3" :id="'year'+it.year">
      <template v-for="(i,indexx) in it.monlist" :key="indexx">
        <li>
          <i @click.prevent="findarticlelist(it.year,index,indexx)" class="bi bi-caret-right-fill text-white me-2" data-bs-toggle="collapse" :data-bs-target="'#year'+it.year+'month'+indexx" aria-expanded="false" :aria-controls="'year'+it.year+'month'+indexx"></i>{{ i.month }}
          <ul class="list-unstyled collapse ms-3" :id="'year'+it.year+'month'+indexx">
            <template v-for="(article,indexxx) in i.artticle" :key="indexxx">
              <li><i class="bi bi-caret-right-fill text-white me-2"></i>
                <router-link class="text-decoration-none link-info fs-5"
                  :to="{ name: 'readpage/articleread', params: { id: article.aid} }">
                  {{ article.title }}
                </router-link>
              </li>
            </template>
          </ul>
        </li>
      </template>
    </ul>
  </template>
</template>

<script>
export default {
  data () {
    return {
      startyear: 2023,
      isccaret: true,
      articlelist: [
        '2023/02/06',
        '2023/02/08',
        '2023/02/10'
      ],
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
    getmonthlist (endyear, endmonth) {
      this.currentyearlist.forEach(
        (element) => {
          element.monlist = []
          if (element.year !== endyear) {
            for (let i = 0; i < 12; i++) {
              const obj = {
                month: this.filters.MonthString(i + 1),
                getdata: true
              }
              element.monlist.push(obj)
            }
          } else {
            for (let i = 0; i < endmonth; i++) {
              const obj = {
                month: this.filters.MonthString(i + 1),
                getdata: true
              }
              element.monlist.push(obj)
            }
          }
        }
      )
    },
    findarticlelist (year, yindex, mindex) { // 取得的是陣列的位置
      console.log('文章列表', yindex, mindex + 1)
      this.currentyearlist[yindex].monlist[mindex].artticle = []
      if (this.currentyearlist[yindex].monlist[mindex].getdata) {
        console.log('送出請求')
        this.axios({
          method: 'get',
          url: 'http://localhost:8080/article/getbymonth/' + year + '/' + (mindex + 1)
        })
          .then((response) => {
            response.data.forEach((element) => {
              const obj = {
                aid: element.aid,
                title: element.title
              }
              this.currentyearlist[yindex].monlist[mindex].artticle.push(obj)
            })
          })
      } else {
        console.log('不送出請求')
      }
      this.currentyearlist[yindex].monlist[mindex].getdata = !this.currentyearlist[yindex].monlist[mindex].getdata
    }
  },
  mounted () {
    const d = new Date()
    console.log(d)
    console.log(d.getFullYear())
    console.log(d.getMonth() + 1)
    console.log(this.getyearlist(2024))
    console.log(this.getyearlist(d.getFullYear()))
    this.currentyearlist = this.getyearlist(d.getFullYear())
    this.getmonthlist(d.getFullYear(), d.getMonth() + 1)
  }
}
</script>
