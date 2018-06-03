package com.lgp.dao.master.token;

import com.lgp.entity.token.TokenInfoEntity;
import com.lgp.entity.token.UserInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-05 23:45
 */
@Repository
public interface TokenDao {

    TokenInfoEntity getTokenInfoByAppId(@Param("appId") String appId);

    UserInfoEntity getUserInfoByAppId(@Param("appId") String appId);

    int updateTokenInfo(TokenInfoEntity tokenInfoEntity);

    int saveTokenInfo(TokenInfoEntity tokenInfoEntity);
}
