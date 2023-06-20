package com.mobile.app.ws;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mobile.app.ws.io.entity.AuthorityEntity;
import com.mobile.app.ws.io.entity.RoleEntity;
import com.mobile.app.ws.io.entity.UserEntity;
import com.mobile.app.ws.io.repository.AuthorityRepository;
import com.mobile.app.ws.io.repository.RoleRepository;
import com.mobile.app.ws.io.repository.UserRepository;
import com.mobile.app.ws.shared.Utils;

@Component
public class InitialUsersSetup {
	
	@Autowired
	AuthorityRepository authorityRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@EventListener
	@Transactional
	public void onApplicationEvent(ApplicationReadyEvent event) {
		
		 AuthorityEntity readAuthority = createAuthority("READ_AUTHORITY");
		 AuthorityEntity writeAuthority = createAuthority("WRITE_AUTHORITY");
		 AuthorityEntity deleteAuthority = createAuthority("DELETE_AUTHORITY");
		 
		 RoleEntity roleUser = createRole("ROLE_USER", Arrays.asList(readAuthority, writeAuthority));
		 RoleEntity roleAdmin = createRole("ROLE_ADMIN", Arrays.asList(readAuthority, writeAuthority, deleteAuthority));
		 
		 if(roleAdmin == null) return;
		 UserEntity adminUser = new UserEntity();
		 adminUser.setFirstName("David");
		 adminUser.setLastName("Malan");
		 adminUser.setEmail("david@gmail.com");
		 adminUser.setEmailVerificationStatus(true);
		 adminUser.setUserId(utils.generatedUserId(30));
		 adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("12345"));
		 adminUser.setRoles(Arrays.asList(roleAdmin));
		 
		 userRepository.save(adminUser);
	}
	
	@Transactional
	private AuthorityEntity createAuthority(String name) {
		AuthorityEntity authority = authorityRepository.findByName(name);
		if(authority == null) {
			authority = new AuthorityEntity(name);
			authorityRepository.save(authority);
		}
		return authority;
	}
	
	@Transactional
	private RoleEntity createRole(String name, Collection<AuthorityEntity> authorities) {
		RoleEntity role = roleRepository.findByName(name);
		if(role == null) {
			role = new RoleEntity(name);
			role.setAuthorities(authorities);
			roleRepository.save(role);
		}
		return role;
	}
}
