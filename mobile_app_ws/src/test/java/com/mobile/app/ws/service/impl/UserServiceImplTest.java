package com.mobile.app.ws.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mobile.app.ws.exceptions.UserServiceException;
import com.mobile.app.ws.io.entity.AddressEntity;
import com.mobile.app.ws.io.entity.UserEntity;
import com.mobile.app.ws.io.repository.UserRepository;
import com.mobile.app.ws.shared.Utils;
import com.mobile.app.ws.shared.dto.AddressDTO;
import com.mobile.app.ws.shared.dto.UserDTO;

class UserServiceImplTest {
	
	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepository;
	
	@Mock
	Utils utils;
	
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	String userId = "sdfsdfsdf";
	
	String encryptedPassword = "errpouoiuu";
	
	UserEntity userEntity;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		
		userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setFirstName("Lana");
		userEntity.setLastName("Blakley");
		userEntity.setUserId(userId);
		userEntity.setEncryptedPassword(encryptedPassword);
		userEntity.setEmail("lana@gmail.com");
		userEntity.setAddresses(getAddressesEntity());
	}

	@Test
	final void testGetUser() {
		
		when(userRepository.findUserByEmail(anyString())).thenReturn(userEntity);
		
		UserDTO userDTO = userService.getUser("test@test.com");
		
		assertNotNull(userDTO);
		assertEquals("Lana", userDTO.getFirstName());
	}
	
	@Test
	final void testGetUser_UsernameNotFoundException() {
		when(userRepository.findUserByEmail(anyString())).thenReturn(null);
		
		assertThrows(UsernameNotFoundException.class, 
				()-> {
					userService.getUser("test@test.com");
				});
	}
	
	@Test
	final void testCreateUser_CreateUserServiceException() {
		when(userRepository.findUserByEmail(anyString())).thenReturn(userEntity);
		
		UserDTO userDTO = new UserDTO();
		userDTO.setAddresses(getAddressesDTO());
		userDTO.setFirstName("Lana");
		userDTO.setLastName("Blakley");
		userDTO.setPassword("123");
		userDTO.setEmail("lana@gmail.com");
		
		assertThrows(UserServiceException.class, 
				()-> {
					userService.createUser(userDTO);
				});
	}
	
	@Test
	final void testCreateUser() {
		
		when(userRepository.findUserByEmail(anyString())).thenReturn(null);
		when(utils.generatedAddressId(anyInt())).thenReturn("sdfsdf");
		when(utils.generatedUserId(anyInt())).thenReturn(userId);
		when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
		
		UserDTO userDTO = new UserDTO();
		userDTO.setAddresses(getAddressesDTO());
		userDTO.setFirstName("Lana");
		userDTO.setLastName("Blakley");
		userDTO.setPassword("123");
		userDTO.setEmail("lana@gmail.com");
		
		UserDTO storedUserDetails = userService.createUser(userDTO);
		assertNotNull(storedUserDetails);
		assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
		assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
		assertNotNull(storedUserDetails.getUserId());	
		assertEquals(storedUserDetails.getAddresses().size(), userEntity.getAddresses().size());
		verify(utils, times(storedUserDetails.getAddresses().size())).generatedAddressId(30);
		verify(bCryptPasswordEncoder, times(1)).encode("123");
		verify(userRepository, times(1)).save(any(UserEntity.class));
	}
	
	private List<AddressDTO> getAddressesDTO() {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setType("shipping");
		addressDTO.setCity("Valencia");
		addressDTO.setCountry("Spain");
		addressDTO.setZipCode("ABC123");
		addressDTO.setStreetName("123 Street name");
		
		AddressDTO billingAddressDTO = new AddressDTO();
		billingAddressDTO.setType("shipping");
		billingAddressDTO.setCity("Valencia");
		billingAddressDTO.setCountry("Spain");
		billingAddressDTO.setZipCode("ABC123");
		billingAddressDTO.setStreetName("123 Street name");
		
		List<AddressDTO> addresses = new ArrayList<>();
		addresses.add(addressDTO);
		addresses.add(billingAddressDTO);
		return addresses;
	}
	
	private List<AddressEntity> getAddressesEntity() {
		List<AddressDTO> addresses = getAddressesDTO();
		Type listType = new TypeToken<List<AddressEntity>>() {}.getType();
		return new ModelMapper().map(addresses, listType);
	}
}
