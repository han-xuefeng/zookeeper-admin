<template>
  <div class="home">
    <a-layout class="layout">
      <HomeHeader />
      <HomeContext />
      <HomeFooter />
    </a-layout>
  </div>
</template>

<script setup>
  import HomeHeader from "@/components/home/HomeHeader.vue"
  import HomeContext from "@/components/home/HomeContext.vue"
  import HomeFooter from "@/components/home/HomeFooter.vue"
</script>
<style lang="less" scoped>
.home{
  min-height: 100vh;
  .layout{
    height: 100%;
  }
}
</style>
