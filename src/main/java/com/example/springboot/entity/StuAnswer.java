package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "stu_answer")
public class StuAnswer {
	@TableId(type= IdType.AUTO)
	private Integer id;

	@Column(name = "exam_id")
	private Integer examId;

	@Column(name = "question_id")
	private Integer questionId;

	@Column(name = "question_text")
	private String questionText;

	@Column(name = "question_img")
	private String questionImg;

	@Column(name = "question_category")
	private String questionCategory;

	@Column(name = "stu_id")
	private Integer stuId;

	@Column(name = "answer_content")
	private String answerContent;

	@Column(name = "correct_answer")
	private String correctAnswer;

}
