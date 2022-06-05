<template>
  <div class="homepage-hero-module">
    <div class="video-container">
      <div class="filter">
        <div style="width: 400px; margin: 100px auto">
          <div style="font-size: 30px; text-align: center; padding: 30px 0; color: #333">欢迎注册</div>
          <el-form ref="form" :model="form" size="normal" :rules="rules">
            <el-form-item prop="name">
              <el-input prefix-icon="user" v-model="form.name" @keyup.enter.native="register" placeholder="请输入帐号"></el-input>
            </el-form-item>
            <el-form-item prop="pwd">
              <el-input prefix-icon="lock" v-model="form.pwd" show-password @keyup.enter.native="register" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item prop="confirm">
              <el-input prefix-icon="lock" v-model="form.confirm" show-password @keyup.enter.native="register" placeholder="请确认密码"></el-input>
            </el-form-item>
            <el-form-item prop="sex">
              <el-select style="width: 100%" prefix-icon="female" v-model="form.sex" placeholder="请选择性别" show-password @keyup.enter.native="register">
                <el-option label="男" value="男" />
                <el-option label="女" value="女" />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button style="width: 100%" type="primary" @click="register">注册</el-button>
            </el-form-item>
            <el-form-item><el-button type="text" @click="$router.push('/pc_login')">&lt;&lt;返回登录 </el-button></el-form-item>
          </el-form>
        </div>
      </div>
      <video :style="bg" autoplay loop muted class="fillWidth" v-on:canplay="canplay">
        <!--        浏览器不支持 video 标签，建议升级浏览器。-->
      </video>
    </div>
  </div>
</template>

<script>

import request from "@/utils/request";

export default {
  name: "pc_register",
  data() {
    return {
      form: {},
      bg: {
        backgroundImage: "url(" + require("@/assets/img/bg.png") + ")",
        backgroundRepeat: "no-repeat",
        backgroundSize: "100% 100%",
      },
      rules: {
        name: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
        ],
        pwd: [
          {required: true, message: '请输入密码', trigger: 'blur'},
        ],
        confirm: [
          {required: true, message: '请确认密码', trigger: 'blur'},
        ],
        sex: [
          {required: true, message: '请选择性别', trigger: 'blur'},
        ],
      }
    }
  },
  methods: {
    register() {
      if (this.form.pwd !== this.form.confirm) {
        this.$message({
          type: "error",
          message: '2次密码输入不一致！'
        })
        return
      }
      request.post("/api/student/student_register", this.form).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "注册成功"
          })
          this.$router.push("/pc_login")  //登录成功之后进行页面的跳转，跳转到主页
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
.homepage-hero-module,
.video-container {
  position: relative;
  height: 100vh;
  overflow: hidden;
}

.video-container .poster img{
  z-index: 0;
  position: absolute;
}

.video-container .filter {
  z-index: 1;
  position: absolute;
  /*background: rgba(0, 0, 0, 0.4);*/
  width: 100%;
}

.fillWidth {
  width: 100%;
}
.demo-input-label {
  display: inline-block;
  width: 130px;
}

.demo-input-suffix {
  margin-bottom: 16px;
}
</style>
