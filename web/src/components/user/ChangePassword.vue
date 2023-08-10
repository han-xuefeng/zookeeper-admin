<template>
  <div class="changePwdTitle">
    修改密码
  </div>
  <a-form
    ref="formRef"
    name="custom-validation"
    :model="formState"
    :rules="rules"
    v-bind="layout"
    @finish="handleFinish"
    @validate="handleValidate"
    @finishFailed="handleFinishFailed"
  >
    <a-form-item has-feedback label="原始密码" name="password">
      <a-input v-model:value="formState.password" type="password"  />
    </a-form-item>
    <a-form-item has-feedback label="新的密码" name="new_password">
      <a-input v-model:value="formState.new_password" type="password" autocomplete="off" />
    </a-form-item>
    <a-form-item has-feedback label="确认密码" name="confirm_password">
      <a-input v-model:value="formState.confirm_password" type="password" autocomplete="off" />
    </a-form-item>
    
    <a-form-item :wrapper-col="{ span: 14, offset: 4 }">
      <a-button type="primary" html-type="submit">Submit</a-button>
    </a-form-item>
  </a-form>
</template>
<script setup>
  import {reactive, ref } from 'vue';

  const formRef = ref();
  const formState = reactive({
    new_password: '',
    confirm_password: '',
    password: '',
  });
  
  let validatePass = async (_rule, value) => {
    console.log(value);
    if (value === '') {
      return Promise.reject('Please input the password');
    }
  };
  let validatePass1 = async (_rule, value) => {
    if (value === '') {
      return Promise.reject('Please input the new password');
    } else {
      if (formState.confirm_password !== '') {
        formRef.value.validateFields('checkPass');
      }
      return Promise.resolve();
    }
  }

  let validatePass2 = async (_rule, value) => {
    if (value === '') {
      return Promise.reject('Please input the password again');
    } else if (value !== formState.password) {
      return Promise.reject("Two inputs don't match!");
    } else {
      return Promise.resolve();
    }
  };
  const rules = {
    password: [{
      required: true,
      validator: validatePass,
      trigger: 'change',
    }],
    new_password: [{
      required: true,
      validator: validatePass1,
      trigger: 'change',
    }],
    confirm_password: [{
      validator: validatePass2,
      trigger: 'change',
    }]
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
</script>
<style lang="less" scoped>
  .changePwdTitle{
    font-size: 25px;
    font-weight: 900;
    padding-bottom: 20px;
  }
</style>