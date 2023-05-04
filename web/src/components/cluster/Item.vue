<template>
  <div class="ItemTop">
    <a-page-header
      title="Cluster Data"
      sub-title="Node Path List"
      @back="() => $router.go(-1)"
    >
      <template #extra>
        <a-button onclick="javascript:location.reload();">
          <template #icon><UndoOutlined /></template>
        </a-button>
        
        
        <a-button type="primary" @click="showAddChildNodeModal">Add Child Node</a-button>
        <AddChildNodeModal :visible="addChildNodeModalVisible" @handle-cancel="hideAddChildNodeModal"></AddChildNodeModal>
      </template>
      <a-descriptions size="small" :column="2">
        <a-descriptions-item label="Cluster Id" size="middle">
          {{ id }}
        </a-descriptions-item>
        <a-descriptions-item label="Cluster name" size="middle">
          {{name}}
        </a-descriptions-item>
        <a-descriptions-item label="Cluster Status" size="middle">
          <span v-if="state.status">
            <a-tag color="#2db7f5">Connect Success</a-tag>
          </span>
          <span v-else>
            <a-tag color="#f50">Connect Failure</a-tag>
          </span>
        </a-descriptions-item>
        <a-descriptions-item label="Node Path">
          <span v-if="state.dataPaths?.length == 0">
           /
          </span>
          <span v-else>
            <a @click="loadChildNode('/')">/</a>
            <a-divider type="vertical" />
          </span>

          <span v-for="(dataPath, index) in state.dataPaths">
            <span v-if="state.dataPaths?.length -1 == index">
              {{dataPath}}
            </span>
            <span v-else>
              <a @click="loadChildNode(dataPath)">{{dataPath}} </a>
              <a-divider type="vertical" />
            </span>
          </span>
        </a-descriptions-item>
        <a-descriptions-item label="Node Data" size="middle">
          <a-button type="primary" size="small" @click="showLoadNodeDataModal()"> View Data </a-button>
        </a-descriptions-item>
      </a-descriptions>
    </a-page-header>
  </div>
  <div class="itemContent">
    <a-table :dataSource="state.dataSource" :columns="columns" :pagination="false">
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'action'">
          <a @click="showLoadNodeDataModal(record.path)">View Data</a>
          <a-divider type="vertical" />
          <a>Delete</a>
        </template>

        <template v-if="column.key === 'path'">
          <a @click="viewChildNode(record.path)">{{ record.path }}</a>
        </template>
      </template>
    </a-table>
  </div>

  <LoadNodeData :visible="visible" :nodeData="nodeData" :nodePath="nodePath" :nodeStat="nodeStat" @handle-cancel="hideLoadNodeDataModal"/>

</template>

<script setup>
  import { UndoOutlined } from '@ant-design/icons-vue'
  import { onMounted, reactive,ref } from 'vue'
  import { useRoute } from 'vue-router'
  import { clusterDataList, clusterDataItem } from '@/request/api/clusterData'
  import { message } from 'ant-design-vue'
  import LoadNodeData from './modal/LoadNodeData.vue'
  import AddChildNodeModal from './modal/AddChildNode.vue'


  const addChildNodeModalVisible = ref(false)
  const showAddChildNodeModal = () => {
    addChildNodeModalVisible.value = true
  }

  const hideAddChildNodeModal = () => {
    addChildNodeModalVisible.value = false
  }



  const route = useRoute()
  const id = route.params.id
  const name = route.query.name
  const nodePath = ref('')
  const nodeData = ref('')
  const nodeStat = reactive({})

  const state = reactive({
    "dataSource":[
      
    ],
    "dataPaths":[
      
    ],
    'status':false,
    'path':''
  }) 

  const columns = reactive(
   [
    {
      title: 'Path',
      dataIndex:'path',
      key: 'path'
    },
    {
      title: 'Action',
      dataIndex: 'action',
      key: 'action',
    }
  ])

  const viewChildNode = (text) => {
    state.dataPaths.push(text)
    clusterDataListApi()
  }

  const loadChildNode = (path)=>{
    if (path == '/') {
      state.dataPaths = []
    } else {
      let p = ''
      while(p = state.dataPaths.pop()) {
        if (p==path) {
          state.dataPaths.push(p)
          break;
        }
      }
    }
    clusterDataListApi()
  }

  const clusterDataListApi = async () => {
    let params = {
      id:id,
      path: state.dataPaths.length == 0 ? '/' : '/' + state.dataPaths.join('/')
    }
    let res = await clusterDataList(params)
    if (res.data.code != 0) {
      state.status = false
      message.error(res.data.message)
      return 
    }
    state.status = true
    state.path = res.data.data.path
    state.dataSource = doList(res.data.data.list)
    state.dataPaths = res.data.data.paths
  }

  const doList = (list) => {
      let listObj = []
      for (const i in list) {
        listObj.push({'path':list[i]})
      }
      console.log(listObj);
      return listObj
  }

  onMounted(()=>{
    clusterDataListApi()
  })

  const visible = ref(false)

  const showLoadNodeDataModal = async (path)=>{
    nodePath.value = path ? ( state.path=='/' ? '/' + path : state.path + '/' + path) : state.path
    let params = {
      id:id,
      path:nodePath.value
    }
    let res = await clusterDataItem(params)
    nodePath.value = res.data.data.path
    nodeData.value = res.data.data.data
    nodeStat.value = res.data.data.stat
    console.log(nodeStat)
    visible.value = true
  }

  const hideLoadNodeDataModal = () => {
    visible.value = false
  }
</script>

<style lang="less" scoped>


</style>