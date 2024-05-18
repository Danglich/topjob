package com.danglich.jobxinseeker.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import com.danglich.jobxinseeker.model.CV;
import com.danglich.jobxinseeker.model.Jobs;
import com.danglich.jobxinseeker.repository.CVRepository;
import com.danglich.jobxinseeker.service.CVService;
import com.danglich.jobxinseeker.service.JobService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@Validated
public class CVController {

	private final CVService service;
	private final JobService jobService;

	@GetMapping("/xem-cv/{cvId}")
	public ResponseEntity<Resource> viewCV(@PathVariable("cvId") int id) {
		CV cv = service.getById(id);

		ByteArrayResource resource = new ByteArrayResource(cv.getContent());

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION,
						"inline;filename=" + cv.getFilename())
				.contentType(MediaType.APPLICATION_PDF)
				.contentLength(resource.contentLength()).body(resource);
	}

	@GetMapping("/xem-cv/image/{cvId}")
	public ResponseEntity<InputStreamResource> viewCVImage(
			@PathVariable("cvId") int id) throws IOException {
		byte[] cvImageData = service.convertPdfToImage(id);

		// Trả về hình ảnh dưới dạng phản hồi HTTP
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(
				new InputStreamResource(new ByteArrayInputStream(cvImageData)));
	}

	@GetMapping("/quan-ly-cv")
	public String showManagerCV(Model theModel) {

		List<Jobs> suggestJobsByUser = jobService.getTop4SuggestJobs();
		theModel.addAttribute("suggestJobsByUser", suggestJobsByUser);

		List<CV> cvs = service.getForCurrentSeeker();
		theModel.addAttribute("cvs", cvs);

		return "cv/list.html";
	}

	@PostMapping("/cv/update")
	public String updateName(@RequestParam(name = "cvId") @NotNull int cvId,
			@RequestParam(name = "filename") @NotBlank String name) {
		service.updateName(cvId, name);

		return "redirect:/quan-ly-cv";
	}

	@GetMapping("/cv/download/{cvId}")
	public ResponseEntity<InputStreamResource> downloadCv(
			@PathVariable("cvId") int id) throws IOException {
		CV cv = service.getById(id);

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
				.header("Content-Disposition",
						"attachment; filename=" + cv.getFilename())
				.body(new InputStreamResource(
						new ByteArrayInputStream(cv.getContent())));
	}
	
	@PostMapping("/cv/delete")
	public String delete(@RequestParam(name = "cvId") @NotNull int cvId) {
		service.delete(cvId);

		return "redirect:/quan-ly-cv";
	}
	
	@PostMapping("/cv/set-default")
	public String setDefault(@RequestParam(name = "cvId") @NotNull int cvId) {
		service.updateIsDefault(cvId);

		return "redirect:/quan-ly-cv";
	}
	
	@PostMapping("/cv/upload")
	public String uploadBySeeker(@RequestParam(name = "file") @NotNull MultipartFile file) throws IOException {
		service.uploadForSeeker(file);
		return "redirect:/quan-ly-cv";
	}
	
	

}
