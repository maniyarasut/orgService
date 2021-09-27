
package com.org.service.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.org.service.model.LoginModel;
import com.org.service.repo.LoginRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	LoginRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<LoginModel> login = repo.findById(username);
		if (login.isPresent()) {
			LoginModel user = login.get();
			return new UserDetails() {

				@Override
				public boolean isEnabled() { // TODO Auto-generated method stub
					boolean value = user.getIsActive() == 'Y' ? true : false;
					return true;
				}

				@Override
				public boolean isCredentialsNonExpired() {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public boolean isAccountNonLocked() {
					boolean value = user.getIsActive() == 'Y' ? true : false;
					return value;
				}

				@Override
				public boolean isAccountNonExpired() {
					System.out.println(user.getIsActive());
					boolean value = user.getIsActive() == 'Y' ? true : false;
					return value;
				}

				@Override
				public String getUsername() {
					System.out.println(user.getEmail());
					return user.getEmail();
				}

				@Override
				public String getPassword() { // TODO Auto-generated method stub
					System.out.println(user.getPassword());
					return user.getPassword();
				}

				@Override
				public Collection<? extends GrantedAuthority> getAuthorities() {
					List<GrantedAuthority> myList = new ArrayList<GrantedAuthority>();
					String[] tempList = user.getRole().split(",");
					for (String role : tempList) {
						System.out.println(role);
						myList.add(new SimpleGrantedAuthority(role));
					}
					return myList;
				}
			};
		} else
			throw new UsernameNotFoundException(username + "does not exist");
	}

}
