package com.lgp.controller.token;

import com.lgp.entity.token.TokenInfoEntity;
import com.lgp.entity.token.UserInfoEntity;
import com.lgp.service.TokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-05-05 23:20
 */
@RestController
@RequestMapping(value = "api")
public class TokenController {

    @Autowired
    TokenService tokenService;

    @GetMapping(value = "index")
    public String index(){
        return "success";
    }


    @GetMapping(value = "token")
    public String getToken(String appId, String appSecret) throws Exception{
        if (StringUtils.isBlank(appId)) {
            throw new Exception("appId is not found!");
        }
        if (StringUtils.isBlank(appSecret)) {
            throw new Exception("ppSecret is not found!");
        }
        UserInfoEntity userInfoEntity = tokenService.getUserInfoByAppId(appId);
        if (userInfoEntity == null){
            throw new Exception("appId : " + appId + " is not found!");
        }
        if (!appSecret.replace(" ", "+").equals(new String(userInfoEntity.getAppSecret()))) {
           throw new Exception("appSecret is not effective!");
        }
        TokenInfoEntity tokenInfoEntity = tokenService.getTokenInfoByAppId(appId);
        String tokenStr = null;
        if (tokenInfoEntity == null) {
            //生成Token
            tokenStr = createToken(appId, userInfoEntity);
            tokenInfoEntity = new TokenInfoEntity();
            tokenInfoEntity.setAppId(appId);
            //更新token
            tokenInfoEntity.setToken(tokenStr.getBytes());
            //更新生成时间
            tokenInfoEntity.setBuildTime(String.valueOf(System.currentTimeMillis()));
            //执行token
            tokenService.saveTokenInfo(tokenInfoEntity);
        } else {
            //判断数据库中token是否过期，如果没有过期不需要更新直接返回数据库中的token即可
            //数据库中生成时间
            long dbBuildTime = Long.valueOf(tokenInfoEntity.getBuildTime());
            //当前时间
            long currentTime = System.currentTimeMillis();
            //如果当前时间 - 数据库中生成时间 < 7200 证明可以正常使用
            long second = TimeUnit.MILLISECONDS.toSeconds(currentTime - dbBuildTime);
            if (second > 0 && second < 7200) {
                tokenStr = new String(tokenInfoEntity.getToken());
            }
            //超时
            else{
                //生成newToken
                tokenStr = createToken(appId, userInfoEntity);
                //更新token
                tokenInfoEntity.setToken(tokenStr.getBytes());
                //更新生成时间
                tokenInfoEntity.setBuildTime(String.valueOf(System.currentTimeMillis()));
                //执行更新
                tokenService.modifyTokenInfo(tokenInfoEntity);
            }
        }
        return tokenStr;
    }


    private String createToken(String appId, UserInfoEntity userInfoEntity){
        //获取当前时间
        Date now = new Date(System.currentTimeMillis());
        //过期时间
        Date expiration = new Date(now.getTime() + 7200000);
        String tokenStr = Jwts.builder()
                .setSubject(appId)
                .claim("LGP_AUTH_ROLES", userInfoEntity)
                .setIssuedAt(now)
                .setIssuer("Online YAuth Builder")
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, "LGPAuthV1.0")
                .compact();
        return tokenStr;
    }

}
