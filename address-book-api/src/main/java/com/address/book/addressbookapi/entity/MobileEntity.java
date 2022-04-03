package com.address.book.addressbookapi.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Mobile")
public class MobileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mobileId;

    private String mobileNumber;

    private String countryCode;

    private String type;

    private String createdBy;

    @CreationTimestamp
    private Date createdDate;

    private String updatedBy;

    @UpdateTimestamp
    private Date updatedDate;

    private Long contactId;

}
