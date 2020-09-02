package org.mickael.libraryclientweb.bean.loan;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mickael.libraryclientweb.bean.book.CopyBean;
import org.mickael.libraryclientweb.bean.customer.CustomerBean;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class LoanBean {

    private Integer id;
    private LocalDate beginLoanDate;
    private LocalDate endingLoanDate;
    private LocalDate extendLoanDate;
    private LocalDate returnLoanDate;
    private boolean extend;
    private String loanStatus;
    private Integer customerId;
    private Integer copyId;

    private CustomerBean customer;
    private CopyBean copy;

}
