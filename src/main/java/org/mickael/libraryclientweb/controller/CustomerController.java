package org.mickael.libraryclientweb.controller;

import org.mickael.libraryclientweb.bean.customer.CustomerBean;
import org.mickael.libraryclientweb.bean.loan.LoanBean;
import org.mickael.libraryclientweb.proxy.FeignProxy;
import org.mickael.libraryclientweb.security.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {


    private final FeignProxy feignProxy;
    private static final String DASHBOARD_VIEW = "dashboard";
    private static final String ACCOUNT_ATT = "accountBean";
    private static final String ERROR_ATT = "errorMessage";
    private static final String ERROR_MSG = "ERREUR! Les modifications n'ont pas été enregistrées!";
    private static final String ACCOUNT_FORM_VIEW = "account";
    private static final String REDIRECT_LOGIN_VIEW = "redirect:/login";
    private static final String REDIRECT_USER_HOME_VIEW = "redirect:/dashboard";


    @Autowired
    public CustomerController(FeignProxy feignProxy) {
        this.feignProxy = feignProxy;
    }


    @GetMapping("/dashboard")
    public String accounts(@CookieValue(value = CookieUtils.HEADER, required = false)String accessToken, Model model){
        if (accessToken == null) return REDIRECT_LOGIN_VIEW;
        Integer customerId = CookieUtils.getUserIdFromJWT(accessToken);
        CustomerBean customerBean = feignProxy.retrieveAccount(customerId, "Bearer " + accessToken);
        List<LoanBean> loanBeans = feignProxy.findAllByCustomerId(customerId, "Bearer " + accessToken);
        for (LoanBean loanBean : loanBeans){
            loanBean.setCustomer(customerBean);
            loanBean.setCopy(feignProxy.retrieveCopy(loanBean.getCopyId(), "Bearer " + accessToken));
        }
        model.addAttribute("customer", customerBean);
        model.addAttribute("loans", loanBeans);
        return DASHBOARD_VIEW;
    }

    @GetMapping("/edit/account")
    public String displayAccountForm(Model model, @CookieValue(value = CookieUtils.HEADER, required = false) String accessToken){
        if (accessToken == null) return REDIRECT_LOGIN_VIEW;
        Integer userId = CookieUtils.getUserIdFromJWT(accessToken);
        Integer customerId = 2;
        CustomerBean customerBean = feignProxy.retrieveAccount(customerId, "Bearer " + accessToken);
        model.addAttribute(ACCOUNT_ATT, customerBean);
        return ACCOUNT_FORM_VIEW;
    }

    @PostMapping("/update/account")
    public String updateClientProfile (@Valid CustomerBean customerBean, Model model, BindingResult bindingResult,
                                       HttpServletResponse response, @CookieValue(value = CookieUtils.HEADER,required = false) String accessToken){

        if (accessToken == null) return REDIRECT_LOGIN_VIEW;

        if (bindingResult.hasErrors()){
            model.addAttribute(ACCOUNT_ATT, customerBean);
            return ACCOUNT_FORM_VIEW;
        }
        Integer userId = CookieUtils.getUserIdFromJWT(accessToken);
        //update account
        CustomerBean updateAccount = feignProxy.updateAccount(userId, customerBean, "Bearer " + accessToken);


        model.addAttribute(ACCOUNT_ATT, updateAccount);
        if (updateAccount != null) {
/*            //refresh token
            AccountLoginBean accountLoginBean = new AccountLoginBean();
            accountLoginBean.setUsername(updateAccount.getEmail());
            accountLoginBean.setPassword(updateAccount.getPassword());
            CookieUtils.clear(response);
            ResponseEntity responseEntity = feignCustomerProxy.login(accountLoginBean);
            String token = responseEntity.getHeaders().getFirst("Authorization").replace("Bearer ", "");
            Cookie cookie = CookieUtils.generateCookie(token);
            response.addCookie(cookie);
            model.addAttribute("account", customerBean);*/
            return REDIRECT_USER_HOME_VIEW;
        } else {
            model.addAttribute(ERROR_ATT,ERROR_MSG);
            return ACCOUNT_FORM_VIEW;
        }
    }

}
