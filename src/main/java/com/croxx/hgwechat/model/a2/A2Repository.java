package com.croxx.hgwechat.model.a2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface A2Repository extends JpaRepository<A2, Long> {

    A2 findByOpenid(String openid);

    A2 findByOpenidAndStatus(String openid, String status);

    @Query("SELECT a2 FROM A2 a2 WHERE (a2.fromid=:fromid OR a2.fromname=:fromname) AND a2.status=:status")
    List<A2> findByFromidOrFromnameAndStatus(@Param("fromid") String fromid, @Param("fromname") String fromname, @Param("status") String status);

}
