package com.croxx.hgwechat.model.didiumbrella;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DidiUmbrellaUserReposity extends JpaRepository<DidiUmbrellaUser, String> {
    DidiUmbrellaUser findByOpenid(String openid);
    DidiUmbrellaUser findByToken(String token);
}
