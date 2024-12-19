package com.example.postingapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.postingapp.Service.PostService;
import com.example.postingapp.entity.Post;
import com.example.postingapp.entity.User;
import com.example.postingapp.form.PostRegisterForm;
import com.example.postingapp.security.UserDetailsImpl;

@Controller
@RequestMapping("/posts")	//各メソッドに共通のパス
public class PostController {
	private final PostService postService;
	
	//DI
	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	//投稿一覧ページにアクセス
	@GetMapping
	public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, Model model) {
		User user = userDetailsImpl.getUser();
		List<Post> posts = postService.findByUserOrderByCreatedAtDesc(user);
		
		model.addAttribute("posts", posts);
		
		return "posts/index";
	}
	
	//投稿詳細ページにアクセス
	@GetMapping("/{id}")
					//@PathVariable：URLの一部をその引数に割り当てることができるアノテーション
	public String show(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes,Model model) {
		//指定したIDを持つ投稿を取得する
		Optional<Post> optionalPost = postService.findPostById(id);
		
		if(optionalPost.isEmpty()) {
			//フラッシュメッセージ：一時的に表示されるメッセージ
			redirectAttributes.addFlashAttribute("errorMessage","投稿が存在しません。");
			
			return "redirect:/posts";
		}
		
		//Optional型をPost型に変換
		Post post = optionalPost.get();
		model.addAttribute("post",post);
		
		return "posts/show";
	}
	
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("postRegisterForm", new PostRegisterForm());
		
		return "posts/register";
	}
	
	@PostMapping("/create")
	public String create(@ModelAttribute @Validated PostRegisterForm postRegisterForm,
						 BindingResult bindingResult,
						 @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
						 RedirectAttributes redirectAttributes,
						 Model model) {
		
		//エラーが存在すれば投稿作成ページを再度表示
		if(bindingResult.hasErrors()) {
			model.addAttribute("postRegisterForm", postRegisterForm);
			
			return "posts/register";
		}
		
		User user = userDetailsImpl.getUser();
		
		//ユーザー情報を含めた投稿情報をpostsテーブルに追加
		postService.createPost(postRegisterForm, user);
		//フラッシュメッセージとともに、投稿一覧ページにリダイレクト
		redirectAttributes.addFlashAttribute("successMessage", "投稿が完了しました。");
		
		return "redirect:/posts";
	}

}
