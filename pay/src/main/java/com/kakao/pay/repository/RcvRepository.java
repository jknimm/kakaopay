package com.kakao.pay.repository;

import com.kakao.pay.model.RcvMoney;
import com.kakao.pay.model.SendMoney;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RcvRepository extends JpaRepository<RcvMoney, Long> {

    List<RcvMoney> findByTokenAndUserIdNullOrderBySeqAsc(String token);
    List<RcvMoney> findByTokenAndUserIdNotNull(String token);

    int countByTokenAndUserId(String token, String userId);
    int countByTokenAndUserIdNull(String token);

}
