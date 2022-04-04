package com.address.book.addressbookapi.service.impl;

import com.address.book.addressbookapi.entity.ContactEntity;
import com.address.book.addressbookapi.helper.ExcelHelper;
import com.address.book.addressbookapi.repo.ContactRepository;
import com.address.book.addressbookapi.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service

public class ExcelServiceImpl implements ExcelService {
    @Autowired
    ContactRepository repository;

    @Override
    public ByteArrayInputStream load() {
        List<ContactEntity> tutorials = repository.findAll();
        return ExcelHelper.tutorialsToExcel(tutorials);
    }
}
