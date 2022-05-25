package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "stu_clazz")
public class StuClazz {
	@TableId(type= IdType.AUTO)
	private Integer id;

	@Column(name = "stu_id")
	private Integer stuId;

	@Column(name = "clazz_id")
	private Integer clazzId;

}
