package com.kakao.pay.repository;

import com.kakao.pay.model.SendMoney;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendRepository extends JpaRepository<SendMoney, String> {

}
