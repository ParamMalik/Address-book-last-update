package com.address.book.addressbookapi.controller;

import com.address.book.addressbookapi.dto.ContactDTO;
import com.address.book.addressbookapi.externalservice.impl.ExternalAddressBookServiceImpl;
import com.address.book.addressbookapi.service.impl.AddressBookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ContactDTO> getAllAddressBook(@PathVariable(name = "isRemote") String isRemote)  {
        if (isRemote.equals("y")) {
            return List.of(externalAddressBookService.getContactList());
        } else {
            return addressBookService.getListOfAddress();
        }
    }

    // Get List Of Contacts by firstName

    @GetMapping("/search/{isRemote}/{firstName}")
    public List<ContactDTO> getAddressByFirstName(@PathVariable String firstName, @PathVariable(name = "isRemote") String isRemote)  {
        if (isRemote.equals("y")) {
            return List.of(externalAddressBookService.getContactListByFirstName(firstName));
        } else {
            return addressBookService.findAddressByFirstName(firstName);
        }


    }

    @PostMapping(path = "/save/{isRemote}")
    public ContactDTO saveAddress(@RequestBody ContactDTO contactDTO, @PathVariable(name = "isRemote") String isRemote) {
        if (isRemote.equals("y")) {
            return externalAddressBookService.saveContact(contactDTO);
        } else {
            return addressBookService.saveAddress(contactDTO);
        }


    }

    @PutMapping(path = "/update/{isRemote}/{contactId}")
    public void updateAddressBook(@PathVariable Long contactId, @PathVariable(name = "isRemote") String isRemote) {
        if (isRemote.equals("y")) {
            externalAddressBookService.deleteContact(contactId);
        } else {
            addressBookService.deleteContact(contactId);
        }


    }


}
