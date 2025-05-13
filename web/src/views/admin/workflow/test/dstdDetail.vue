<template>
  <div>
    <p>
      版本编号：{{ versionCode }}
    </p>
    <p>
      标准名称：{{ dstdName }}
    </p>
  </div>
</template>
<script>
// import { getWorkflowDetail } from '@/api/dgmp/dstd/version/index'

export default {
  props: {
    instanceId: {
      type: String,
      required: true,
      default: ''
    }
  },
  mounted () {
    console.log(this)
    this.handleGetDetail()
  },
  data () {
    return {
       dstdName: '',
       versionCode: ''
    }
  },
  methods: {
    handleGetDetail () {
      // getWorkflowDetail(this.instanceId).then(res => {
      //   if (res.code === 0) {
      //     console.log(res)
      //     this.dstdName = res.data.dstdName
      //     this.versionCode = res.data.versionCode
      //   }
      // })
    }
  }
}
</script>
