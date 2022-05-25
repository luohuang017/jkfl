package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springboot.common.Result;
import com.example.springboot.entity.*;
import com.example.springboot.mapper.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/exam")
public class ExamController {
    @Resource
    ExamMapper examMapper;
    @Resource
    QuestionMapper questionMapper;
    @Resource
    StuScoresMapper stuScoresMapper;
    @Resource
    StuAnswerMapper stuAnswerMapper;
    @Resource
    StuClazzMapper stuClazzMapper;

    @PostMapping("/add_exam")
    public Result<?> addExam(@RequestBody Clazz clazz, @RequestBody Exam exam) {
        exam.setClazzId(clazz.getId());
        long startTime = exam.getStartTime().getTime();
        long endTime = exam.getEndTime().getTime();
        if (startTime < System.currentTimeMillis()) {
            return Result.error("-1", "开始时间需要晚于当前时间");
        }
        if (startTime > endTime) {
            return Result.error("-1", "结束时间需要晚于开始时间");
        }
        examMapper.insert(exam);
        return Result.success();
    }

    @PostMapping("/delete_exam")
    public Result<?> deleteExam(@RequestBody Exam exam) {
        LambdaQueryWrapper<StuAnswer> wrappers = Wrappers.<StuAnswer>lambdaQuery();
        wrappers.eq(StuAnswer::getExamId, exam.getId());
        stuAnswerMapper.delete(wrappers);
        examMapper.deleteById(exam.getId());
        return Result.success();
    }

    @PostMapping("/update_exam")
    public Result<?> updateExam(@RequestBody Exam exam) {
        examMapper.updateById(exam);
        return Result.success();
    }

    @PostMapping("/find_exam_list")
    public Result<?> findExamList(@RequestParam(defaultValue = "") String search, @RequestBody Clazz clazz) {
        LambdaQueryWrapper<Exam> wrappers = Wrappers.<Exam>lambdaQuery();
        wrappers.like(Exam::getCode, search).or().eq(Exam::getClazzId, clazz.getId());
        List<Exam> examList = examMapper.selectList(wrappers);
        if (examList == null) {
            return Result.error("-1", "这样的考试不存在");
        }
        return Result.success(examList);
    }

    @PostMapping("/random_set_question")
    public Result<?> randomSetQuestion(@RequestBody Clazz clazz, @RequestBody Exam exam, @RequestParam Double score,
                                       @RequestParam Integer xzNum, @RequestParam Integer zgNum) {
        LambdaQueryWrapper<StuClazz> wrappers = Wrappers.<StuClazz>lambdaQuery();
        wrappers.eq(StuClazz::getClazzId, clazz.getId());
        List<StuClazz> studentList = stuClazzMapper.selectList(wrappers);
        Double avgScore=score/100;
        LambdaQueryWrapper<Question> xzWrappers = Wrappers.<Question>lambdaQuery();
        xzWrappers.eq(Question::getCategory, "xz");
        List<Question> xzQuestionList=questionMapper.selectList(xzWrappers);

        LambdaQueryWrapper<Question> zgWrappers = Wrappers.<Question>lambdaQuery();
        zgWrappers.eq(Question::getCategory, "zg");
        List<Question> zgQuestionList=questionMapper.selectList(zgWrappers);

        for (StuClazz stuClazz : studentList) {
            for (int i=0;i<xzNum;i++) {
                Question question = xzQuestionList.get(i);
                StuAnswer stuAnswer = new StuAnswer();
                stuAnswer.setExamId(exam.getId());
                stuAnswer.setQuestionId(question.getId());
                stuAnswer.setQuestionImg(question.getImg());
                stuAnswer.setQuestionCategory(question.getCategory());
                stuAnswer.setStuId(stuClazz.getStuId());
                stuAnswer.setCorrectAnswer(question.getSampleAnswer());
                stuAnswer.setDefaultScores(question.getDefaultScores());
                stuAnswerMapper.insert(stuAnswer);
            }

            for (int i=0;i<zgNum;i++) {
                Question question = zgQuestionList.get(i);
                StuAnswer stuAnswer = new StuAnswer();
                stuAnswer.setExamId(exam.getId());
                stuAnswer.setQuestionId(question.getId());
                stuAnswer.setQuestionImg(question.getImg());
                stuAnswer.setQuestionCategory(question.getCategory());
                stuAnswer.setStuId(stuClazz.getStuId());
                stuAnswer.setCorrectAnswer(question.getSampleAnswer());
                stuAnswer.setDefaultScores(question.getDefaultScores());
                stuAnswerMapper.insert(stuAnswer);
            }
        }
        return Result.success();

    }

    @PostMapping("/free_set_question")
    public Result<?> freeSetQuestion(@RequestBody Clazz clazz, @RequestBody Exam exam, @RequestBody List<Question> questionList) {
        LambdaQueryWrapper<StuClazz> wrappers = Wrappers.<StuClazz>lambdaQuery();
        wrappers.eq(StuClazz::getClazzId, clazz.getId());
        List<StuClazz> studentList = stuClazzMapper.selectList(wrappers);
        for (StuClazz stuClazz : studentList) {
            for (Question question : questionList) {
                StuAnswer stuAnswer = new StuAnswer();
                stuAnswer.setExamId(exam.getId());
                stuAnswer.setQuestionId(question.getId());
                stuAnswer.setQuestionImg(question.getImg());
                stuAnswer.setQuestionCategory(question.getCategory());
                stuAnswer.setStuId(stuClazz.getStuId());
                stuAnswer.setCorrectAnswer(question.getSampleAnswer());
                stuAnswer.setDefaultScores(question.getDefaultScores());
                stuAnswerMapper.insert(stuAnswer);
            }
        }
        return Result.success();
    }

    @PostMapping("/find_paper_list")
    public Result<?> findPaperList(@RequestBody Exam exam) {
        LambdaQueryWrapper<StuAnswer> wrappers = Wrappers.<StuAnswer>lambdaQuery();
        wrappers.eq(StuAnswer::getExamId, exam.getId());
        List<StuAnswer> StuAnswerList = stuAnswerMapper.selectList(wrappers);
        return Result.success(StuAnswerList);
    }

    @PostMapping("/submit_check_anwser")
    public Result<?> submitCheckAnwser(@RequestBody StuAnswer stuAnswer, @RequestParam Double score) {
        Question question = questionMapper.selectById(stuAnswer.getQuestionId());
        LambdaQueryWrapper<StuScores> wrappers = Wrappers.<StuScores>lambdaQuery();
        wrappers.eq(StuScores::getExamId, stuAnswer.getId());
        StuScores stuScores = stuScoresMapper.selectOne(wrappers);
        if (stuAnswer.getStuScores() != null) {
            question.setAcScores(question.getAcScores() - stuAnswer.getStuScores());
            question.setTotScores(question.getTotScores() - stuAnswer.getDefaultScores());
            stuScores.setScores(stuScores.getScores() - stuAnswer.getStuScores());
        }
        question.setAcScores(question.getAcScores() + score);
        question.setTotScores(question.getTotScores() + stuAnswer.getDefaultScores());
        question.setDifficultyRatio(question.getAcScores() / question.getTotScores());
        stuScores.setScores(stuScores.getScores() + score);
        questionMapper.updateById(question);
        stuScoresMapper.updateById(stuScores);


        return Result.success();
    }

}
