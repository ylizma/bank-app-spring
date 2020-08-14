package com.ylizma.bankmanagement.model;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    @Column(name="CUST_ID")
    private Long id;

    private String firstName;

    private String lastName;

    private String middleName;

    private Long customerNumber;

    private String status;

    @ManyToOne(cascade=CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    private Address customerAddress;

    @OneToOne(cascade=CascadeType.ALL)
    @NotFound(action = NotFoundAction.IGNORE)
    private Contact contactDetails;

    @Temporal(TemporalType.TIME)
	private Date createDateTime;

    @Temporal(TemporalType.TIME)
	private Date updateDateTime;

}
