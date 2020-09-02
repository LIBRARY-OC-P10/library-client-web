package org.mickael.libraryclientweb.bean.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressBean {

    private Integer id;
    private String housenumber;
    private String street;
    private String postcode;
    private String city;

    @Override
    public String toString() {
        return housenumber + " " + street + " " + postcode + " " + city;
    }
}
