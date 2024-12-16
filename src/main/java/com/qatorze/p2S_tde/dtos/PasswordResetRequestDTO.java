package com.qatorze.p2S_tde.dtos;

/*Questa classe è una DTO (Data Transfer Object) che contiene solo l'email dell'utente. 
 * È usata per fare la richiesta di reset della password (per inviare il link di reset).*/
public class PasswordResetRequestDTO {
    private String email;

    public PasswordResetRequestDTO() {}

    public PasswordResetRequestDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
