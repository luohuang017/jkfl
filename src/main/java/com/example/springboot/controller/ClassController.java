package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springboot.entity.Clazz;
import com.example.springboot.entity.StuClazz;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.ClazzMapper;
import com.example.springboot.mapper.StuClazzMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.result.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/class")
public class ClassController {
    @Resource
    ClazzMapper clazzMapper;
    @Resource
    StuClazzMapper stuClazzMapper;
    @Resource
    UserMapper userMapper;

    @PostMapping("/add_class")
    public Result<?> addClass(@RequestBody Clazz clazz) {
        clazzMapper.insert(clazz);
        return Result.success();
    }

    @PostMapping("/delete_class")
    public Result<?> deleteClass(@RequestBody Clazz clazz) {
        clazzMapper.deleteById(clazz.getId());
        return Result.success();
    }

    @PostMapping("/update_class_info")
    public Result<?> updateClassInfo(@RequestBody Clazz clazz) {
        clazzMapper.updateById(clazz);
        return Result.success();
    }

    @PostMapping("/find_class_info")
    public Result<?> findClassInfo(@RequestParam(defaultValue = "") Integer clazzId,
                                   @RequestParam(defaultValue = "") String clazzCode) {
        Clazz clazz = null;
        if(clazzId != 0) clazz = clazzMapper.selectById(clazzId);
        else if(!clazzCode.equals("")) {
            LambdaQueryWrapper<Clazz> wrappers = Wrappers.<Clazz>lambdaQuery();
            wrappers.eq(Clazz::getCode, clazzCode);
            clazz = clazzMapper.selectOne(wrappers);
        }
        if(clazz == null) {
            return Result.error("-1", "该课程不存在");
        }
        return Result.success(clazz);
    }

    @PostMapping("/find_class_list")
    public Result<?> findClassList(@RequestParam(defaultValue = "") String teacherId,
                                   @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Clazz> wrappers = Wrappers.<Clazz>lambdaQuery();
        if(!search.equals("")) {
            wrappers.like(Clazz::getName, search).
                    or().like(Clazz::getTeacherId, search).
                    or().like(Clazz::getCode, search);
        }
        if(!teacherId.equals("")) {
            wrappers.eq(Clazz::getTeacherId, Integer.parseInt(teacherId));
        }
        List<Clazz> userList = clazzMapper.selectList(wrappers);
        return Result.success(userList);
    }

    @PostMapping("/add_class_stu")
    public Result<?> addClassStu(@RequestParam Integer clazzId, @RequestParam String studentCode) {
        LambdaQueryWrapper<User> wrappers = Wrappers.<User>lambdaQuery();
        wrappers.eq(User::getCode, studentCode);
        User user = userMapper.selectOne(wrappers);
        if(user == null) {
            return Result.error("-1", "该学生不存在");
        }
        LambdaQueryWrapper<StuClazz> wrappers1 = Wrappers.<StuClazz>lambdaQuery();
        wrappers1.eq(StuClazz::getStuCode, studentCode);
        wrappers1.eq(StuClazz::getClazzId, clazzId);
        StuClazz tempStuClazz = stuClazzMapper.selectOne(wrappers1);
        if(tempStuClazz != null) {
            return Result.error("-1", "该学生已在课程内");
        }
        StuClazz stuClazz = new StuClazz();
        stuClazz.setStuId(user.getId());
        stuClazz.setClazzId(clazzId);
        stuClazz.setStuCode(user.getCode());
        stuClazz.setStuName(user.getName());
        stuClazzMapper.insert(stuClazz);
        return Result.success();
    }

    @PostMapping("/delete_class_stu")
    public Result<?> deleteClassStu(@RequestParam Integer clazzId, @RequestParam Integer studentId) {
        LambdaQueryWrapper<StuClazz> wrappers = Wrappers.<StuClazz>lambdaQuery();
        wrappers.eq(StuClazz::getClazzId, clazzId);
        wrappers.eq(StuClazz::getStuId, studentId);
        StuClazz res = stuClazzMapper.selectOne(wrappers);
        if(res == null) {
            return Result.error("-1", "该课程内不存在该学生");
        }
        stuClazzMapper.deleteById(res.getId());
        return Result.success();
    }

    @PostMapping("/find_class_stu_list")
    public Result<?> findClassStuList(@RequestParam(defaultValue = "") Integer clazzId,
                                      @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<StuClazz> wrappers = Wrappers.<StuClazz>lambdaQuery();
        wrappers.eq(StuClazz::getClazzId, clazzId);
        if(!search.equals("")) {
            wrappers.like(StuClazz::getStuCode, search).or().like(StuClazz::getStuName, search);
        }
        List<StuClazz> userList = stuClazzMapper.selectList(wrappers);
        return Result.success(userList);
    }

}
