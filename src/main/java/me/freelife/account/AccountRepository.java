package me.freelife.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//JpaRepository< Entity의 타입, ID의 타입>
public interface AccountRepository extends JpaRepository<Account, Long> {
//    Account findByUsername(String username);
    Optional<Account> findByUsername(String username);
}

