package didastudy.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class JWTUtil {

    public static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);

    //toekn过期时间(30分钟)
    private static final long EXPIRE_TIME = 30 * 60 *1000;

    /**
     * 生成token签名,30min后过期
     * @param username 用户名
     * @param secret 用户密码
     * @return 加密token
     */
    public static String sign(String username, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        //附带username信息
        return JWT.create()
                .withClaim("username", username)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 校验token是否正确
     * @param token 密钥
     * @param username 用户名
     * @param secret 用户密码
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withClaim("username", username)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return true;
    }

    /**
     * 不解密获取token中的信息
     * @param token
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("username").asString();
    }
}
