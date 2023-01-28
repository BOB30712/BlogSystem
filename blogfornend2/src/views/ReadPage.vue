<template>
  <router-view/>
  <targetlist/>
</template>

<script>
import targetlist from '@/components/TargetList.vue'
export default {
  components: {
    targetlist
  }
}
</script>
