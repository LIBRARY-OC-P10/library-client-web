package org.mickael.libraryclientweb.bean.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CustomerBean {

    private Integer id;
    private String lastName;
    private String firstName;
    private AddressBean address;
    private String phoneNumber;
    private String email;
    private String password;
    private LocalDateTime registrationDate;
    private LocalDateTime updateDate;
    private String roles;
    private String permissions;
    private boolean active;

    @Override
    public String toString() {
        return "CustomerBean{" +
                       "id=" + id +
                       ", lastName='" + lastName + '\'' +
                       ", firstName='" + firstName + '\'' +
                       ", address=" + address +
                       ", phoneNumber='" + phoneNumber + '\'' +
                       ", email='" + email + '\'' +
                       ", password='" + password + '\'' +
                       ", registrationDate=" + registrationDate +
                       ", updateDate=" + updateDate +
                       ", roles='" + roles + '\'' +
                       ", permissions='" + permissions + '\'' +
                       ", active=" + active +
                       '}';
    }
}
