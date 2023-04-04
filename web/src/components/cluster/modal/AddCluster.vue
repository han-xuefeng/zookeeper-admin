<template>
  <a-modal v-model:visible="visible" 
  title="Basic Modal" 
  @ok="handleOk"
  :confirm-loading="confirmLoading"
  >
    <a-form
    ref="formRef"
    name="custom-validation"
    :model="formState"
    :rules="rules"
    v-bind="layout"
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

  const visible = ref(false);
  const confirmLoading = ref(false);
  const showModal = () => {
    visible.value = true;
  };
  const handleOk = e => {
    console.log(formState);
    confirmLoading.value = true;
    setTimeout(() => {
      visible.value = false;
      confirmLoading.value = false;
    }, 2000);
  };
  

  const formState = reactive({
    cluster_name: '',
    cluster_server: '',
    cluster_acl: '',
  });

  let validateName = async (_rule, value) => {
    if (value === '') {
      return Promise.reject('Please input the Name');
    }
  };

  let validateServer = async (_rule, value) => {
    if (value === '') {
      return Promise.reject('Please input the Server');
    }
  };
    
  const rules = {
    cluster_name: [{
      required: true,
      validator: validateName,
      trigger: 'change',
    }],
    cluster_server: [{
      required: true,
      validator: validateServer,
      trigger: 'change',
    }],
    
  };
    const layout = {
      labelCol: {
        span: 4,
      },
      wrapperCol: {
        span: 14,
      },
    };
    const handleFinish = values => {
      console.log(values, formState);
    };
    const handleFinishFailed = errors => {
      console.log(errors);
    };
    const resetForm = () => {
      formRef.value.resetFields();
    };
    const handleValidate = (...args) => {
      console.log(args);
    };

  defineExpose({
    showModal
  })
</script>