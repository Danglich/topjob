package com.danglich.jobxinseeker.security.oauth2;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User{
	
	private OAuth2User oauth2User;
    
    public CustomOAuth2User(OAuth2User oauth2User) {
        this.oauth2User = oauth2User;
    }
    
	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return oauth2User.getAttributes();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return oauth2User.getAuthorities();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return oauth2User.getAttribute("email");
	}
	
	public String getEmail() {
		
		return oauth2User.getAttribute("email");
	}
	
	public String getAvatar() {
		
		return oauth2User.getAttribute("picture");
	}
	
	public String getFullname() {
		
		return oauth2User.getAttribute("name");
	}

}
