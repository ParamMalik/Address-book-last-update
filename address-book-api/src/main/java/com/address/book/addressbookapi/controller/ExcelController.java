package com.address.book.addressbookapi.controller;

import com.address.book.addressbookapi.helper.ExcelHelper;
import com.address.book.addressbookapi.service.impl.ExcelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping(value = "/excel")
public class ExcelController {
    @Autowired
    ExcelServiceImpl fileService;

    @GetMapping(path = "/download")
    public ResponseEntity<Resource> getFile() {
        String filename = "Address_Book.xlsx";

        InputStreamResource file = new InputStreamResource(fileService.load());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }


    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile multipartFile) {
        long start = System.currentTimeMillis();
        if (ExcelHelper.checkExcelFormat(multipartFile)) {
            fileService.save(multipartFile);
            long end = System.currentTimeMillis();
            System.out.println(end - start);
            return ResponseEntity.ok(Map.of("message", "file is uploaded"));

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Upload Excel File only");
    }


}



