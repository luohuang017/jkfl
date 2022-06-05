package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "clazz")
public class Clazz {

	@TableId(type= IdType.AUTO)
	private Integer id;

	@Column(name = "teacher_id")
	private Integer teacherId;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "is_close")
	private Integer isClose;

}
