package service.impl;

import domain.Account;
import domain.Customer;
import domain.Transaction;
import domain.Type;
import repository.AccountRepository;
import repository.CustomerRepository;
import repository.TransactionRepository;
import service.BankService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BankServiceImpl implements BankService {
    private final AccountRepository accountRepository = new AccountRepository();
    private final TransactionRepository transactionRepository = new TransactionRepository();
    private final CustomerRepository customerRepository = new CustomerRepository();
    @Override
    public String openAccount(String name, String email, String accountType) {
        String customerId= UUID.randomUUID().toString();

        //Create Customer
        Customer c=new Customer(customerId,name,email);
        customerRepository.save(c);

        //Will change later
//        String accountNumber=UUID.randomUUID().toString();
        String accountNumber = getAccountNumber();

        Account account=new Account(accountNumber,customerId,(double)0,accountType);

        //SAVE
        accountRepository.save(account);


        return accountNumber;
    }

    @Override
    public List<Account> listAccounts() {
        return accountRepository.findAll().stream().sorted(Comparator.comparing(Account::getAccountNumber))
                .collect(Collectors.toList());
    }

    @Override
    public void deposit(String accountNumber, Double amount, String deposite) {
        Account account=accountRepository.findByNumber(accountNumber)
                .orElseThrow(()->new RuntimeException("Account not Found: "+ accountNumber));
        account.setBalance(account.getBalance()+amount);
        Transaction transaction=new Transaction(UUID.randomUUID()
                .toString(), Type.DEPOSIT,account.getAccountNumber(),
                amount, LocalDateTime.now(),"Amount added");
        transactionRepository.add(transaction);
    }

    @Override
    public void withdraw(String accountNumber, Double amount, String withdrownSuccessfully) {
        Account account=accountRepository.findByNumber(accountNumber)
                .orElseThrow(()->new RuntimeException("Account not Found: "+ accountNumber));
        if(account.getBalance().compareTo(amount)<0)
            throw new RuntimeException("Insufficient Balance");
        account.setBalance(account.getBalance()-amount);
        Transaction transaction=new Transaction(UUID.randomUUID()
                .toString(), Type.WITHDRAW,account.getAccountNumber(),
                amount, LocalDateTime.now(),"Amount deducted");
        transactionRepository.add(transaction);
    }

    @Override
    public void transfer(String fromAcc, String toAcc, Double amount, String transfered) {
        if(fromAcc.equals(toAcc))
            throw new RuntimeException("Select different account to transfer");
        Account from=accountRepository.findByNumber(fromAcc)
                .orElseThrow(()->new RuntimeException("Account "+fromAcc+" not found"));
        Account to=accountRepository.findByNumber(toAcc)
                .orElseThrow(()->new RuntimeException("Account "+toAcc+" not found"));
        if(from.getBalance().compareTo(amount)<0)
            throw new RuntimeException("Insufficient Balance");
        from.setBalance(from.getBalance()-amount);
        to.setBalance(to.getBalance()+amount);

        Transaction fromTransaction=new Transaction(UUID.randomUUID()
                .toString(), Type.TRANSFER_OUT,from.getAccountNumber(),
                amount, LocalDateTime.now(),"Amount deducted");
        transactionRepository.add(fromTransaction);

        Transaction toTransaction=new Transaction(UUID.randomUUID()
                .toString(), Type.TRANSFER_IN,to.getAccountNumber(),
                amount, LocalDateTime.now(),"Amount added");
        transactionRepository.add(toTransaction);
    }

    @Override
    public List<Transaction> getStatement(String account) {
        return transactionRepository.findByAccount(account).stream().
                sorted(Comparator.comparing(Transaction::getTimeStamp))
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> searchAccountByName(String q) {
        String query=(q==null) ? "":q.toLowerCase();
        List<Account>result=new ArrayList<>();
        for (Customer c: customerRepository.findAll()){
            if (c.getName().toLowerCase().contains(query))
                result.addAll(accountRepository.findByCustomerId(c.getId()));
        }
        result.sort(Comparator.comparing(Account::getAccountNumber));
        return result;
    }

    private String getAccountNumber() {
        int size=accountRepository.findAll().size()+1;
        return String.format("AC%06d",size);
    }
}
