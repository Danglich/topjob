package com.danglich.jobxinseeker.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import com.danglich.jobxinseeker.dto.ChangePasswordDTO;
import com.danglich.jobxinseeker.dto.LoginDTO;
import com.danglich.jobxinseeker.dto.RegisterDTO;
import com.danglich.jobxinseeker.exception.AuthException;
import com.danglich.jobxinseeker.exception.ConfirmationTokenExpiredException;
import com.danglich.jobxinseeker.exception.ExitedRegistationException;
import com.danglich.jobxinseeker.exception.IncorrectPasswordException;
import com.danglich.jobxinseeker.model.Company;
import com.danglich.jobxinseeker.model.ConfirmationToken;
import com.danglich.jobxinseeker.model.JobSeekers;
import com.danglich.jobxinseeker.model.Provider;
import com.danglich.jobxinseeker.model.Role;
import com.danglich.jobxinseeker.model.User;
import com.danglich.jobxinseeker.repository.CompanyRepository;
import com.danglich.jobxinseeker.repository.ConfirmationTokenRepository;
import com.danglich.jobxinseeker.repository.JobSeekerRepository;
import com.danglich.jobxinseeker.repository.UserRepository;
import com.danglich.jobxinseeker.security.oauth2.CustomOAuth2User;
import com.danglich.jobxinseeker.service.AuthService;
import com.danglich.jobxinseeker.service.ConfirmationTokenService;
import com.danglich.jobxinseeker.service.MailService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{
	
	private final UserRepository userRepository;
	private final JobSeekerRepository seekerRepository;
	private final PasswordEncoder passwordEncoder;
	private final MailService mailService;
	private final ConfirmationTokenService confirmationTokenService;
	private final ConfirmationTokenRepository confirmationTokenRepository;
	private final AuthenticationManager authenticationManager;
	private final CompanyRepository companyRepository;
	
	@Override
	@Transactional
	public void register(RegisterDTO registerDTO) {
		
		Optional<User> userOptional = userRepository.findByEmail(registerDTO.getEmail());
		
		if(userOptional.isPresent()) {
			if(userOptional.get().isEnabled())
				throw new AuthException("Email đã có tài khoản, vui lòng đăng nhập");
			else
				throw new ExitedRegistationException("This email already registered!");
		}
		
		User newUser = userRepository.save(User.builder()
				.email(registerDTO.getEmail())
				.password(passwordEncoder.encode(registerDTO.getPassword()))
				.enabled(false)
				.role(registerDTO.isEmployer() ? Role.ROLE_EMPLOYER : Role.ROLE_SEEKER)
				.provider(Provider.DATABASE)
				.build());
		
		JobSeekers seeker = JobSeekers.builder()
				.user(newUser)
				.build();
		seekerRepository.save(seeker);
		
		if(registerDTO.isEmployer()) {
			Company company = Company.builder()
										.user(newUser)
										.build();
			companyRepository.save(company);
		}
		
		String token = confirmationTokenService.save(newUser);
		
		
		String link = "http://localhost:8080/auth/confirm?token=" + token;
		String email = this.buildEmailConfirm("", link);
		mailService.send(registerDTO.getEmail(), "Xác thực tài khoản", email);
		
		
	} 

	@Override
	public void reSendConfirmationToken(String email) {
		User user = userRepository.findByEmail(email)
								.orElseThrow(() -> new ResourceAccessException("Not found accout with email : " + email));
		
		String token = confirmationTokenService.save(user);
		String link = "http://localhost:8080/auth/confirm?token=" + token;
		String emailHtml = this.buildEmailConfirm("", link);
		mailService.send(email, "Xác thực tài khoản", emailHtml);
		
	}
	
	private String buildEmailConfirm(String name, String link) {
		// Nội dung email dưới dạng HTML với CSS
        String htmlContent = "<html><head><style>"
                + "body { font-family: Arial, sans-serif; background-color: #f4f4f4; }"
                + "h1 { color: #333333; }"
                + "p { color: #666666; }"
                + "a { color: #007bff; text-decoration: none; }"
                + "</style></head><body>"
                + "<h1>Xin chào, " + name + "!</h1>"
                + "<p>Cảm ơn bạn đã đăng ký tài khoản của chúng tôi. Rất vui vì điều đó !</p>"
                + "<p>Vui lòng click vào đường dẫn sau để kích hoạt tài khoản của bạn: <a href=\"" + link + "\">Kích hoạt</a></p>"
                + "<p>Kích hoạt sẽ hết hiệu lực trong <strong>24h</strong></p>"
                + "</body></html>";

        return htmlContent;
    }
	
	private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

	@Override
	@Transactional
	public void confirm(String token , HttpServletRequest request, HttpServletResponse response) {
		ConfirmationToken confirmationToken = 
				confirmationTokenRepository.findByToken(token)
				  			.orElseThrow(() -> new ResourceAccessException("Not found the token"));
		if(confirmationToken.getExpiredAt().isBefore(LocalDateTime.now())) {
			throw new ConfirmationTokenExpiredException("Token has expired");
		}
		
		confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
		User user = confirmationToken.getUser();
		userRepository.enable(confirmationToken.getUser().getId());
		
		// Auto authenticate
		SecurityContextRepository securityContextRepository =
		        new HttpSessionSecurityContextRepository(); 

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        		user, null, user.getAuthorities()); 
        
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication); 
        securityContextHolderStrategy.setContext(context);
        securityContextRepository.saveContext(context, request, response); 
        
        
	}

	@Override
	public String login(LoginDTO loginDTO, HttpServletRequest request , HttpServletResponse response) {
		
		SecurityContextRepository securityContextRepository =
		        new HttpSessionSecurityContextRepository();
		
			UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
				loginDTO.getEmail(), loginDTO.getPassword()); 
		    Authentication authentication = authenticationManager.authenticate(token); 
		    SecurityContext context = securityContextHolderStrategy.createEmptyContext();
		    context.setAuthentication(authentication); 
		    securityContextHolderStrategy.setContext(context);
		    securityContextRepository.saveContext(context, request, response); 
		    
		    HttpSession session = request.getSession();
	        SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
	        if(savedRequest != null) 
	        	return savedRequest.getRedirectUrl();
	        else return null;
		
	}

	@Override
	@Transactional
	public void resetPassword(String email) {
		User user =  userRepository.findByEmail(email)
							.orElseThrow(() -> new ResourceAccessException("Tài khoản email của bạn chưa đăng ký"));
		String randomPassword = UUID.randomUUID().toString().substring(0, 8);
		String passwordEncode = passwordEncoder.encode(randomPassword);
		
		userRepository.resetPassword(user.getId(), passwordEncode);
		
		String htmlContent = "<html><head><style>"
                + "body { font-family: Arial, sans-serif; background-color: #f4f4f4; }"
                + "h1 { color: #333333; }"
                + "p { color: #666666; }"
                + "a { color: #007bff; text-decoration: none; }"
                + "</style></head><body>"
                + "<h1>Xin chào, </h1>" 
                + "<p>Mật khẩu mới của bạn là : " + randomPassword + "</p>"
                + "<p>Vui lòng tuyệt đối không cho ai biết thông tin mật khẩu này!</p>"
                + "</body></html>";
		
		mailService.send(user.getEmail(), "Gửi lại mật khẩu", htmlContent);
		
	}

	@Override
	@Transactional
	public void processLoginWithOAuth(CustomOAuth2User oAuth2User) {
		String email = oAuth2User.getEmail();
		Optional<User> userOptional = userRepository.findByEmail(email);
		if (userOptional.isEmpty()) {
			User user = User.builder()
					.enabled(true)
					.provider(Provider.GOOGLE)
					.email(email)
					.fullname(oAuth2User.getFullname())
					.role(Role.ROLE_SEEKER)
					.build();
			userRepository.save(user);
			
			JobSeekers seeker = JobSeekers.builder()
										.avatar(oAuth2User.getAvatar())
										.user(user)
										.build();
			seekerRepository.save(seeker);
		}
//		else {
//			User user = userOptional.get();
//			seeker.setAvatar(oAuth2User.getAvatar());
//			if(seeker.getFullName() == null)
//				seeker.setFullName(oAuth2User.getFullName());
//			repository.save(seeker);
//		}
		

	}

	@Override
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		User user = userRepository.findByEmail(authentication.getName())
				.orElseThrow(() -> new ResourceAccessException(
						"Not found the user"));
		return user;
	}

	@Override
	@Transactional
	public void changePassword(ChangePasswordDTO request) {
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new ResourceAccessException(
						"Not found user with this email"));

		if (!passwordEncoder.matches(request.getOldPassword(),
				user.getPassword())) {
			throw new IncorrectPasswordException("Mật khẩu không đúng");
		}
		String newPassword = passwordEncoder.encode(request.getNewPassword());
		userRepository.resetPassword(user.getId(), newPassword);

	}

}
