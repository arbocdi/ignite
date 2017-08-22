package net.sf.arbocdi;

import java.io.Serializable;

import lombok.Data;
import org.apache.ignite.cache.query.annotations.QueryTextField;

@Data
public class Company implements Serializable {

private Long id;
private String cat;
@QueryTextField
private String companyName;
@QueryTextField
private String email;
@QueryTextField
private String address;
@QueryTextField
private String city;
@QueryTextField
private String state;
@QueryTextField
private String zipCode;
private String phoneNumber;
private String faxNumber;

    public Company(Long id, String cat, String companyName, String email, String address, String city, String state, String zipCode, String phoneNumber, String faxNumber) {
        this.id = id;
        this.cat = cat;
        this.companyName = companyName;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
    }


}
