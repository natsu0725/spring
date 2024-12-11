package com.example.postingapp.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.postingapp.entity.User;
import com.example.postingapp.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	private final UserRepository userRepository;
	
	//DI（コンストラクタインジェクション）
	@Autowired	//コンストラクタが1つの場合は省略可能
	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			//メールアドレスをキーにユーザー取得
			User user = userRepository.findByEmail(email);
			//役割の取得
			String userRoleName = user.getRole().getName();
			//ユーザーが持つ権限を格納するためのリストを作成
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			//ユーザーの権限を追加
			authorities.add(new SimpleGrantedAuthority(userRoleName));
			//ユーザー情報と権限情報を合わせてオブジェクト生成
			return new UserDetailsImpl(user, authorities);
		}catch(Exception e) {
			throw new UsernameNotFoundException("ユーザーが見つかりませんでした。");
		}
	}

}
