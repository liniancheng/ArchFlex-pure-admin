<template>
  <a-modal
    :title="modalTitle"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel"
    :footer="null"
    :width="800"

  >
    <a-spin :spinning="confirmLoading">
      <a-transfer
        :listStyle="listStyle"
        :data-source="listRole"
        :titles="['角色列表', '授权列表']"
        :target-keys="targetKeys"
        :selected-keys="selectedKeys"
        :render="item => item.title"
        @change="handleChange"
        @selectChange="handleSelectChange"
      />
    </a-spin>
  </a-modal>
</template>
<script>
import { listRel, saveItemRel, deleteItemRel } from '@/api/admin/layout/layoutItem/index'
import { Transfer } from 'ant-design-vue'
export default {
  components: {
    Transfer
  },
  data () {
    return {
      listStyle: { width: '350px', maxHeight: '400px', minHeight: '400px' },
      visible: false,
      confirmLoading: false,
      modalTitle: '',
      itemId: null,
      listRole: [],
      targetKeys: [],
      selectedKeys: []
    }
  },
  methods: {
    show (id, name) {
      this.visible = true
      this.itemId = id
      this.modalTitle = '【' + name + '】 授权'
      this.confirmLoading = true
      this.hanleListRole(id)
    },
    hanleListRole (id) {
      listRel(id).then(res => {
        if (res.code === 0) {
          this.listRole = res.data
          this.targetKeys = res.data.filter(item => item.checked).map(item => item.key)
        }
        this.confirmLoading = false
      })
    },
    handleChange (nextTargetKeys, direction, moveKeys) {
      const params = { itemId: this.itemId, roleIds: moveKeys.join(',') }
      if (direction === 'left') { // 删除
        deleteItemRel(params).then(res => {
          if (res.code === 0) {
            this.targetKeys = nextTargetKeys
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
        })
      } else { // 新增
        saveItemRel(params).then(res => {
          if (res.code === 0) {
            this.targetKeys = nextTargetKeys
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
        })
      }
    },
    handleSelectChange (sourceSelectedKeys, targetSelectedKeys) {
      this.selectedKeys = [...sourceSelectedKeys, ...targetSelectedKeys]
    },
    handleCancel () {
      this.visible = false
      this.confirmLoading = false
    }
  }
}
</script>
