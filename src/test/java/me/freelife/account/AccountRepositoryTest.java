package me.freelife.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void di() throws SQLException {
        /*
        try(Connection connection = dataSource.getConnection()){
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println(metaData.getURL());
            System.out.println(metaData.getDriverName());
            System.out.println(metaData.getUserName());
        }
        */

        Account account = new Account();
        account.setUsername("freelife");
        account.setPassword("1879asdf");

        // 새로운 account 등록
        Account newAccount = accountRepository.save(account);
        // 새로운 account가 등록 됐는지 확인
        assertThat(newAccount).isNotNull();

        // 새로운 account의 username으로 조회해서 데이터가 있는지 확인
//        Account existingAccount = accountRepository.findByUsername(newAccount.getUsername());
//        assertThat(existingAccount).isNotNull();
        Optional<Account> existingAccount = accountRepository.findByUsername(newAccount.getUsername());
        assertThat(existingAccount).isNotEmpty();

        // 없는 username으로 조회해서 데이터가 없는지 확인
//        Account nonExistingAccount = accountRepository.findByUsername("ironman");
//        assertThat(nonExistingAccount).isNull();//
        Optional<Account> nonExistingAccount = accountRepository.findByUsername("ironman");
        assertThat(nonExistingAccount).isEmpty();
    }
}