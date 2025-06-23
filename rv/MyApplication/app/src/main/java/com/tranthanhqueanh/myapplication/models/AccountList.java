package com.tranthanhqueanh.myapplication.models;

import java.util.ArrayList;
import java.util.List;

public class AccountList {
    private static List<Account> accountList = new ArrayList<>();

    static {
        // Khởi tạo 10 tài khoản mẫu
        accountList.add(new Account(1, "user1", "pass1"));
        accountList.add(new Account(2, "user2", "pass2"));
        accountList.add(new Account(3, "user3", "pass3"));
        accountList.add(new Account(4, "user4", "pass4"));
        accountList.add(new Account(5, "user5", "pass5"));
        accountList.add(new Account(6, "user6", "pass6"));
        accountList.add(new Account(7, "user7", "pass7"));
        accountList.add(new Account(8, "user8", "pass8"));
        accountList.add(new Account(9, "user9", "pass9"));
        accountList.add(new Account(10, "user10", "pass10"));
    }

    public static List<Account> getAccounts() {
        return accountList;
    }

    public static void addAccount(Account account) {
        accountList.add(account);
    }
}
