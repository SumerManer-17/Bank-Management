package service;

import domain.Account;

import java.util.List;

public interface BankService {
    String openAccount(String name,String email,String accountType);
    List<Account> listAccounts();

    void deposit(String accountNumber, Double amount, String deposite);

    void withdraw(String accountNumber, Double amount, String withdrownSuccessfully);

    void transfer(String from, String to, Double amount, String transfered);
}
