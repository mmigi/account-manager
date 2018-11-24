package me.migachev.accountmanager.service;

import me.migachev.accountmanager.model.Account;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;

public interface AccountService {
    Account withdraw(String accountId, BigDecimal amount);

    Account deposit(String accountId, BigDecimal amount);

    Pair<Account, Account> transfer(String accountFromId, String accountToId, BigDecimal amount);
}
