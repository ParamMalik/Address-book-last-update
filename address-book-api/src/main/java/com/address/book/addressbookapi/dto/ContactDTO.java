package com.address.book.addressbookapi.dto;


import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelRow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class ContactDTO {

    @ExcelRow
    private Integer rowIndex;

    @ExcelCellName("ContactId")
    private Long contactId;
    @ExcelCellName("FirstName")
    private String firstName;
    @ExcelCellName("LastName")
    private String lastName;
    @ExcelCellName("Email")
    private String emailAddress;
    @ExcelCellName("createdBy")
    private String createdBy;
    @ExcelCellName("createdDate")
    private Date createdDate;
    @ExcelCellName("updatedBy")
    private String updatedBy;
    @ExcelCellName("updatedDate")
    private Date updatedDate;
    @ExcelCellName("isActive")
    private String isActive;

    private List<MobileDTO> mobileEntities;


}
