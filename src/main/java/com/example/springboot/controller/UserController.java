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
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getCode, user.getCode()).eq(User::getPwd, user.getPwd()));
        if(res == null) {
            return Result.error("-1", "用户名或密码错误");
        }
//        Timestamp date =  new Timestamp(System.currentTimeMillis());
//        LocalRecord lr = null;
//        lr = localRecordMapper.selectOne(Wrappers.<LocalRecord>lambdaQuery().eq(LocalRecord::getName, user.getCode()).eq(LocalRecord::getDate, date));
//        if(lr == null) {
//            LocalRecord localRecord = new LocalRecord();
//            localRecord.setName(user.getCode());
//            localRecord.setDate(date);
//            localRecordMapper.insert(localRecord);
//        }
        return Result.success(res);
    }
    @PostMapping("/add_user")
    public Result<?> addUser(@RequestBody User user) {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getCode, user.getCode()));
        if(user.getCode() == null || user.getCode().equals("")) {
            return Result.error("-1", "用户名不可为空");
        }
        if(user.getPwd() == null || user.getPwd().equals("")) {
            return Result.error("-1", "密码不可为空");
        }
        if(res != null) {
            return Result.error("-1", "用户名已存在");
        }
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
        userMapper.updateById(user);
        return Result.success();
    }
    @PostMapping("find_person_list")
    public Result<?> findPersonList(@RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<User> wrappers = Wrappers.<User>lambdaQuery();
        if(!search.equals("")) {
            wrappers.like(User::getName, search).or().like(User::getCode, search);
        }
        List<User> userList = userMapper.selectList(wrappers);
        return Result.success(userList);
    }
}
