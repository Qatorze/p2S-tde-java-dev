package com.qatorze.p2S_tde.utils;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {

    /**
     * Genera un token di reset password unico.
     *
     * @return Un token di reset password come stringa.
     */
    public String generateResetToken() {
        return UUID.randomUUID().toString();
    }
}
