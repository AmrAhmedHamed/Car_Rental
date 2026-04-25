package com.example.project905.Service.MassegeHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class bandlMassegeService {

    private final ResourceBundleMessageSource messageSource;

    @Autowired
    public bandlMassegeService(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMassegeEn(String key){
        try {
            return messageSource.getMessage(key, null, Locale.ENGLISH);
        } catch (NoSuchMessageException e) {
            // fallback → رجّع الرسالة نفسها لو مافيش key
            return key;
        }
    }

    public String getMassegeAr(String key){
        try {
            return messageSource.getMessage(key, null, new Locale("ar"));
        } catch (NoSuchMessageException e) {
            // fallback → رجّع الرسالة نفسها لو مافيش key
            return key;
        }
    }
}
