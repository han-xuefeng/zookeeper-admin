<template>
  <a-modal 
  :visible="visible" 
  title="Add Child Node"  
  :destroyOnClose=true 
  @cancel="closeAddChildDataModal"
  @ok="handleOk"
  :confirm-loading="confirmLoading"
  >
    <a-form
    ref="formRef"
    name="custom-validation"
    :model="formState"
    >
      <a-form-item has-feedback label="Node" name="node">
        <a-input v-model:value="formState.node" type="text" autocomplete="off" />
      </a-form-item>
      <a-form-item has-feedback label="Data" name="data">
        <a-input v-model:value="formState.data" type="text" autocomplete="off" />
      </a-form-item>
      <a-form-item has-feedback label="Acl" name="acl">
        <a-input v-model:value="formState.acl" type="text" autocomplete="off" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script setup>

  import {reactive, ref} from 'vue'
  import { clusterDataCreate } from '@/request/api/clusterData'
  import { message } from 'ant-design-vue'
  const confirmLoading = ref(false);

  const prop = defineProps(['visible', 'nodePath', 'id'])

  const formState = reactive({
    node: '',
    data: '',
    acl: '',
  });

  const emit = defineEmits(['handleCancel', 'handleSuccess'])

  const closeAddChildDataModal = () => {
    emit('handleCancel')
  }

  const handleOk = async e => {
    confirmLoading.value = true;
    //调取api

    console.log(prop)

    const data = {
      'path':formState.node,
      'data':formState.data,
      'acl':formState.acl,
      'parentPath':prop.nodePath
    }

    const res = await clusterDataCreate(data, {'id':prop.id})
    if (res.data.code == 0) {
      //  这里成功了
      //  页面重新渲染
      emit('handleCancel')
      emit('handleSuccess')
    } else {
      message.error(res.data.message)
    }
    confirmLoading.value = false;
  };


</script>