<template>
  <div style="width: 100%; padding:10px">
    <el-breadcrumb separator=">>" replace>
      <el-breadcrumb-item>教师列表</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">添加教师</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="输入名称关键字查询" style="width: 20%" clearable @keyup.enter.native="load"/>
      <el-button type="primary" style="margin-left: 5px;" @click="load">查询</el-button>
    </div>     <!--      搜索-->

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
      <el-dialog v-model="dialogVisible" title="添加教师" width="30%">
        <el-form ref="form" :model="form" label-width="120px">
          <el-form-item label="用户名">
            <el-input v-model="form.name" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="教师号">
            <el-input v-model="form.code" style="width: 75%"/>
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
            <el-button type="primary" @click="onSubmitAdd">创建</el-button>
            <el-button @click="dialogVisible = false;load()">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>     <!--    添加-->

    <div style="margin: 10px 0">
      <el-dialog v-model="dialogVisibleEdit" title="修改教师信息" width="30%">
        <el-form ref="editForm" :model="editForm" label-width="120px">
          <el-form-item label="用户名">
            <el-input v-model="editForm.name" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="教师号">
            <el-input v-model="editForm.code" style="width: 75%" disabled/>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="editForm.pwd" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="姓别">
            <el-select v-model="editForm.sex" placeholder="请选择" style="width: 75%">
              <el-option label="男" value="男"/>
              <el-option label="女" value="女"/>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">修改</el-button>
            <el-button @click="dialogVisibleEdit = false;load()">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>     <!--    修改-->
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "pc_teacher_list",
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
      request.post("/api/user/find_user_list", null, {
        params: {
          type: "teacher",
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
    onSubmitAdd() {
      this.form.userType = "teacher"
      request.post("/api/user/add_user", this.form).then(res => {
        if(res.code === '0') {
          this.$message({
            type: "success",
            message: "创建成功"
          })
          // console.log(res)
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
      request.post("/api/user/update_user_info", this.editForm).then(res => {
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
      request.post("/api/user/delete_user", temp).then(res => {
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
  }
}
</script>

<style scoped>

</style>
