import {createRouter, createWebHistory} from 'vue-router'
import pcLayout from '../layout/pc_layout.vue'
import mLayout from '../layout/m_layout.vue'

const pc_router = [
  {
    path: '/',
    name: 'pc_layout',
    component: pcLayout,
    redirect: "/pc_login",
    children: [
      {
        path: '/pc_index',
        name: 'pc_index',
        component: () => import ("@/views/pc_index"),
      },
      {
        path: '/pc_stu_list',
        name: 'pc_stu_list',
        component: () => import ("@/views/pc_stu_list"),
      },
      {
        path: '/pc_teacher_list',
        name: 'pc_teacher_list',
        component: () => import ("@/views/pc_teacher_list"),
      },
      {
        path: '/pc_clazz_list',
        name: 'pc_clazz_list',
        component: () => import ("@/views/pc_clazz_list"),
      },
      {
        path: '/pc_notice_list',
        name: 'pc_notice_list',
        component: () => import ("@/views/pc_notice_list"),
      },
      {
        path: '/pc_question_list',
        name: 'pc_question_list',
        component: () => import ("@/views/pc_question_list"),
      },
      {
        path: '/pc_exam_list',
        name: 'pc_exam_list',
        component: () => import ("@/views/pc_exam_list"),
      },
      {
        path: '/pc_tea_my_clazz',
        name: 'pc_tea_my_clazz',
        component: () => import ("@/views/pc_tea_my_clazz"),
      },
      {
        path: '/pc_tea_my_clazz_info',
        name: 'pc_tea_my_clazz_info',
        component: () => import ("@/views/pc_tea_my_clazz_info"),
      },
      {
        path: '/pc_tea_my_exam',
        name: 'pc_tea_my_exam',
        component: () => import ("@/views/pc_tea_my_exam"),
      },
      {
        path: '/pc_tea_my_exam_info',
        name: 'pc_tea_my_exam_info',
        component: () => import ("@/views/pc_tea_my_exam_info"),
      },
      {
        path: '/pc_stu_my_clazz',
        name: 'pc_stu_my_clazz',
        component: () => import ("@/views/pc_stu_my_clazz"),
      },
      {
        path: '/pc_stu_my_clazz_info',
        name: 'pc_stu_my_clazz_info',
        component: () => import ("@/views/pc_stu_my_clazz_info"),
      },
      {
        path: '/pc_stu_my_exam',
        name: 'pc_stu_my_exam',
        component: () => import ("@/views/pc_stu_my_exam"),
      },
      {
        path: '/pc_stu_my_exam_info',
        name: 'pc_stu_my_exam_info',
        component: () => import ("@/views/pc_stu_my_exam_info"),
      },
      {
        path: '/pc_stu_history_exams',
        name: 'pc_stu_history_exams',
        component: () => import ("@/views/pc_stu_history_exams"),
      },
      {
        path: '/pc_tea_notice',
        name: 'pc_tea_notice',
        component: () => import ("@/views/pc_tea_notice"),
      },
    ]
  },
  {
    path: '/pc_login',
    name: 'pc_login',
    component: () => import("@/views/pc_login"),
  },
  {
    path: '/pc_register',
    name: 'pc_register',
    component: () => import("@/views/pc_register"),
  },
]

const m_router = [
  {
    path: '/',
    name: 'm_layout',
    component: mLayout,
    redirect: "/m_login",
    children: [
      {
        path: '/m_index',
        name: 'm_index',
        component: () => import ("@/views/m_index"),
      },
      {
        path: '/m_model',
        name: 'm_model',
        component: () => import ("@/views/m_model"),
      },
    ]
  },
  {
    path: '/m_login',
    name: 'm_login',
    component: () => import("@/views/m_login"),
  },
  {
    path: '/m_register',
    name: 'm_register',
    component: () => import("@/views/m_register"),
  },
]

const ISMOBILE = function () {
  let flag = navigator.userAgent.match(
    /(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i
  );
  return flag;
};

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes: ISMOBILE() ? m_router : pc_router,
})

export default router
