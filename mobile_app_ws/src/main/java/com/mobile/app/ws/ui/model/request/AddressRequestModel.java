package com.mobile.app.ws.ui.model.request;

public class AddressRequestModel {

	private String city;
	private String country;
	private String streetName;
	private String zipcode;
	private String type;
	
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
	
	public String getZipcode() {
		return zipcode;
	}
	
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
