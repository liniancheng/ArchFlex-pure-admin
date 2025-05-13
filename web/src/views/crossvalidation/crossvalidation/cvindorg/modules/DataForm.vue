<template>
  <a-modal
    :title="modalTitle"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleSubmit"
    @cancel="handleCancel"
    :width="1200"
    okText="保存"
    cancelText="关闭"
    :destroyOnClose="true"
  >
    <div class="tree">
      <div id="left">
        <a-card title="指标树" :headStyle="indBack">
          <div style="margin-top: -10px">
            <a-tree
              filterMultiple="true"
              :replaceFields="{ children: 'children', title: 'title', key: 'key' }"
              :blockNode="true"
              :selectable="false"
              :tree-data="dataList"
              :checkable="true"
              :checkStrictly="checkStrictly"
              @check="onCheck"
              show-icon
              :icon="getIcon"
            >
            </a-tree>
          </div>
        </a-card>
      </div>
      <div id="right" style="border-top: 1px solid #b5b9a9">
        <div class="definition"><h3>指标法人配置</h3></div>
        <a-spin :spinning="confirmLoading">
          <a-form :form="form">
            <a-form-item label="机构/法人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-select
                placeholder="请选择"
                mode="multiple"
                :showSearch="true"
                :allowClear="true"
                v-decorator="['orgVals', { rules: [{ required: true, message: '请选择机构/法人' }] }]">
                <a-select-option v-for="org in orgList" :value="org.branchNo" :key="org.branchName">
                  {{ org.branchName }}
                </a-select-option>
                <div slot="dropdownRender" slot-scope="menu">
                  <v-nodes :vnodes="menu"/>
                  <a-divider style="margin: 4px 0"/>
                  <div style="padding: 4px 8px; cursor: pointer" @mousedown="(e) => e.preventDefault()">
                    <a-button type="link" @click="selectAll">全选</a-button>
                    <a-button type="link" @click="clearAll">清空</a-button>
                  </div>
                </div>
              </a-select>
            </a-form-item>
            <a-form-item label="法人适用" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-radio-group
                name="radioGroup"
                v-decorator="['isUse', {initialValue:this.isUse, rules: [{ required: true, message: '请选择法人适用' }] }]">
                <a-radio value="Y"> 是</a-radio>
                <a-radio value="N"> 否</a-radio>
              </a-radio-group>
            </a-form-item>
            <a-form-item label="参与对比" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-radio-group
                name="radioGroup"
                v-decorator="['isShow', { initialValue:this.isShow,rules: [{ required: true, message: '请选择参与对比' }] }]">
                <a-radio value="Y"> 是</a-radio>
                <a-radio value="N"> 否</a-radio>
              </a-radio-group>
            </a-form-item>
          </a-form>
        </a-spin>
      </div>
    </div>
  </a-modal>
</template>

<script>
  import { treeNode } from '@/api/crossvalidation/cvchkind/index'
  import { fetchBranchs } from '@/api/admin/branch/index'
  import { save } from '@/api/crossvalidation/cvindorg/index'

  export default {
    components: {
      VNodes: {
        functional: true,
        render: (h, ctx) => ctx.props.vnodes
      }
    },
    data () {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 }
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 15 }
        },
        checkStrictly: true,
        dataList: [],
        oldData: [],
        firstNode: {
          checked: false,
          children: [],
          disabled: false,
          key: '-1',
          leaf: false,
          parentId: null,
          selectable: true,
          title: '全选',
          type: null,
          value: '-1'
        },
        isUse: 'Y',
        isShow: 'Y',
        // 法人
        orgList: [],
        indBack: { background: '#0097f4' },
        modalWidth: window.innerWidth * 0.5,
        visible: false,
        confirmLoading: false,
        mdl: {},
        indNoList: [],
        modalTitle: '',
        form: this.$form.createForm(this)
      }
    },
    methods: {
      // tree图标
       getIcon (props) {
            const { type } = props
            if (type === null) {
                return <svg t="1656646759612" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8913" width="32" height="32"><path d="M835.3 240.8H606.9c0-52.5-42.5-95-95-95H227c-52.4 0-95 42.5-95 95v455h760V297.4c0-31.3-25.4-56.6-56.7-56.6z" fill="#FFC41F" p-id="8914"></path><path d="M227 531.2V321.7c0-20.8 16.9-37.7 37.7-37.7h494.6c20.8 0 37.7 16.9 37.7 37.7v209.5c0 20.8-16.9 37.7-37.7 37.7H264.7c-20.9 0-37.7-16.9-37.7-37.7z" fill="#FFFFFF" p-id="8915"></path><path d="M891.9 755.9v-369c0-26.2-21.3-47.5-47.5-47.5H179.5c-26.2 0-47.5 21.3-47.5 47.5v368.8c0 32.3 26.2 58.6 58.6 58.6h642.8c32.3 0.2 58.5-26.1 58.5-58.4z" fill="#FFC41F" p-id="8916"></path><path d="M527.8 459.9l24.6 49.9c2.6 5.3 7.6 8.9 13.4 9.7l55 7.9c14.6 2.1 20.4 20 9.8 30.4l-39.9 38.7c-4.2 4.1-6.1 9.9-5.1 15.7L595 667c2.5 14.6-12.8 25.5-25.8 18.8L520 659.9c-5.2-2.7-11.4-2.7-16.6 0l-49.2 25.9c-13 6.9-28.2-4.2-25.8-18.8l9.4-54.8c0.9-5.8-0.9-11.6-5.1-15.7l-39.6-38.6c-10.5-10.3-4.7-28.2 9.8-30.4l55-7.9c5.8-0.9 10.8-4.5 13.4-9.7l24.6-49.9c6.6-13.4 25.4-13.4 31.9-0.1z" fill="#FFFFFF" p-id="8917"></path></svg>
            }
        },
      add () {
        this.modalTitle = '新增指标法人配置'
        this.visible = true
        this.loadData()
        this.loadOrgList()
      },
      // tree请求
      loadData () {
        this.loading = true
        treeNode().then((res) => {
          if (res.code === 0) {
            this.firstNode.children = res.data
            this.oldData = []
            this.oldData.push(this.firstNode)
            this.dataList = this.exchangeNodes(this.oldData)
          } else {
            this.$message.error(res.message)
          }
        }).finally(() => {
          this.loading = false
        })
      },
      exchangeNodes (nodes) {
        if (nodes && nodes.length > 0) {
          return nodes.map((item) => {
            const node = { ...item, scopedSlots: { title: 'indNm' }, children: this.exchangeNodes(item.children) }
            return node
          })
        } else {
          return null
        }
      },
      // 法人
      loadOrgList () {
        fetchBranchs().then((res) => {
          if (res.code === 0) {
            this.orgList = res.data
          } else {
            this.$message.error(res.message)
          }
        }).finally(() => {
          this.loading = false
        })
      },
      // 清空 -- 清空select绑定的值
      clearAll () {
        this.form.setFieldsValue({
          orgVals: []
        })
      },
      // 全选 -- 把数据源赋给select绑定的值
      selectAll () {
        const arr = []
        this.orgList.forEach((item) => {
          arr.push(item.branchNo)
        })
        this.form.setFieldsValue({
          orgVals: arr
        })
      },
      onCheck (checkedKeys, e) {
        if (checkedKeys.length === 0) {
          this.checkStrictly = true
        }
        this.mdl.indNos = checkedKeys.checked
        if (checkedKeys.checked.includes(this.firstNode.key)) {
          this.checkStrictly = false
          this.getIndNos(this.dataList)
          this.mdl.indNos = this.indNoList
        }
      },
      getIndNos (datas) {
        if (datas !== null) {
          datas.forEach(item => {
            if (item.children !== null) {
              item.children.forEach(child => {
                this.indNoList.push(child.key)
              })
              this.getIndNos(item.children)
            } else {
              return null
            }
          })
        }
      },
      handleSubmit () {
        const { form: { validateFields } } = this
        this.confirmLoading = true
        validateFields((errors, values) => {
          if (!errors) {
            save(Object.assign({ ...this.mdl, ...values })).then(res => {
              this.handleResponse(res)
            })
          } else {
            this.confirmLoading = false
          }
        })
      },
      handleResponse (res) {
        this.confirmLoading = false
        if (res.data) {
          this.form.resetFields()
          this.$message.success(res.message)
          this.visible = false
          this.$emit('ok')
        } else {
          this.$message.error(res.message)
        }
      },
      handleCancel () {
        this.visible = false
        this.form.resetFields()
        this.confirmLoading = false
      }
    }
  }
</script>
<style scoped>
/* tree图标 */
.icon{
  width:25px;
  height: 24px;
  margin-right: 5px;
}
  #box {
    width: 100%;
    height: 400px;
    position: relative;
    overflow: hidden;
  }

  .top {
    width: 100%;
    height: 80px;
    background: #ffe0c6;
  }

  #left {
    width: calc(30% - 5px);
    height: 100%;
    float: left;
    overflow: auto;
  }

  #resize {
    position: relative;
    width: 5px;
    height: 100%;
    cursor: w-resize;
    float: left;
  }

  #right {
    width: 70%;
    height: 100%;
    float: left;
    overflow: hidden;
    padding-right: 10px;
  }

  .definition {
    width: 100%;
    height: 58px;
    background: #0097f4;
    line-height: 58px;
    padding-left: 30px;
  }

  .tree {
    height: 400px;
  }
</style>
