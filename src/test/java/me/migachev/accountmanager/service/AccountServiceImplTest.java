package me.migachev.accountmanager.service;

import me.migachev.accountmanager.model.Account;
import me.migachev.accountmanager.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceImplTest {
    @Autowired
    private AccountRepository repository;
    @Autowired
    private AccountService accountService;

    @Before
    public void setUp() {
        IntStream.range(1, 4)
                .forEach(i -> {
                    Account account = new Account();
                    account.setAmount(new BigDecimal(i * 100));
                    account.setId(String.valueOf(i));
                    repository.save(account);
                });
        assertThat(repository.count()).isEqualTo(5L);
    }

    @Test
    public void withdraw() {
        Optional<Account> before = repository.findById("1");
        assertThat(before.isPresent()).isTrue();
        assertThat(before.get().getAmount()).isEqualTo(new BigDecimal("100.00"));
        Account after = accountService.withdraw(before.get().getId(), new BigDecimal(10));
        assertThat(after.getAmount()).isEqualTo(new BigDecimal("90.00"));
    }

    @Test
    public void deposit() {
        Optional<Account> before = repository.findById("1");
        assertThat(before.isPresent()).isTrue();
        assertThat(before.get().getAmount()).isEqualTo(new BigDecimal("100.00"));
        Account after = accountService.deposit(before.get().getId(), new BigDecimal(10));
        assertThat(after.getAmount()).isEqualTo(new BigDecimal("110.00"));
    }

    @Test
    public void transfer() {
        Optional<Account> beforeFrom = repository.findById("3");
        Optional<Account> beforeTo = repository.findById("2");
        assertThat(beforeFrom.isPresent()).isTrue();
        assertThat(beforeTo.isPresent()).isTrue();
        assertThat(beforeFrom.get().getAmount()).isEqualTo(new BigDecimal("300.00"));
        assertThat(beforeTo.get().getAmount()).isEqualTo(new BigDecimal("200.00"));
        Pair<Account, Account> after = accountService.transfer(beforeFrom.get().getId(), beforeTo.get().getId(), new BigDecimal(10));
        assertThat(after.getFirst().getAmount()).isEqualTo(new BigDecimal("290.00"));
        assertThat(after.getSecond().getAmount()).isEqualTo(new BigDecimal("210.00"));
    }
}