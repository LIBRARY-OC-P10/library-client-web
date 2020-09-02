package org.mickael.libraryclientweb.bean.book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CopyBean {

    private Integer id;
    private boolean available;
    private BookBean book;

    @Override
    public String toString() {
        return "CopyBean{" +
                       "id=" + id +
                       ", available=" + available +
                       ", book=" + book +
                       '}';
    }
}
