package com.example.demo.service;

public interface IMailService {

    void sendMailForActivationAccount(String Url,String email);

    void sendMailForUpdatePassword(String Url,String email);
}
