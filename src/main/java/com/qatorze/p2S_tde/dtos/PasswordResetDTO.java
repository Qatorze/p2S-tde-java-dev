package com.qatorze.p2S_tde.dtos;

/*
 * Questa DTO viene utilizzata per il reset vero e proprio della password. Contiene il 
 * token di reset e la nuova password che l'utente desidera impostare.
 * */
public class PasswordResetDTO {
    private String token;
    private String newPassword;

    public PasswordResetDTO() {}

    public PasswordResetDTO(String token, String newPassword) {
        this.token = token;
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
