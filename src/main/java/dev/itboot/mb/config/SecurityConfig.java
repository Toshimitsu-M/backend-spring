package dev.itboot.mb.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
//    @Bean
//    public DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//            .setType(EmbeddedDatabaseType.H2)
//            .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
//            .build();
//    }

//    @Bean
//    public UserDetailsManager users(DataSource dataSource) {
//        UserDetails user = User.withDefaultPasswordEncoder()
//            .username("user")
//            .password("password")
//            .roles("ROLE")
//            .build();
//        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//        users.createUser(user);
//        return users;
//    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.formLogin(login -> login. // フォーム認証の設定記述開始
				loginProcessingUrl("/login") // ユーザ名・パスワードの送信先URL
				.loginPage("/login") // ログイン画面のURL
				.defaultSuccessUrl("/home") // ログイン成功後のリダイレクト先URL
				.failureUrl("/login?error") // ログイン失敗後のリダイレクト先URL
				.permitAll()) // ログイン画面は未ログインでもアクセス可能
				.logout(logout -> logout // ログアウトの設定記述開始
						.logoutSuccessUrl("/")) // ログアウト成功後のリダイレク先URL
				.authorizeHttpRequests(
						authz -> authz.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
								.mvcMatchers("/").permitAll().mvcMatchers("/general").hasRole("GENERAL")
								.mvcMatchers("/admin").hasRole("ADMIN").anyRequest().authenticated());
//		http
//        .authorizeHttpRequests((authz) -> authz
//            .anyRequest().authenticated()
//        )
//        .httpBasic();
		return http.build();
	}

//	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//	System.out.@println(encoder.encode("元のパスワード"));
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.formLogin(login -> login
//                .loginProcessingUrl("/login")
//                .loginPage("/login")
//                .defaultSuccessUrl("/")
//                .failureUrl("/login?error")
//                .permitAll()
//        ).logout(logout -> logout
//                .logoutSuccessUrl("/")
//        ).authorizeHttpRequests(authz -> authz
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
//                .mvcMatchers("/").permitAll()
//                .mvcMatchers("/general").hasRole("ROLE_GENERAL")
//                .mvcMatchers("/admin").hasRole("ROLE_ADMIN")
//                .anyRequest().authenticated()
//        );
//        return http.build();
//    }

//    

//    @Configuration
//    public class SecurityConfiguration {
//
//        @Bean
//        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//            http
//                .authorizeHttpRequests((authz) -> authz
//                    .anyRequest().authenticated()
//                )
//                .httpBasic(withDefaults());
//            return http.build();
//        }
//
//    }
//    
//    
//    @Configuration
//    public class SecurityConfiguration {
//
//        @Bean
//        public WebSecurityCustomizer webSecurityCustomizer() {
//            return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
//        }
//
//    }
//    

//}