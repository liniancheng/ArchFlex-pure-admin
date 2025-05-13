<template>
  <a-modal
    :title="modalTitle"
    :visible="visible"
    width="800px"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel"
    :footer="null"
  >
    <a-spin :spinning="confirmLoading">
      <div>
        <div class="table-operator">
          <a-button type="primary" icon="plus" @click="handleAdd">新增</a-button>
          <a-button icon="sync" @click="loadData">刷新</a-button>
        </div>
        <a-table
          ref="table"
          size="default"
          rowKey="dirId"
          :pagination="false"
          :columns="columns"
          :data-source="data"
          :loading="loading"
        >
          <span slot="action" slot-scope="text, record">
            <template>
              <a class="table_blue" @click="$refs.createModal.add(record)">【新增】</a>
              <a class="table_blue" @click="handleEdit(record)">【修改】</a>
              <a-popconfirm title="确定要删除?" @confirm="() => handleDelete(record.dirId)">
                <a class="table_orange">【删除】</a>
              </a-popconfirm>
            </template>
          </span>
        </a-table>
        <dir-data-form ref="createModal" @ok="handleOk" />
      </div>
    </a-spin>
  </a-modal>
</template>

<script>
import DirDataForm from './DirDataForm'
import { getdirs, removeById } from '@/api/admin/dir/index'
export default {
  components: {
    DirDataForm
  },
  data () {
    return {
       authBtns: {
        insert: this.checkButtonAuth('/admin/knowledge/dir:insert'),
        update: this.checkButtonAuth('/admin/knowledge/dir:update'),
        delete: this.checkButtonAuth('/admin/knowledge/dir:delete'),
        select: this.checkButtonAuth('/admin/knowledge/dir:select')
      },
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 15 }
      },
      data: [],
      visible: false,
      confirmLoading: false,
      checkStrictly: false,
      mdl: {},
      columns: [
        {
          key: 'dirName',
          title: '分类名称',
          dataIndex: 'dirName'
        },
        {
          key: 'dirOrder',
          title: '分类排序',
          dataIndex: 'dirOrder'
        },
        {
          key: 'dirRmk',
          title: '分类描述',
          dataIndex: 'dirRmk'
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: 250,
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      modalTitle: '',
      loading: false,
      form: this.$form.createForm(this)
    }
  },
  mounted () {
    this.loadData()
  },
  methods: {
    add (record) {
      this.modalTitle = '新增 - 知识类型'
      this.visible = true
    },
    handleEdit (record) {
      this.$refs.createModal.edit(record)
    },
    handleDelete (id) {
      removeById(id).then(res => {
        if (res.code === 0) {
          // this.$message.success(res.message)
          this.$message.success('删除成功!')
          this.handleOk()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    loadData () {
      this.loading = true
      getdirs()
        .then(res => {
          this.data = res.data
        })
        .finally(() => {
          this.loading = false
        })
    },
    handleAdd () {
      this.$refs.createModal.addParent()
    },
     handleOk () {
      this.loadData()
      this.$emit('clearSelected')
    },
    handleResponse (res) {
      this.confirmLoading = false
      if (res.code === 0) {
        this.$message.success('操作成功！')
        this.visible = false
        this.$emit('ok')
        this.$emit('clearSelected')
      } else {
        this.$message.error(res.message)
      }
    },
    handleCancel () {
      this.visible = false
      this.form.resetFields()
      this.$emit('clearSelected')
      this.confirmLoading = false
    }
  }
}
</script>
