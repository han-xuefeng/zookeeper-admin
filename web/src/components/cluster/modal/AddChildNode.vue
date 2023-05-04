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
  const confirmLoading = ref(false);

  defineProps(['visible'])

  const formState = reactive({
    node: '',
    data: '',
    acl: '',
  });

  const emit = defineEmits(['handleCancel'])

  const closeAddChildDataModal = () => {
    emit('handleCancel')
  }

  const handleOk = async e => {
    confirmLoading.value = true;
    //调取api
    // const res = await clustersCreate(formState)
    // if (res.data.code == 0) {
    //   window.location.href = '/'
    // } else {
    //   confirmLoading.value = false;
    //   message.error(res.data.message)
    // }
  };


</script>