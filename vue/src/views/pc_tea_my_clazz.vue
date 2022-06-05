<template>
  <div style="width: 100%; padding:10px">
    <el-breadcrumb separator=">>" replace>
      <el-breadcrumb-item>我的课程</el-breadcrumb-item>
    </el-breadcrumb>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">添加课程</el-button>
    </div>
    <div style="margin: 10px 0">
      <!--      搜索-->
      <el-input v-model="search" placeholder="输入名称关键字查询" style="width: 20%" clearable @keyup.enter.native="load"/>
      <el-button type="primary" style="margin-left: 5px;" @click="load">查询</el-button>
    </div>
    <el-table height="510" style="width: 99%" :data="tableData" border stripe>
      <el-table-column prop="name" label="课程名称"/>
      <el-table-column prop="teacherId" label="授课教师"/>
      <el-table-column prop="code" label="课程编号"/>
      <el-table-column prop="myIsClose" label="是否关闭"/>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button style="margin: 5px auto;" type="primary" @click="goDetail(scope.row)">查看课程学生</el-button>
          <el-button style="margin: 5px auto;" color="#626aef" type="primary" @click="goExam(scope.row)">查看课程考试</el-button>
<!--          <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>-->
<!--          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row)">-->
<!--            <template #reference>-->
<!--              <el-button native-type="button" type="danger">删除</el-button>-->
<!--            </template>-->
<!--          </el-popconfirm>-->
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 0">
      <el-dialog v-model="dialogVisible" title="添加课程" width="30%">
        <el-form ref="form" :model="form" label-width="120px">
          <el-form-item label="课程名称">
            <el-input v-model="form.name" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="授课教师">
            <el-input v-model="form.teacherName" style="width: 75%" disabled/>
          </el-form-item>
          <el-form-item label="课程编号">
            <el-input v-model="form.code" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="是否开设">
            <el-select v-model="form.myIsClose" placeholder="请选择" style="width: 75%">
              <el-option label="是" value="是"/>
              <el-option label="否" value="否"/>
            </el-select>
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
            <el-input v-model="editForm.name" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="授课教师">
            <el-input v-model="editForm.teacherName" style="width: 75%" disabled/>
          </el-form-item>
          <el-form-item label="课程编号">
            <el-input v-model="editForm.code" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="是否开设">
            <el-select v-model="editForm.myIsClose" placeholder="请选择" style="width: 75%">
              <el-option label="是" value="是"/>
              <el-option label="否" value="否"/>
            </el-select>
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
import { useRouter } from 'vue-router'
export default {
  name: "pc_tea_my_clazz",
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
    add() {
      this.dialogVisible = true
      this.form = {
        teacherId: this.user.id,
        teacherName: this.user.name
      }
    },
    load() {
      request.post("/api/class/find_class_list", null, {
        params: {
          teacherId: this.user.id,
          search: this.search
        }
      }).then(res => {
        console.log(res)
        let len = res.data.length
        for(let i = 0;i < len;i++) {
          if(res.data[i].isClose === 1) res.data[i].myIsClose = '是'
          else res.data[i].myIsClose = '否'
        }
        this.tableData = res.data
      })
    },
    onSubmitAdd() {
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
      this.editForm.teacherName = this.user.name
    },
    onSubmit() {
      if(this.editForm.myIsClose === '是') this.editForm.isClose = 1
      else this.editForm.isClose = 0
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
    },
    goDetail(temp) {
      sessionStorage.setItem("info", JSON.stringify(temp.valueOf()))
      this.$router.push('/pc_tea_my_clazz_info')
    },
    goExam(temp) {
      sessionStorage.setItem("info", JSON.stringify(temp.valueOf()))
      this.$router.push('/pc_tea_my_exam')
    }
  }
}
</script>

<style scoped>

</style>
