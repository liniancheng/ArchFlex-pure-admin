<template>
  <div>
    实例ID：{{ instanceId }}
  </div>
</template>
<script>
export default {
  props: {
    instanceId: {
      type: String,
      required: true,
      default: ''
    }
  }
}
</script>
