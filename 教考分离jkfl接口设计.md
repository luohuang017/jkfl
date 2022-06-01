# 教考分离jkfl接口设计

[TOC]

## 学生管理

### 学生注册

```
请求方式：POST
接口地址：localhost:8765/student/student_register
```

```json
传入格式：
{
    "code":"31901007",
    "name":"uuu",
    "pwd":"123",
    "sex":"男",
    "fullname":"uuu"
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": null
}
错误返回Json：
{
    "code": "-1",
    "msg": "用户名已存在",
    "data": null
}
```

### 学生修改个人信息

```
请求方式：POST
接口地址：localhost:8765/student/student_updateInfo
```

```json
传入格式：
{
    "id":7,
    "code":"31901007",
    "name":"uuu",
    "pwd":"123",
    "sex":"男",
    "fullname":"uuuu"
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": null
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```

### 学生提交答案

```
请求方式：POST
接口地址：localhost:8765/student/submit_answer
```

```json
传入格式：
[
    {
        "id":1,
        "examId":1,
        "questionId":2,
        "questionText":"1+2=?",
        "questionImg":"https://iknow-pic.cdn.bcebos.com/2fdda3cc7cd98d103c0c0033253fb80e7aec90d3",
        "questionCategory":"xz",
        "stuId":7,
        "answerContent":"3",
        "correctAnswer":"3",
        "stuScores":null,
        "defaultScores":2.00
    },
    {
        "id":2,
        "examId":1,
        "questionId":10,
        "questionText":"1+11=?",
        "questionImg":"https://iknow-pic.cdn.bcebos.com/2fdda3cc7cd98d103c0c0033253fb80e7aec90d3",
        "questionCategory":"zg",
        "stuId":7,
        "answerContent":"11",
        "correctAnswer":"12",
        "stuScores":null,
        "defaultScores":5.00
    }
]
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": null
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```

### 学生查看历史考试

```
请求方式：POST
接口地址：localhost:8765/student/find_history_exams
```

```json
传入格式：
{
    "id":1,
    "code":"31901001",
    "name":"qqq",
    "pwd":"123",
    "sex":"男",
    "userType":"student",
    "fullname":"qqq"
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": [
        {
            "id": 1,
            "clazzId": 1,
            "examId": 1,
            "stuId": 1,
            "scores": 89.0,
            "endTime": "2022-05-30",
            "state": "已批改"
        }
    ]
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```

### 学生查看课程

```
请求方式：POST
接口地址：localhost:8765/student/stu_find_class
```

```json
传入格式：
{
    "id":1,
    "code":"31901001",
    "name":"qqq",
    "pwd":"123",
    "sex":"男",
    "userType":"student",
    "fullname":"qqq"
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": [
        {
            "id": 1,
            "stuId": 1,
            "clazzId": 1,
            "stuCode": "31901001",
            "stuName": "qqq"
        }
    ]
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```

### 学生查看题目

```
请求方式：POST
接口地址：localhost:8765/student/stu_find_question
```

```json
传入格式：
{
    "user":{
        "id":1,
        "code":"31901001",
        "name":"qqq",
        "pwd":"123",
        "sex":"男",
        "userType":"student",
        "fullname":"qqq"
    },
    "exam":{
        "id":1
    }
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": [
        {
            "id": 1,
            "examId": 1,
            "questionId": 2,
            "questionText": "1+2=?",
            "questionImg": "https://iknow-pic.cdn.bcebos.com/2fdda3cc7cd98d103c0c0033253fb80e7aec90d3",
            "questionCategory": "xz",
            "stuId": 1,
            "answerContent": "3",
            "correctAnswer": "3",
            "stuScores": 2.0,
            "defaultScores": 2.0
        },
        {
            "id": 2,
            "examId": 1,
            "questionId": 10,
            "questionText": "1+11=?",
            "questionImg": "https://iknow-pic.cdn.bcebos.com/2fdda3cc7cd98d103c0c0033253fb80e7aec90d3",
            "questionCategory": "zg",
            "stuId": 1,
            "answerContent": "11",
            "correctAnswer": "12",
            "stuScores": null,
            "defaultScores": 5.0
        }
    ]
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```

### 学生查看题目选项

```
请求方式：POST
接口地址：localhost:8765/student/stu_find_question_option
```

```json
传入格式：
{
    "id":1,
    "examId":1,
    "questionId":2,
    "questionText":"1+2=?",
    "questionImg":"https://iknow-pic.cdn.bcebos.com/2fdda3cc7cd98d103c0c0033253fb80e7aec90d3",
    "questionCategory":"xz",
    "stuId":7,
    "answerContent":"3",
    "correctAnswer":"3",
    "stuScores":null,
    "defaultScores":2.00
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": [
        {
            "id": 1,
            "stuAnswerId": 1,
            "optionText": "1"
        },
        {
            "id": 2,
            "stuAnswerId": 1,
            "optionText": "2"
        },
        {
            "id": 3,
            "stuAnswerId": 1,
            "optionText": "3"
        }
    ]
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```

## 教师管理

### 教师查询课程

```
请求方式：POST
接口地址：localhost:8765/teacher/teacher_find_myclass
```

```json
传入格式：
{
    "id":8,
    "code":"1000",
    "name":"iii",
    "pwd":"123",
    "sex":"男",
    "userType":"teacher",
    "fullname":"iii"
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": [
        {
            "id": 1,
            "teacherId": 8,
            "code": "00000",
            "name": "高数",
            "isClose": 0
        }
    ]
}
错误返回Json：
{
    "code": "-1",
    "msg": "用户名已存在",
    "data": null
}
```



## 问题管理

### 添加问题

```
请求方式：POST
接口地址：localhost:8765/question/add_question
```

```json
传入格式：
{
    "text":"3+5=?",
    "img":"address",
    "category":"xz/zg",
    "sample_answer":"8",
    "clazz_code":"00000",
    "difficulty_ratio":0.8,
    "default_scores":4.0
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": null
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```



### 删除问题

```
请求方式：POST
接口地址：localhost:8765/question/delete_question
```

```json
传入格式：
{
    "id":3
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": null
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```



###  更新问题

```
请求方式：POST
接口地址：localhost:8765/question/update_question
```

```json
传入格式：
{
    "id":2,
    "text":"3+5=?",
    "img":"address",
    "category":"xz/zg",
    "sample_answer":"8",
    "clazz_code":"00000",
    "difficulty_ratio":0.8,
    "default_scores":4.0
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": null
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```



### 搜索问题列表

```
请求方式：POST
接口地址：localhost:8765/question/find_question_list
```

```json
传入格式：
{
    "search":"1"
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": [
        {
            "id": 1,
            "text": "1+1=?",
            "img": "https://iknow-pic.cdn.bcebos.com/2fdda3cc7cd98d103c0c0033253fb80e7aec90d3",
            "category": "xz",
            "sampleAnswer": "2",
            "clazzCode": "00000",
            "acScores": 18.0,
            "totScores": 14.0,
            "difficultyRatio": 1.29,
            "defaultScores": 2.0
        }
    ]
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```



### 添加选项

```
请求方式：POST
接口地址：localhost:8765/question/add_question_option
```

```json
传入格式：
{
    "optionText":"777",
    "questionId":6,
    "isCorrect":"0"
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": null
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```



### 删除选项

```
请求方式：POST
接口地址：localhost:8765/question/delete_question_option
```

```json
传入格式：
{
    "id":1,
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": null
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```



### 更新选项

```
请求方式：POST
接口地址：localhost:8765/question/update_question_option
```

```json
传入格式：
{
    "id":2,
    "optionText":"666",
    "questionId":6,
    "isCorrect":"0"
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": null
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```



### 显示问题对应的选项

```
请求方式：POST
接口地址：localhost:8765/question/find_class_stu_list
```

```json
传入格式：
{
    "id":3
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": [
        {
            "id": 9,
            "optionText": "1",
            "questionId": 3,
            "isCorrect": "0"
        },
        {
            "id": 10,
            "optionText": "2",
            "questionId": 3,
            "isCorrect": "0"
        },
        {
            "id": 11,
            "optionText": "3",
            "questionId": 3,
            "isCorrect": "0"
        },
        {
            "id": 12,
            "optionText": "5",
            "questionId": 3,
            "isCorrect": "0"
        }
    ]
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```





## 考试管理



### 添加考试

```
请求方式：POST
接口地址：localhost:8765/exam/add_exam
```

```json
传入格式：
{
    "clazz":{
        "id":1,
        "teacher_id":1,
        "code":"0000",
        "name":"高数",
        "is_close":0
    },
    "exam":{
        "code":"考试名称",
        "clazz_id":1,
        "start_time":"2022-06-02",
        "end_time":"2022-06-03"
    }

}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": null
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```



### 删除考试

```
请求方式：POST
接口地址：localhost:8765/exam/delete_exam
```

```json
传入格式：
{
    "id":1
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": null
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```



### 更新考试

```
请求方式：POST
接口地址：localhost:8765/exam/update_exam
```

```json
传入格式：
{
    "id":1,
    "code":"xxx",
    "start_time":"2022-07-01",
    "end_time":"2022-07-02"
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": null
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```

### 

### 搜索考试列表

```
请求方式：POST
接口地址：localhost:8765/exam/find_exam_list
```

```json
传入格式：
{
    "search":"xxx",
    "clazz":{
        "id":1
    }
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": [
        {
            "id": 2,
            "code": "xxx",
            "clazzId": 1,
            "startTime": "2022-06-01",
            "endTime": "2022-06-02"
        }
    ]
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```



###   随机出题

```
请求方式：POST
接口地址：localhost:8765/exam/random_set_question
```

```json
{
    "clazz":{
        "id":1
    },
    "exam":{
        "id":2
    },
    "score":80.0,
    "xzNum":1,
    "zgNum":1
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": null
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```



### 自由出题

```
请求方式：POST
接口地址：localhost:8765/exam/free_set_question
```

```json
{
    "clazz":{
        "id":1
    },
    "exam":{
        "id":2
    },
    "questionList":[
        {
            "id":1
        },{
            "id":2
        }
    ]
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": null
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```



###   批改试卷

```
请求方式：POST
接口地址：localhost:8765/exam/find_paper_list
```

```json
{
    "id":2
}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": [
        {
            "id": 114,
            "examId": 2,
            "questionId": 1,
            "questionText": "1+1=?",
            "questionImg": "img_address",
            "questionCategory": "xz",
            "stuId": 1,
            "answerContent": null,
            "correctAnswer": "2",
            "stuScores": null,
            "defaultScores": 2.0
        }
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```



###   提交批改结果

```
请求方式：POST
接口地址：localhost:8765/exam/submit_check_anwser
```

```json
传入格式：
{
    "stuAnswer":{
        "id":114
    },
    "score":5.0

}
```

```
正确返回Json：
{
    "code": "0",
    "msg": "成功",
    "data": null
}
错误返回Json：
{
    "code": "-1",
    "msg": "错误信息",
    "data": null
}
```

