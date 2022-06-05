package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springboot.entity.LocalRecord;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.LocalRecordMapper;
import com.example.springboot.result.Result;
import com.example.springboot.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserMapper userMapper;
    @Resource
    LocalRecordMapper localRecordMapper;

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) throws ParseException {
        if(user.getCode() == null || user.getCode().equals("")) {
            return Result.error("-1", "学号不可为空");
        }
        if(user.getPwd() == null || user.getPwd().equals("")) {
            return Result.error("-1", "密码不可为空");
        }
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getCode, user.getCode()).eq(User::getPwd, user.getPwd()));
        if(res == null) {
            return Result.error("-1", "学号或密码错误");
        }
        Timestamp date = new Timestamp(System.currentTimeMillis());
        LocalRecord lr = null;
        lr = localRecordMapper.selectOne(Wrappers.<LocalRecord>lambdaQuery().eq(LocalRecord::getCode, user.getCode()).eq(LocalRecord::getDate, date));
        if(lr == null) {
            LocalRecord localRecord = new LocalRecord();
            localRecord.setCode(user.getCode());
            localRecord.setDate(date);
            localRecordMapper.insert(localRecord);
        }
        return Result.success(res);
    }

    @PostMapping("/add_user")
    public Result<?> addUser(@RequestBody User user) {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getCode, user.getCode()));
        if(user.getCode() == null || user.getCode().equals("")) {
            return Result.error("-1", "教师号不可为空");
        }
        if(user.getPwd() == null || user.getPwd().equals("")) {
            return Result.error("-1", "密码不可为空");
        }
        if(user.getSex() == null || user.getSex().equals("")) {
            return Result.error("-1", "性别不可为空");
        }
        if(res != null) {
            return Result.error("-1", "学号已存在");
        }
        user.setUserType("teacher");
        userMapper.insert(user);
        return Result.success();
    }

    @PostMapping("/delete_user")
    public Result<?> deleteUser(@RequestBody User user) {
        userMapper.deleteById(user.getId());
        return Result.success();
    }

    @PostMapping("/update_user_info")
    public Result<?> updateUserInfo(@RequestBody User user) {
        if(user.getCode() == null || user.getCode().equals("")) {
            return Result.error("-1", "教师号不可为空");
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

    @PostMapping("/find_user_info")
    public Result<?> findUserInfo(@RequestParam Integer userId) {
        User user = userMapper.selectById(userId);
        return Result.success(user);
    }

    @PostMapping("find_user_list")
    public Result<?> findUserList(@RequestParam(defaultValue = "") String type,
                                    @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<User> wrappers = Wrappers.<User>lambdaQuery();
        if(!type.equals("")) {
            wrappers.eq(User::getUserType, type);
        }
        if(!search.equals("")) {
            wrappers.like(User::getName, search).or().like(User::getCode, search);
        }
        List<User> userList = userMapper.selectList(wrappers);
        return Result.success(userList);
    }

}
