package com.lgp.service;

import com.lgp.entity.token.TokenInfoEntity;
import com.lgp.entity.token.UserInfoEntity;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-05 23:09
 */
public interface TokenService {

    TokenInfoEntity getTokenInfoByAppId(String appId);

    UserInfoEntity getUserInfoByAppId(String appId);

    void saveTokenInfo(TokenInfoEntity tokenInfoEntity);

    void modifyTokenInfo(TokenInfoEntity tokenInfoEntity);

}
