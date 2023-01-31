<template>
  <managenavbar/>
  <router-view/>
</template>

<script>
import managenavbar from '@/components/ManageNavBar.vue'
export default {
  components: {
    managenavbar
  }
}
</script>
