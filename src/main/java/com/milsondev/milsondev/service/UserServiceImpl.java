package com.milsondev.milsondev.service;


import com.milsondev.milsondev.db.entities.Role;
import com.milsondev.milsondev.db.entities.User;
import com.milsondev.milsondev.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList(new Role("ROLE_USER")));
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		Optional<User> userOptional = userRepository.findByEmail(userEmail);
		if(!userOptional.isPresent()) {
			throw new UsernameNotFoundException("User credentials was not found...");
		}
		User user = userOptional.get();
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));		
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	public User getUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Optional<User> userOptional = userRepository.findByEmail(auth.getName());
		if(!userOptional.isPresent()) {
			throw new UsernameNotFoundException("User credentials was not found...");
		}
		return userRepository.findByEmail(auth.getName()).get();
	}

	public Long getAuthenticatedUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Optional<User> userOptional = userRepository.findByEmail(auth.getName());
		if(!userOptional.isPresent()) {
			throw new UsernameNotFoundException("User credentials was not found...");
		}
		return userRepository.findByEmail(auth.getName()).get().getId();
	}

	public User updateUser(User user) {
		return userRepository.save(user);
	}



	public User getUserbyName(String userName){
		return userRepository.findByUserName(userName);
	}

	public User getUserbyId(Long id){
		Optional<User> userOptional = userRepository.findById(id);
		if(!userOptional.isPresent()) {
			throw new UsernameNotFoundException("User not found...");
		}
		return userOptional.get();
	}
	
}
