<template>
  <div style="width: 100%; padding:10px">
    <el-breadcrumb separator=">>" replace>
      <el-breadcrumb-item>问题列表</el-breadcrumb-item>
    </el-breadcrumb>
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">添加问题</el-button>
    </div>
    <div style="margin: 10px 0">
      <!--      搜索-->
      <el-input v-model="search" placeholder="输入名称关键字查询" style="width: 20%" clearable @keyup.enter.native="load"/>
      <el-button type="primary" style="margin-left: 5px;" @click="load">查询</el-button>
    </div>
    <el-table height="510" style="width: 99%" :data="tableData" border stripe default-expand-all>
      <el-table-column type="expand">
        <template #default="props">
          <div v-for="(value, index) in props.row.options" :key="index">
            <p style="margin: 5px"> {{ toChar(index) + ". " }} {{ value.optionText }}</p>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="id" label="编号"/>
      <el-table-column prop="text" label="题目"/>
      <el-table-column prop="myCategory" label="类型"/>
      <el-table-column prop="sampleAnswer" label="答案"/>
      <el-table-column prop="difficultyRatio" label="得分率"/>
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
      <el-dialog v-model="dialogVisible" title="添加问题" width="30%">
        <el-form ref="form" :model="form" label-width="120px">
          <el-form-item label="题目">
            <el-input v-model="form.text" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="类型">
            <el-radio-group v-model="form.myCategory" style="width: 75%">
              <el-radio label="选择题"/>
              <el-radio label="主观题"/>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="答案">
            <el-input v-model="form.sampleAnswer" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="课程编号">
            <el-input v-model="form.clazzCode" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="难度系数">
            <el-input v-model="form.difficultyRatio" placeholder="0-1之间" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="分值">
            <el-input v-model="form.defaultScores" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="选项">
            <div v-for="(item, index) in form.options" :key="index">
              <div>
                <el-input v-model="form.options[index].optionText" style="width: 50%;margin: 0 5px 0 0 ;"/>
                <el-button style="width: 20%" type="danger" @click="myDelete(index)">-</el-button>
              </div>
            </div>
            <el-input v-model="option" style="width: 50%;margin: 0 5px 0 0 ;"></el-input>
            <el-button style="width: 20%" type="success" @click="addOption">+</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmitAdd">创建</el-button>
            <el-button @click="dialogVisible = false;load()">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
    <div style="margin: 10px 0">
      <el-dialog v-model="dialogVisibleEdit" title="修改教师信息" width="30%">
        <el-form ref="editForm" :model="editForm" label-width="120px">
          <el-form-item label="题目">
            <el-input v-model="editForm.text" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="类型">
            <el-radio-group v-model="editForm.myCategory" style="width: 75%">
              <el-radio label="选择题"/>
              <el-radio label="主观题"/>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="答案">
            <el-input v-model="editForm.sampleAnswer" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="课程编号">
            <el-input v-model="editForm.clazzCode" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="难度系数">
            <el-input v-model="editForm.difficultyRatio" placeholder="0-1之间" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="分值">
            <el-input v-model="editForm.defaultScores" style="width: 75%"/>
          </el-form-item>
          <el-form-item label="选项">
            <div v-for="(item, index) in editForm.options" :key="index">
              <div>
                <el-input v-model="editForm.options[index].optionText" style="width: 50%;margin: 0 5px 0 0 ;"/>
                <el-button style="width: 20%" type="danger" @click="myEditDelete(index)">-</el-button>
              </div>
            </div>
            <el-input v-model="option" style="width: 50%;margin: 0 5px 0 0 ;"></el-input>
            <el-button style="width: 20%" type="success" @click="addEditOption">+</el-button>
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
  name: "pc_question_list",
  data() {
    return {
      user: [],
      dialogVisible: false,
      dialogVisibleEdit: false,
      search: '',
      tableData: [],
      form: {},
      editForm: {},
      value: '',
      option: null,
    }
  },
  created() {
    this.user = sessionStorage.getItem("user")
    this.user = JSON.parse(this.user);
    this.load()
  },
  methods: {
    toChar(x) {
      return String.fromCharCode(x + 65);
    },
    myDelete(index) {
      this.form.options.splice(index, 1)
    },
    addOption() {
      if(this.option === undefined || this.option === null || this.option === "") {
        this.$message({
          type: "error",
          message: "选项值为空"
        })
        return;
      }
      let w = {
        optionText: this.option
      }
      this.form.options.push(w)
      this.option = null
    },
    myEditDelete(index) {
      this.editForm.options.splice(index, 1)
    },
    addEditOption() {
      if(this.option === undefined || this.option === null || this.option === "") {
        this.$message({
          type: "error",
          message: "选项值为空"
        })
        return;
      }
      let w = {
        optionText: this.option
      }
      this.editForm.options.push(w)
      this.option = null
    },
    load() {
      request.post("/api/question/find_question_list", null, {
        params: {
          // type: "teacher",
          search: this.search
        }
      }).then(res => {
        // console.log(res)
        let len = res.data.length
        this.tableData = res.data
        for(let i = 0; i < len; i++) {
          if(this.tableData[i].category === "xz") this.tableData[i].myCategory = "选择题"
          else this.tableData[i].myCategory = "主观题"
          request.post("/api/question/find_question_options_list", res.data[i]).then(tempRes => {
            // console.log(tempRes.data)
            this.tableData[i].options = tempRes.data
          })
        }
      })
    },
    add() {
      this.dialogVisible = true
      this.form = {
        options: [],
      }
    },
    onSubmitAdd() {
      if(this.form.myCategory === "选择题") this.form.category = "xz"
      else  this.form.category = "zg"
      request.post("/api/question/add_question", this.form).then(res => {
        if(res.code === '0') {
          this.$message({
            type: "success",
            message: "创建成功"
          })
          let len = this.form.options.length
          for(let i = 0;i < len;i++) {
            if(this.form.options[i].optionText === this.form.sampleAnswer) this.form.options[i].isCorrect = 1
            else this.form.options[i].isCorrect = 0
            request.post("/api/question/add_question_option", this.form.options[i]).then(res => {
              // console.log(res)
            })
          }
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
    handleEdit(temp) {
      this.dialogVisibleEdit = true
      this.editForm = temp
    },
    onSubmit() {
      if(this.editForm.myCategory === "选择题") this.editForm.category = "xz"
      else  this.editForm.category = "zg"
      request.post("/api/question/update_question", this.editForm).then(res => {
        if(res.code === '0') {
          this.$message({
            type: "success",
            message: "修改成功"
          })
          let len = this.editForm.options.length
          for(let i = 0;i < len;i++) {
            if(this.editForm.options[i].optionText === this.editForm.sampleAnswer) this.editForm.options[i].isCorrect = 1
            else this.editForm.options[i].isCorrect = 0
            request.post("/api/question/update_question_option", this.editForm.options[i]).then(res => {
              // console.log(res)
            })
          }
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
      request.post("/api/question/delete_question", temp).then(res => {
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
