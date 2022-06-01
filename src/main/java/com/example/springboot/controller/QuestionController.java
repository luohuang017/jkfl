package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springboot.entity.*;
import com.example.springboot.mapper.OptionsMapper;
import com.example.springboot.mapper.QuestionMapper;
import com.example.springboot.result.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.swing.text.html.Option;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Resource
    QuestionMapper questionMapper;
    @Resource
    OptionsMapper optionsMapper;

    @PostMapping("/add_question")
    public Result<?> addQuestion(@RequestBody Question question) {
        questionMapper.insert(question);
        return Result.success();
    }

    @PostMapping("/delete_question")
    public Result<?> deleteQuestion(@RequestBody Question question) {
        questionMapper.deleteById(question.getId());
        return Result.success();
    }

    @PostMapping("/update_question")
    public Result<?> updateQuestion(@RequestBody Question question) {
        questionMapper.updateById(question);
        return Result.success();
    }

    @PostMapping("/find_question_list")
    public Result<?> findQuestionList(@RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Question> wrappers = Wrappers.<Question>lambdaQuery();
        wrappers.like(Question::getText, search).
                or().like(Question::getImg, search).
                or().like(Question::getDifficultyRatio, search);
        List<Question> userList = questionMapper.selectList(wrappers);
        return Result.success(userList);
    }

    @PostMapping("/add_question_option")
    public Result<?> addQuestionOption(@RequestBody Options options) {
        optionsMapper.insert(options);
        return Result.success();
    }

    @PostMapping("/delete_question_option")
    public Result<?> deleteQuestionOption(@RequestBody Options options) {
        optionsMapper.deleteById(options.getId());
        return Result.success();
    }

    @PostMapping("/update_question_option")
    public Result<?> updateQuestionOption(@RequestBody Options options) {
        optionsMapper.updateById(options);
        return Result.success();
    }

    @PostMapping("/find_class_stu_list")
    public Result<?> findClassStuList(@RequestBody Question question) {
        LambdaQueryWrapper<Options> wrappers = Wrappers.<Options>lambdaQuery();
        wrappers.eq(Options::getQuestionId, question.getId());
        List<Options> userList = optionsMapper.selectList(wrappers);
        return Result.success(userList);
    }

}
