<template>
  <div style="width: 100%; padding:10px">
    <el-breadcrumb separator=">>" replace>
      <el-breadcrumb-item>发布通知</el-breadcrumb-item>
    </el-breadcrumb>
    <el-form :model="form" label-width="70px"
             style="width: 90%;height: 70%;border: #8ba8b4 solid 2px;border-radius: 20px;margin: 50px auto;padding: 20px">
      <el-form-item label="发布课程">
        <el-select style="width: 100%;" v-model="form.clazzId" placeholder="请选择课程" size="large">
          <el-option
              v-for="item in tableData"
              :key="item.id"
              :label="item.name"
              :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="通知内容">
        <el-input
            style="width: 100%;margin: 10px 0"
            v-model="form.content"
            :rows="15"
            type="textarea"
            placeholder="请输入内容"
        />
      </el-form-item>
    </el-form>
    <div style="width: 20%;margin: 0 auto">
      <el-button type="primary" @click="submit">发布</el-button>
    </div>

  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "pc_tea_notice",
  data() {
    return {
      user: [],
      search: '',
      tableData: [],
      form: {},
      info: {},
    }
  },
  created() {
    this.user = sessionStorage.getItem("user")
    this.user = JSON.parse(this.user);
    this.load()
  },
  methods: {
    load() {
      request.post("/api/teacher/teacher_find_myclass", this.user).then(res => {
        this.tableData = res.data
      })
    },
    submit() {
      if(this.form.clazzId === undefined) {
        this.$message({
          type: "error",
          message: "请选择发布课程"
        })
        return;
      }
      if(this.form.content === undefined) {
        this.$message({
          type: "error",
          message: "通知无内容"
        })
        return;
      }
      request.post("/api/notice/publish_notice", this.form).then(res => {
        if(res.code === '0') {
          this.$message({
            type: "success",
            message: "发布成功"
          })
          this.$router.push("/pc_index")
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
