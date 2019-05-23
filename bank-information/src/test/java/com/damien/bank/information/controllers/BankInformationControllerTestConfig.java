package com.damien.bank.information.controllers;

import com.damien.bank.information.advice.InputNullCheckAdvice;
import com.damien.bank.information.clients.AccountClient;
import com.damien.bank.information.controllers.BankInformationController;
import com.damien.bank.information.decoders.BankInformationErrorDecoder;
import com.damien.bank.information.services.BankInformationService;
import com.damien.bank.information.services.impl.BankInformationServiceImpl;
import com.damien.bank.information.utilities.JsonUtility;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class BankInformationControllerTestConfig {

    @Bean
    public JsonUtility jsonUtility() {
        return new JsonUtility();
    }

    @Bean
    public BankInformationController bankInformationController() {
        return new BankInformationController();
    }

    @Bean
    public BankInformationService bankInformationService() {
        return new BankInformationServiceImpl();
    }

    @Bean
    public AccountClient accountClient() {
        return Mockito.mock(AccountClient.class);
    }

    @Bean
    public BankInformationErrorDecoder bankInformationErrorDecoder() {
        return new BankInformationErrorDecoder();
    }

    @Bean
    public InputNullCheckAdvice inputNullCheckAdvice() {
        return new InputNullCheckAdvice();
    }
}
