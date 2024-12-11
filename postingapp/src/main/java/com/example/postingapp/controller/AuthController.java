package com.example.postingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//認証（ログイン、会員登録機能）
@Controller
public class AuthController {
	@GetMapping("/login")
	public String login() {
		return "auth/login";
	}

}
