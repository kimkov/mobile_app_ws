package com.mobile.app.ws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mobile.app.ws.exceptions.UserServiceException;
import com.mobile.app.ws.io.entity.UserEntity;
import com.mobile.app.ws.io.repository.PasswordResetTokenRepository;
import com.mobile.app.ws.io.repository.UserRepository;
import com.mobile.app.ws.service.UserService;
import com.mobile.app.ws.shared.Utils;
import com.mobile.app.ws.shared.dto.AddressDTO;
import com.mobile.app.ws.shared.dto.UserDTO;
import com.mobile.app.ws.ui.model.response.ErrorMessages;
import com.mobile.app.ws.io.entity.PasswordResetTokenEntity;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	PasswordResetTokenRepository passwordResetTokenRepository;
	
	
	
	@Override
	public UserDTO createUser(UserDTO user) {
		
		if(userRepository.findUserByEmail(user.getEmail()) != null) throw new UserServiceException("Record already exist");
		
		for(int i = 0; i < user.getAddresses().size(); i++) {
			AddressDTO address = user.getAddresses().get(i);
			address.setUserDetails(user);
			address.setAddressId(utils.generatedAddressId(30));
			user.getAddresses().set(i, address);
		}
		
//		UserEntity userEntity = new UserEntity();
		ModelMapper modelMapper = new ModelMapper();
		UserEntity userEntity = modelMapper.map(user, UserEntity.class);
//		BeanUtils.copyProperties(user, userEntity);
		
		String publicUserId = utils.generatedUserId(30);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setUserId(publicUserId);
		
		UserEntity storedUserDetails = userRepository.save(userEntity);
		
		UserDTO returnValue = modelMapper.map(storedUserDetails, UserDTO.class);
//		BeanUtils.copyProperties(storedUserDetails, returnValue);
		
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findUserByEmail(username);
		if(userEntity == null) throw new UsernameNotFoundException(username);
		return new User(username, userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDTO getUser(String email) {
		UserEntity userEntity = userRepository.findUserByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		
		UserDTO returnValue = new UserDTO();
		BeanUtils.copyProperties(userEntity, returnValue);
		
		return returnValue;
	}

	@Override
	public UserDTO getUserByUserId(String userId) {
		UserDTO returnValue = new UserDTO();
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null) throw new UsernameNotFoundException(userId);
		BeanUtils.copyProperties(userEntity, returnValue);
		return returnValue;
	}

	@Override
	public UserDTO updateUser(String userId, UserDTO user) {
		UserDTO returnValue = new UserDTO();
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if(userEntity == null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		
		UserEntity updatedUserDetails = userRepository.save(userEntity);
		BeanUtils.copyProperties(updatedUserDetails, returnValue);
		return returnValue;
	}

	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if(userEntity == null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		userRepository.delete(userEntity);
	}

	@Override
	public List<UserDTO> getUsers(int page, int limit) {
		List<UserDTO> returnValue = new ArrayList<>();
		
		if(page > 0) page-=1;
		
		Pageable pageableRequest = PageRequest.of(page, limit);
		
		Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
		List<UserEntity> users = usersPage.getContent();
		
		for(UserEntity userEntity : users) {
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(userEntity, userDTO);
			returnValue.add(userDTO);
		}
		return returnValue;
	}

	@Override
	public boolean requestPasswordReset(String email) {
		boolean  returnValue = false;
		
		UserEntity userEntity = userRepository.findUserByEmail(email);
		
		if(userEntity == null) {
			return returnValue;
		}
		
		String token = new Utils().generatePasswordResetToken(userEntity.getUserId());
		
		PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
		passwordResetTokenEntity.setToken(token);
		passwordResetTokenEntity.setUserDetails(userEntity);
		passwordResetTokenRepository.save(passwordResetTokenEntity);
		
		return returnValue;
	}
}
