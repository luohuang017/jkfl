<template>
  <div style="width: 100%; padding:10px">
    <el-breadcrumb separator=">>" replace>
      <el-breadcrumb-item :to="{ path: '/pc_tea_my_clazz' }">我的课程</el-breadcrumb-item>
      <el-breadcrumb-item>课程考试</el-breadcrumb-item>
    </el-breadcrumb>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">添加考试</el-button>
    </div>
    <div style="margin: 10px 0">
      <!--      搜索-->
      <el-input v-model="search" placeholder="输入名称关键字查询" style="width: 20%" clearable @keyup.enter.native="load"/>
      <el-button type="primary" style="margin-left: 5px;" @click="load">查询</el-button>
    </div>
    <el-table height="510" style="width: 99%" :data="tableData" border stripe>
      <el-table-column prop="code" label="考试名称"/>
      <el-table-column prop="clazzCode" label="课程编号"/>
      <el-table-column prop="clazzName" label="课程名称"/>
      <el-table-column prop="myStartTime" label="开始时间"/>
      <el-table-column prop="myEndTime" label="结束时间"/>
      <el-table-column fixed="right" label="操作">
        <template #default="scope">
          <el-button type="primary" @click="handleEdit(scope.row)" :disabled="check(scope.row) !== 0">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row)">
            <template #reference>
              <el-button native-type="button" type="danger" :disabled="check(scope.row) !== 0">删除</el-button>
            </template>
          </el-popconfirm>
          <el-button style="float: left;margin: 5px 0" type="primary" @click="goExamDetail(scope.row)" :disabled="check(scope.row) !== 2">
            批改试卷
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div style="margin: 10px 0">
      <el-dialog v-model="dialogVisible" title="添加考试" width="30%">
        <el-form ref="form" :model="form" label-width="120px">
          <el-form-item label="考试名称">
            <el-input v-model="form.code" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="课程编号">
            <el-input v-model="form.clazzCode" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="考试开始时间">
            <el-date-picker
                v-model="form.startTime"
                type="datetime"
                placeholder="请选择开始时间"
                style="width: 75%"
            />
          </el-form-item>
          <el-form-item label="考试结束时间">
            <el-date-picker
                v-model="form.endTime"
                type="datetime"
                placeholder="请选择结束时间"
                style="width: 75%"
            />
          </el-form-item>
          <el-form-item label="出题方式">
            <el-radio-group v-model="form.pattern">
              <el-radio label="随机出题"/>
              <el-radio label="自由出题" disabled/>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="难度系数" v-if="form.pattern === '随机出题'">
            <el-input v-model="form.score" style="width: 75%" placeholder="0-1之间"/>
          </el-form-item>
          <el-form-item label="选择题数量" v-if="form.pattern === '随机出题'">
            <el-input v-model="form.xzNum" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="主观题数量" v-if="form.pattern === '随机出题'">
            <el-input v-model="form.zgNum" style="width: 75%"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmitAdd">创建</el-button>
            <el-button @click="dialogVisible = false;load()">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>

    <div style="margin: 10px 0">
      <el-dialog v-model="dialogVisibleEdit" title="修改考试" width="40%">
        <el-form ref="editForm" :model="editForm" label-width="120px">
          <el-form-item label="考试编号">
            <el-input v-model="editForm.code" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="课程编号">
            <el-input v-model="editForm.clazzCode" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="考试开始时间">
            <el-date-picker
                v-model="editForm.startTime"
                type="datetime"
                placeholder="请选择开始时间"
                style="width: 75%"
            />
          </el-form-item>
          <el-form-item label="考试结束时间">
            <el-date-picker
                v-model="editForm.endTime"
                type="datetime"
                placeholder="请选择结束时间"
                style="width: 75%"
            />
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
  name: "pc_tea_my_exam",
  data() {
    return {
      user: [],
      dialogVisible: false,
      dialogVisibleEdit: false,
      search: '',
      tableData: [],
      form: {},
      editForm: {},
      info: {},
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
    load() {
      request.post("/api/exam/find_exam_list", {
        "search": this.search,
        "clazz": {
          "id": this.info.id,
        }
      }).then(res => {
        let len = res.data.length
        this.tableData = res.data
        for(let i = 0; i < len; i++) {
          request.post("/api/class/find_class_info", null, {
            params: {
              clazzId: res.data[i].clazzId,
            }
          }).then(res => {
            this.tableData[i].clazzCode = res.data.code
            this.tableData[i].clazzName = res.data.name
          })

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
    add() {
      this.dialogVisible = true
      this.form = {}
    },
    onSubmitAdd() {
      if(this.form.clazzCode === undefined) {
        this.$message({
          type: "error",
          message: "课程编号不能为空"
        })
        return;
      }
      if(this.form.code === undefined) {
        this.$message({
          type: "error",
          message: "考试名称不能为空"
        })
        return;
      }
      if(this.form.startTime === undefined) {
        this.$message({
          type: "error",
          message: "考试开始时间不能为空"
        })
        return;
      }
      if(this.form.endTime === undefined) {
        this.$message({
          type: "error",
          message: "考试结束时间不能为空"
        })
        return;
      }
      if(this.form.pattern === undefined) {
        this.$message({
          type: "error",
          message: "请选择出题方式"
        })
        return;
      }
      if(this.form.score === undefined) {
        this.$message({
          type: "error",
          message: "难度系数不能为空"
        })
        return;
      }
      if(this.form.xzNum === undefined) {
        this.$message({
          type: "error",
          message: "选择题数量不能为空"
        })
        return;
      }
      if(this.form.zgNum === undefined) {
        this.$message({
          type: "error",
          message: "主观题数量不能为空"
        })
        return;
      }
      request.post("/api/class/find_class_info", null, {
        params: {
          clazzId: 0,
          clazzCode: this.form.clazzCode
        }
      }).then(res => {
        this.form.clazzId = res.data.id
        request.post("/api/exam/add_exam", {
          "clazz": res.data,
          "exam": this.form,
        }).then(res2 => {
          if(res2.code === '0') {
            this.$message({
              type: "success",
              message: "创建成功"
            })
            request.post("/api/exam/random_set_question", {
              "clazz": {
                "id": res.data.id
              },
              "exam": {
                "id": res2.data.id
              },
              "score": this.form.score,
              "xzNum": this.form.xzNum,
              "zgNum": this.form.zgNum
            }).then(res3 => {
              if(res3.code === '0') {
                this.$message({
                  type: "success",
                  message: "创建成功"
                })
                this.load()
                this.dialogVisible = false
              } else {
                this.$message({
                  type: "error",
                  message: res3.msg
                })
              }
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        })
      })
    },
    handleEdit(temp) {
      this.dialogVisibleEdit = true
      this.editForm = temp
    },
    onSubmit() {
      request.post("/api/class/find_class_info", null, {
        params: {
          clazzId: 0,
          clazzCode: this.form.clazzCode
        }
      }).then(res => {
        this.editForm.clazzId = res.data.id
        request.post("/api/exam/update_exam", this.editForm).then(res2 => {
          if(res2.code === '0') {
            this.$message({
              type: "success",
              message: "修改成功"
            })
            this.load()
            this.dialogVisibleEdit = false
          } else {
            this.$message({
              type: "error",
              message: res2.msg
            })
          }
        })
      })
    },
    handleDelete(temp) {
      request.post("/api/exam/delete_exam", temp).then(res => {
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
    goExamDetail(temp) {
      sessionStorage.setItem("myExamInfo", JSON.stringify(temp.valueOf()))
      this.$router.push('/pc_tea_my_exam_info')
    }
  }
}
</script>

<style scoped>

</style>
