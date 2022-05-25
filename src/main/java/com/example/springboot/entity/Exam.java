package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "exam")
public class Exam {
	@TableId(type= IdType.AUTO)
	private Integer id;

	private String code;

	@Column(name = "clazz_id")
	private Integer clazzId;

	@Column(name = "start_time")
	private java.util.Date startTime;

	@Column(name = "end_time")
	private java.util.Date endTime;

}
