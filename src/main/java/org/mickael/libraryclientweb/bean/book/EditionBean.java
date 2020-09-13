package org.mickael.libraryclientweb.bean.book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class EditionBean {

    private Integer id;
    private String label;

    @Override
    public String toString() {
        return "EditionBean{" +
                       "id=" + id +
                       ", label='" + label + '\'' +
                       '}';
    }
}
