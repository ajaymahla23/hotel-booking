package com.hotel.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.hotel.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

	@Bean
	public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
		return new CustomAuthenticationSuccessHandler();
	}

	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

//	    configure method
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
		auth.inMemoryAuthentication().withUser("admn").password(this.passwordEncoder().encode("admn")).roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/resources/**", "/addproductimg/**").permitAll().antMatchers("/admin/**")
				.hasRole("ADMIN").antMatchers("/user/**").hasRole("USER")
				.antMatchers("/login", "/register", "/addRegister", "/admin-login", "/dologin", "/fonts/**").permitAll()
				.antMatchers("/css/**", "/fonts/ionicons/css/**", "/images/**", "/js/**", "/scss/**", "/scss/mixins/**",
						"/scss/utilities/**")
				.permitAll().antMatchers("/admin-login").permitAll().antMatchers("/dashboard/personalProfile")
				.authenticated().anyRequest().authenticated().and().formLogin().loginPage("/login")
				.loginProcessingUrl("/dologin").successHandler(authenticationSuccessHandler).and().logout()
				.logoutSuccessUrl("/").logoutUrl("/logout").and().csrf().disable().exceptionHandling()
				.accessDeniedPage("/access-denied");

	}

	@Component
	public static class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws IOException, ServletException {
			String logintype = request.getParameter("login");
			if ("admin".equals(logintype)) {
				setDefaultTargetUrl("/personal-profile");
			}

			if ("agent".equals(logintype)) {
				setDefaultTargetUrl("/home");
			}
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}
}
