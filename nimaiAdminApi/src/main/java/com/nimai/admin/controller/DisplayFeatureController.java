package com.nimai.admin.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nimai.admin.model.NimaiMDisplayFeatures;
import com.nimai.admin.payload.ApiResponse;
import com.nimai.admin.payload.DisplayFeatureRequest;
import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.property.DisplayFeatureExcel;
import com.nimai.admin.service.DisplayFeatureService;
import com.nimai.admin.util.ExcelHelper;

/**
 * 
 * @author sahadeo.naik
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/displayFeature")
public class DisplayFeatureController {

	@Autowired
	DisplayFeatureService displayFeatureService;

	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";
		if (ExcelHelper.hasExcelFormat(file)) {
			try {
				List<DisplayFeatureRequest> dispList = DisplayFeatureExcel.excelToDisplayReq(file.getInputStream());
				List<NimaiMDisplayFeatures> mDisplay = displayFeatureService.saveDispFeat(dispList);
				if (mDisplay.size() > 0) {
					message = "Data Transferred to Database Successfully !!! ";
					return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true, message));
				} else {
					return new ResponseEntity<>(new ApiResponse(false, "Failed To Transfer Data"),
							HttpStatus.BAD_REQUEST);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<>(new ApiResponse(false, "File is not an excel"), HttpStatus.BAD_REQUEST);

	}

	@PostMapping("/list")
	public PagedResponse<?> getAllDetails(@RequestBody SearchRequest request) {

		return displayFeatureService.listDisplayFeatures(request);
	}

	@GetMapping("/download")
	public ResponseEntity<?> getFile() {
		String filename = "displayFeature.xlsx";
		InputStreamResource file = new InputStreamResource(DisplayFeatureExcel.loadExampleExcel());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
	
	@GetMapping("/countryList")
	public List<String> countryList() {
		return displayFeatureService.countryList();
	}

}
