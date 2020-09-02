package org.mickael.libraryclientweb.bean.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AccountLoginBean {


    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @NotBlank
    private String password;


}
