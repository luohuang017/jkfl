<template>
  <div id="building" class="homepage-hero-module">
    <div class="video-container">
      <div class="filter">
        <div style="width: 400px; margin: 100px auto">
          <div style="font-size: 40px; text-align: center; padding: 30px 0; color: #333;font-family: 'Wingdings 2',serif">欢迎登录</div>
          <el-form ref="form" :model="form" size="normal">
            <el-form-item>
              <el-input prefix-icon="user" v-model="form.code" placeholder="请输入学号"
                        @keyup.enter.native="login"></el-input>
            </el-form-item>
            <el-form-item>
              <el-input prefix-icon="lock" v-model="form.pwd" @keyup.enter.native="login"
                        show-password placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-button style="width: 100%;" type="primary" @click="login">登 录</el-button>
            <el-form-item>
              <el-button type="text" @click="$router.push('/pc_register')">前往注册 >></el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </div>
</template>


<script>

import request from "@/utils/request";

export default {
  name: "pc_login",
  data() {
    return {
      form: {}
    }
  },
  methods: {
    login() {
      request.post("/api/user/login", this.form).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "登录成功"
          })
          sessionStorage.setItem("user", JSON.stringify(res.data));  // 缓存用户信息
          this.$router.push("/pc_index")  //登录成功之后进行页面的跳转，跳转到主页
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
#building{
  background:url("../assets/img/bg.png");
  width:100%;
  height:100%;
  position:fixed;
  background-size:100% 100%;
}

@media screen and (min-width: 1200px) {
  .homepage-hero-module,
  .video-container {
    position: relative;
    height: 100vh;
    overflow: hidden;
  }

  .video-container .poster img {
    z-index: 0;
    position: absolute;
  }

  .video-container .filter {
    z-index: 1;
    position: absolute;
    /*background: rgba(0, 0, 0, 0.4);*/
    width: 100%;
  }


  .demo-input-label {
    display: inline-block;
    width: 130px;
  }

  .demo-input-suffix {
    margin-bottom: 16px;
  }
}

</style>
