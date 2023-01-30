<template>
  <navbar/>
  <router-view/>
  <targetlist/>
</template>

<script>
import targetlist from '@/components/TargetList.vue'
import navbar from '@/components/NavBar.vue'
export default {
  components: {
    targetlist,
    navbar
  }
}
</script>
