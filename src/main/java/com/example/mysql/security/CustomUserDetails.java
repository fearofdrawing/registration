package com.example.mysql.security;

import com.example.mysql.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.example.mysql.model.user.UserRole.USER;

public class CustomUserDetails implements UserDetails {
	
	private User user;

	public CustomUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
		Set <SimpleGrantedAuthority> existingAuthorities;
		try {
			existingAuthorities = user.getUserRole().getGrantedAuthorities();
			return existingAuthorities;
		}		catch (NullPointerException e){

			grantedAuthorities.addAll(USER.getGrantedAuthorities());
		}
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public String getFullName() {
		return user.getFirstName() + " " + user.getLastName();
	}

}
