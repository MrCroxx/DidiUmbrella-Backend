package com.croxx.hgwechat.model.didiumbrella;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface DidiUmbrellaOrderReposity extends JpaRepository<DidiUmbrellaOrder, Long> {
    @Query("SELECT o FROM DidiUmbrellaOrder o WHERE o.from_openid=:from_openid ORDER BY o.create_time DESC")
    List<DidiUmbrellaOrder> findLatestFromOrder(@Param("from_openid") String from_openid);

    @Query("SELECT o FROM DidiUmbrellaOrder o WHERE o.to_openid=:to_openid ORDER BY o.create_time DESC")
    List<DidiUmbrellaOrder> findLatestToOrder(@Param("to_openid") String to_openid);

    @Query("SELECT o FROM DidiUmbrellaOrder o WHERE o.from_openid=:from_openid ORDER BY o.create_time DESC")
    List<DidiUmbrellaOrder> findOrders(@Param("from_openid") String from_openid);

    DidiUmbrellaOrder findById(long id);

    List<DidiUmbrellaOrder> findByStatus(String status);

    @Query("SELECT o FROM DidiUmbrellaOrder o WHERE o.from_openid=:openid OR o.to_openid=:openid ORDER BY o.create_time DESC")
    List<DidiUmbrellaOrder> findHistory(@Param("openid") String openid);
}
