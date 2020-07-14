package com.cvte.notesync.utils;


import com.cvte.notesync.common.enums.NoteHttpStatus;
import com.cvte.notesync.common.exception.NoteException;
import com.cvte.notesync.entity.Audience;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    public static final String AUTH_HEADER_KEY = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer-";

    /**
     * 解析jwt
     * @param jwtToken
     * @param base64Security
     * @return
     */
    public static Claims parseJwt(String jwtToken, String base64Security) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                    .parseClaimsJws(jwtToken).getBody();
            return claims;
        } catch (ExpiredJwtException eje) {
            throw new NoteException(NoteHttpStatus.TOKEN_EXPIRE_ERROR);
        } catch (Exception e){
            throw new NoteException(NoteHttpStatus.ORDER_ERROR);
        }
    }

    /**
     * 生成jwt
     * @param userId
     * @param username
     * @param audience
     * @return
     */
    public static String createJwt(String userId, String username, Audience audience) {
        try {
            // 使用HS256加密算法
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            //生成签名密钥
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(audience.getBase64Secret());
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
            //userId是重要信息，进行加密下
//            String encryId = Base64Util.encode(userId);
            //添加构成JWT的参数
            JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                    // 可以将基本不重要的对象信息放到claims
                    .claim("userId", userId)
                    .setSubject(username)           // 代表这个JWT的主体，即它的所有人
                    .setIssuer(audience.getClientId())              // 代表这个JWT的签发主体；
                    .setIssuedAt(new Date())        // 是一个时间戳，代表这个JWT的签发时间；
                    .setAudience(audience.getName())          // 代表这个JWT的接收对象；
                    .signWith(signatureAlgorithm, signingKey);
            //添加Token过期时间
            int TTLMillis = audience.getExpiresSecond();
            if (TTLMillis >= 0) {
                long expMillis = nowMillis + TTLMillis;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp)  // 是一个时间戳，代表这个JWT的过期时间；
                        .setNotBefore(now); // 是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
            }
            //生成JWT
            return builder.compact();
        } catch (Exception e) {
            throw new NoteException(NoteHttpStatus.SYSTEM_ERROR);
        }
    }

    /**
     * 从Claims中获取username
     * @param jwtToken
     * @param base64Security
     * @return
     */
    public static String getUsername(String jwtToken, String base64Security) {
        return parseJwt(jwtToken, base64Security).getSubject();
    }

    /**
     * 从
     * @param jwtToken
     * @param base64Security
     * @return
     */
    public static String getUserId(String jwtToken, String base64Security) {
        return parseJwt(jwtToken, base64Security).get("userId", String.class);
    }

    /**
     * 查看token是否过期
     * @param jwtToken
     * @param base64Security
     * @return
     */
    public static boolean isExpiration(String jwtToken, String base64Security) {
        return parseJwt(jwtToken, base64Security).getExpiration().before(new Date());
    }
}
