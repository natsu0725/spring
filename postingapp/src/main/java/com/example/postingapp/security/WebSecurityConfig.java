package com.example.postingapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


//Spring Securityの設定を行うためのクラス

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			//どのページへのアクセスを許可するか
			.authorizeHttpRequests((requests) -> requests
					.requestMatchers("/css/**").permitAll()	//全てのユーザーにアクセスを許可するURL
					.anyRequest().authenticated()			//上記以外のURLはログインが必要
					)
			//ログイン時
			.formLogin((form) -> form
					.loginPage("/login")					//ログインページのURL
					.loginProcessingUrl("/login")			//ログインフォームの送信先URL
//					.defaultSuccessUrl("/?loggedIn")		//ログイン成功時のリダイレクト先URL
					.defaultSuccessUrl("/posts?loggedIn")  // ログイン成功時のリダイレクト先URL
					.failureUrl("/login?error")				//ログイン失敗時のリダイレクト先URL
					.permitAll()
					)
			//ログアウト時
			.logout((logout) -> logout
					.logoutSuccessUrl("/login?loggedOut")  // ログアウト時のリダイレクト先URL
					.permitAll()
					);
		
		return http.build();
	}
	
	//ハッシュ化
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
