package com.danglich.jobxinseeker.service;

import java.util.List;

import com.danglich.jobxinseeker.model.Address;

public interface AddressService {
	
	List<Address> getAll();
	
	Address getById(int id);
	
	void create(Address address);

}
