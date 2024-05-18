package com.danglich.jobxinseeker.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.danglich.jobxinseeker.exception.ResourceNotFoundException;
import com.danglich.jobxinseeker.model.Address;
import com.danglich.jobxinseeker.repository.AddressRepository;
import com.danglich.jobxinseeker.service.AddressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

	private final AddressRepository repository;

	@Override
	public List<Address> getAll() {

		return repository.findAll();
	}

	@Override
	public void create(Address address) {
		// TODO Auto-generated method stub

	}

	@Override
	public Address getById(int id) {

		return repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Not found address"));
	}

}
