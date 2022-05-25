package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.Options;
import com.example.springboot.entity.Question;
import com.example.springboot.mapper.QuestionMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Resource
    QuestionMapper questionMapper;

    @PostMapping("/add_question")
    public Result<?> addQuestion(@RequestBody Question question) {

        return null;
    }

    @PostMapping("/delete_question")
    public Result<?> deleteQuestion(@RequestBody Question question) {

        return null;
    }

    @PostMapping("/update_question")
    public Result<?> updateQuestion(@RequestBody Question question) {

        return null;
    }

    @PostMapping("/find_question_list")
    public Result<?> findQuestionList(@RequestParam String search) {

        return null;
    }

    @PostMapping("/add_question_option")
    public Result<?> addQuestionOption(@RequestBody Options options) {

        return null;
    }

    @PostMapping("/delete_question_option")
    public Result<?> deleteQuestionOption(@RequestBody Options options) {

        return null;
    }

    @PostMapping("/update_question_option")
    public Result<?> updateQuestionOption(@RequestBody Options options) {

        return null;
    }

    @PostMapping("/find_question_option")
    public Result<?> findQuestionOption(@RequestBody Options options) {

        return null;
    }

}
