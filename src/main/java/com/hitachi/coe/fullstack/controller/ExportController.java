package com.hitachi.coe.fullstack.controller;

import com.hitachi.coe.fullstack.model.ExportRequest;
import com.hitachi.coe.fullstack.service.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class ExportController {


	private final ExportService exportService;

	@PostMapping("export")
	public ResponseEntity<Resource> export(@RequestBody ExportRequest exportRequest ) {
		return exportService.exportExcel( exportRequest );
	}
}

