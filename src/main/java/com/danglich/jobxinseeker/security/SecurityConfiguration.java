package com.danglich.jobxinseeker.security;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.danglich.jobxinseeker.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.danglich.jobxinseeker.service.AuthService;
import com.danglich.jobxinseeker.service.JobSeekerService;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
	
	private  AuthenticationProvider authenticationProvider;
	private  AuthService authService;
	private  OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService;
	
	public SecurityConfiguration( AuthenticationProvider authenticationProvider,
			@Lazy AuthService authService,
			OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService) {
		super();
		this.authenticationProvider = authenticationProvider;
		this.authService = authService;
		this.oAuth2UserService = oAuth2UserService;
	}
	

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				configurer ->
					configurer
						.requestMatchers("/").permitAll()
						.requestMatchers("/home").permitAll()
						.requestMatchers("/auth/**").permitAll()
						.requestMatchers("/oauth/**").permitAll()
						.requestMatchers("/static/**").permitAll()
						.requestMatchers("/style/**").permitAll()
						.requestMatchers("/viec-lam/**").permitAll()
						.requestMatchers(HttpMethod.GET , "/cong-ty/**").permitAll()
						.anyRequest().authenticated()
			
				)
			.formLogin(form -> form.loginPage("/auth/login")
									//.loginProcessingUrl("/login-process")
									//.defaultSuccessUrl("/", true)
									.usernameParameter("email")
									.permitAll()
					)
			.logout(logout -> 
				logout.logoutUrl("/logout")
					.logoutSuccessUrl("/auth/login?logout=true")
					  .permitAll())
			.oauth2Login(o -> 
							o.loginPage("/auth/login")
								.successHandler(oAuthenticationSuccessHandler())
								.userInfoEndpoint(u -> u.userService(oAuth2UserService)))
			.authenticationProvider(authenticationProvider)
			.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
		;

		return http.build();
	}
	
	
	@Bean 
	public AuthenticationSuccessHandler oAuthenticationSuccessHandler() {
		
		return new OAuth2AuthenticationSuccessHandler(authService);
	}

	@Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    

	public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                AuthenticationException exception) throws IOException, ServletException {
            if (exception instanceof DisabledException) {
                
                response.sendRedirect("/disabled-account");
            } else {
                
                response.sendRedirect("/login-error");
            }
        }
    }
	
	
	

}
