<template>
  <div style="width: 100%; padding:10px">
    <el-breadcrumb separator=">>" replace>
      <el-breadcrumb-item :to="{ path: '/pc_tea_my_clazz' }">我的课程</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/pc_tea_my_exam' }">课程考试</el-breadcrumb-item>
      <el-breadcrumb-item>{{ info.code }}</el-breadcrumb-item>
    </el-breadcrumb>

    <el-table height="570" :data="tableData" border stripe default-expand-all>
      <el-table-column prop="id" label="编号"/>
      <el-table-column prop="questionText" label="问题"/>
      <el-table-column prop="answerContent" label="学生答案"/>
      <el-table-column prop="correctAnswer" label="正确答案"/>
      <el-table-column prop="defaultScores" label="该题分数"/>
      <el-table-column prop="stuScores" label="学生得分">
        <template #default="scope">
          <el-input style="width: 60%" v-model="scope.row.stuScores" placeholder="得分"/>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button v-if="scope.row.state === 1" type="primary" @click="submit(scope.row)">提交</el-button>
          height="510" style="width: 99%"
          <el-tag style="margin: 5px" v-if="scope.row.state === 1"  type="warning">未提交</el-tag>
          <el-tag style="margin: 5px" v-else  type="success">已提交</el-tag>
        </template>
      </el-table-column>
    </el-table>

<!--    <div style="margin: 10px 0;">-->
<!--      <el-popconfirm title="确定提交吗？" @confirm="submit">-->
<!--        <template #reference>-->
<!--          <el-button style="margin: auto;" type="primary">提交</el-button>-->
<!--        </template>-->
<!--      </el-popconfirm>-->
<!--    </div>-->
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "pc_tea_my_exam_info",
  data() {
    return {
      user: [],
      search: '',
      tableData: [],
      info: {},
    }
  },
  created() {
    this.info = sessionStorage.getItem("myExamInfo")
    this.info = JSON.parse(this.info);

    this.user = sessionStorage.getItem("user")
    this.user = JSON.parse(this.user);
    this.load()
  },
  methods: {
    toChar(x) {
      return String.fromCharCode(x + 65);
    },
    load() {
      request.post("/api/exam/find_paper_list", this.info).then(res => {
        let len = res.data.length
        this.tableData = res.data
        for(let i = 0; i < len; i++) {
          if(this.tableData[i].stuScores === null) {
            this.tableData[i].stuScores = 0
            this.tableData[i].state = 1
          }
          if(this.tableData[i].answerContent != null) {
            if(this.tableData[i].answerContent === this.tableData[i].correctAnswer) {
              this.tableData[i].stuScores = this.tableData[i].defaultScores
            }
          }
        }
      })
    },
    submit(temp) {
      console.log(temp)
      if(temp.stuScores < 0) {
        this.$message({
          type: "error",
          message: "分数不能小于0"
        })
        this.load()
        return;
      }
      if(temp.stuScores > temp.defaultScores) {
        this.$message({
          type: "error",
          message: "分数不能大于题目分数"
        })
        this.load()
        return;
      }
      request.post("/api/exam/submit_check_answer", {
        "stuAnswer": {
          "id": temp.id,
        },
        "score": temp.stuScores.toString(),
      }).then(res => {
        if(res.code === '0') {
          this.$message({
            type: "success",
            message: "提交成功"
          })
          this.load()
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
