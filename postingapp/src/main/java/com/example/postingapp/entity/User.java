package com.example.postingapp.entity;




import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@ManyToOne	//多対一の関係。1人のユーザーは1つのロールに属するが、1つのロールは複数のユーザーを持てる
	@JoinColumn(name = "role_id")	//外部キーのカラム名を指定
	private Role role;
	
	@Column(name = "enabled")
	private Boolean enabled;
	
	/*insertable：そのカラムに値を挿入できるかどうか
	  updatable：そのカラムの値を更新できるかどうか
	  	true（デフォルト値）：カラムに挿入したり更新したりする値はアプリ側で管理
	  				   false：カラムに挿入または更新する値の管理をデータベース側に任せられる
	  */
	
	//作成日時
	@Column(name = "created_at" , insertable = false, updatable = false)
	private Timestamp createdAt;
	
	//更新日時
	@Column(name = "updated_at", insertable = false, updatable = false)
	private Timestamp updatedAt;

}
