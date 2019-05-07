package com.mslim.userinfoservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("admin").password("{noop}1111").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests()
			.anyRequest().authenticated()	// TODO - 서버끼리 통신할 때, 인증이 되었는지의 여부를 매번 체크를 해야하는 필요성이 보인다. 또한 여기 permitAll()하면 안될 거 같은데;
//			.antMatchers(HttpMethod.POST, "/login").permitAll()
//			.antMatchers(HttpMethod.POST, "/users").permitAll()		
			.and().csrf().disable()
			.headers().frameOptions().disable(); // TODO - should be reconsidered in terms of security
	}

}
