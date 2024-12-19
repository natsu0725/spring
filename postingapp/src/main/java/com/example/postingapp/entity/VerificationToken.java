package com.example.postingapp.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "varification_tokens")
public class VerificationToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@OneToOne	//1対1「1つのuser_idに対応するuserは1つ」
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "token")
	private String token;
	
	//作成日時
	@Column(name = "created_at",insertable = false, updatable = false)
	private Timestamp createdAt;
	
	//更新日時
	@Column(name = "updated_at",insertable = false, updatable = false)
	private Timestamp updatedAt;
}
