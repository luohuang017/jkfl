package com.example.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springboot.result.Result;
import com.example.springboot.entity.*;
import com.example.springboot.mapper.*;
import com.example.springboot.tool.JsonXMLUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

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
    @Resource
    StuAnswerOptionMapper stuAnswerOptionMapper;
    @Resource
    OptionsMapper optionsMapper;
    @PostMapping("/add_exam")
    public Result<?> addExam(@RequestBody Map<String, Object> models) throws Exception {
        Exam exam = JsonXMLUtils.map2obj((Map<String, Object>) models.get("exam"), Exam.class);
        Clazz clazz = JsonXMLUtils.map2obj((Map<String, Object>) models.get("clazz"), Clazz.class);
        exam.setClazzId(clazz.getId());
        long startTime = exam.getStartTime().getTime();
        long endTime = exam.getEndTime().getTime();
        if(startTime < System.currentTimeMillis()) {
            return Result.error("-1", "开始时间需要晚于当前时间");
        }
        if(startTime > endTime) {
            return Result.error("-1", "结束时间需要晚于开始时间");
        }
        examMapper.insert(exam);
        return Result.success();
    }

    @PostMapping("/delete_exam")
    public Result<?> deleteExam(@RequestBody Exam exam) {
        Exam res = examMapper.selectById(exam.getId());
        if(res == null) {
            return Result.error("-1", "该考试不存在");
        }
        LambdaQueryWrapper<StuAnswer> wrappers = Wrappers.<StuAnswer>lambdaQuery();
        wrappers.eq(StuAnswer::getExamId, exam.getId());
        stuAnswerMapper.delete(wrappers);
        examMapper.deleteById(exam.getId());
        return Result.success();
    }

    @PostMapping("/update_exam")
    public Result<?> updateExam(@RequestBody Exam exam) throws Exception {
        Exam res = examMapper.selectById(exam.getId());
        if(res == null) {
            return Result.error("-1", "该考试不存在");
        }
        long startTime = exam.getStartTime().getTime();
        long endTime = exam.getEndTime().getTime();
        if(startTime < System.currentTimeMillis()) {
            return Result.error("-1", "开始时间需要晚于当前时间");
        }
        if(startTime > endTime) {
            return Result.error("-1", "结束时间需要晚于开始时间");
        }
        System.out.println(exam.getStartTime());
        examMapper.updateById(exam);
        return Result.success();
    }

    @PostMapping("/find_exam_info")
    public Result<?> findExamInfo(@RequestParam Integer examId) {
        Exam exam = examMapper.selectById(examId);
        return Result.success(exam);
    }

    @PostMapping("/find_exam_list")
    public Result<?> findExamList(@RequestBody Map<String, Object> models) throws Exception {
        String search = (String) models.get("search");
        Object tmpClazz = models.get("clazz");
        LambdaQueryWrapper<Exam> wrappers = Wrappers.<Exam>lambdaQuery();
        if(tmpClazz != null) {
            Clazz clazz = JsonXMLUtils.map2obj((Map<String, Object>) models.get("clazz"), Clazz.class);
            wrappers.eq(Exam::getClazzId, clazz.getId());
        }
        if(search != null && !search.equals("")) {
            wrappers.like(Exam::getCode, search);
        }
        List<Exam> examList = examMapper.selectList(wrappers);
//        if (examList == null) {
//            return Result.error("-1", "这样的考试不存在");
//        }
        return Result.success(examList);
    }

    //高一个档次的1/10，低一个档次的2/10，低两个档次的3/10,同一档次的4/10
    @PostMapping("/random_set_question")
    public Result<?> randomSetQuestion(@RequestBody Map<String, Object> models) throws Exception {
        Clazz clazz = JsonXMLUtils.map2obj((Map<String, Object>) models.get("clazz"), Clazz.class);
        Exam exam = JsonXMLUtils.map2obj((Map<String, Object>) models.get("exam"), Exam.class);
        Double score = (Double) models.get("score");
        Integer xzNum = (Integer) models.get("xzNum");
        Integer zgNum = (Integer) models.get("zgNum");
        LambdaQueryWrapper<StuClazz> wrappers = Wrappers.<StuClazz>lambdaQuery();
        wrappers.eq(StuClazz::getClazzId, clazz.getId());
        List<StuClazz> studentList = stuClazzMapper.selectList(wrappers);
        Double avgScore = score / 100;
        //筛选选择题
        LambdaQueryWrapper<Question> xzWrappers = Wrappers.<Question>lambdaQuery();
        xzWrappers.eq(Question::getCategory, "xz");
        List<Question> xzQuestionList = questionMapper.selectList(xzWrappers);
        //筛选主观题
        LambdaQueryWrapper<Question> zgWrappers = Wrappers.<Question>lambdaQuery();
        zgWrappers.eq(Question::getCategory, "zg");
        List<Question> zgQuestionList = questionMapper.selectList(zgWrappers);
        List<List<Question>> xzLists = new ArrayList<>();
        List<List<Question>> zgLists = new ArrayList<>();
        //题目总共分为10个档次
        for(int i = 0; i <= 10; i++) {
            List<Question> questionList = new ArrayList<>();
            xzLists.add(questionList);
        }
        for(int i = 0; i <= 10; i++) {
            List<Question> questionList = new ArrayList<>();
            zgLists.add(questionList);
        }
        for(Question question : xzQuestionList) {
            int level = (int) (question.getDifficultyRatio() / 0.1);
            xzLists.get(level).add(question);
        }
        for(Question question : zgQuestionList) {
            int level = (int) (question.getDifficultyRatio() / 0.1);
            zgLists.get(level).add(question);
        }
        //根据档次划分题目数量
        int level = (int) (score / 10);
        int[] levelArr = new int[5];
        levelArr[1] = Math.min(level + 1, 20);
        levelArr[2] = Math.max(level - 1, 0);
        levelArr[3] = Math.max(level - 2, 0);
        levelArr[4] = level;
        int[] xzCnt = new int[5];
        xzCnt[1] = xzNum / 10;
        xzCnt[2] = xzNum * 2 / 10;
        xzCnt[3] = xzNum * 3 / 10;
        xzCnt[4] = xzNum - xzCnt[1] - xzCnt[2] - xzCnt[3];
        int[] zgCnt = new int[5];
        zgCnt[1] = zgNum / 10;
        zgCnt[2] = zgNum * 2 / 10;
        zgCnt[3] = zgNum * 3 / 10;
        zgCnt[4] = zgNum - zgCnt[1] - zgCnt[2] - zgCnt[3];
        //根据学生列表分配各自答题卡
        for(StuClazz stuClazz : studentList) {
            //随机
            for(int i = 0; i <= 10; i++) {
                Collections.shuffle(xzLists.get(i));
                Collections.shuffle(zgLists.get(i));
            }
            for(int i = 1; i <= 4; i++) {
                List<Question> xzTmpList = xzLists.get(levelArr[i]);
                for(int j = 0; j < xzCnt[i]; j++) {
                    Question question = xzTmpList.get(j);
                    StuAnswer stuAnswer = new StuAnswer();
                    stuAnswer.setExamId(exam.getId());
                    stuAnswer.setQuestionId(question.getId());
                    stuAnswer.setQuestionImg(question.getImg());
                    stuAnswer.setQuestionText(question.getText());
                    stuAnswer.setQuestionCategory(question.getCategory());
                    stuAnswer.setStuId(stuClazz.getStuId());
                    stuAnswer.setCorrectAnswer(question.getSampleAnswer());
                    stuAnswer.setDefaultScores(question.getDefaultScores());
                    stuAnswerMapper.insert(stuAnswer);
                    LambdaQueryWrapper<StuAnswer> tmp = Wrappers.<StuAnswer>lambdaQuery();
                    tmp.eq(StuAnswer::getQuestionId, stuAnswer.getQuestionId()).eq(StuAnswer::getExamId, stuAnswer.getExamId()).eq(StuAnswer::getStuId, stuAnswer.getStuId());
                    stuAnswer=stuAnswerMapper.selectOne(tmp);
                    LambdaQueryWrapper<Options> optionWrappers = Wrappers.<Options>lambdaQuery();
                    optionWrappers.eq(Options::getQuestionId, question.getId());
                    List<Options> optionsList = optionsMapper.selectList(optionWrappers);
                    for(Options options:optionsList){
                        StuAnswerOption stuAnswerOption = new StuAnswerOption();
                        stuAnswerOption.setStuAnswerId(stuAnswer.getId());
                        stuAnswerOption.setOptionText(options.getOptionText());
                        stuAnswerOptionMapper.insert(stuAnswerOption);
                    }
                }
                List<Question> zgTmpList = zgLists.get(levelArr[i]);
                for(int j = 0; j < zgCnt[i]; j++) {
                    Question question = zgTmpList.get(j);
                    StuAnswer stuAnswer = new StuAnswer();
                    stuAnswer.setExamId(exam.getId());
                    stuAnswer.setQuestionId(question.getId());
                    stuAnswer.setQuestionImg(question.getImg());
                    stuAnswer.setQuestionText(question.getText());
                    stuAnswer.setQuestionCategory(question.getCategory());
                    stuAnswer.setStuId(stuClazz.getStuId());
                    stuAnswer.setCorrectAnswer(question.getSampleAnswer());
                    stuAnswer.setDefaultScores(question.getDefaultScores());
                    stuAnswerMapper.insert(stuAnswer);
                    LambdaQueryWrapper<StuAnswer> tmp = Wrappers.<StuAnswer>lambdaQuery();
                    tmp.eq(StuAnswer::getQuestionId, stuAnswer.getQuestionId()).eq(StuAnswer::getExamId, stuAnswer.getExamId()).eq(StuAnswer::getStuId, stuAnswer.getStuId());
                    stuAnswer=stuAnswerMapper.selectOne(tmp);
                    LambdaQueryWrapper<Options> optionWrappers = Wrappers.<Options>lambdaQuery();
                    optionWrappers.eq(Options::getQuestionId, question.getId());
                    List<Options> optionsList = optionsMapper.selectList(optionWrappers);
                    for(Options options:optionsList){
                        StuAnswerOption stuAnswerOption = new StuAnswerOption();
                        stuAnswerOption.setStuAnswerId(stuAnswer.getId());
                        stuAnswerOption.setOptionText(options.getOptionText());
                        stuAnswerOptionMapper.insert(stuAnswerOption);
                    }
                }
            }
        }
        return Result.success();

    }

    @PostMapping("/free_set_question")
    public Result<?> freeSetQuestion(@RequestBody Map<String, Object> models) throws Exception {
        Clazz clazz = JsonXMLUtils.map2obj((Map<String, Object>) models.get("clazz"), Clazz.class);
        Exam exam = JsonXMLUtils.map2obj((Map<String, Object>) models.get("exam"), Exam.class);
        List<Question> questionIdList = (List<Question>) models.get("questionList");
        String s = JSON.toJSONString(questionIdList); //json转换有问题，需要重新转，并指明类型
        List<Question> questionList = JSON.parseArray(s, Question.class);// 指定转换的类型
        LambdaQueryWrapper<StuClazz> wrappers = Wrappers.<StuClazz>lambdaQuery();
        wrappers.eq(StuClazz::getClazzId, clazz.getId());
        List<StuClazz> studentList = stuClazzMapper.selectList(wrappers);
//        List<Question> questionList= new ArrayList<>();
        for(StuClazz stuClazz : studentList) {
            for(int i = 0; i < questionList.size(); i++) {
                Question question = questionMapper.selectById(questionList.get(i).getId());
                StuAnswer stuAnswer = new StuAnswer();
                stuAnswer.setExamId(exam.getId());
                stuAnswer.setQuestionId(question.getId());
                stuAnswer.setQuestionImg(question.getImg());
                stuAnswer.setQuestionText(question.getText());
                stuAnswer.setQuestionCategory(question.getCategory());
                stuAnswer.setStuId(stuClazz.getStuId());
                stuAnswer.setCorrectAnswer(question.getSampleAnswer());
                stuAnswer.setDefaultScores(question.getDefaultScores());
                stuAnswerMapper.insert(stuAnswer);
                LambdaQueryWrapper<StuAnswer> tmp = Wrappers.<StuAnswer>lambdaQuery();
                tmp.eq(StuAnswer::getQuestionId, stuAnswer.getQuestionId()).eq(StuAnswer::getExamId, stuAnswer.getExamId()).eq(StuAnswer::getStuId, stuAnswer.getStuId());
                stuAnswer=stuAnswerMapper.selectOne(tmp);
                LambdaQueryWrapper<Options> optionWrappers = Wrappers.<Options>lambdaQuery();
                optionWrappers.eq(Options::getQuestionId, question.getId());
                List<Options> optionsList = optionsMapper.selectList(optionWrappers);
                for(Options options:optionsList){
                    StuAnswerOption stuAnswerOption = new StuAnswerOption();
                    stuAnswerOption.setStuAnswerId(stuAnswer.getId());
                    stuAnswerOption.setOptionText(options.getOptionText());
                    stuAnswerOptionMapper.insert(stuAnswerOption);
                }
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
    public Result<?> submitCheckAnwser(@RequestBody Map<String, Object> models) throws Exception {
        StuAnswer tmpStuAnswer = JsonXMLUtils.map2obj((Map<String, Object>) models.get("stuAnswer"), StuAnswer.class);
        Double score = (Double) models.get("score");
        StuAnswer stuAnswer = stuAnswerMapper.selectById(tmpStuAnswer.getId());
        Question question = questionMapper.selectById(stuAnswer.getQuestionId());
        LambdaQueryWrapper<StuScores> wrappers = Wrappers.<StuScores>lambdaQuery();
        wrappers.eq(StuScores::getExamId, stuAnswer.getExamId());
        StuScores stuScores = stuScoresMapper.selectOne(wrappers);
        LambdaQueryWrapper<StuClazz> clazzSearch = Wrappers.<StuClazz>lambdaQuery();
        clazzSearch.eq(StuClazz::getStuId, stuAnswer.getStuId());
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
        stuScores.setEndTime(new Date(System.currentTimeMillis()));
        questionMapper.updateById(question);
        stuScoresMapper.updateById(stuScores);
        return Result.success();
    }

}
