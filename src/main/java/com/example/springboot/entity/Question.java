package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name = "question")
public class Question {
	@TableId(type= IdType.AUTO)
	private Integer id;

	private String text;

	private String img;

	private String category;

	@Column(name = "sample_answer")
	private String sampleAnswer;

	@Column(name = "clazz_code")
	private String clazzCode;

	@Column(name = "ac_scores")
	private Integer acScores;

	@Column(name = "tot_scores")
	private Integer totScores;

	@Column(name = "difficulty_ratio")
	private Double difficultyRatio;

	@Column(name = "difficulty_scores")
	private Double difficultyScores;
}
