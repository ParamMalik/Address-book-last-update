package com.address.book.addressbookapi.service.impl;

import com.address.book.addressbookapi.dto.ContactDTO;
import com.address.book.addressbookapi.entity.ContactEntity;
import com.address.book.addressbookapi.mapper.DtoAndEntityMapper;
import com.address.book.addressbookapi.repo.ContactRepository;
import com.address.book.addressbookapi.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private ContactRepository contactRepository;

    // Save New
    @Override
    public ContactDTO saveAddress(ContactDTO contactDTO) {
        ContactEntity save = contactRepository.save(DtoAndEntityMapper.MAPPER.dtoToEntity(contactDTO));
        return DtoAndEntityMapper.MAPPER.entityToDto(save);
    }


    // Get All Address
    @Override
    public List<ContactDTO> getListOfAddress() {

        return DtoAndEntityMapper.MAPPER.contactEntityListToDto(contactRepository.findAll());

    }

    // Get Address By firstName
    @Override
    public List<ContactDTO> findAddressByFirstName(String firstName) {

        return DtoAndEntityMapper.MAPPER.contactEntityListToDto(contactRepository.findByFirstName(firstName));


    }

    // Delete Address By changing the status
    @Override
    public void deleteContact(Long customerId) {
        ContactEntity byContactId = contactRepository.findByContactId(customerId);
        byContactId.setIsActive("N");
        contactRepository.save(byContactId);

    }

}
