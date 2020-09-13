package org.mickael.libraryclientweb.bean.book;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class BookBean {

    private Integer id;
    private String isbn;
    private String title;
    private String summary;
    private Integer stock;
    private AuthorBean author;
    private EditionBean edition;
    private CoverBean cover;

    private List<CategoryBean> categories;
    private List<CopyBean> copies;

    @Override
    public String toString() {
        return "BookBean{" +
                       "id=" + id +
                       ", isbn='" + isbn + '\'' +
                       ", title='" + title + '\'' +
                       ", summary='" + summary + '\'' +
                       ", stock=" + stock +
                       ", author=" + author +
                       ", edition=" + edition +
                       ", cover=" + cover +
                       '}';
    }
}
