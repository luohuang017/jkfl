package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "stu_scores")
public class StuScores {
	@TableId(type= IdType.AUTO)
	private Integer id;

	@Column(name = "clazz_id")
	private Integer clazzId;

	@Column(name = "exam_id")
	private Integer examId;

	@Column(name = "stu_id")
	private Integer stuId;

	private Double scores;

	@Column(name = "end_time")
	private java.util.Date endTime;

	private String state;

}
