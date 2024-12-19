package com.example.postingapp.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.example.postingapp.Service.PostService;
import com.example.postingapp.entity.Post;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles
public class PostControllerTest {
	
	//MockMvcインスタンスを注入(フィールドインジェクション)
	@Autowired
	private MockMvc mockMvc;
	
    @Autowired
    private PostService postService;  
	
	/*indexメソッド*/
	@Test
	@WithUserDetails("taro.samurai@example.com")
	public void ログイン済みの場合は投稿一覧ページが正しく表示される() throws Exception{
		//GETメソッドで "/posts"にHTTPリクエストを送信する（投稿一覧ページにアクセスする）
		mockMvc.perform(get("/posts"))
				.andExpect(status().isOk())
				.andExpect(view().name("posts/index"));
	}
	
	@Test
	public void 未ログインの場合は投稿一覧ページからログインページにリダイレクトする() throws Exception{
		mockMvc.perform(get("/posts"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
	}
	
	/*showメソッド*/
	@Test
	@WithUserDetails("taro.samurai@example.com")
	public void ログイン済みの場合は投稿詳細ページが正しく表示される() throws Exception {
		mockMvc.perform(get("/posts/1"))
				.andExpect(status().isOk())
				.andExpect(view().name("posts/show"));
	}
	
	@Test
	public void 未ログインの場合は投稿詳細ページからログインページにリダイレクトする() throws Exception {
		mockMvc.perform(get("/posts/1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
	}
	
	/*registerメソッド*/
	@Test
	@WithUserDetails("taro.samurai@example.com")
	public void ログイン済みの場合は投稿作成ページが正しく表示される() throws Exception {
		mockMvc.perform(get("/posts/register"))
				.andExpect(status().isOk())
				.andExpect(view().name("posts/register"));
	}
	
	@Test
	public void 未ログインの場合は投稿作成ページからログインページにリダイレクトする() throws Exception {
		mockMvc.perform(get("/posts/register"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
		
	}
	
	/*createメソッド*/
	@Test
	@WithUserDetails("taro.samurai@example.com")
	@Transactional	//テストメソッドの実行後にデータベースの変更をロールバック、
					//つまりテストメソッド実行前の状態に戻してくれる
	public void ログイン済みの場合は投稿作成後に投稿一覧ページにリダイレクトする() throws Exception{
		mockMvc.perform(post("/posts/create").with(csrf()).param("title", "テストタイトル").param("content","テスト内容"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/posts"));
		//ログイン済みの場合
		Post post = postService.findFirstPostByOrderByIdDesc();
		assertThat(post.getTitle()).isEqualTo("テストタイトル");
		assertThat(post.getContent()).isEqualTo("テスト内容");
	}
	
	@Test
	@Transactional
	public void 未ログインの場合は投稿を作成せずにログインページにリダイレクトする() throws Exception{
		mockMvc.perform(post("/posts/create").with(csrf()).param("title","テストタイトル").param("content", "テスト内容"))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("http://localhost/login"));
		//未ログインの場合
		Post post = postService.findFirstPostByOrderByIdDesc();
		assertThat(post.getTitle()).isNotEqualTo("テストタイトル");
		assertThat(post.getContent()).isNotEqualTo("テスト内容");
	}

}
