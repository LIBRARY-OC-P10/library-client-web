package org.mickael.libraryclientweb.controller;


import org.mickael.libraryclientweb.bean.book.BookBean;
import org.mickael.libraryclientweb.bean.book.CopyBean;
import org.mickael.libraryclientweb.bean.book.SearchBean;
import org.mickael.libraryclientweb.bean.customer.CustomerBean;
import org.mickael.libraryclientweb.bean.reservation.ReservationBean;
import org.mickael.libraryclientweb.proxy.FeignProxy;
import org.mickael.libraryclientweb.security.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final FeignProxy feignProxy;

    private static final String LIST_BOOK = "books";
    private static final String BOOK = "book";
    private static final String CATALOG = "catalog";
    private static final String PREFIX = "Bearer ";

    @Autowired
    public BookController(FeignProxy feignProxy) {
        this.feignProxy = feignProxy;
    }

    @GetMapping("/catalog")
    public String showCatalog(Model model, @CookieValue(value = CookieUtils.HEADER, required = false)String accessToken){
        model.addAttribute(LIST_BOOK, feignProxy.getBooks(PREFIX + accessToken));
        model.addAttribute("searchAttribut", new SearchBean());
        return CATALOG;
    }

    @PostMapping("/catalog/search")
    public String displaySearchResult(@ModelAttribute("searchAttribut") SearchBean searchBean, Model model,
                                      @CookieValue(value = CookieUtils.HEADER, required = false)String accessToken){
        try{
            List<BookBean> books = feignProxy.getBooksBySearchValue(searchBean,PREFIX + accessToken);
            model.addAttribute(LIST_BOOK, books);
            return CATALOG;
        } catch (Exception e){
            List<BookBean> books = new ArrayList<>();
            model.addAttribute(LIST_BOOK, books);
            model.addAttribute("NoResult", "Pas de résultats");
            return CATALOG;
        }
    }

    @GetMapping("/catalog/book/{bookId}")
    public String showBook(@PathVariable Integer bookId, Model model, @CookieValue(value = CookieUtils.HEADER, required = false)String accessToken){
        accessToken = PREFIX + accessToken;
        BookBean book = feignProxy.retrieveBook(bookId, accessToken);
        List<CopyBean> copies;
        try{
            copies = feignProxy.getCopiesAvailableForOneBook(bookId, accessToken);
        } catch (Exception e){
            copies = new ArrayList<>();
        }
        book.setCopies(copies);
        model.addAttribute("book", book);

        List<ReservationBean> reservationBeans = feignProxy.getAllReservationsByBookId(bookId, accessToken);
        reservationBeans.sort(Comparator.comparing(ReservationBean::getPosition));
        model.addAttribute("reservations", reservationBeans);
        if(!reservationBeans.isEmpty()){
            LocalDate soonDisp = reservationBeans.get(reservationBeans.size()-1).getSoonDisponibilityDate();
            model.addAttribute("soonDisponibilityDate", soonDisp);
        }
        return BOOK;
    }

    @PostMapping("/catalog/book/{bookId}/reserve")
    public String reserveBook(@PathVariable Integer bookId, @ModelAttribute BookBean book, Model model, @CookieValue(value = CookieUtils.HEADER, required = false)String accessToken, RedirectAttributes redirect){
        Integer customerId = CookieUtils.getUserIdFromJWT(accessToken);
        accessToken = PREFIX + accessToken;
        if (feignProxy.checkIfLoanExistForCustomerIdAndBookId(customerId,bookId,accessToken)){
            redirect.addFlashAttribute("error", "Réservation impossible. Vous avez déjà un emprunt en cours pour ce livre.");
            return "redirect:/books/catalog/book/{bookId}";
        }
        CustomerBean customerBean = feignProxy.retrieveCustomer(customerId, accessToken);
        ReservationBean reservationBean = new ReservationBean();
        reservationBean.setBookId(bookId);
        reservationBean.setBookTitle(book.getTitle());
        reservationBean.setCustomerId(customerId);
        reservationBean.setCustomerEmail(customerBean.getEmail());
        reservationBean.setCustomerLastname(customerBean.getLastName());
        reservationBean.setCustomerFirstname(customerBean.getFirstName());
        try {
            feignProxy.createReservation(reservationBean, accessToken);
            redirect.addFlashAttribute("success", "Réservation validée");
            return "redirect:/books/catalog/book/{bookId}";
        } catch (Exception e) {
            redirect.addFlashAttribute("error", "Réservation impossible. Vous avez déjà une réservation pour ce livre.");
            return "redirect:/books/catalog/book/{bookId}";
        }
    }



}
