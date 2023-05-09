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
            v-model:value="search"
            @search="onSearch"
          />
          <!-- <a-tooltip>
            <a-button href="https://www.google.com">
              <template #icon><UndoOutlined /></template>
            </a-button>
          </a-tooltip> -->
          <a-divider type="vertical" />
          <a-tooltip>
            <a-button type="dashed" @click="showAddClusterModal">
              <template #icon><PlusOutlined /></template>
            </a-button>
            <AddClusterModal :visible="addClusterModalVisible" @handle-cancel="closeAddClusterModal"/>
          </a-tooltip>
          <a-tooltip>
            <a-button @click="onRefreshList">
              <template #icon><SyncOutlined /></template>
            </a-button>
          </a-tooltip>
          <!-- <a-tooltip>
            <a-button href="https://www.google.com" danger>
              <template #icon><DeleteOutlined /></template>
            </a-button>
          </a-tooltip> -->
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
          <a @click="deleteCluster(record)">Delete</a>
        </template>
        <template v-if="column.key === 'status'">
            <a-switch v-model:checked="record.status" :loading="record.loading ? true : false" @change="connectCluster(record)"/>
        </template>
      </template>
      
    </a-table>
  </div>
</template>
<script setup>
  import { PlusOutlined, SyncOutlined, DeleteOutlined } from '@ant-design/icons-vue'
  import { onMounted, reactive,ref } from 'vue'
  import AddClusterModal from './modal/AddCluster.vue'
  import { clustersPaging, clustersConnect,clustersDelete } from '@/request/api/cluster'
  import { message } from 'ant-design-vue';

  const pageSize = ref(5)
  const pageIndex = ref(1)
  const search = ref('')

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
      "pageIndex": pageIndex.value,
      "name": search.value
    }
    let res = await clustersPaging(data)
    state.dataSource = res.data.data.items
    state.pagination.total = res.data.data.total
  }

  onMounted( async() => {
    clustersPagingApi()
  })


  const onSearch = ()=>{
    clustersPagingApi()
  }

  const onRefreshList = () => {
    search.value =  ''
    pageSize.value = 5
    pageIndex.value = 1
    clustersPagingApi();
  }

  // addClusterModalVisible
  const addClusterModalVisible = ref(false)
  const showAddClusterModal = ()=> {
    addClusterModalVisible.value = true
  }

  const closeAddClusterModal =() => {
    addClusterModalVisible.value = false
  }

  const connectCluster = async (record) => {
    record.loading = true
    let data = {
      "id" : record.id
    }
    let res = await clustersConnect(data)
    if (res.data.code != 0) {
      message.error(res.data.message)
      record.status = false
    }
    record.loading = false
  }

  const deleteCluster = async (record) => {
    let data = {
      "id" : record.id
    }
    const res = await clustersDelete(data)
    if (res.data.code != 0) {
      message.error(res.data.message)
    } else {
      message.success("Success")
      clustersPagingApi()
    }
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