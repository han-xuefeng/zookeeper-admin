<template>
  <div class="listTop">
    <a-row type="flex">
      <a-col :flex="2">
        <h1>Cluster</h1>
      </a-col>
      <a-col :flex="2" class="topRight">
        <a-space>
          <a-input-search
            placeholder="input search text"
            style="width: 200px"
          />
          <a-tooltip>
            <a-button href="https://www.google.com">
              <template #icon><UndoOutlined /></template>
            </a-button>
          </a-tooltip>
          <a-divider type="vertical" />
          <a-tooltip>
            <a-button type="dashed" @click="addCluster">
              <template #icon><PlusOutlined /></template>
            </a-button>
            <AddClusterModal ref="addClusterModal"/>
          </a-tooltip>
          <a-tooltip>
            <a-button href="https://www.google.com">
              <template #icon><SyncOutlined /></template>
            </a-button>
          </a-tooltip>
          <a-tooltip>
            <a-button href="https://www.google.com" danger>
              <template #icon><DeleteOutlined /></template>
            </a-button>
          </a-tooltip>
        </a-space>
      </a-col>
    </a-row>
  </div>
  <div class="listContent">
    <a-table :columns="columns" :data-source="state.dataSource" :total="state.total" :pagination="state.pagination" bordered>
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'action'">
          <router-link :to="{name:'clusterItem', params: {id:record.id}, query:{'name':record.name}}">View Data</router-link>
          <a-divider type="vertical" />
          <a>Delete</a>
        </template>
        <template v-if="column.key === 'status'">
            <a-switch v-model:checked="record.status" :loading="record.loading ? true : false" @change="connectCluster(record)"/>
        </template>
      </template>
      
    </a-table>
  </div>
</template>
<script setup>
  import { UndoOutlined, PlusOutlined, SyncOutlined, DeleteOutlined } from '@ant-design/icons-vue'
  import { onMounted, reactive,ref } from 'vue'
  import AddClusterModal from './modal/AddCluster.vue'
  import { clustersPaging, clustersConnect } from '@/request/api/cluster'
  import { message } from 'ant-design-vue';


  const addClusterModal = ref(null)

  const pageSize = ref(5)
  const pageIndex = ref(1)

  const state = reactive({
    "dataSource":[],
    "total": 0,
    "pagination":{
      current: pageIndex,
      pageSize:pageSize,
      total:0,
      onChange:(page, page_size) => {
        state.pagination.current = page
        pageIndex: page
        pageSize: page_size
        clustersPagingApi()
      }
    }
  })

  const columns = reactive(
   [
    {
      title: 'Name',
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: 'Servers',
      dataIndex: 'servers',
      key: 'age',
    },
    {
      title: 'Acl',
      dataIndex: 'acl',
      key: 'acl',
    },
    {
      title: 'Status',
      dataIndex: 'status',
      key: 'status',
    },
    {
      title: 'Action',
      key: 'action',
    }
  ])

  const clustersPagingApi = async function(){
    let data = {
      "pageSize":pageSize.value,
      "pageIndex": pageIndex.value
    }
    let res = await clustersPaging(data)
    state.dataSource = res.data.data.items
    state.pagination.total = res.data.data.total
  }

  onMounted( async() => {
    clustersPagingApi()
  })


  const addCluster = ()=> {
    addClusterModal.value.showModal()
  }

  const connectCluster = async (record) => {
    record.loading = true
    let data = {
      "id" : record.id
    }
    let res = await clustersConnect(data)
    // res.then(v=>{
    //   console.log(v);
    // })
    if (res.data.code != 0) {
      message.error(res.data.message)
      record.status = false
    }
    record.loading = false
  }

</script>

<style lang="less" scoped>
.listTop{
  margin-bottom: 20px;
  .topRight{
    text-align: right;
  }
}

</style>