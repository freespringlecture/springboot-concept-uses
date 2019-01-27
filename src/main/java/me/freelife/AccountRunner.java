package me.freelife;

import me.freelife.account.Account;
import me.freelife.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AccountRunner implements ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account freelife = accountService.createAccount("freelife", "1234");
        System.out.println(freelife.getUsername() + " password: " + freelife.getPassword());
    }
}
