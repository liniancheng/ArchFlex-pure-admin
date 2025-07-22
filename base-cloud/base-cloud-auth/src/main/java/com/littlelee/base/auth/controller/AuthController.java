package com.littlelee.base.auth.controller;

import com.littlelee.base.auth.query.OAuth2AccessTokenQuery;
import com.littlelee.base.common.constants.CommonConstants;
import com.littlelee.base.common.util.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 6.x 令牌管理端点
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final OAuth2AuthorizationService authorizationService;
    private final JdbcTemplate jdbcTemplate;

    /* ======================== 1. 撤销令牌 ======================== */

    /**
     * DELETE /token/{token}
     * 根据 access_token 值撤销整条授权记录
     */
    @DeleteMapping("/token/{token}")
    public ApiResult<Boolean> revokeToken(@PathVariable String token) {
        OAuth2Authorization authorization =
                authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        if (authorization != null) {
            authorizationService.remove(authorization);
            return new ApiResult<>(true);
        }
        return new ApiResult<>(false);
    }

    /* ======================== 2. 查询全部令牌 ======================== */

    /**
     * GET /token
     * 返回指定 client 的全部 access_token（不分页）
     */
    @GetMapping("/token")
    public ApiResult<List<TokenVo>> allTokens() {
        String sql = """
                SELECT access_token_value, access_token_issued_at, access_token_expires_at, principal_name
                FROM oauth2_authorization
                WHERE registered_client_id = ?
                """;
        List<TokenVo> list = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new TokenVo(
                        rs.getString("access_token_value"),
                        rs.getTimestamp("access_token_issued_at").toInstant(),
                        rs.getTimestamp("access_token_expires_at").toInstant(),
                        rs.getString("principal_name")
                ),
                CommonConstants.CLOUD_CLIENT_ID      // 对应旧 SecurityConstants.CLOUD
        );
        return new ApiResult<>(list);
    }

    /* ======================== 3. 分页查询 ======================== */

    /**
     * GET /token/page
     * 支持简单分页
     */
    @GetMapping("/token/page")
    public ApiResult<Page<TokenVo>> tokenPage(OAuth2AccessTokenQuery query) {
        String countSql = "SELECT COUNT(*) FROM oauth2_authorization WHERE registered_client_id = ?";
        Long total = jdbcTemplate.queryForObject(countSql, Long.class, CommonConstants.CLOUD_CLIENT_ID);
        if (total == null || total == 0) {
            return new ApiResult<>(Page.empty());
        }

        Pageable pageable = PageRequest.of( Math.max(query.getCurrent() - 1, 0),  query.getSize());
        String dataSql = """
                SELECT access_token_value, access_token_issued_at, access_token_expires_at, principal_name
                FROM oauth2_authorization
                WHERE registered_client_id = ?
                ORDER BY access_token_issued_at DESC
                LIMIT ? OFFSET ?
                """;
        List<TokenVo> content = jdbcTemplate.query(
                dataSql,
                (rs, rowNum) -> new TokenVo(
                        rs.getString("access_token_value"),
                        rs.getTimestamp("access_token_issued_at").toInstant(),
                        rs.getTimestamp("access_token_expires_at").toInstant(),
                        rs.getString("principal_name")
                ),
                CommonConstants.CLOUD_CLIENT_ID,
                pageable.getPageSize(),
                pageable.getOffset()
        );

        Page<TokenVo> page = new PageImpl<>(content, pageable, total);
        query.setTotal((int) page.getTotalElements());
        query.setRecords(page.getContent());
        return new ApiResult<>(page);
    }

    /* ======================== 4. 简单 VO ======================== */

    public record TokenVo(String tokenValue, Instant issuedAt, Instant expiresAt, String username) {}
}