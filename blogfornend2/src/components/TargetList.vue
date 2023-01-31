<template>
  <h1>標籤</h1>
  <div class="d-flex flex-wrap mb-5" >
    <template v-for="(item,index) in targetlist" :key="item.tid">
      <router-link class="text-decoration-none link-info fs-5"
      :to="{ name: 'readpage/articletarget', params: { id: item.tid, tname: item.tname} }"
      v-if="index+1!=targetlist.length">
        {{ item.tname }}/
      </router-link>
      <router-link class="text-decoration-none link-info fs-5"
      :to="{ name: 'readpage/articletarget', params: { id: item.tid, tname: item.tname} }"
      v-else>
        {{ item.tname }}
      </router-link>
    </template>
  </div>
</template>

<script>
export default {
  data () {
    return {
      targetlist: [
        {
          tid: 4,
          tname: '怎麼會這樣'
        },
        {
          tid: 5,
          tname: '窩不知道'
        }
      ]
    }
  },
  methods: {
    getalltarget () {
      this.axios({
        method: 'get',
        url: 'http://localhost:8080/Target/get/all'
      })
        .then((response) => {
          console.log(response)
          this.targetlist = response.data
        })
        .catch((error) => console.log(error))
    }
  },
  mounted () {
    const date = new Date()
    console.log(date)
    this.getalltarget()
  }
}
</script>
