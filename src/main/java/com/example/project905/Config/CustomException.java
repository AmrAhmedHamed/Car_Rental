package com.example.project905.Config;



import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final String messageEn;
    private final String messageAr;

    public CustomException(String messageEn, String messageAr) {
        super(messageEn);
        this.messageEn = messageEn;
        this.messageAr = messageAr;
    }
}
