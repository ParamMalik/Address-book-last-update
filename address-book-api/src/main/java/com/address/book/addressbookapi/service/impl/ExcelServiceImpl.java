package com.address.book.addressbookapi.service.impl;

import com.address.book.addressbookapi.bulkdatasave.JdbcTemplateBulkOperations;
import com.address.book.addressbookapi.dto.ContactDTO;
import com.address.book.addressbookapi.entity.ContactEntity;
import com.address.book.addressbookapi.exception.customexception.EmptyDatabaseException;
import com.address.book.addressbookapi.helper.ExcelHelper;
import com.address.book.addressbookapi.mapper.DtoAndEntityMapper;
import com.address.book.addressbookapi.repo.ContactRepository;
import com.address.book.addressbookapi.service.ExcelService;
import com.poiji.bind.Poiji;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service

public class ExcelServiceImpl implements ExcelService {
    @Autowired
    private ContactRepository repository;

    @Autowired
    JdbcTemplateBulkOperations jdbcTemplateBulkOperations;


    @Override
    public ByteArrayInputStream load() {
        List<ContactEntity> contacts = repository.findAll();
        if (contacts.isEmpty()) {
            throw new EmptyDatabaseException();
        } else {
            return ExcelHelper.contactsToExcel(contacts);
        }

    }

    public void save(MultipartFile multipartFile) {
        try {
            ArrayList<ContactEntity> contactEntities = ExcelHelper.convertExcelToListOfProduct(multipartFile.getInputStream());

//            repository.saveAll(contactEntities);
            jdbcTemplateBulkOperations.bulkPersist(contactEntities);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    public void uploadExcel(MultipartFile multipartFile) throws IOException {
//        InputStream inputStream = multipartFile.getInputStream();
//        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//
//        XSSFSheet sheet = workbook.getSheet("Address_Book");
//        List<ContactDTO> contacts = Poiji.fromExcel(sheet, ContactDTO.class);
//        List<ContactEntity> contactEntities = DtoAndEntityMapper.MAPPER.dtoListTOEntityList(contacts);
//        jdbcTemplateBulkOperations.bulkPersist((ArrayList<ContactEntity>) contactEntities);
//
//        System.out.println(contacts.get(1).getFirstName());
//    }
//

//    public void upload(File multipartFile){
//        List<ContactDTO> contactDTOS = Poiji.fromExcel(multipartFile, ContactDTO.class);
//        jdbcTemplateBulkOperations.bulkPersist();
//    }


}
