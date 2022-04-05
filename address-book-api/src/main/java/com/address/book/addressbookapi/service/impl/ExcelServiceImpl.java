package com.address.book.addressbookapi.service.impl;

import com.address.book.addressbookapi.bulkdatasave.JdbcTemplateBulkOperations;
import com.address.book.addressbookapi.entity.ContactEntity;
import com.address.book.addressbookapi.exception.customexception.EmptyDatabaseException;
import com.address.book.addressbookapi.helper.ExcelHelper;
import com.address.book.addressbookapi.repo.ContactRepository;
import com.address.book.addressbookapi.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
//            this.repository.saveAll(contactEntities);
            jdbcTemplateBulkOperations.bulkPersist(contactEntities);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
