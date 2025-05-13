<template>
  <a-result :status="status" title="激活结果" :sub-title="message" />
</template>
<script>
import { activePassword } from '@/api/login'

export default {
  data () {
    return {
      message: null,
      status: null
    }
  },
  mounted () {
    this.activePassword()
  },
  methods: {
    activePassword () {
      activePassword(this.$route.params.code)
        .then(res => {
          this.status = (res.code === 0) ? 'success' : '403'
          if (res.code === 0) {
            this.message = '新密码已激活'
          } else {
            this.message = res.message
          }
        })
        .catch(err => {
          this.message = err
        })
    }
  }
}
</script>
