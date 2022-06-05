<template>
  <div style="width: 100%; padding:10px">
    <el-breadcrumb separator=">>" replace>
      <el-breadcrumb-item :to="{ path: '/pc_stu_my_clazz' }">我的课程</el-breadcrumb-item>
      <el-breadcrumb-item>课程考试</el-breadcrumb-item>
    </el-breadcrumb>

    <div style="margin: 10px 0">
      <!--      搜索-->
      <el-input v-model="search" placeholder="输入名称关键字查询" style="width: 20%" clearable @keyup.enter.native="load"/>
      <el-button type="primary" style="margin-left: 5px;" @click="load">查询</el-button>
    </div>
    <el-table height="510" style="width: 99%" :data="tableData" border stripe>
      <el-table-column prop="code" label="考试编号"/>
      <el-table-column prop="clazzId" label="课程编号"/>
      <el-table-column prop="myStartTime" label="开始时间"/>
      <el-table-column prop="myEndTime" label="结束时间"/>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button v-if="check(scope.row) === 0" style="width: 40%;margin: 5px auto;" type="success"
                     disabled>
            未开始
          </el-button>
          <el-button v-else-if="check(scope.row) === 1" style="width: 40%;margin: 5px auto;" type="primary"
                     @click="goStartExam(scope.row)">
            开始答题
          </el-button>
          <el-button v-else style="width: 40%;margin: 5px auto;" type="info"
                     disabled>
            已结束
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "pc_stu_my_exam",
  data() {
    return {
      user: [],
      search: '',
      tableData: [],
      info: {},
    }
  },
  created() {
    this.info = sessionStorage.getItem("stuMyExam")
    this.info = JSON.parse(this.info);

    this.user = sessionStorage.getItem("user")
    this.user = JSON.parse(this.user);
    this.load()
  },
  methods: {
    load() {
      request.post("/api/exam/find_exam_list", {
        "search": this.search,
        "clazz": {
          "id": this.info.clazzId,
        }
      }).then(res => {
        let len = res.data.length
        this.tableData = res.data
        for(let i = 0; i < len; i++) {
          let d1 = new Date(Date.parse(res.data[i].startTime));
          this.tableData[i].myStartTimeSp = d1.getTime()
          d1.setTime(d1.getTime() + 8 * 3600 * 1000)
          let s1 = d1.toISOString()
          this.tableData[i].myStartTime = s1.substr(0, 10) + " " + s1.substr(11, 8)

          let d2 = new Date(Date.parse(res.data[i].endTime));
          this.tableData[i].myEndTimeSp = d2.getTime()
          d2.setTime(d2.getTime() + 8 * 3600 * 1000)
          let s2 = d2.toISOString()
          this.tableData[i].myEndTime = s2.substr(0, 10) + " " + s2.substr(11, 8)
        }
      })
    },
    check(temp) {
      let date = new Date()
      let date2 = new Date()
      date2.setTime(temp.myStartTimeSp)
      if(date.getTime() < temp.myStartTimeSp) return 0;
      else if(date.getTime() < temp.myEndTimeSp) return 1;
      else return 2
    },
    goStartExam(temp) {
      sessionStorage.setItem("startExam", JSON.stringify(temp.valueOf()))
      this.$router.push('./pc_stu_my_exam_info')
    }
  }
}
</script>

<style scoped>

</style>
