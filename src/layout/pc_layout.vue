<template>
  <div>
    <!--    头部-->
    <Header/>
    <!--    侧边栏-->
    <div style="display: flex">
      <Aside/>
      <!-- 内容区域 -->
      <router-view/>
    </div>

  </div>
</template>

<script>

import Header from "@/components/pc_header";
import Aside from "@/components/pc_aside";
export default {
  name:"pc_layout",
  components: {
    Header,
    Aside,
  }
}
</script>

<style scoped>

</style>
