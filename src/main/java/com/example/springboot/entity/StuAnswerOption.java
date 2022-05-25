package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "stu_answer_option")
public class StuAnswerOption {
	@TableId(type= IdType.AUTO)
	private Integer id;

	@Column(name = "stu_answer_id")
	private Integer stuAnswerId;

	@Column(name = "option_text")
	private String optionText;

}
