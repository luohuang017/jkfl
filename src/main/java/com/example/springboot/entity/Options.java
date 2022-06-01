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

	@Column(name = "option_text")
	private String optionText;

	@Column(name = "question_id")
	private Integer questionId;

	@Column(name = "is_correct")
	private String isCorrect;
}
