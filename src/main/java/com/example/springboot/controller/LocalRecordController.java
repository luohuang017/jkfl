package com.example.springboot.controller;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springboot.entity.LocalRecord;
import com.example.springboot.result.Result;
import com.example.springboot.mapper.LocalRecordMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/local_record")
public class LocalRecordController {
    @Resource
    LocalRecordMapper localRecordMapper;
    @GetMapping
    public Result<?> find(@RequestParam String name,
                          @RequestParam Long dateTime) {
//        System.out.println(12313);
        LambdaQueryWrapper<LocalRecord> wrappers = Wrappers.<LocalRecord>lambdaQuery();
        Date date = new Date(dateTime);
        wrappers.eq(LocalRecord::getName, name);
        wrappers.ge(LocalRecord::getDate, date);
        List<LocalRecord> list = localRecordMapper.selectList(wrappers);
        int[] dayList = new int[400];
        long dayTime = 3600 * 24 * 1000L;
        for(var e : list) {
//            System.out.print(e.getDate() + "  ");
//            long t = e.getDate().getTime() / dayTime * dayTime;
//            System.out.println(t);
//            System.out.println(e.getDate());
            dayList[(int) ((e.getDate().getTime() - dateTime) / dayTime)] = 1;
        }
        return Result.success(dayList);
    }
}
