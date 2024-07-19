package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // register account
    public Account registerAccount(Account account) {
        if (account.getUsername().length() == 0 || account.getPassword().length() < 4) {
            return null;
        }
        
        if (findByUsername(account.getUsername()) != null) {
            return null;
        }
        return accountRepository.save(account);
        
    }

    // check for account by username
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    // attempt to login 
    public Account loginAccount(Account account) {
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount != null && account.getPassword().equals(existingAccount.getPassword())) {
            return existingAccount;
        }
        return null;
    }
}
