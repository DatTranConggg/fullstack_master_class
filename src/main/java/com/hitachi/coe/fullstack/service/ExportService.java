package com.hitachi.coe.fullstack.service;

import com.hitachi.coe.fullstack.model.ExportRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface ExportService {


	ResponseEntity<Resource> exportExcel(ExportRequest exportRequest ) ;


}