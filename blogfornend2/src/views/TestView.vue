<template>
<button @click.prevent="open" type="button" class="btn btn-primary">Show live toast</button>
<div class="toast-container position-fixed bottom-0 end-0">
  <div id="liveToast" class="liveToast toast align-items-center" role="alert" aria-live="assertive" aria-atomic="true">
    <div class="d-flex">
      <div class="toast-body">
      Hello, world! This is a toast message.
    </div>
      <button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
    </div>
  </div>

  <div id="liveToast" class="liveToast toast align-items-center" role="alert" aria-live="assertive" aria-atomic="true">
    <div class="d-flex">
      <div class="toast-body">
      Hello, world! This is a toast message.
    </div>
      <button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
    </div>
  </div>

  <div id="liveToast" class="liveToast toast align-items-center" role="alert" aria-live="assertive" aria-atomic="true">
    <div class="d-flex">
      <div class="toast-body">
      Hello, world! This is a toast message.
    </div>
      <button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
    </div>
  </div>
</div>
</template>

<script>
import { Toast } from 'bootstrap'
export default {
  methods: {
    data () {
      return {
        taost: ''
      }
    },
    open () {
      console.log('test')
      this.taost.show()
    }
  },
  mounted () {
    /*
    const toastlive = document.getElementById('liveToast')
    this.taost = new Toast(toastlive)
    const taost1 = new Toast(document.getElementById('liveToast2'))
    const taost2 = new Toast(document.getElementById('liveToast3'))
    taost1.show()
    taost2.show()
    */
    Array.from(document.querySelectorAll('.liveToast'))
      .forEach((toastNode) => {
        const t = new Toast(toastNode)
        t.show()
      })
  }
}
</script>
