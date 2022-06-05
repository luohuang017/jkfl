<template>
  <div style="width: 100%; padding:10px">
    <el-breadcrumb separator=">>" replace>
      <el-breadcrumb-item :to="{ path: '/pc_tea_my_clazz' }">我的课程</el-breadcrumb-item>
      <el-breadcrumb-item>课程学生</el-breadcrumb-item>
    </el-breadcrumb>

    <div style="margin: 10px 0">
<!--      <el-button type="info" @click="goback">返回</el-button>-->
      <el-button type="primary" @click="add">添加学生</el-button>
    </div>  <!--   头部按钮-->

    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="输入名称关键字查询" style="width: 20%" clearable @keyup.enter.native="load"/>
      <el-button type="primary" style="margin-left: 5px;" @click="load">查询</el-button>
    </div> <!--    搜索-->

    <el-table height="510" style="width: 99%" :data="tableData" border stripe>
      <el-table-column prop="code" label="学号"/>
      <el-table-column prop="name" label="姓名"/>
      <el-table-column prop="sex" label="性别"/>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row)">
            <template #reference>
              <el-button native-type="button" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>  <!--  列表-->

    <div style="margin: 10px 0">
      <el-dialog v-model="dialogVisible" title="添加课程学生" width="30%">
        <el-form ref="form" :model="form" label-width="120px">
          <el-form-item label="学号">
            <el-input v-model="form.stuCode" style="width: 75%"/>
          </el-form-item>
<!--          <el-form-item label="姓名">-->
<!--            <el-input v-model="form.stuName" style="width: 75%"/>-->
<!--          </el-form-item>-->
          <el-form-item>
            <el-button type="primary" @click="onSubmitAdd">添加</el-button>
            <el-button @click="dialogVisible = false;load()">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>  <!--  添加课程学生-->

  </div>
</template>

<script>
import {useRoute, useRouter} from 'vue-router'
import request from "@/utils/request";

export default {
  name: "pc_tea_my_clazz_info",
  data() {
    return {
      info: [],
      user: [],
      dialogVisible: false,
      dialogVisibleEdit: false,
      search: '',
      tableData: [],
      form: {},
      editForm: {},
      // router: useRouter(),
    }
  },
  created() {
    this.info = sessionStorage.getItem("info")
    this.info = JSON.parse(this.info);

    this.user = sessionStorage.getItem("user")
    this.user = JSON.parse(this.user);
    this.load()
  },
  methods: {
    // goback() {
    //   this.router.go(-1)
    // },
    load() {
      // console.log(this.info.id)
      request.post("/api/class/find_class_stu_list", null, {
        params: {
          clazzId: this.info.id,
          search: this.search
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data
        let len = res.data.length
        for(let i = 0; i < len; i++) {
          request.post("/api/user/find_user_info", null, {
            params: {
              userId: res.data[i].stuId
            }
          }).then(res2 => {
              this.tableData[i] = res2.data
            }
          )
        }
      })
    },
    add() {
      this.dialogVisible = true
      this.form = {}
    },
    onSubmitAdd() {
      this.form.clazzId = this.info.id
      request.post("/api/class/add_class_stu", null, {
        params : {
          clazzId : this.info.id,
          studentCode : this.form.stuCode
        }
      }).then(res => {
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
