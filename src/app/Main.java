package app;

import service.BankService;
import service.impl.BankServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        BankService bankService=new BankServiceImpl() {};
        boolean running=true;
        System.out.println("Welcome to Console Bank");
        while(running){
            System.out.println("""
                1)Open Account
                2)Deposit
                3)Withdraw
                4)Transfer
                5)Statement
                6)List Accounts
                7)Search Account by Customer Name
                0)Exit
                """);

            System.out.print("CHOOSE: ");
            String choice=scanner.nextLine().trim();
            System.out.println("Choice "+choice);


            switch (choice){
                case "1" -> openAccount(scanner,bankService);
                case "2" -> deposit(scanner,bankService);
                case "3" -> withdraw(scanner,bankService);
                case "4" -> transfer(scanner);
                case "5" -> statements(scanner);
                case "6" -> listAccounts(scanner,bankService);
                case "7" -> searchAccount(scanner);
                case "0" -> running=false;
            }
        }
    }

    private static void searchAccount(Scanner scanner) {
    }

    private static void listAccounts(Scanner scanner,BankService bankService) {
        bankService.listAccounts().forEach(a->{
            System.out.println(a.getAccountNumber()+" | "+ a.getAccountType()+" | "+a.getBalance());
        });
    }

    private static void statements(Scanner scanner) {
        
    }

    private static void transfer(Scanner scanner) {
        
    }

    private static void withdraw(Scanner scanner,BankService bankService) {
        System.out.println("Account number: ");
        String accountNumber=scanner.nextLine().trim();
        System.out.println("Amount: ");
        Double amount=Double.valueOf(scanner.nextLine().trim());
        bankService.withdraw(accountNumber,amount,"Withdrown Successfully");
        System.out.println("Withdraw");
    }

    private static void deposit(Scanner scanner,BankService bankService) {
        System.out.println("Account number: ");
        String accountNumber=scanner.nextLine().trim();
        System.out.println("Amount: ");
        Double amount=Double.valueOf(scanner.nextLine().trim());
        bankService.deposit(accountNumber,amount,"Deposite");
        System.out.println("Deposited");
    }

    private static void openAccount(Scanner scanner,BankService bankService) {
        System.out.println("Customer Name: ");
        String name=scanner.nextLine().trim();
        System.out.println("Customer Email: ");
        String email=scanner.nextLine().trim();
        System.out.println("Account Type (SAVING/CURRENT): ");
        String type=scanner.nextLine().trim();
        System.out.println("Initial Deposite (OPTIONAL): ");
        String amountStr=scanner.nextLine().trim();
        Double initial=Double.valueOf(amountStr);
        String AccountNumber=bankService.openAccount(name,email,type);
        if(initial>0)
            bankService.deposit(AccountNumber,initial,"Initial Deposit");
        System.out.println("Account "+AccountNumber+" opened successfully");
    }
}
