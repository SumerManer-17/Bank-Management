package app;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
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
                case "1" -> openAccount(scanner);
                case "2" -> deposit(scanner);
                case "3" -> withdraw(scanner);
                case "4" -> transfer(scanner);
                case "5" -> statements(scanner);
                case "6" -> listAccounts(scanner);
                case "7" -> searchAccount(scanner);
                case "0" -> running=false;
            }
        }
    }

    private static void searchAccount(Scanner scanner) {
    }

    private static void listAccounts(Scanner scanner) {
        
    }

    private static void statements(Scanner scanner) {
        
    }

    private static void transfer(Scanner scanner) {
        
    }

    private static void withdraw(Scanner scanner) {
        
    }

    private static void deposit(Scanner scanner) {
        
    }

    private static void openAccount(Scanner scanner) {
        System.out.println("Customer Name: ");
        String name=scanner.nextLine().trim();
        System.out.println("Customer Email: ");
        String email=scanner.nextLine().trim();
        System.out.println("Account Type (SAVING/CURRENT): ");
        String type=scanner.nextLine().trim();
        System.out.println("Initial Deposite (OPTIONAL): ");
        String amountStr=scanner.nextLine().trim();
        Double initial=Double.valueOf(amountStr);
    }
}
