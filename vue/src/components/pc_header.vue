<template>
  <div style="height: 50px;line-height: 50px;border-bottom: 1px solid #ccc; display: flex">
    <div style="width: 200px; padding-left: 30px;font-weight: bold;color: dodgerblue;cursor: pointer;"
         @click="this.$router.push('/pc_index')">教考分离系统
    </div>
    <div style="flex: 1"></div>
    <div id="currentTime" style="width: 300px;border: 1px solid #4aaddc;font-family: 'Microsoft JhengHei',serif;
     border-radius: 5px;margin: 5px;text-align: center;position: relative;line-height: 35px"></div>
    <div style="width: 200px;">
      <el-dropdown style="float: right">
        <span class="el-dropdown-link" style="position: relative;top: 15px;right: 20px">
        {{ "您好!  " + user.name }}
          <el-icon class="el-icon--right">
            <arrow-down/>
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="information">个人信息</el-dropdown-item>
            <el-dropdown-item @click="quit">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
    <!--   新建窗口-->
    <el-dialog v-model="dialogVisible" @close="this.$refs.ruleForm.clearValidate();" :title="title" width="30%">
      <el-form :model="form" label-width="120px" :rules="rules" ref="ruleForm" class="demo-ruleForm">
        <el-form-item label="学号" prop="code" @keyup.enter.native="save('ruleForm')">
          <el-input v-model="form.code" style="width: 80%" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name" @keyup.enter.native="save('ruleForm')">
          <el-input v-model="form.name" style="width: 80%" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓别" prop="sex"  @keyup.enter.native="save('ruleForm')">
          <el-select v-model="form.sex" placeholder="请选择" style="width: 80%" disabled>
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="密码" prop="pwd" @keyup.enter.native="save('ruleForm')">
          <el-input v-model="form.pwd" style="width: 80%"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible = false;this.$refs.ruleForm.clearValidate();">取 消</el-button>
              <el-button type="primary" @click="save('ruleForm')">确 认</el-button>
            </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import {ArrowDown} from '@element-plus/icons'
import request from "@/utils/request";
import router from "@/router";

export default {
  name: "pc_header",
  components: {
    ArrowDown
  },
  data() {
    return {
      dialogVisible: false,
      form: {},
      user: [],
      tableData: [],
      title: '',
      usernameStyle: {
        color: '#5b9bea',
        fontSize: '12px',
        lineHeight: '1',
        paddingTop: '4px',
        position: 'absolute',
        top: '100%',
        left: '0',
      },
      ruleForm: {
        name: '',
        pwd: '',
      },
      rules: {
        pwd: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ]
      }
    }
  },
  created() {
    this.user = sessionStorage.getItem("user")
      this.user = JSON.parse(this.user);
      this.form = this.user
  },
  mounted() {
    this.time();
  },
  methods: {
    quit() {
      sessionStorage.setItem('user', null);
      this.$router.push('/pc_login');
    },
    time() {
      function change(data) {
        return data <= 9 ? "0" + data : data;
      }

      setInterval(current, 500);

      function current() {
        var mydate = new Date();
        var year = mydate.getFullYear();
        var month = mydate.getMonth() + 1;
        var date = mydate.getDate();
        var day = mydate.getDay();
        var week = ["日", "一", "二", "三", "四", "五", "六"];
        var hours = mydate.getHours();
        var minutes = mydate.getMinutes();
        var seconds = mydate.getSeconds();
        var str = "123";
        var str = year + "/" + change(month) + "/" + change(date) + "  星期" + week[day]
            + "  " + change(hours) + ":" + change(minutes) + ":" + change(seconds);
        if(hours <= 4) str += "   夜晚好";
        else if(hours <= 7) str += "   清晨好";
        else if(hours <= 11) str += "   上午好";
        else if(hours <= 14) str += "   中午好";
        else if(hours <= 17) str += "   下午好";
        else if(hours <= 24) str += "   晚上好";
        if(document.getElementById("currentTime"))
          document.getElementById("currentTime").innerHTML = str;
      }
    },
    information() {
      this.title = '修改个人信息'
      this.dialogVisible = true
    },
    save(formName) {
      this.$refs[formName].validate((valid) => {
        if(valid) {
          console.log('submit!');
          request.put("/api/user/update", this.form).then(res => {
            if(res.code === '0') {
              this.dialogVisible = false
              this.$message({
                message: '更新成功',
                type: 'success'
              });
              // this.load()
            } else {
              this.$message({
                message: res.msg,
                type: 'error'
              });
            }
          })
          this.$refs.ruleForm.clearValidate();
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
  }
}
</script>

<style scoped>

</style>
