<template>
  <div :class="prefixCls">
    <div ref="editor" class="editor-wrapper"></div>
  </div>
</template>

<script>
import WEditor from 'wangeditor'

export default {
  name: 'WangEditor',
  props: {
    prefixCls: {
      type: String,
      default: 'ant-editor-wang'
    },
    // eslint-disable-next-line
    value: {
      type: String
    }
  },
  data () {
    return {
      editor: null,
      editorContent: null
    }
  },
  watch: {
    value (val) {
      if (this.editorContent !== val) {
        this.editor.txt.html(val)
        this.editorContent = val
      }
    }
  },
  mounted () {
    this.initEditor()
    this.editor.txt.html(this.value)
  },
  methods: {
    initEditor () {
      this.editor = new WEditor(this.$refs.editor)
      this.editor.config.showLinkImg = true
      this.editor.config.dragdrop = true
      this.editor.config.menus = [
          'head', // 标题
          'bold', // 粗体
          'fontSize', // 字号
          'fontName', // 字体
          'italic', // 斜体
          'underline', // 下划线
          'strikeThrough', // 删除线
          'foreColor', // 文字颜色
          'backColor', // 背景颜色
          'list', // 列表
          'justify', // 对齐方式
          'quote', // 引用
          'image', // 插入图片
          'table', // 表格
          'emoticon', // 表情
          'code', // 插入代码
          'undo', // 撤销
          'redo' // 重复
        ]
      this.editor.config.uploadImgMaxSize = 1 * 1024 * 1024
      this.editor.config.uploadImgShowBase64 = true // 使用 base64 保存图片
      this.editor.config.onchange = html => {
        this.editorContent = html
        this.$emit('change', this.editorContent)
      }
      this.editor.create()
    }
  }
}
</script>

<style lang="less" scoped>
.ant-editor-wang {
  padding: 0px;
  .editor-wrapper {
    text-align: left;
  }
}
</style>
