package com.adtec.rdc.base.auth.controller;

import com.adtec.rdc.base.auth.query.OAuth2AccessTokenQuery;
import com.adtec.rdc.base.common.constants.CommonConstants;
import com.adtec.rdc.base.common.constants.SecurityConstants;
import com.adtec.rdc.base.common.util.ApiResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author: JTao
 * @date 2018年10月31日13:23:22
 */
@RestController
public class AuthController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ConsumerTokenServices consumerTokenServices;

    @Autowired
    private RedisTokenStore redisTokenStore;

    @DeleteMapping("/token/{token}")
    public ApiResult<Boolean> removeAccessToken(@PathVariable("token") String token) {
        boolean ret = consumerTokenServices.revokeToken(token);
        if (ret) {
            Collection<OAuth2AccessToken> collections = redisTokenStore.findTokensByClientId(SecurityConstants.CLOUD);
            for (OAuth2AccessToken auth2AccessToken : collections) {
                if (token.equals(auth2AccessToken.getValue())) {
                    JdkSerializationRedisSerializer serializer = new JdkSerializationRedisSerializer();
                    redisTemplate.opsForSet().remove(CommonConstants.BASE_REDIS_LIST_LEY, (Object) serializer.serialize(auth2AccessToken));
                    break;
                }
            }
        }
        return new ApiResult<>(ret);
    }

    @GetMapping("/token")
    public ApiResult<Collection<OAuth2AccessToken>> readAllToken() {
        return new ApiResult<>(redisTokenStore.findTokensByClientId(SecurityConstants.CLOUD));
    }

    @GetMapping("/token/page")
    public ApiResult getTokenList(OAuth2AccessTokenQuery oAuth2AccessTokenQuery) {
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        Set<Object> all = redisTemplate.opsForSet().members(CommonConstants.BASE_REDIS_LIST_LEY);
        if(all != null){
            int len  = all.size();
            List<OAuth2AccessToken> records = new ArrayList<>();
            all.forEach(token -> {
                records.add((OAuth2AccessToken)token);
            });
            oAuth2AccessTokenQuery.setTotal(all.size());
            oAuth2AccessTokenQuery.setRecords(records);
        }
        return new ApiResult(oAuth2AccessTokenQuery);
    }

}
