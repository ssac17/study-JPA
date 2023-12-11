package com.study.account;

import com.study.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

//readOnly = true 옵션은 해당 트랜잭션을 읽기 전용으로 설정, 데이터를 변경하는 쓰기 작업이 없고, 데이터를 조회하는 읽기 작업만 수행
@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
