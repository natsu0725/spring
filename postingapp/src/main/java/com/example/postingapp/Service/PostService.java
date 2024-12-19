package com.example.postingapp.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.postingapp.entity.Post;
import com.example.postingapp.entity.User;
import com.example.postingapp.form.PostRegisterForm;
import com.example.postingapp.repository.PostRepository;

import jakarta.transaction.Transactional;

@Service
public class PostService {
	private final PostRepository postRepository;
	
	//DI
	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	// 特定のユーザーに紐づく投稿の一覧を作成日時が新しい順で取得する
	public List<Post> findByUserOrderByCreatedAtDesc(User user){
		return postRepository.findByUserOrderByCreatedAtDesc(user);
	}
	
	//指定したIDを持つ投稿を取得する
		 //Optional：nullを持つ可能性のあるオブジェクトをより安全かつ便利に扱うためのクラス
	public Optional<Post> findPostById(Integer id){
		return postRepository.findById(id);
	}
	
	//IDが最も大きい投稿を取得する
	public Post findFirstPostByOrderByIdDesc() {
		return postRepository.findFirstByOrderByIdDesc();
	}
	
	@Transactional
	public void createPost(PostRegisterForm postRegisterForm,User user) {
		Post post = new Post();
		
		post.setTitle(postRegisterForm.getTitle());
		post.setContent(postRegisterForm.getContent());
		post.setUser(user);
		
		postRepository.save(post);
	}

}
