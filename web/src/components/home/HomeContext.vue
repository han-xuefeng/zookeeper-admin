<template>
  <a-layout-content>
    <div :style="{ background: '#fff', padding: '20px', minHeight: '280px' }">
      <RouterView></RouterView>
    </div>
  </a-layout-content>
</template>
<style lang="less">
.ant-layout-content{
  margin: 20px auto;
  width: 80%;
}
</style>