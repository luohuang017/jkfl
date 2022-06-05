<template>
  <div>
    <el-menu
        style="width: 200px;min-height:calc(100vh - 50px) "
        :default-active="$route.path"
        router
    >
      <el-sub-menu index="1" v-if="user.userType === 'manager'">
        <template #title>
          <el-icon>
            <setting/>
          </el-icon>
          <span>用户管理</span>
        </template>
        <el-menu-item index="pc_stu_list">
          学生名单
        </el-menu-item>
        <el-menu-item index="pc_teacher_list">
          教师名单
        </el-menu-item>
      </el-sub-menu> <!--  用户管理 -->
      <el-sub-menu index="2" v-if="user.userType === 'manager'">
        <template #title>
          <el-icon>
            <Reading/>
          </el-icon>
          <span>课程管理</span>
        </template>
        <el-menu-item index="pc_clazz_list">
          课程列表
        </el-menu-item>
      </el-sub-menu> <!--  课程管理 -->
      <el-sub-menu index="3" v-if="user.userType === 'manager'">
        <template #title>
          <el-icon>
            <Bell/>
          </el-icon>
          <span>通知管理</span>
        </template>
        <el-menu-item index="pc_notice_list">
          通知列表
        </el-menu-item>
      </el-sub-menu> <!--  通知管理 -->
      <el-sub-menu index="4" v-if="user.userType === 'manager'">
        <template #title>
          <el-icon>
            <Tickets/>
          </el-icon>
          <span>问题管理</span>
        </template>
        <el-menu-item index="pc_question_list">
          问题列表
        </el-menu-item>
      </el-sub-menu> <!--  问题管理 -->
      <el-sub-menu index="5" v-if="user.userType === 'manager'">
        <template #title>
          <el-icon>
            <Collection/>
          </el-icon>
          <span>考试管理</span>
        </template>
        <el-menu-item index="pc_exam_list">
          考试列表
        </el-menu-item>
      </el-sub-menu> <!--  考试管理 -->

      <el-sub-menu index="1" v-if="user.userType === 'teacher'">
        <template #title>
          <el-icon>
            <Reading/>
          </el-icon>
          <span>课程管理</span>
        </template>
        <el-menu-item index="pc_tea_my_clazz">
          我的课程
        </el-menu-item>
        <el-menu-item index="">

        </el-menu-item>
      </el-sub-menu> <!--  课程管理 -->
      <el-sub-menu index="2" v-if="user.userType === 'teacher'">
        <template #title>
          <el-icon>
            <Tickets/>
          </el-icon>
          <span>问题管理</span>
        </template>
        <el-menu-item index="pc_question_list">
          问题列表
        </el-menu-item>
      </el-sub-menu> <!--  问题管理 -->
      <el-sub-menu index="3" v-if="user.userType === 'teacher'">
        <template #title>
          <el-icon>
            <Bell/>
          </el-icon>
          <span>通知管理</span>
        </template>
        <el-menu-item index="pc_tea_notice">
          发布通知
        </el-menu-item>
      </el-sub-menu> <!--  通知管理 -->

      <el-sub-menu index="1" v-if="user.userType === 'student'">
        <template #title>
          <el-icon>
            <Reading/>
          </el-icon>
          <span>课程管理</span>
        </template>
        <el-menu-item index="pc_stu_my_clazz">
          我的课程
        </el-menu-item>
        <el-menu-item index="pc_stu_history_exams">
          历史考试
        </el-menu-item>
      </el-sub-menu> <!--  课程管理 -->
    </el-menu>
  </div>
</template>

<script>
export default {
  name: "pc_aside",
  data() {
    return {
      user: [],
    }
  },
  created() {
    this.user = JSON.parse(sessionStorage.getItem("user"))
  }
}
</script>

<style scoped>

</style>
