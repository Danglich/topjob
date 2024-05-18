package com.danglich.jobxinseeker.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import com.danglich.jobxinseeker.model.CV;

public interface CVService {
	
	CV uploadForApplication(MultipartFile file) throws IOException;
	
	CV uploadForSeeker(MultipartFile file) throws IOException;
	
	CV getById(int id);
	
	byte[] convertPdfToImage(int id) throws IOException; 
	
	List<CV> getForCurrentSeeker();
	
	void updateName(int id , String name);
	
	void delete(int id);
	
	void updateIsDefault(int id);
	
}
