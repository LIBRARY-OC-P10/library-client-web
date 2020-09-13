package org.mickael.libraryclientweb.bean.book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class CategoryBean {

    private Integer id;
    private String label;

    private List<BookBean> books;

    @Override
    public String toString() {
        return label;
    }
}
