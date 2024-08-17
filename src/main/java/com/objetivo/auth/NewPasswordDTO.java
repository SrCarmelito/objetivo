package com.objetivo.auth;

import lombok.Data;

@Data
public class NewPasswordDTO {

    private String senha;
    private String senhaConfirmacao;
    private String token;

}
