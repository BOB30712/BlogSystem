<template>
  <!-- Modal -->
  <div class="modal fade text-dark" id="myModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          {{data.title}}
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary">Save changes</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Modal } from 'bootstrap'
export default {
  data () {
    return {
      modal: {}
    }
  },
  props: {
    data: {
      type: [Object, Array]
    }
  },
  watch: {
    data () {
      console.log(this.data)
      if (this.data.aid != null) {
        console.log('這是物件')
      } else {
        console.log('這是陣列')
      }
    }
  },
  methods: {
    show () {
      this.modal.show()
    }
  },
  mounted () {
    this.modal = new Modal(document.getElementById('myModal'))
  }
}
</script>
