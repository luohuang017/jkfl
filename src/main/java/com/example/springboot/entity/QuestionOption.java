package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "question_option")
public class QuestionOption {
	@TableId(type= IdType.AUTO)
	private Integer id;

	@Column(name = "question_id")
	private Integer questionId;

	@Column(name = "option_id")
	private Integer optionId;

	@Column(name = "is_correct")
	private Integer isCorrect;

}
