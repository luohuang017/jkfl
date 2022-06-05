<template>
  <div style="width: 100%; padding:10px">
    <el-breadcrumb separator=">>" replace>
      <el-breadcrumb-item>历史考试</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin: 10px 0">
      <!--      搜索-->
      <el-input v-model="search" placeholder="输入名称关键字查询" style="width: 20%" clearable @keyup.enter.native="load"/>
      <el-button type="primary" style="margin-left: 5px;" @click="load">查询</el-button>
    </div>
    <el-table height="510" style="width: 99%" :data="tableData" border stripe>
      <el-table-column prop="clazzName" label="课程名称"/>
      <el-table-column prop="examCode" label="考试编号"/>
      <el-table-column prop="state" label="状态"/>
      <el-table-column prop="scores" label="成绩"/>
      <!--      <el-table-column fixed="right" label="操作">-->
      <!--        <template #default="scope">-->
      <!--          <el-button style="margin: 5px auto;" type="primary" @click="goDetail(scope.row)">查看课程学生</el-button>-->
      <!--          <el-button style="margin: 5px auto;" color="#626aef" type="primary" @click="goExam(scope.row)">查看课程考试-->
      <!--          </el-button>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
    </el-table>
  </div>
</template>

<script>
import request from "@/utils/request";
import {useRouter} from 'vue-router'

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
    load() {
      request.post("/api/student/find_history_exams", this.user).then(res => {
        console.log(res)
        let len = res.data.length
        this.tableData = res.data
        for(let i = 0; i < len; i++) {
          request.post("/api/class/find_class_info", null, {
            params: {
              clazzId: res.data[i].clazzId
            }
          }).then(tempRes => {
            console.log(tempRes)
            this.tableData[i].clazzName = tempRes.data.name
          })
          request.post("/api/exam/find_exam_info", null, {
            params: {
              examId: res.data[i].examId
            }
          }).then(tempRes => {
            console.log(tempRes)
            this.tableData[i].examCode = tempRes.data.code
          })
        }
      })
    },
  }
}
</script>

<style scoped>

</style>
