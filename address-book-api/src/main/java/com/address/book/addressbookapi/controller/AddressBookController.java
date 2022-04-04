package com.address.book.addressbookapi.controller;

import com.address.book.addressbookapi.dto.ContactDTO;
import com.address.book.addressbookapi.externalservice.impl.ExternalAddressBookServiceImpl;
import com.address.book.addressbookapi.service.impl.AddressBookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AddressBookController {

    @Autowired
    private AddressBookServiceImpl addressBookService;

    @Autowired
    private ExternalAddressBookServiceImpl externalAddressBookService;


    // Get List Of All Contacts

    @GetMapping("/search/{isRemote}")
    public ResponseEntity<List<ContactDTO>> getAllAddressBook(@PathVariable(name = "isRemote") String isRemote) {
        if (isRemote.equals("y")) {
            return ResponseEntity.ok(List.of(externalAddressBookService.getContactList()));
        } else {
            return ResponseEntity.ok(addressBookService.getListOfAddress());
        }
    }

    // Get List Of Contacts by firstName

    @GetMapping("/search/{isRemote}/{firstName}")
    public ResponseEntity<List<ContactDTO>> getAddressByFirstName(@PathVariable String firstName, @PathVariable(name = "isRemote") String isRemote) {
        if (isRemote.equals("y")) {
            return ResponseEntity.ok(List.of(externalAddressBookService.getContactListByFirstName(firstName)));
        } else {
            return ResponseEntity.ok(addressBookService.findAddressByFirstName(firstName));
        }


    }

    @PostMapping(path = "/save/{isRemote}")
    public ResponseEntity<ContactDTO> saveAddress(@Validated @RequestBody ContactDTO contactDTO, @PathVariable(name = "isRemote") String isRemote) {
        if (isRemote.equals("y")) {
            return new ResponseEntity<>(externalAddressBookService.saveContact(contactDTO), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(addressBookService.saveAddress(contactDTO), HttpStatus.OK);
        }


    }

    @PutMapping(path = "/update/{isRemote}/{contactId}")
    public ResponseEntity<String> updateAddressBook(@PathVariable Long contactId, @PathVariable(name = "isRemote") String isRemote) {
        if (isRemote.equals("y")) {
            externalAddressBookService.deleteContact(contactId);
        } else {
            addressBookService.deleteContact(contactId);
        }
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);


    }


}
