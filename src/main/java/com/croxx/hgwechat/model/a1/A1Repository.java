package com.croxx.hgwechat.model.a1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface A1Repository extends JpaRepository<A1, Long> {
    A1 findA1ByOpenidAndStatus(String openid, String status);

    @Query("SELECT a1 FROM A1 a1 JOIN A1Open a1open ON a1.id=a1open.opener_aid WHERE a1open.maker_aid=:aid")
    List<A1> findOpeners(@Param("aid") Long aid);

}
