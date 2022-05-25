package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "user")
public class User {
	@TableId(type= IdType.AUTO)
	private Integer id;

	private String code;

	private String name;

	private String pwd;

	private String sex;

	@Column(name = "user_type")
	private String userType;

	private String fullname;

}
