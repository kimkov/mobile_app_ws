package com.mobile.app.ws.ui.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mobile.app.ws.service.impl.UserServiceImpl;
import com.mobile.app.ws.shared.dto.AddressDTO;
import com.mobile.app.ws.shared.dto.UserDTO;
import com.mobile.app.ws.ui.model.response.UserRest;

class UserControllerTest {
	
	@InjectMocks
	UserController userController;
	
	@Mock
	UserServiceImpl userService;
	
	UserDTO userDTO;
	
	final String USER_ID = "123";
	

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		userDTO = new UserDTO();
		userDTO.setFirstName("Lana");
		userDTO.setLastName("Blakley");
		userDTO.setEmail("lana@gmail.com");
		userDTO.setEmailVerificationStatus(Boolean.FALSE);
		userDTO.setEmailVerificationToken(null);
		userDTO.setUserId(USER_ID);
		userDTO.setAddresses(getAddressesDTO());
		userDTO.setEncryptedPassword("123");
	}

	@Test
	void testGetUser() {
		when(userService.getUserByUserId(anyString())).thenReturn(userDTO);
		
		UserRest userRest = userController.getUser(USER_ID);
		
		assertNotNull(userRest);
		assertEquals(USER_ID, userRest.getUserId());
		assertEquals(userDTO.getFirstName(), userRest.getFirstName());
		assertEquals(userDTO.getLastName(), userRest.getLastName());
		assertTrue(userDTO.getAddresses().size() == userRest.getAddresses().size());
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

}
