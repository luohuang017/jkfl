package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "local_record")
public class LocalRecord {
	@TableId(type= IdType.AUTO)
	private Integer id;

	private String name;

	private java.util.Date date;

}
