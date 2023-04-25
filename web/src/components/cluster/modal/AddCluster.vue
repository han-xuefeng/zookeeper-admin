<template>
  <a-modal :visible="visible" 
  title="Basic Modal" 
  @ok="handleOk"
  @cancel="hideLoadNodeDataModal"
  :confirm-loading="confirmLoading"
  >
    <a-form
    ref="formRef"
    name="custom-validation"
    :model="formState"
    >
      <a-form-item has-feedback label="Name" name="name">
        <a-input v-model:value="formState.name" type="text" autocomplete="off" />
      </a-form-item>
      <a-form-item has-feedback label="Server" name="servers">
        <a-input v-model:value="formState.servers" type="text" autocomplete="off" />
      </a-form-item>
      <a-form-item has-feedback label="Acl" name="acl">
        <a-input v-model:value="formState.acl" type="text" autocomplete="off" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup>
  import { ref,reactive } from 'vue';
  import { clustersCreate } from '@/request/api/cluster'
  import { message } from 'ant-design-vue'
  const confirmLoading = ref(false);
  const formState = reactive({
    name: '',
    servers: '',
    acl: '',
  });

  defineProps(['visible'])

  const emit = defineEmits(['handleCancel'])

  const handleOk = async e => {
    confirmLoading.value = true;
    //调取api
    const res = await clustersCreate(formState)
    if (res.data.code == 0) {
      window.location.href = '/'
    } else {
      confirmLoading.value = false;
      message.error(res.data.message)
    }
  };
  

  const hideLoadNodeDataModal = () => {
    emit('handleCancel')
  }

  
</script>