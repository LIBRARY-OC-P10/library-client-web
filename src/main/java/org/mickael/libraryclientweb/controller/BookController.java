package org.mickael.libraryclientweb.controller;


import org.mickael.libraryclientweb.bean.book.BookBean;
import org.mickael.libraryclientweb.bean.book.CopyBean;
import org.mickael.libraryclientweb.bean.book.SearchBean;
import org.mickael.libraryclientweb.proxy.FeignProxy;
import org.mickael.libraryclientweb.security.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final FeignProxy feignProxy;

    private static final String LIST_BOOK = "books";
    private static final String BOOK = "book";
    private static final String CATALOG = "catalog";


    @Autowired
    public BookController(FeignProxy feignProxy) {
        this.feignProxy = feignProxy;
    }

    @GetMapping("/catalog")
    public String showCatalog(Model model, @CookieValue(value = CookieUtils.HEADER, required = false)String accessToken){
        model.addAttribute(LIST_BOOK, feignProxy.getBooks("Bearer " + accessToken));
        model.addAttribute("searchAttribut", new SearchBean());
        return CATALOG;
    }

    @PostMapping("/catalog/search")
    public String displaySearchResult(@ModelAttribute("searchAttribut") SearchBean searchBean, Model model,
                                      @CookieValue(value = CookieUtils.HEADER, required = false)String accessToken){
        try{
            List<BookBean> books = feignProxy.getBooksBySearchValue(searchBean,"Bearer " + accessToken);
            model.addAttribute(LIST_BOOK, books);
            return CATALOG;
        } catch (Exception e){
            List<BookBean> books = new ArrayList<>();
            model.addAttribute(LIST_BOOK, books);
            model.addAttribute("NoResult", "Pas de résultats");
            return CATALOG;
        }
    }

    @GetMapping("/catalog/book/{id}")
    public String showBook(@PathVariable Integer id, Model model, @CookieValue(value = CookieUtils.HEADER, required = false)String accessToken){
        accessToken = "Bearer " + accessToken;
        BookBean book = feignProxy.retrieveBook(id, accessToken);
        List<CopyBean> copies;
        try{
            copies = feignProxy.getCopiesAvailableForOneBook(id, accessToken);
        } catch (Exception e){
            copies = new ArrayList<>();
        }
        book.setCopies(copies);
        model.addAttribute("book", book);
        return BOOK;
    }



}
