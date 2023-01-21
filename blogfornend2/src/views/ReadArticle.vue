<template>
  <h1>這是一個ReadArticle頁面</h1>
  <h1>目前的id: {{id}}</h1>
</template>

<script>
export default {
  data () {
    return {
      id: ''
    }
  },
  methods: {

  },
  mounted () {
    console.log('Param', this.$route.params.id)
    this.id = this.$route.params.id
  }
}
</script>
