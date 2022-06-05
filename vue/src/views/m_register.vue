<template>
  <div>
    <!-- vant导航栏 -->
    <van-nav-bar style="background-color: #4fade0;">
      <template #left>
        <van-icon @click="back" color="white" name="arrow-left"/>
      </template>
      <template #title>
        <span style="color: white;">用户注册</span>
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
    <van-form>
      <van-field
          v-model="form.name"
          name="账号"
          label="账号"
          left-icon="manager"
          placeholder="输入用户名"
      />
      <van-field
          v-model="form.pwd"
          type="password"
          name="密码"
          left-icon="lock"
          label="密码"
          placeholder="设置密码"
      />
      <van-field
          v-model="form.confirm"
          type="password"
          name="密码"
          left-icon="lock"
          label="密码"
          placeholder="重复密码"
      />
      <div style="margin: 16px;">
        <van-button @click="register" round block type="primary" native-type="submit">
          注册
        </van-button>
      </div>
    </van-form>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "m_register",
  data() {
    return {
      form: {
        name: '',
        pwd: '',
        confirm: '',
      }
    }
  },
  methods: {
    register() {
      if(this.form.pwd !== this.form.confirm) {
        this.$message({
          type: "error",
          message: '2次密码输入不一致！'
        })
        return
      }
      request.post("/api/student/student_register", this.form).then(res => {
        if(res.code === '0') {
          this.$message({
            type: "success",
            message: "注册成功"
          })
          this.$router.push("/m_login")  //登录成功之后进行页面的跳转，跳转到主页
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },
    back() { //返回上一级
      window.history.back()
    }
  }
}
</script>

<style scoped>

</style>
