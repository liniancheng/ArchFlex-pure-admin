<template>
  <a-config-provider :locale="locale">
    <div id="app">
      <router-view />
    </div>
  </a-config-provider>
</template>

<script>
import { domTitle, setDocumentTitle } from '@/utils/domUtil'
import { updateTheme } from '@/components/SettingDrawer/settingConfig'

export default {
  data () {
    return {
      theme: localStorage.getItem('theme')
    }
  },
  watch: {
    theme: function (newVal) {
      this.theme = this.$root.theme
    }
  },
  mounted () {
    if (localStorage.getItem('theme') === null) {
      this.theme = 'blue'
      this.$root.theme = this.theme
    } else {
      this.theme = this.$root.theme = localStorage.getItem('theme')
    }
    var color = ''
    if (this.theme === 'green') {
      color = '#35af54'
    } else if (this.theme === 'blue') {
      color = '#0097f4'
    } else if (this.theme === 'yellow') {
      color = '#ef9e1d'
    } else if (this.theme === 'purple') {
      color = '#a47af4'
    }
    updateTheme(color)
  },
  computed: {
    codeMap () {
      return this.$store.getters.config
    },
    locale () {
      // 只是为了切换语言时，更新标题
      const title = this.codeMap['system.title'] || domTitle
      setDocumentTitle(`${title}`)
      return this.$i18n.getLocaleMessage(this.$store.getters.lang).antLocale
    }
  }
}
</script>
