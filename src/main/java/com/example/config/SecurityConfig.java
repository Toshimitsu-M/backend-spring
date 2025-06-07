package com.example.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.formLogin(login -> login. // フォーム認証の設定記述開始
				loginProcessingUrl("/login") // ユーザ名・パスワードの送信先URL
				.loginPage("/login") // ログイン画面のURL
				.defaultSuccessUrl("/home") // ログイン成功後のリダイレクト先URL
				.failureUrl("/login?error") // ログイン失敗後のリダイレクト先URL
				.permitAll()) // ログイン画面は未ログインでもアクセス可能
				.logout(logout -> logout // ログアウトの設定記述開始
						.logoutSuccessUrl("/")
						.invalidateHttpSession(true)

				) // ログアウト成功後のリダイレク先URL
				.authorizeHttpRequests(
						authz -> authz.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
								.requestMatchers("/").permitAll()
								.requestMatchers("/general").hasRole("GENERAL")
								.requestMatchers("/admin").hasRole("ADMIN")
								.requestMatchers("/api/anime").permitAll()
								.requestMatchers("/characterComment/next-sequence").permitAll()
								.requestMatchers("/characterComment/**").permitAll()
								.requestMatchers("/chat").permitAll()
								.anyRequest().authenticated())
				.csrf().disable();

		;

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
