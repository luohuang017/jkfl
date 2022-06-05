<template>
  <div style="width: 100%; padding:10px">
    <el-breadcrumb separator=">>" replace>
      <el-breadcrumb-item>考试列表</el-breadcrumb-item>
    </el-breadcrumb>

    <div style="margin: 10px 0">
      <!--      搜索-->
      <el-input v-model="search" placeholder="输入名称关键字查询" style="width: 20%" clearable @keyup.enter.native="load"/>
      <el-button type="primary" style="margin-left: 5px;" @click="load">查询</el-button>
    </div>

    <el-table height="580" :data="tableData" border stripe>
      <el-table-column prop="id" label="编号"/>
      <el-table-column prop="code" label="考试编号"/>
      <el-table-column prop="clazzId" label="课程编号"/>
      <el-table-column prop="myStartTime" label="开始时间"/>
      <el-table-column prop="myEndTime" label="结束时间"/>
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
      <el-dialog v-model="dialogVisibleEdit" title="修改考试" width="40%">
        <el-form ref="editForm" :model="editForm" label-width="120px">
          <el-form-item label="编号">
            <el-input v-model="editForm.id" style="width: 75%" disabled/>
          </el-form-item>
          <el-form-item label="考试编号">
            <el-input v-model="editForm.code" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="课程编号">
            <el-input v-model="editForm.clazzId" style="width: 75%"/>
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
  name: "pc_exam_list",
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
      request.post("/api/exam/find_exam_list", {
        "search": this.search,
        "clazz": null
      }).then(res => {
        let len = res.data.length
        this.tableData = res.data
        for(let i = 0; i < len; i++) {
          let d1 = new Date(Date.parse(res.data[i].startTime));
          d1.setTime(d1.getTime() + 8*3600*1000)
          let s1 = d1.toISOString()
          this.tableData[i].myStartTime = s1.substr(0, 10) + " " + s1.substr(11, 8)

          let d2 = new Date(Date.parse(res.data[i].endTime));
          d2.setTime(d2.getTime() + 8*3600*1000)
          let s2 = d2.toISOString()
          this.tableData[i].myEndTime = s2.substr(0, 10) + " " + s2.substr(11, 8)
        }
      })
    },
    handleEdit(temp) {
      this.dialogVisibleEdit = true
      this.editForm = temp
    },
    onSubmit() {
      request.post("/api/exam/update_exam", this.editForm).then(res => {
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
    }
  }
}
</script>

<style scoped>

</style>
