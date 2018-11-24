package me.migachev.accountmanager.service;

import me.migachev.accountmanager.exception.AccountNotFoundException;
import me.migachev.accountmanager.exception.NotEnoughAcoountAmountException;
import me.migachev.accountmanager.model.Account;
import me.migachev.accountmanager.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AccountRepository repository;

    @Autowired
    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Account withdraw(String accountId, BigDecimal amount) {
        log.debug("Start withdraw operation for {} account id", accountId);
        Optional<Account> optionalAccount = repository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (amount.compareTo(account.getAmount()) > 0) {
                throw new NotEnoughAcoountAmountException("The account " + account + " does not have enough amount to withdraw with" + amount);
            }
            account.setAmount(account.getAmount().subtract(amount).setScale(2, RoundingMode.HALF_UP));
            Account saved = repository.save(account);
            log.debug("Finished successfully withdraw operation for {} account id", accountId);
            return saved;
        }
        throw new AccountNotFoundException("There is no account with " + accountId + " id");
    }

    @Override
    @Transactional
    public Account deposit(String accountId, BigDecimal amount) {
        log.debug("Start deposit operation for {} account id", accountId);
        Optional<Account> optionalAccount = repository.findById(accountId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setAmount(account.getAmount().add(amount).setScale(2, RoundingMode.HALF_UP));
            Account saved = repository.save(account);
            log.debug("Finished successfully deposit operation for {} account id", accountId);
            return saved;
        }
        throw new AccountNotFoundException("There is no account with " + accountId + " id");
    }

    @Override
    @Transactional
    public Pair<Account, Account> transfer(String accountFromId, String accountToId, BigDecimal amount) {
        log.debug("Start transfer operation for accounts with {} account from, {} account to ids", accountFromId, accountToId);
        Optional<Account> optionalAccountFronm = repository.findById(accountFromId);
        if (optionalAccountFronm.isPresent()) {
            Optional<Account> optionalAccountTo = repository.findById(accountToId);
            if (optionalAccountTo.isPresent()) {
                Account accountFrom = optionalAccountFronm.get();
                Account accountTo = optionalAccountTo.get();
                if (accountFrom.getAmount().compareTo(amount) < 0) {
                    throw new NotEnoughAcoountAmountException("The account " + accountFromId + " does not have enough amount to withdraw with" + amount);
                }
                accountFrom.setAmount(accountFrom.getAmount().subtract(amount).setScale(2, RoundingMode.HALF_UP));
                accountTo.setAmount(accountTo.getAmount().add(amount).setScale(2, RoundingMode.HALF_UP));
                Account savedFrom = repository.save(accountFrom);
                Account savedTo = repository.save(accountTo);
                log.debug("Finished successfully transfer operation for accounts with {} account from, {} account to ids", accountFromId, accountToId);
                return Pair.of(savedFrom, savedTo);
            }
            throw new AccountNotFoundException("There is no account with " + accountToId + " id");
        }
        throw new AccountNotFoundException("There is no account with " + accountFromId + " id");
    }
}