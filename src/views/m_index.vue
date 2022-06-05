<template>
  <div>
    <van-image
        width="100%"
        height="25%"
        :src="require('../assets/img/jkfl.png')"
    >
      <template v-slot:loading>
        <van-loading type="spinner" size="20"/>
      </template>
      <template v-slot:error>加载失败</template>
    </van-image>

    <van-calendar
        title="日历"
        :poppable="false"
        :show-confirm="false"
        :style="{ height: '500px' }"
    />

  </div>
</template>

<script>

export default {
  name: "m_index",
}
</script>

<style scoped>

</style>
