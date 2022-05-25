# 教考分离jkfl接口设计

> StudentController

| 接口名称             | 传入参数                | 返回类型         | 作用             |
| -------------------- | ----------------------- | ---------------- | ---------------- |
| student_register     | User user               | void             | 学生注册         |
| student_updateInfo   | User user               | void             | 学生修改个人信息 |
| submit_answer        | List<StuAnswer>         | void             | 学生提交答案     |
| find_history_exams   | User user,String search | List<Stu_Scores> | 学生查看历史考试 |
| find_classmates_list | User user,Clazz class   | List<User>       | 学生查看同学     |
| stu_find_class       | User user               | List<Clazz>      | 学生查看课程     |
| stu_find_question    | User user,Exam exam     | List<Stu_Answer> | 学生查看题目     |
|                      |                         |                  |                  |

> TeacherController

| 接口名称           | 传入参数  | 返回类型    | 作用         |
| ------------------ | --------- | ----------- | ------------ |
| teacherUpdateInfo  | User user | void        | 教师修改信息 |
| teacherFindMyClass | User user | List<Clazz> | 教师查看课程 |
|                    |           |             |              |

> UserController

| 接口名称       | 传入参数      | 返回类型   | 说明         |
| -------------- | ------------- | ---------- | ------------ |
| login          | User user     | void       | 用户登录     |
| addUser        | User user     | void       | 添加用户     |
| deleteUser     | int userId    | void       | 删除用户     |
| updateUserInfo | User user     | void       | 修改用户信息 |
| findUserList   | String search | list<User> | 查询用户列表 |
|                |               |            |              |
|                |               |            |              |

> ClassController

| 接口名称            | 传入参数                   | 返回类型      | 说明         |
| ------------------- | -------------------------- | ------------- | ------------ |
| add_class           | Clazz clazz                | void          | 添加课程     |
| delete_cass         | int clazzId                | void          | 删除课程     |
| update_class_info   | Clazz clazz                | void          | 更新课程信息 |
| find_class_list     | String search              | list<Clazz>   | 查询课程     |
| add_class_stu       | int clazzId, int studentId | void          | 添加课程学生 |
| delete_class_stu    | int clazzId, int studentId | void          | 删除课程学生 |
| find_class_stu_list | String clazzId, String search              | list<Student> | 查询课程学生 |
|                     |                            |               |              |

> NoticeController

| 接口名称         | 传入参数      | 返回类型     | 说明         |
| ---------------- | ------------- | ------------ | ------------ |
| publish_notice   | Notice notice | void         | 发布通知     |
| delete_notice    | int noticeId  | void         | 删除通知     |
| update_notice    | Notice notice | void         | 修改通知     |
| find_notice_list | String search | list<Notice> | 查询通知列表 |
|                  |               |              |              |

> QuestionController

| 接口名称               | 传入参数          | 返回类型       | 作用               |
| ---------------------- | ----------------- | -------------- | ------------------ |
| add_question           | Question question | void           | 添加问题           |
| delete_question        | Question question | void           | 删除问题           |
| update_question        | Question question | void           | 更新问题           |
| find_question_list     | String search     | List<Question> | 搜索问题列表       |
| add_question_option    | Options options   | void           | 添加选项           |
| delete_question_option | Options options   | void           | 删除选项           |
| update_question_option | Options options   | void           | 更新选项           |
| find_question_option   | Question question | List<Options>  | 显示问题对应的选项 |
|                        |                   |                |                    |
|                        |                   |                |                    |
|                        |                   |                |                    |

> ExamController

| 接口名称            | 传入参数                 | 返回类型        | 作用         |
| ------------------- | ------------------------ | --------------- | ------------ |
| add_exam            | Clazz clazz              | void            | 添加考试     |
| delete_exam         | Clazz clazz              | void            | 删除考试     |
| update_exam         | Clazz clazz              | void            | 更新考试     |
| find_exam_list      | String search            | List<Exam>      | 搜索考试列表 |
| random_set_question | Exam exam,Integer score  | void            | 随机出题     |
| free_set_question   | Exam exam,List<Question> | void            | 自由出题     |
| find_paper_list     | Exam exam                | List<StuAnswer> | 批改试卷     |
| submit_check_anwser | StuScores stuScores      | void            | 提交批改结果 |

> xxxController

| 接口名称 |      |      |
| -------- | ---- | ---- |
|          |      |      |
|          |      |      |
|          |      |      |
