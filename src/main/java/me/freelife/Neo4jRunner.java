package me.freelife;

import me.freelife.account.Account;
import me.freelife.account.AccountRepository;
import me.freelife.account.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Neo4jRunner implements ApplicationRunner {

//    @Autowired
//    SessionFactory sessionFactory;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
//        account.setEmail("freejava1191@gmail.com");
//        account.setUsername("freelife");
//        account.setEmail("flash@gmail.com");
//        account.setUsername("flash");
        account.setEmail("doctorstranger@gmail.com");
        account.setUsername("doctorstranger");

        Role role = new Role();
//        role.setName("admin"); //admin 권한 생성
        role.setName("user"); //user 권한 생성

        account.getRoles().add(role); //생성된 유저에 권한 부여

//        Session session = sessionFactory.openSession();
//        session.save(account); // 저장
//        session.clear(); // 캐싱을 비워줌
//        sessionFactory.close();

        accountRepository.save(account);

        System.out.println("finished");
    }
}
