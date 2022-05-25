package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "options")
public class Options {
	@TableId(type= IdType.AUTO)
	private Integer id;

	private String optionText;

	private Integer questionId;

	private String isCorrect;
}
