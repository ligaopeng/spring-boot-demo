package com.lgp.service.impl;

import com.lgp.dao.master.token.TokenDao;
import com.lgp.entity.token.TokenInfoEntity;
import com.lgp.entity.token.UserInfoEntity;
import com.lgp.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-06 00:02
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    TokenDao tokenDao;

    @Override
    public TokenInfoEntity getTokenInfoByAppId(String appId) {
        TokenInfoEntity tokenInfoEntity = tokenDao.getTokenInfoByAppId(appId);
        return tokenInfoEntity;
    }

    @Override
    public UserInfoEntity getUserInfoByAppId(String appId) {
        UserInfoEntity userInfoEntity = tokenDao.getUserInfoByAppId(appId);
        return userInfoEntity;
    }

    @Override
    public void saveTokenInfo(TokenInfoEntity tokenInfoEntity) {
        tokenDao.saveTokenInfo(tokenInfoEntity);
    }

    @Override
    public void modifyTokenInfo(TokenInfoEntity tokenInfoEntity) {
        tokenDao.updateTokenInfo(tokenInfoEntity);
    }
}
