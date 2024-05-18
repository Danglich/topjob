package com.danglich.jobxinseeker.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import com.danglich.jobxinseeker.exception.ForbiddenException;
import com.danglich.jobxinseeker.exception.ResourceNotFoundException;
import com.danglich.jobxinseeker.model.CV;
import com.danglich.jobxinseeker.model.JobSeekers;
import com.danglich.jobxinseeker.repository.CVRepository;
import com.danglich.jobxinseeker.service.CVService;
import com.danglich.jobxinseeker.service.JobSeekerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CVServiceImpl implements CVService {

	private final CVRepository repository;
	private final JobSeekerService seekerService;

	@Override
	public CV uploadForApplication(MultipartFile file) throws IOException {

		if (file.getContentType().equals("application/pdf")) {
			CV cv = CV.builder().content(file.getBytes())
					.filename(file.getOriginalFilename()).isDefault(false)
					.build();

			return repository.save(cv);
		} else {
			throw new IllegalArgumentException("File must PDF format");
		}

	}
	
	@Override
	public CV uploadForSeeker(MultipartFile file) throws IOException {

		JobSeekers seeker = seekerService.getCurrentUser();

		if (file.getContentType().equals("application/pdf")) {
			CV cv = CV.builder().content(file.getBytes())
					.filename(file.getOriginalFilename()).isDefault(false)
					.seeker(seeker).build();

			return repository.save(cv);
		} else {
			throw new IllegalArgumentException("File must PDF format");
		}

	}

	@Override
	public CV getById(int id) {

		return repository.findById(id).orElseThrow(
				() -> new ResourceAccessException("Not found the CV"));
	}

	@Override
	public byte[] convertPdfToImage(int id) throws IOException {
		CV theCv = repository.findById(id).orElseThrow(
				() -> new ResourceAccessException("Not found the CV"));
		return convertPdfToImage(theCv.getContent());
	}

	private byte[] convertPdfToImage(byte[] pdfData) throws IOException {
		try (PDDocument document = PDDocument.load(pdfData)) {
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			BufferedImage image = pdfRenderer.renderImageWithDPI(0, 300); // DPI
																			// là
																			// độ
																			// phân
																			// giải
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", outputStream);
			return outputStream.toByteArray();
		}
	}

	@Override
	public List<CV> getForCurrentSeeker() {
		JobSeekers seeker = seekerService.getCurrentUser();

		return repository.findBySeeker(seeker);
	}

	@Override
	@Transactional
	public void updateName(int id, String name) {
		CV cv = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Not found the CV"));
		if (!cv.getSeeker().getUser().getEmail().equals(SecurityContextHolder.getContext()
				.getAuthentication().getName())) {
			throw new ForbiddenException(
					"You are not allowed to perform this action");
		}

		repository.updateName(id, name);

	}

	@Override
	@Transactional
	public void delete(int id) {
		CV cv = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Not found the CV"));
		if (!cv.getSeeker().getUser().getEmail().equals(SecurityContextHolder.getContext()
				.getAuthentication().getName())) {
			throw new ForbiddenException(
					"You are not allowed to perform this action");
		}
		repository.deleteBySeeker(id);

	}

	@Override
	@Transactional
	public void updateIsDefault(int id) {
		CV newDefaultCv = repository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Not found the CV"));
		if (!newDefaultCv.getSeeker().getUser().getEmail().equals(SecurityContextHolder.getContext()
				.getAuthentication().getName())) {
			throw new ForbiddenException(
					"You are not allowed to perform this action");
		}
		
		JobSeekers seeker = seekerService.getCurrentUser();
		
		CV oldDefaultCv = repository.findByIsDefaultTrueAndSeekerId(seeker.getId()).stream().findFirst().orElse(null);

		if(!newDefaultCv.equals(oldDefaultCv)) {
			repository.updateIsDefault(newDefaultCv.getId(), true);
			if(oldDefaultCv != null)
				repository.updateIsDefault(oldDefaultCv.getId(), false);
		}
	}
}
