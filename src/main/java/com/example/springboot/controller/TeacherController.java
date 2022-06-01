package com.example.springboot.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springboot.entity.*;
import com.example.springboot.mapper.*;
import com.example.springboot.result.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Resource
    ClazzMapper clazzMapper;
    @PostMapping("/teacher_find_myclass")
    public Result<?> TeacherFindMyClass(@RequestBody User user) throws Exception {
        LambdaQueryWrapper<Clazz> wrappers = Wrappers.<Clazz>lambdaQuery();
        wrappers.eq(Clazz::getTeacherId,user.getId());
        List<Clazz> clazzList = clazzMapper.selectList(wrappers);
        return Result.success(clazzList);
    }
}
