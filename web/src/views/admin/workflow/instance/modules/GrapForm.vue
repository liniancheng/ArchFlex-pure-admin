<template>
  <a-modal
    :title="modalTitle"
    :width="1040"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel"
    :footer="null"
  >
    <a-spin :spinning="confirmLoading">
      <div id="container" style="width:1000px;height:300px;overflow:auto;">
      </div>
    </a-spin>
  </a-modal>
</template>

<script>
import { graph } from '@/api/admin/workflow/instance/graph'
import mxgraph from '@/utils/mxgraph/index'
const { mxGraph, mxClient, mxUtils, mxConstants, mxHierarchicalLayout } = mxgraph

const colorMap = {
  done: '#72CC4A;', // 完成 - 绿
  ding: '#1A91FF;', // 进行中 - 蓝
  back: '#d95e5e;', // 不通过 - 红
  undo: '#C9C9C9;' // 未开始 - 灰
}

export default {
  data () {
    return {
      visible: false,
      confirmLoading: false,
      modalTitle: ''
    }
  },
  methods: {
    handleShow (instanceId, instanceName) {
      this.visible = true
      this.modalTitle = instanceName + ' - 流程进度'
      this.loadData(instanceId)
    },
    /* eslint-disable new-cap */
    loadData (instanceId) {
      this.confirmLoading = true
      graph(instanceId).then(res => {
          if (res.code === 0) {
            this.loadGraph(res.data)
          } else {
            this.$message.error(res.message)
          }
          this.confirmLoading = false
        })
    },
    getStringLength (str) {
      var len = 0
      for (var i = 0; i < str.length; i++) {
        if (str.charCodeAt(i) > 127 || str.charCodeAt(i) === 94) {
             len += 2
         } else {
             len++
         }
      }
      return len
    },
    loadGraph (datas) {
      if (!mxClient.isBrowserSupported()) {
        mxUtils.error('Browser is not supported!', 200, false)
      } else {
        const container = document.getElementById('container')
        container.innerHTML = ''
        var mxgraph = new mxGraph(container)
        mxgraph.setEnabled(false)
        var vertexStyle = 'dashed=0;align=center;fontSize=12;shape=rect;strokeColor=#006EAF;fontStyle=0;shadow=1;fontColor=#ffffff;rounded=1;fillColor='
        var edgeStyle = 'rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitDx=0;exitDy=0;entryDx=0;entryDy=0;strokeColor=#A3A3A3;orthogonal=1;edgeStyle=orthogonalEdgeStyle;curved=1;'
        mxgraph.gridSize = 40
        var parent = mxgraph.getDefaultParent()
        var layout = new mxHierarchicalLayout(mxgraph, mxConstants.DIRECTION_WEST)
        layout.forceConstant = 80
        mxgraph.getModel().beginUpdate()
        var w = 120
        var h = 50
        try {
              const vertexIdArray = []
              const vertexObjArray = []
              for (let i = 0; i < datas.vertexs.length; i++) {
                const data = datas.vertexs[i]
                let width = w
                const nameLen = this.getStringLength(data.vertexName)
                if (nameLen > 12) {
                    width = w + (nameLen - 12) * 10
                }
                let style = vertexStyle
                style += colorMap[data.vertexStatus]
                // mxGraph.insertVertex(parent, id, value, x, y, width, height, style)
                const obj = mxgraph.insertVertex(parent, data.vertexId, data.vertexName, 0, 0, width, h, style)
                vertexIdArray[i] = data.vertexId
                vertexObjArray[i] = obj
              }
              for (let i = 0; i < datas.edges.length; i++) {
                const data = datas.edges[i]

                // mxGraph.insertEdge(parent, id, value, source, target, style)
                let source = null
                let target = null
                for (let j = 0; j < vertexIdArray.length; j++) {
                    if (vertexIdArray[j] === data.sourceVertexId) {
                        source = vertexObjArray[j]
                    }
                    if (vertexIdArray[j] === data.targetVertexId) {
                        target = vertexObjArray[j]
                    }
                }
                if (source !== null && target !== null) {
                  mxgraph.insertEdge(parent, null, data.title, source, target, edgeStyle)
                }
              }
          layout.execute(parent)
        } finally {
          mxgraph.getModel().endUpdate()
        }
      }
    },
    handleCancel () {
      this.visible = false
    }
  }
}
</script>
