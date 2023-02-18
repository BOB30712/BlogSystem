<template>
  <div class="container">
    <table class="table text-light">
      <thead>
        <tr class="table-dark">
          <th scope="col">#</th>
          <th scope="col">匿名</th>
          <th scope="col">內容</th>
          <th scope="col">時間</th>
          <th scope="col">回覆</th>
          <th scope="col">刪除</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <th scope="row">1</th>
          <td>測試1</td>
          <td>123456</td>
          <td>123456</td>
          <td v-if="select=='a'"><button  @click="chose(a)" type="button" class="btn btn btn-danger" data-bs-toggle="collapse" data-bs-target="#collapsewrite" >回覆中</button></td>
          <td v-if="select!='a'"><button @click="chose(a)" class="btn btn-outline-primary" data-bs-toggle="collapse" data-bs-target="#collapsewrite">回覆</button></td>
          <td><button type="button" class="btn btn-outline-danger">刪</button></td>
        </tr>
        <tr>
          <th scope="row">2</th>
          <td>測試2</td>
          <td>123456</td>
          <td>123456</td>
          <td v-if="select=='b'"><button @click="b=>chose(b)" type="button" class="btn btn btn-danger" data-bs-toggle="collapse" data-bs-target="#collapsewrite">回覆中</button></td>
          <td v-if="select!='b'"><button @click="chose(b)" class="btn btn-outline-primary" data-bs-toggle="collapse" data-bs-target="#collapsewrite">回覆</button></td>
          <td><button type="button" class="btn btn-outline-danger">刪</button></td>
        </tr>
      </tbody>
    </table>
    <div class="collapse" id="collapsewrite">
    <div class="text-light input-group">
      <input type="text" class="form-control rounded-0 bg-dark text-white border-light" aria-describedby="button-sendout">
      <button class="btn btn-outline-light" type="button" id="button-sendout">送出</button>
    </div>
  </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      a: 'a',
      b: 'b',
      select: ''
    }
  },
  methods: {
    chose (str) {
      if (this.select !== str) {
        this.select = str
      } else {
        this.select = ''
      }
    }
  }
}
</script>
