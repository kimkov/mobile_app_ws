package com.mobile.app.ws.io.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "addresses")
public class AddressEntity implements Serializable {

	private static final long serialVersionUID = 4980272614838256588L;
	
	@Id
	@GeneratedValue()
	private long id;
	
	@Column(length=30, nullable = false)
	private String addressId;
	
	@Column(length=15, nullable = false)
	private String city;
	
	@Column(length=15, nullable = false)
	private String country;
	
	@Column(length=100, nullable = false)
	private String streetName;
	
	@Column(length=7, nullable = false)
	private String zipCode;
	
	@Column(length=10, nullable = false)
	private String type;
	
	@ManyToOne
	@JoinColumn(name="users_id")
	private UserEntity userDetails;

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

	public UserEntity getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserEntity userDetails) {
		this.userDetails = userDetails;
	}
	
	
}
