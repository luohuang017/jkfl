package com.example.springboot.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springboot.entity.StuAnswer;
import com.example.springboot.entity.StuScores;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.StuAnswerMapper;
import com.example.springboot.mapper.StuScoresMapper;
import com.example.springboot.result.Result;
import com.example.springboot.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    UserMapper userMapper;
    @Resource
    StuAnswerMapper stuAnswerMapper;
    @Resource
    StuScoresMapper stuScoresMapper;
    @PostMapping("/student_register")
    public Result<?> StudentRegister(@RequestBody User user) throws Exception {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getName, user.getName()));
        if(user.getName() == null || user.getName().equals("")) {
            return Result.error("-1", "用户名不可为空");
        }
        if(user.getPwd() == null || user.getPwd().equals("")) {
            return Result.error("-1", "密码不可为空");
        }
        if(res != null) {
            return Result.error("-1", "用户名已存在");
        }
        user.setUserType("student");
        userMapper.insert(user);
        return Result.success();
    }
    @PostMapping("/student_updateInfo")
    public Result<?> StudentUpdateInfo(@RequestBody User user) throws Exception {
        userMapper.updateById(user);
        return Result.success();
    }
    @PostMapping("/submit_answer")
    public Result<?> SubmitAnswer(@RequestBody List<StuAnswer> stuAnswerList) throws Exception {
        for(StuAnswer stuAnswer:stuAnswerList){
            stuAnswerMapper.updateById(stuAnswer);
        }
        return Result.success();
    }
    @PostMapping("/find_history_exams")
    public Result<?> FindHistoryExams(@RequestBody User user) throws Exception {
        LambdaQueryWrapper<StuScores> wrappers = Wrappers.<StuScores>lambdaQuery();
        wrappers.eq(StuScores::getStuId,user.getId());
        List<StuScores> stuScoresList = stuScoresMapper.selectList(wrappers);
        return Result.success(stuScoresList);
    }
    @PostMapping("/stu_find_class")
    public Result<?> StuFindClass(@RequestBody User user) throws Exception {
        LambdaQueryWrapper<StuScores> wrappers = Wrappers.<StuScores>lambdaQuery();
        wrappers.eq(StuScores::getStuId,user.getId());
        List<StuScores> stuScoresList = stuScoresMapper.selectList(wrappers);
        return Result.success(stuScoresList);
    }
    @PostMapping("/stu_find_question")
    public Result<?> StuFindQuestion(@RequestBody User user) throws Exception {
        LambdaQueryWrapper<StuScores> wrappers = Wrappers.<StuScores>lambdaQuery();
        wrappers.eq(StuScores::getStuId,user.getId());
        List<StuScores> stuScoresList = stuScoresMapper.selectList(wrappers);
        return Result.success(stuScoresList);
    }
}
