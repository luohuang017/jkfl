package com.example.springboot.controller;
import com.example.springboot.entity.Clazz;
import com.example.springboot.mapper.ClazzMapper;
import com.example.springboot.result.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.text.ParseException;
public class ClassController {
    @Resource
    ClazzMapper clazzMapper;
    @PostMapping("/add_class")
    public Result<?> addClass(@RequestBody Clazz clazz) throws ParseException {

        return Result.success();
    }
}
