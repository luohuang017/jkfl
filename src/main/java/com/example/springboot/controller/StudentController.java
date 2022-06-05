package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springboot.entity.*;
import com.example.springboot.mapper.*;
import com.example.springboot.result.Result;
import com.example.springboot.tool.JsonXMLUtils;
import org.springframework.web.bind.annotation.*;
//import org.apache.commons.lang.time.DateFormatUtils;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    UserMapper userMapper;
    @Resource
    ExamMapper examMapper;
    @Resource
    StuAnswerMapper stuAnswerMapper;
    @Resource
    StuScoresMapper stuScoresMapper;
    @Resource
    StuClazzMapper stuClazzMapper;
    @Resource
    QuestionMapper questionMapper;
    @Resource
    StuAnswerOptionMapper stuAnswerOptionMapper;

    @PostMapping("/student_register")
    public Result<?> StudentRegister(@RequestBody User user) throws Exception {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getName, user.getName()));
        if(user.getName() == null || user.getName().equals("")) {
            return Result.error("-1", "用户名不可为空");
        }
        if(user.getPwd() == null || user.getPwd().equals("")) {
            return Result.error("-1", "密码不可为空");
        }
        if(user.getSex() == null || user.getSex().equals("")) {
            return Result.error("-1", "性别不可为空");
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
        if(user.getCode() == null || user.getCode().equals("")) {
            return Result.error("-1", "学号不可为空");
        }
        if(user.getPwd() == null || user.getPwd().equals("")) {
            return Result.error("-1", "密码不可为空");
        }
        if(user.getSex() == null || user.getSex().equals("")) {
            return Result.error("-1", "性别不可为空");
        }
        userMapper.updateById(user);
        return Result.success();
    }

    @PostMapping("/submit_answer")
    public Result<?> SubmitAnswer(@RequestBody List<StuAnswer> stuAnswerList) throws Exception {
        for(StuAnswer stuAnswer : stuAnswerList) {
            if(stuAnswer.getQuestionCategory().equals("xz")) {
                if(stuAnswer.getCorrectAnswer().equals(stuAnswer.getAnswerContent())) {
                    // ----------------
                    Double score = stuAnswer.getDefaultScores();
                    Question question = questionMapper.selectById(stuAnswer.getQuestionId());
                    LambdaQueryWrapper<StuScores> wrappers = Wrappers.<StuScores>lambdaQuery();
                    wrappers.eq(StuScores::getExamId, stuAnswer.getExamId());
                    StuScores stuScores = stuScoresMapper.selectOne(wrappers);
                    LambdaQueryWrapper<StuClazz> clazzSearch = Wrappers.<StuClazz>lambdaQuery();
                    clazzSearch.eq(StuClazz::getStuId, stuAnswer.getStuId());
                    Exam exam = examMapper.selectById(stuAnswer.getExamId());
                    clazzSearch.eq(StuClazz::getClazzId, exam.getClazzId());
                    StuClazz stuClazz = stuClazzMapper.selectOne(clazzSearch);
                    if(stuScores == null) {
                        stuScores = new StuScores();
                        stuScores.setScores(0.0);
                        stuScores.setClazzId(stuClazz.getClazzId());
                        stuScores.setExamId(stuAnswer.getExamId());
                        stuScores.setStuId(stuAnswer.getStuId());
                        stuScores.setEndTime(new Date(System.currentTimeMillis()));
                        stuScores.setState("finish");
                        stuScoresMapper.insert(stuScores);
                    }
                    stuScores = stuScoresMapper.selectOne(wrappers);
                    if(stuAnswer.getStuScores() != null) {
                        question.setAcScores(question.getAcScores() - stuAnswer.getStuScores());
                        question.setTotScores(question.getTotScores() - stuAnswer.getDefaultScores());
                        stuScores.setScores(stuScores.getScores() - stuAnswer.getStuScores());
                    }
                    question.setAcScores(question.getAcScores() + score);
                    question.setTotScores(question.getTotScores() + stuAnswer.getDefaultScores());
                    question.setDifficultyRatio(question.getAcScores() / question.getTotScores());
                    stuScores.setScores(stuScores.getScores() + score);
                    stuAnswer.setStuScores(score);
                    stuScores.setEndTime(new Date(System.currentTimeMillis()));
                    stuAnswerMapper.updateById(stuAnswer);
                    questionMapper.updateById(question);
                    stuScoresMapper.updateById(stuScores);
                    // ----------------
                    stuAnswer.setStuScores(stuAnswer.getDefaultScores());
                }
                else {
                    stuAnswer.setStuScores(0.0);
                }
            }
            //前端查询stuAnswer时若为选择题则直接呈现分数，主观题则给予文本框用于打分
            stuAnswerMapper.updateById(stuAnswer);
        }
        return Result.success();
    }

    @PostMapping("/find_history_exams")
    public Result<?> FindHistoryExams(@RequestBody User user) throws Exception {
        LambdaQueryWrapper<StuScores> wrappers = Wrappers.<StuScores>lambdaQuery();
        wrappers.eq(StuScores::getStuId, user.getId());
        Date date = new Date(System.currentTimeMillis());
        wrappers.le(StuScores::getEndTime, date);
        List<StuScores> stuScoresList = stuScoresMapper.selectList(wrappers);
        return Result.success(stuScoresList);
    }

    @PostMapping("/stu_find_class")
    public Result<?> StuFindClass(@RequestBody User user) throws Exception {
        LambdaQueryWrapper<StuClazz> wrappers = Wrappers.<StuClazz>lambdaQuery();
        wrappers.eq(StuClazz::getStuId, user.getId());
        List<StuClazz> stuClazzList = stuClazzMapper.selectList(wrappers);
        return Result.success(stuClazzList);
    }

    @PostMapping("/stu_find_question")
    public Result<?> StuFindQuestion(@RequestBody Map<String, Object> models) throws Exception {
        User tmpUser = JsonXMLUtils.map2obj((Map<String, Object>) models.get("user"), User.class);
        Exam tmpExam = JsonXMLUtils.map2obj((Map<String, Object>) models.get("exam"), Exam.class);
        LambdaQueryWrapper<StuAnswer> wrappers = Wrappers.<StuAnswer>lambdaQuery();
        wrappers.eq(StuAnswer::getStuId, tmpUser.getId()).eq(StuAnswer::getExamId, tmpExam.getId());
        List<StuAnswer> stuAnswerList = stuAnswerMapper.selectList(wrappers);
        return Result.success(stuAnswerList);
    }

    @PostMapping("/stu_find_question_option")
    public Result<?> StuFindQuestionOption(@RequestBody StuAnswer stuAnswer) throws Exception {
        LambdaQueryWrapper<StuAnswerOption> wrappers = Wrappers.<StuAnswerOption>lambdaQuery();
        wrappers.eq(StuAnswerOption::getStuAnswerId, stuAnswer.getId());
        List<StuAnswerOption> stuAnswerOption = stuAnswerOptionMapper.selectList(wrappers);
        return Result.success(stuAnswerOption);
    }

}
