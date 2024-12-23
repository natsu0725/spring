package com.example.postingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.postingapp.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	//指定したメールアドレスに一致するユーザーを取得
	public User findByEmail(String email);
}
