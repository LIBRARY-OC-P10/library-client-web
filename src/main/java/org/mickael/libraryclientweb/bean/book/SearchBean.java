package org.mickael.libraryclientweb.bean.book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class SearchBean {

    private String criteria;
    private String searchValue;
}
