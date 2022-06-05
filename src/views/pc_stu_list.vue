<template>
  <div style="width: 100%; padding:10px">
    <el-breadcrumb separator=">>" replace>
      <el-breadcrumb-item>学生列表</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin: 10px 0">
      <!--      搜索-->
      <el-input v-model="search" placeholder="输入名称关键字查询" style="width: 20%" clearable @keyup.enter.native="load"/>
      <el-button type="primary" style="margin-left: 5px;" @click="load">查询</el-button>
    </div>
    <el-table height="510" style="width: 99%" :data="tableData" border stripe>
      <el-table-column prop="id" label="编号"/>
      <el-table-column prop="name" label="姓名"/>
      <el-table-column prop="code" label="学号"/>
      <el-table-column prop="sex" label="性别"/>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row)">
            <template #reference>
              <el-button native-type="button" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 0">
      <el-dialog v-model="dialogVisible" title="学生信息" width="30%">
        <el-form :model="form" label-width="100px">
          <el-form-item label="编号">
            <el-input v-model="form.id" disabled style="width: 75%"/>
          </el-form-item>
          <el-form-item label="学号">
            <el-input v-model="form.code" disabled style="width: 75%"/>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="form.name" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="form.pwd" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="姓别">
            <el-select v-model="form.sex" placeholder="请选择" style="width: 75%">
              <el-option label="男" value="男"/>
              <el-option label="女" value="女"/>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">确认</el-button>
            <el-button @click="dialogVisible = false;load()">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>

  </div>
</template>

<script>

import request from "@/utils/request";
import {defineComponent, ref} from 'vue'

export default {
  data() {
    return {
      user: [],
      form: {},
      dialogVisible: false,
      search: '',
      tableData: [],
    }
  },
  created() {
    this.user = sessionStorage.getItem("user")
    this.user = JSON.parse(this.user);
    this.load()
  },
  methods: {
    load() {
      request.post("/api/user/find_user_list", null, {
        params: {
          type: "student",
          search: this.search
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data
      })
    },
    handleEdit(temp) {
      this.dialogVisible = true
      this.form = temp
    },
    onSubmit() {
      request.post("/api/student/student_updateInfo", this.form).then(res => {
        if(res.code === '0') {
          this.$message({
            type: "success",
            message: "修改成功"
          })
          this.load()
          this.dialogVisible = false
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },
    handleDelete(temp) {
      request.post("/api//user/delete_user", temp).then(res => {
        if(res.code === '0') {
          this.$message({
            type: "success",
            message: "删除成功"
          })
          this.load()  // 删除之后重新加载表格的数据
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    }
  },
  name: 'pc_stu_list',
  components: {}
}
</script>
