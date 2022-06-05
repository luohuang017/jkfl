<template>
  <div style="width: 100%; padding:10px">
    <el-breadcrumb separator=">>" replace>
      <el-breadcrumb-item :to="{ path: '/pc_stu_my_clazz' }">我的课程</el-breadcrumb-item>
      <el-breadcrumb-item :to="{ path: '/pc_stu_my_exam' }">课程考试</el-breadcrumb-item>
      <el-breadcrumb-item>{{ info.code }}</el-breadcrumb-item>
    </el-breadcrumb>

    <el-table height="510" style="width: 99%" :data="tableData" border stripe default-expand-all>
      <el-table-column type="expand">
        <template #default="props">
          <div v-for="(value, index) in props.row.option" :key="index">
            <p style="margin: 5px"> {{ toChar(index) + ". " }} {{ value.optionText }}</p>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="id" label="编号"/>
      <el-table-column prop="questionText" label="问题"/>
      <el-table-column prop="answerContent" label="答案">
        <template #default="props">
          <el-select v-model="props.row.answerContent" placeholder="请选择你的答案">
            <el-option
                v-for="(value, index) in props.row.option" :key="index"
                :label="toChar(index)" :value="props.row.option[index].optionText"
            />
          </el-select>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 0;">
      <el-popconfirm title="确定提交吗？" @confirm="submit">
        <template #reference>
           <el-button style="margin: auto;" type="primary">提交</el-button>
        </template>
      </el-popconfirm>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "pc_stu_my_exam_info",
  data() {
    return {
      user: [],
      search: '',
      tableData: [],
      info: {},
    }
  },
  created() {
    this.info = sessionStorage.getItem("startExam")
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
      request.post("/api/student/stu_find_question", {
        "user": this.user,
        "exam": {
          "id": this.info.id,
        }
      }).then(res => {
        let len = res.data.length
        this.tableData = res.data
        for(let i = 0; i < len; i++) {
          console.log(res.data[i])
          request.post("/api/student/stu_find_question_option", res.data[i]).then(tempRes => {
            console.log(tempRes.data)
            this.tableData[i].option = tempRes.data
          })
        }
      })
    },
    submit() {
      console.log(this.tableData)
      request.post("/api/student/submit_answer", this.tableData).then(res => {
        if(res.code === '0') {
          this.$message({
            type: "success",
            message: "提交成功"
          })
          this.$router.push("/pc_stu_my_exam")  //提交成功之后进行页面的跳转
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
