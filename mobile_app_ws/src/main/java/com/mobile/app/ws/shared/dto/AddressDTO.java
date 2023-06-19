package com.mobile.app.ws.shared.dto;

public class AddressDTO {

	private long id;
	private String addressId;
	private String city;
	private String country;
	private String streetName;
	private String zipCode;
	private String type;
	private UserDTO userDetails;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getStreetName() {
		return streetName;
	}
	
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	
	public String getZipCode() {
		return zipCode;
	}
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public UserDTO getUserDetails() {
		return userDetails;
	}
	
	public void setUserDetails(UserDTO userDetails) {
		this.userDetails = userDetails;
	}
}
