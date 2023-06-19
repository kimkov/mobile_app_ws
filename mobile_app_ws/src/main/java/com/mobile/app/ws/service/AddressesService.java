package com.mobile.app.ws.service;

import java.util.List;

import com.mobile.app.ws.shared.dto.AddressDTO;

public interface AddressesService {

	List<AddressDTO> getAddresses(String userId);
	
	AddressDTO getAddress(String addressId);
}
