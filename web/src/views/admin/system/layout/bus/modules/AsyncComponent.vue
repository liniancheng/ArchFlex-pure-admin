<template>
  <component :is="loadComponent" :dataSource="dataSource" />
</template>
<script>

export default {
  name: 'AsyncComponent',
  props: {
    path: { type: String, required: true },
    // eslint-disable-next-line
    dataSource: { required: false }
  },
  data () {
    return {}
  },
  computed: {
    loadComponent () {
      return () => import(`@/${this.path}`)
    }
  }
}
</script>
