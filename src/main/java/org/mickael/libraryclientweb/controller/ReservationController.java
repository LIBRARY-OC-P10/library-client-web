package org.mickael.libraryclientweb.controller;

import org.mickael.libraryclientweb.proxy.FeignProxy;
import org.mickael.libraryclientweb.security.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final FeignProxy feignProxy;
    private static final String REDIRECT_CUSTOMER_DASHBOARD = "redirect:/customers/dashboard";

    @Autowired
    public ReservationController(FeignProxy feignProxy) {
        this.feignProxy = feignProxy;
    }

    @GetMapping("/cancel/{reservationId}")
    public String cancelReservation(@CookieValue(value = CookieUtils.HEADER, required = false)String accessToken, @PathVariable Integer reservationId){
        feignProxy.deleteReservationAfterTwoDays(reservationId,"Bearer " + accessToken);
        return REDIRECT_CUSTOMER_DASHBOARD;
    }
}
