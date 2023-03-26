package org.zzd.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.zzd.constant.SecurityConstants;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;



/**
 * JWT工具类
 * @author :zzd
 * @date : 2022/11/4
 */
@Component
public class JwtTokenUtil {

    /**
     * @apiNote 根据用户名生成token
     * @date 2023/3/12 21:28
     * @param userDetails: userDetails
     * @return java.lang.String
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(SecurityConstants.CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(SecurityConstants.CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }


    /**
     * @apiNote 根据载荷生成token
     * @param claims: 载荷
     * @return java.lang.String
     */
    private String generateToken(Map<String, Object> claims) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())   //过期时间
                .signWith(signatureAlgorithm, generalKey())
                .compact();
    }

    /**
     * @apiNote token失效时间
     * @date 2023/3/12 21:28
     * @return java.util.Date
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis()+ SecurityConstants.EXPIRATION_TIME);
    }

    /**
     * @apiNote 从token中获取用户名
     * @date 2023/3/12 21:31
     * @param token: token
     * @return java.lang.String
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(generalKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * @apiNote 验证令牌
     * @date 2023/3/24 10:34
     * @param token: 令牌
     * @param userDetails: 用户
     * @return java.lang.Boolean
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * @apiNote 判断token是否过期
     * @date 2023/3/12 21:32
     * @param token: token
     * @return boolean
     */
    private boolean isTokenExpired(String token) {
        Date expireDate = getExpiredDateFromToken(token);
        return expireDate.before(new Date());
    }

    /**
     * @apiNote 从token中获取过期时间
     * @date 2023/3/12 21:30
     * @param token: token
     * @return java.util.Date
     */
    public Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }


    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * @apiNote 刷新token
     * @date 2023/3/12 21:30
     * @param token: token
     * @return java.lang.String
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(SecurityConstants.CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    public static String getUUID(){
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        return token;
    }

    /**
     * @apiNote 生成加密后的密钥
     * @date 2023/3/2 10:10
     * @return javax.crypto.SecretKey
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getMimeDecoder().decode(SecurityConstants.SECRET_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

}