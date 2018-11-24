package me.migachev.accountmanager.repository;

import me.migachev.accountmanager.model.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, String> {
}
