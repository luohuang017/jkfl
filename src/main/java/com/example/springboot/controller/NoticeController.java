package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springboot.entity.LocalRecord;
import com.example.springboot.entity.Notice;
import com.example.springboot.entity.StuClazz;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.NoticeMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.result.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Resource
    NoticeMapper noticeMapper;

    @PostMapping("/publish_notice")
    public Result<?> publishNotice(@RequestBody Notice notice) {
        noticeMapper.insert(notice);
        return Result.success();
    }

    @PostMapping("/delete_notice")
    public Result<?> deleteNotice(@RequestParam int noticeId) {
        noticeMapper.deleteById(noticeId);
        return Result.success();
    }

    @PostMapping("/update_notice")
    public Result<?> updateNotice(@RequestBody Notice notice) {
        noticeMapper.updateById(notice);
        return Result.success();
    }

    @GetMapping("/find_class_stu_list")
    public Result<?> findClassStuList(@RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Notice> wrappers = Wrappers.<Notice>lambdaQuery();
        wrappers.like(Notice::getText, search);
        List<Notice> userList = noticeMapper.selectList(wrappers);
        return Result.success(userList);
    }

}