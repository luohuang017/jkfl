package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springboot.entity.Clazz;
import com.example.springboot.entity.StuClazz;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.ClazzMapper;
import com.example.springboot.mapper.StuClazzMapper;
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

    @PostMapping("/add_class")
    public Result<?> addClass(@RequestBody Clazz clazz) {
//        Clazz res = clazzMapper.selectOne(Wrappers.<Clazz>lambdaQuery().eq(Clazz::getName, clazz.getName()).eq(Clazz::getTeacherId, clazz.getTeacherId()));
//        if(res != null) {
//            return Result.error("-1", "该老师课程已存在");
//        }
        clazzMapper.insert(clazz);
        return Result.success();
    }

    @PostMapping("/delete_class")
    public Result<?> deleteClass(@RequestParam Integer clazzId) {
        clazzMapper.deleteById(clazzId);
        return Result.success();
    }

    @PostMapping("/update_class_info")
    public Result<?> updateClassInfo(@RequestBody Clazz clazz) {
        clazzMapper.updateById(clazz);
        return Result.success();
    }

    @GetMapping("/find_class_list")
    public Result<?> findClassList(@RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Clazz> wrappers = Wrappers.<Clazz>lambdaQuery();
        wrappers.like(Clazz::getName, search).
                or().like(Clazz::getTeacherId, search).
                or().like(Clazz::getCode, search);
        List<Clazz> userList = clazzMapper.selectList(wrappers);
        return Result.success(userList);
    }

    @PostMapping("/add_class_stu")
    public Result<?> addClassStu(@RequestParam Integer clazzId, @RequestParam Integer studentId) {
        StuClazz stuClazz = new StuClazz();
        stuClazz.setStuId(studentId);
        stuClazz.setClazzId(clazzId);
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

    @GetMapping("/find_class_stu_list")
    public Result<?> findClassStuList(@RequestParam(defaultValue = "") String clazzId,
                                      @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<StuClazz> wrappers = Wrappers.<StuClazz>lambdaQuery();
        wrappers.eq(StuClazz::getClazzId, clazzId);
        if(!search.equals("")) {
            wrappers.like(StuClazz::getStuCode, search).
                    or().like(StuClazz::getStuName, search);
        }
        List<StuClazz> userList = stuClazzMapper.selectList(wrappers);
        return Result.success(userList);
    }

}
