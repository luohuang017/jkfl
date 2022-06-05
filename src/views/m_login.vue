<template>
  <div>
    <!-- vant导航栏 -->
    <van-nav-bar style="background-color: #4fade0;">
      <template #title>
        <span style="color: white;">用户登录</span>
      </template>
    </van-nav-bar>

    <van-image
      width="100%"
      height="100%"
      :src="require('../assets/img/jkfl.png')"
    >
      <template v-slot:loading>
        <van-loading type="spinner" size="20" />
      </template>
      <template v-slot:error>加载失败</template>
    </van-image>

    <!-- vant表单 -->
    <van-form style="position: relative;">
      <van-field
          v-model="form.name"
          name="账号"
          label="账号"
          left-icon="manager"
          placeholder="请输入用户名"
      />
      <van-field
          v-model="form.pwd"
          type="password"
          name="密码"
          left-icon="lock"
          label="密码"
          placeholder="请输入密码"
      />
      <div style="margin: 16px;">
        <van-button @click="login" round block type="primary" native-type="submit">
          登录
        </van-button>
      </div>
      <div style="float: right;width: 30%; margin: 16px;">
        <van-button color="#7232dd" @click="$router.push('/m_register')"
                    round block plain type="primary" native-type="submit" size="normal">
          前往注册
        </van-button>
      </div>
    </van-form>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "m_login",
  data() {
    return {
      form: {
        name: "",
        pwd: ""
      }
    }
  },
  methods: {
    login() {
      request.post("/api/user/login", this.form).then(res => {
        if(res.code === '0') {
          this.$message({
            type: "success",
            message: "登录成功"
          })
          console.log(res)
          sessionStorage.setItem("user", JSON.stringify(res.data));  // 缓存用户信息
          this.$router.push("/m_index")  //登录成功之后进行页面的跳转，跳转到主页
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
