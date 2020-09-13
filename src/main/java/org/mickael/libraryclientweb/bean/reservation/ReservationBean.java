package org.mickael.libraryclientweb.bean.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationBean {

    private Integer id;
    private LocalDateTime creationReservationDate;
    private LocalDate soonDisponibilityDate;
    private LocalDate endOfPriority;
    private Integer customerId;
    private String customerEmail;
    private String customerFirstname;
    private String customerLastname;
    private Integer bookId;
    private String bookTitle;
    private Integer position;
}
