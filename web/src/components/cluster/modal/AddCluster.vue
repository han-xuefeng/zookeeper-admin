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
      <a-form-item has-feedback label="Name" name="cluster_name">
        <a-input v-model:value="formState.cluster_name" type="text" autocomplete="off" />
      </a-form-item>
      <a-form-item has-feedback label="Server" name="cluster_server">
        <a-input v-model:value="formState.cluster_server" type="text" autocomplete="off" />
      </a-form-item>
      <a-form-item has-feedback label="Acl" name="cluster_acl">
        <a-input v-model:value="formState.cluster_acl" type="text" autocomplete="off" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup>
  import { ref,reactive } from 'vue';
  const confirmLoading = ref(false);
  const formState = reactive({
    cluster_name: '',
    cluster_server: '',
    cluster_acl: '',
  });

  defineProps(['visible'])

  const emit = defineEmits(['handleCancel'])

  const handleOk = e => {
    confirmLoading.value = true;
    //调取api
    setTimeout(() => {
      confirmLoading.value = false;
      emit('handleCancel')
    }, 2000);
  };
  

  const hideLoadNodeDataModal = () => {
    emit('handleCancel')
  }

  
</script>