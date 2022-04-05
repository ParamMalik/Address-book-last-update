package com.address.book.addressbookapi.bulkdatasave;

import com.address.book.addressbookapi.entity.ContactEntity;

import java.awt.*;
import java.util.ArrayList;

public interface BulkOperations {
    public void bulkPersist(ArrayList<ContactEntity> entityList);
}
