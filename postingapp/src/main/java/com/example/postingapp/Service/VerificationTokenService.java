package com.example.postingapp.Service;

import org.springframework.stereotype.Service;

import com.example.postingapp.entity.User;
import com.example.postingapp.entity.VerificationToken;
import com.example.postingapp.repository.VerificationTokenRepository;

import jakarta.transaction.Transactional;

@Service
public class VerificationTokenService {
	private final VerificationTokenRepository verificationTokenRepository;
	
	//DI（コンストラクタインジェクション）
	public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
		this.verificationTokenRepository = verificationTokenRepository;
	}
	
	@Transactional
	public void create(User user, String token) {
		VerificationToken verificationToken = new VerificationToken();
		
		verificationToken.setUser(user);
		verificationToken.setToken(token);
		
		verificationTokenRepository.save(verificationToken);
	}
	
	//トークンの文字列で検索した結果を返す
	public VerificationToken getVerificationToken(String token) {
		return verificationTokenRepository.findByToken(token);
	}

}
