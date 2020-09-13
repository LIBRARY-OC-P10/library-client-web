package org.mickael.libraryclientweb.proxy;

import org.mickael.libraryclientweb.bean.book.BookBean;
import org.mickael.libraryclientweb.bean.book.CopyBean;
import org.mickael.libraryclientweb.bean.book.SearchBean;
import org.mickael.libraryclientweb.bean.customer.AccountLoginBean;
import org.mickael.libraryclientweb.bean.customer.CustomerBean;
import org.mickael.libraryclientweb.bean.loan.LoanBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "library-gateway", url = "localhost:8763")
public interface FeignProxy {

    /* ================================ */
    /* === No Authentication needed === */
    /* ================================ */


    /* ==== AUTH ==== */
    @PostMapping("/library-auth-server/login")
    ResponseEntity<?> login(@Valid @RequestBody AccountLoginBean accountLoginBean);

    @PostMapping(value = "/library-auth-server/register")
    ResponseEntity<?> register(@RequestBody CustomerBean customerBean);

    /* ==== CUSTOMERS ==== */
    @GetMapping("/library-ms-customer/api/customers/{id}")
    @ResponseStatus(HttpStatus.OK)
    CustomerBean retrieveAccount(@PathVariable("id") Integer id, @RequestHeader("Authorization") String accessToken);

    @PutMapping("/library-ms-customer/api/customers/{id}")
    @ResponseStatus(HttpStatus.OK)
    CustomerBean updateAccount(@PathVariable("id") Integer id, @RequestBody CustomerBean customerBean, @RequestHeader("Authorization") String accessToken);


    /* ==== BOOKS ==== */
    @GetMapping("/library-ms-book/api/books")
    List<BookBean> getBooks(@RequestHeader("Authorization") String accessToken);

    @GetMapping("/library-ms-book/api/books/search")
    List<BookBean> getBooksBySearchValue(@SpringQueryMap SearchBean searchBean,
                                         @RequestHeader("Authorization") String accessToken);

    @GetMapping("/library-ms-book/api/books/{id}")
    BookBean retrieveBook(@PathVariable("id") Integer id, @RequestHeader("Authorization") String accessToken);

    /* ==== COPIES ==== */
    @GetMapping("/library-ms-book/api/copies/search")
    List<CopyBean> getCopiesBySearchValue(@RequestParam(value = "criteria", required = false) String criteria,
                                          @RequestParam(value = "searchValue", required = false) String searchValue,
                                          @RequestParam(value = "available", required = false) boolean available,
                                          @RequestHeader("Authorization") String accessToken);
    @GetMapping("/library-ms-book/api/copies/available/{id}")
    List<CopyBean> getCopiesAvailableForOneBook(@PathVariable("id") Integer id, @RequestHeader("Authorization") String accessToken);

    @GetMapping("/library-ms-book/api/copies/book/{id}")
    List<CopyBean> getCopiesForOneBook(@PathVariable("id") Integer id, @RequestHeader("Authorization") String accessToken);

    @GetMapping("/library-ms-book/api/copies/{id}")
    CopyBean retrieveCopy(@PathVariable("id") Integer id, @RequestHeader("Authorization") String accessToken);

    /* ==== LOANS ==== */
    @GetMapping("/library-ms-loan/api/loans/customer/{customerId}")
    List<LoanBean> findAllByCustomerId(@PathVariable("customerId") Integer customerId, @RequestHeader("Authorization") String accessToken);

    @GetMapping("/library-ms-loan/api/loans/extend/{id}")
    LoanBean extendLoan(@PathVariable("id") Integer id, @RequestHeader("Authorization") String accessToken);



}
