<template>
  <div style="width: 100%; padding:10px">
    <el-breadcrumb separator=">>" replace>
      <el-breadcrumb-item>我的课程</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin: 10px 0">
      <!--      搜索-->
      <el-input v-model="search" placeholder="输入名称关键字查询" style="width: 20%" clearable @keyup.enter.native="load"/>
      <el-button type="primary" style="margin-left: 5px;" @click="load">查询</el-button>
    </div>
    <el-table height="510" style="width: 99%" :data="tableData" border stripe>
      <el-table-column prop="clazzName" label="课程名称"/>
      <el-table-column prop="teacherName" label="授课教师"/>
      <el-table-column prop="clazzCode" label="课程编号"/>
      <el-table-column prop="myIsClose" label="是否关闭"/>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button style="margin: 5px auto;" type="primary" @click="goDetail(scope.row)">查看课程学生</el-button>
          <el-button style="margin: 5px auto;" color="#626aef" type="primary" @click="goExam(scope.row)">查看课程考试
          </el-button>
        </template>
      </el-table-column>
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
      request.post("/api/student/stu_find_class", this.user).then(res => {
        console.log(res)
        let len = res.data.length
        this.tableData = res.data
        for(let i = 0; i < len; i++) {
          request.post("/api/class/find_class_info", null, {
            params: {
              clazzId : res.data[i].clazzId
            }
          }).then(res2 => {
            this.tableData[i].clazzName = res2.data.name
            this.tableData[i].clazzCode = res2.data.code
            this.tableData[i].teacherId = res2.data.teacherId
            this.tableData[i].isClose = res2.data.isClose
            request.post("/api/user/find_user_info", null, {
              params: {
                userId : res2.data.teacherId
              }
            }).then(res3 => {
              this.tableData[i].teacherName = res3.data.name
            })
          })
          if(this.tableData[i].isClose === 1) this.tableData[i].myIsClose = '是'
          else this.tableData[i].myIsClose = '否'
        }

      })
    },
    goDetail(temp) {
      sessionStorage.setItem("stuClazzInfo", JSON.stringify(temp.valueOf()))
      this.$router.push('/pc_stu_my_clazz_info')
    },
    goExam(temp) {
      sessionStorage.setItem("stuMyExam", JSON.stringify(temp.valueOf()))
      this.$router.push('/pc_stu_my_exam')
    }
  }
}
</script>

<style scoped>

</style>
