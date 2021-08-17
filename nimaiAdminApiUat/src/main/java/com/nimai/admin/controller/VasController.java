package com.nimai.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimai.admin.payload.PagedResponse;
import com.nimai.admin.payload.SearchRequest;
import com.nimai.admin.payload.VasResponse;
import com.nimai.admin.payload.VasUpdateRequestBody;
import com.nimai.admin.service.VasService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/vas")
public class VasController {

	@Autowired
	VasService vasService;

	@PostMapping("/planSave")
	public ResponseEntity<?> saveVasDetails(@RequestBody VasResponse vasRequest) {
		return vasService.saveVasDetails(vasRequest);
	}

	@PostMapping("/getVasList")
	public PagedResponse<?> getAllDetails(@RequestBody SearchRequest request) {
		return vasService.getAllVasDetails(request);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getVasDetailById(@PathVariable(value = "id") Integer vasId) {
		return vasService.getVasDetailById(vasId);
	}
	
	@PostMapping("/checkerUpdate")
	public ResponseEntity<?> checkerUpdate(@RequestBody VasUpdateRequestBody request) {
		System.out.println("Controller " + request.getVasid());
		return vasService.checkerUpdate(request);
	}
}
