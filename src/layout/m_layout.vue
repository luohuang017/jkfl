<template>
  <div>
    <!--    头部-->
    <Header/>
    <!--    侧边栏-->
    <div style="display: flex">
<!--      <Aside/>-->
      <!-- 内容区域 -->
      <router-view/>
    </div>

  </div>
</template>

<script>

import Header from "@/components/m_header";
import Aside from "@/components/m_aside";
export default {
  name:"m_layout",
  components: {
    Header,
    Aside,
  }
}
</script>

<style scoped>

</style>
