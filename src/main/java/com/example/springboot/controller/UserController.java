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
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getName, user.getName()).eq(User::getPwd, user.getPwd()));
        if(res == null) {
            return Result.error("-1", "用户名或密码错误");
        }
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
//        String s = sdf.format(new Timestamp(System.currentTimeMillis()));
        Timestamp date =  new Timestamp(System.currentTimeMillis());
        LocalRecord lr = null;
        lr = localRecordMapper.selectOne(Wrappers.<LocalRecord>lambdaQuery().eq(LocalRecord::getName, user.getName()).eq(LocalRecord::getDate, date));
        if(lr == null) {
            LocalRecord localRecord = new LocalRecord();
            localRecord.setName(user.getName());
            localRecord.setDate(date);
            localRecordMapper.insert(localRecord);
        }
        return Result.success(res);
    }
    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
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
        user.setUserType("1");
        userMapper.insert(user);
        return Result.success();
    }
    @PostMapping
    public Result<?> save(@RequestBody User user) {
        if(user.getPwd() == null) {
            user.setPwd("123456");
        }
        userMapper.insert(user);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody User user) {
        userMapper.updateById(user);
        return Result.success();
    }
    @GetMapping
    public Result<?> findPersonList(@RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<User> wrappers = Wrappers.<User>lambdaQuery();
        if(!search.equals("")) {
            wrappers.like(User::getName, search).or().like(User::getCode, search);
        }
        List<User> userList = userMapper.selectList(wrappers);
        return Result.success(userList);
    }
}
