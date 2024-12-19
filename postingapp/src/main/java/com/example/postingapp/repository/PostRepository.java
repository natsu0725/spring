package com.example.postingapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.postingapp.entity.Post;
import com.example.postingapp.entity.User;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
	//指定されたユーザーが作成した投稿を、最新の投稿から順に（降順(Desc)）取得するメソッド
	public List<Post> findByUserOrderByCreatedAtDesc(User user);
	
	
	//idカラムの値で降順に並べ替え、最初の1件を取得する（IDが最も大きい投稿を取得）
	public Post findFirstByOrderByIdDesc();
}
