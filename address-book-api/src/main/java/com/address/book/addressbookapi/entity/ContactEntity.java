package com.address.book.addressbookapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "Contact")
public class ContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long contactId;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String createdBy;

    @CreatedDate
    private Date createdDate;

    private String updatedBy;

    @UpdateTimestamp
    private Date updatedDate;

    private String isActive;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contactId", referencedColumnName = "contactId")
    private List<MobileEntity> mobileEntities;

}
