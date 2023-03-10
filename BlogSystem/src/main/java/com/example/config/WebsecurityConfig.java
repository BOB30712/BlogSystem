package com.example.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.ServletException;


@Configuration
@EnableWebSecurity
public class WebsecurityConfig {
			
	  @Bean
	  public SecurityFilterChain configure(HttpSecurity http) throws Exception {

	    http
	    	.authorizeHttpRequests()
	    			.requestMatchers("/test").hasAuthority("大總管")
	    			.requestMatchers("/log").authenticated()
	    			.anyRequest().permitAll()
			.and()
			.formLogin()//若未不符合authorize條件，則產生預設login表單
				.defaultSuccessUrl("/logoutlogout")
			.and()
				.httpBasic() //產生基本表單
			.and()
			.logout()
				.logoutSuccessUrl("/logoutSuccess")
				.permitAll()
			.and()
				.cors().and().csrf().disable();
	    //http.addFilterBefore(null, BasicAuthenticationFilter.class);
		//http.addFilterBefore(null, UsernamePasswordAuthenticationFilter.class);
	    return http.build();
	  }
	  //建立密碼加密方式以純文字驗證
	  public class PasswordEnconderTest implements PasswordEncoder {
	    @Override
	    public String encode(CharSequence charSequence) {
	        return charSequence.toString();
	    }

	    @Override
	    public boolean matches(CharSequence charSequence, String s) {
	        return charSequence.toString().equals(s);
	    }
	  }
	  //密碼加密方式
	  @Bean
	  public PasswordEncoder passwordEncoder(){
	      return new PasswordEnconderTest();
	  }
	
}
