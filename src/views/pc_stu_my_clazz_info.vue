<template>
  <div style="width: 100%; padding:10px">
    <el-breadcrumb separator=">>" replace>
      <el-breadcrumb-item :to="{ path: '/pc_stu_my_clazz' }">我的课程</el-breadcrumb-item>
      <el-breadcrumb-item>课程学生</el-breadcrumb-item>
    </el-breadcrumb>

    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder="输入名称关键字查询" style="width: 20%" clearable @keyup.enter.native="load"/>
      <el-button type="primary" style="margin-left: 5px;" @click="load">查询</el-button>
    </div> <!--    搜索-->

    <el-table height="510" style="width: 99%" :data="tableData" border stripe>
      <el-table-column prop="code" label="学号"/>
      <el-table-column prop="name" label="姓名"/>
      <el-table-column prop="sex" label="性别"/>
    </el-table>  <!--  列表-->

  </div>
</template>

<script>
import {useRoute, useRouter} from 'vue-router'
import request from "@/utils/request";

export default {
  name: "pc_stu_my_clazz_info",
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
    }
  },
  created() {
    this.info = sessionStorage.getItem("stuClazzInfo")
    this.info = JSON.parse(this.info);

    this.user = sessionStorage.getItem("user")
    this.user = JSON.parse(this.user);
    this.load()
  },
  methods: {
    load() {
      console.log(this.info)
      request.post("/api/class/find_class_stu_list", null, {
        params: {
          clazzId: this.info.clazzId,
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
  }
}
</script>

<style scoped>

</style>
