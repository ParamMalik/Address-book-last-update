package com.address.book.addressbookapi.controller;

import com.address.book.addressbookapi.bulkdatasave.JdbcTemplateBulkOperations;
import com.address.book.addressbookapi.helper.ExcelHelper;
import com.address.book.addressbookapi.service.impl.ExcelServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(value = "/excel")
public class ExcelController {
    @Autowired
    ExcelServiceImpl fileService;


    @ApiOperation("Download Data From Database To Excel File")
    @GetMapping(path = "/download")
    public ResponseEntity<Resource> getFile() {
        String filename = "Address_Book.xlsx";

        InputStreamResource file = new InputStreamResource(fileService.load());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }


    @ApiOperation("Upload Data From Excel To Database")
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {


        final Logger logger = LoggerFactory.getLogger(ExcelController.class);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        if (ExcelHelper.checkExcelFormat(multipartFile)) {
//            fileService.uploadExcel(multipartFile);
            fileService.save(multipartFile);
            stopWatch.stop();
            logger.info("Complete Ops -> Total time in seconds: " + stopWatch.getTotalTimeSeconds());

            long endTime = System.currentTimeMillis();


            return ResponseEntity.ok("file is uploaded");

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Upload Excel File only");
    }


}



