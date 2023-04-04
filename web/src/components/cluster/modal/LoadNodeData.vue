<template>
    <a-modal :visible="visible" title="Node Detail" @cancel="closeLoadNodeDataModal" :footer="null" :destroyOnClose=true>
      <a-descriptions title="Node Data">
        <a-descriptions-item label="path">{{nodePath}}</a-descriptions-item>
        <a-descriptions-item label="data">{{ nodeData }}</a-descriptions-item>
      </a-descriptions>
    
      <a-descriptions title="Node Stat">
        <template v-for="(value,key) in nodeStat.value">
          <a-descriptions-item :label=key >{{value}}</a-descriptions-item>
        </template>
        
      </a-descriptions>
    </a-modal>
</template>
<script setup>

  defineProps(['visible', 'nodeStat', 'nodePath', 'nodeData'])
  
  const emit = defineEmits(['handleCancel'])

  const closeLoadNodeDataModal = ()=>{
    emit('handleCancel')
  }
  
</script>