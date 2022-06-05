<template>
  <div style="width: 100%; padding:10px">
    <el-breadcrumb separator=">>" replace>
      <el-breadcrumb-item>课程列表</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">添加课程</el-button>
    </div>
    <div style="margin: 10px 0">
      <!--      搜索-->
      <el-input v-model="search" placeholder="输入名称关键字查询" style="width: 20%" clearable @keyup.enter.native="load"/>
      <el-button type="primary" style="margin-left: 5px;" @click="load">查询</el-button>
    </div>
    <el-table height="580" :data="tableData" border stripe>
      <el-table-column prop="name" label="课程名称"/>
      <el-table-column prop="teacherId" label="授课教师"/>
      <el-table-column prop="code" label="课程编号"/>
      <el-table-column prop="isClose" label="是否开设"/>
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
      <el-dialog v-model="dialogVisible" title="创建课程" width="30%">
        <el-form ref="form" :model="form" label-width="120px">
          <el-form-item label="课程名称">
            <el-input v-model="form.name" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="授课教师">
            <el-input v-model="form.teacherId" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="课程编号">
            <el-input v-model="form.code" style="width: 75%"/>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="onSubmit">创建</el-button>
            <el-button @click="dialogVisible = false;load()">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
    <div style="margin: 10px 0">
      <el-dialog v-model="dialogVisibleEdit" title="修改课程信息" width="30%">
        <el-form ref="editForm" :model="editForm" label-width="120px">
          <el-form-item label="课程名称">
            <el-input v-model="form.name" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="授课教师">
            <el-input v-model="form.teacherId" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="课程编号">
            <el-input v-model="form.code" style="width: 75%"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">修改</el-button>
            <el-button @click="dialogVisibleEdit = false;load()">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "pc_clazz_list",
  data() {
    return {
      user: [],
      dialogVisible: false,
      dialogVisibleEdit: false,
      search: '',
      tableData: [],
      form: {},
      editForm: {},
    }
  },
  created() {
    this.user = sessionStorage.getItem("user")
    this.user = JSON.parse(this.user);
    this.load()
  },
  methods: {
    load() {
      request.post("/api/class/find_class_list", null, {
        params: {
          // type: "teacher",
          search: this.search
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data
      })
    },
    add() {
      this.dialogVisible = true
      this.form = {}
    },
    onSubmitADD() {
      request.post("/api/class/add_class", this.form).then(res => {
        if(res.code === '0') {
          this.$message({
            type: "success",
            message: "创建成功"
          })
          console.log(res)
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
    handleEdit(temp) {
      this.dialogVisibleEdit = true
      this.editForm = temp
    },
    onSubmit() {
      request.post("/api/class/update_class_info", this.editForm).then(res => {
        if(res.code === '0') {
          this.$message({
            type: "success",
            message: "修改成功"
          })
          this.load()
          this.dialogVisibleEdit = false
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
      })
    },
    handleDelete(temp) {
      request.post("/api/class/delete_cass", temp).then(res => {
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
    },
  }
}
</script>

<style scoped>

</style>
