package com.address.book.addressbookapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity(name = "Contact")
public class ContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;


    @NotBlank(message = "first name shouldn't be blank")
    private String firstName;

    @NotBlank(message = "last name shouldn't be blank")
    private String lastName;

    @NotBlank(message = "Email can not be empty")
    private String emailAddress;

    private String createdBy;

    @CreatedDate
    private Date createdDate;

    private String updatedBy;

    @UpdateTimestamp
    private Date updatedDate;

    @NotBlank(message = "Please Enter Yes for active status")
    private String isActive;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contactId", referencedColumnName = "contactId")
    private List<MobileEntity> mobileEntities;

}
