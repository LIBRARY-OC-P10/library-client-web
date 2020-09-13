package org.mickael.libraryclientweb.bean.book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class CoverBean {

    private Integer id;
    private String name;
    private String url;

    @Override
    public String toString() {
        return "CoverBean{" +
                       "id=" + id +
                       ", name='" + name + '\'' +
                       ", url='" + url + '\'' +
                       '}';
    }
}
