<template>
  <div>
    <el-menu
        style="width: 200px;min-height:calc(100vh - 50px) "
        :default-active="$route.path"
        router
    >
      <el-menu-item index="m_index" @click="update">
        <el-icon><HomeFilled /></el-icon>
        首页
      </el-menu-item>
      <el-sub-menu>
        <template #title>
          <el-icon>
            <setting/>
          </el-icon>
          <span>问卷管理</span>
        </template>
        <el-menu-item index="m_model" @click="update">
          模板列表
        </el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script>

export default {
  name: "m_aside",
  data() {
    return {
      user: [],
    }
  },
  created() {
    this.user = JSON.parse(sessionStorage.getItem("user"))
  },
  methods: {
    update() {
      this.$store.commit('update')
    }
  }
}
</script>

<style scoped>

</style>
