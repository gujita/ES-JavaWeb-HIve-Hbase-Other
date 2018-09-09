package ipl.common.token;

import io.jsonwebtoken.*;
import ipl.common.enums.TokenValidatiEnum;
import ipl.common.utils.JacksonUtil;
import ipl.common.utils.ResultFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Descirption:对token的一些基本管理</p>
 *
 * @author 王海
 * @version V1.0
 * @package ipl.common.token
 * @date 2018/3/23 9:07
 * @since api1.0
 */
public class JWTManager {

    //    TODO：部署生产环境时，修改SECRET
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTManager.class);
    private static final String SECRET = "WH#$%(!)(#*!()!KL<55$6><MQLMNQNQJQK EiLCJuYW1lIjoif>?N<:{LWPW";
    // Base64就是一种基于64个可打印字符来表示二进制数据的方法。可查看RFC2045～RFC2049，上面有MIME的详细规范。
    // signWith中，如果传递字符串，需要传递base64编码的字符串

    /**
     * 生成的密钥
     */
    private static final Key SIGN_KEY = generalSecretKey(SignatureAlgorithm.HS256, SECRET);

    /**
     * 为登录用户生成key
     *
     * @param playLoadHelper 自定义的playload对象
     * @param validTime      token持续时间
     * @return 生成的token
     */
    public static String createToken(PlayLoadHelper playLoadHelper, long validTime) {
        final Map<String, Object> header = new HashMap<>(4, 1);
        header.put("alg", "HS256");
        header.put("typ", "JWT");
        long nowMillis = playLoadHelper.getIat().getTime();
        Date expireDate = null;
        if (validTime > 0) {
            expireDate = new Date(nowMillis + validTime);
        } else {
            expireDate = new Date(nowMillis + 60 * 60 * 1000);
        }

        JwtBuilder builder = Jwts.builder()
                .setHeader(header)
                .setIssuer(playLoadHelper.getIss())
                .setIssuedAt(playLoadHelper.getIat())
                .setSubject(playLoadHelper.getSub())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, SIGN_KEY);
        // 貌似也可以简便的写为signWith(SignatureAlgorithm.HS256,"SECRET".getBytes("UTF-8")【未验证】

//        compact()将它压缩成它的字符串形式。
        String compactJws = builder.compact();
        LOGGER.info("用户，{}，生成/再次生成token==================" + compactJws, playLoadHelper.getSub());
        return compactJws;
    }

    /**
     * 生成key，用于生成token
     *
     * @param signatureAlgorithm 指定加密算法
     * @param secretKey          自定义秘钥，仅仅后台知道
     * @return Key
     */
    public static Key generalSecretKey(SignatureAlgorithm signatureAlgorithm, String secretKey) {
        // 在Java 8在java.util包下面实现了BASE64编解码API，API简单易懂
        byte[] base64EncodedSecretKey = null;
        try {
            base64EncodedSecretKey = Base64.getEncoder().encode(secretKey.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 这样也是可以的 Key key = MacProvider.generateKey()
        if (base64EncodedSecretKey == null) {
            throw new IllegalArgumentException();
        }
        return new SecretKeySpec(base64EncodedSecretKey, signatureAlgorithm.getJcaName());
    }
/*
    *//**
     * @param token 用户请求api时携带的token
     * @return 更新后的token
     *//*
    private String updateTokenWithBase64(String token) {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            token = new String(decoder.decodeBuffer(token), "utf-8");
            Claims claims = parseJWT(token);
            String id = claims.getId();
            String subject = claims.getSubject();
            String issuer = claims.getIssuer();
            Date date = claims.getExpiration();
            String newToken = generToken(id, issuer, subject);
            return base64Encoder.encode(newToken.getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "0";
    }*/

    /**
     * 通过SIGN_KEY解析token字符串,解析成功说明可信任，用户为在线状态
     *
     * @param compactJws 已生成的token
     * @return Claims claims工厂中有我们之前定义的字段，可以将其对应的，我们设置的值解析出来
     */
    public static Claims parseJWT(String compactJws) throws Exception {
        Claims claims =  Jwts.parser()
                .setSigningKey(SIGN_KEY)
                .parseClaimsJws(compactJws)
                .getBody();
        LOGGER.info("该token的主人的email，{}",claims.getSubject());
        return claims;
    }

    /**
     * 在请求api前，都需要验证token，以确保用户为正确的登录状态
     *
     * @param compactJws 已生成的token
     * @return 自定义的返回响应结构
     */
    public static String validateJWT(String compactJws) {
        String resultFormat;
        try {
            Claims claims = parseJWT(compactJws);
            // 验证通过,仅在返回值为“ok”时，才能执行api接口的调用
            resultFormat = "ok";
            // 验证不通过，拒绝请求api接口，并给前端返回resultFormat
        } catch (ExpiredJwtException e) {
            resultFormat = JacksonUtil.bean2Json(ResultFormat.build(TokenValidatiEnum.EXPIRE.getErrorCode(), TokenValidatiEnum.EXPIRE.getDesc(), 1, "后台对每次请求进行token验证", null));
        } catch (SignatureException e) {
            resultFormat = JacksonUtil.bean2Json(ResultFormat.build(TokenValidatiEnum.NO_TRUST.getErrorCode(), TokenValidatiEnum.NO_TRUST.getDesc(), 1, "后台对每次请求进行token验证", null));
        } catch (Exception e) {

            resultFormat = JacksonUtil.bean2Json(ResultFormat.build(TokenValidatiEnum.OTHER_ERRORE.getErrorCode(), TokenValidatiEnum.OTHER_ERRORE.getDesc(), 1, "后台对每次请求进行token验证", null));
        }
        return resultFormat;
    }

}


