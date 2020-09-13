package org.mickael.libraryclientweb.bean.book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AuthorBean {

    private Integer id;
    private String lastName;
    private String firstName;

    @Override
    public String toString() {
        return "AuthorBean{" +
                       "id=" + id +
                       ", lastName='" + lastName + '\'' +
                       ", firstName='" + firstName + '\'' +
                       '}';
    }
}
