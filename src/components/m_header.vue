<template>
  <van-nav-bar>
    <template #title>
      <h3  @click="$router.push('/m_index')">教考分离系统</h3>
    </template>
    <template #left>
      <!--      <van-icon v-if="!headerFlag" name="bars" size="18" @click="onClickLeft"/>-->
      <!--      <van-icon v-else name="revoke" size="18" @click="onClickLeft"/>-->
      <van-cell @click="onClickLeft" style="position: relative;right: 1rem">
        <van-icon name="bars" size="25"/>
      </van-cell>
      <van-popup v-model:show="headerFlag"
                 position="left"
                 closeable
                 @click-close-icon="onClickLeft"
                 style="width: 50%; height: 100%">
        <Aside/>
      </van-popup>

    </template>
    <template #right>
      <van-icon name="manager" size="25" color="black"/>
      <span>{{ user.name }}</span>
    </template>
  </van-nav-bar>

</template>

<script>
import {ref} from 'vue'
import {mapState} from 'vuex'
import Aside from "@/components/m_aside";
export default {
  name: "m_header",
  components: {
    Aside
  },
  data() {
    return {
      user: [],
    }
  },
  created() {
    this.user = sessionStorage.getItem("user")
    this.user = JSON.parse(this.user);
  },
  computed: {
    ...mapState(['headerFlag']),
  },
  methods: {
    onClickLeft() {
      this.$store.commit('update')
    },
  },
}
</script>

<style scoped>

</style>
