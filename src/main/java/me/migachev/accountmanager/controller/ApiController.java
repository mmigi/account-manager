package me.migachev.accountmanager.controller;

import me.migachev.accountmanager.model.Account;
import me.migachev.accountmanager.model.Response;
import me.migachev.accountmanager.model.TransferRequest;
import me.migachev.accountmanager.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import static me.migachev.accountmanager.enums.ResponseCode.OK;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    private final AccountService accountService;

    @Autowired
    public ApiController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/account/{accountId}/withdraw")
    ResponseEntity<Response<Account>> withdraw(@Validated @PathVariable String accountId, BigDecimal amount) {
        Response<Account> res = new Response<>(OK);
        res.setPayload(accountService.withdraw(accountId, amount));
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/account/{accountId}/deposit")
    ResponseEntity<Response<Account>> deposit(@Validated @PathVariable String accountId, BigDecimal amount) {
        Response<Account> res = new Response<>(OK);
        res.setPayload(accountService.deposit(accountId, amount));
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/accounts/transfer")
    ResponseEntity<Response<Pair<Account, Account>>> transfer(@RequestBody @Validated TransferRequest request) {
        Response<Pair<Account, Account>> res = new Response<>(OK);
        res.setPayload(accountService.transfer(request.getAccountFrom(), request.getAccountTo(), request.getAmount()));
        return ResponseEntity.ok().body(res);
    }
}
