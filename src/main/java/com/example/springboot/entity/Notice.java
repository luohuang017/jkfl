package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "notice")
public class Notice {
	@TableId(type= IdType.AUTO)
	private Integer id;

	@Column(name = "clazz_id")
	private Integer clazzId;

	@Column(name = "content")
	private Integer content;
}
