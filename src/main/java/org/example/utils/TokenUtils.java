package org.example.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.example.handler.exceptionhandler.JavaWebException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zxd on 2023/2/22 9:04
 */
@Slf4j
public class TokenUtils {

    //设置过期时间
    private static final long EXPIRE_DATE=2*60*60000;

    //token秘钥
    private static final String TOKEN_SECRET = "fsdfvsdvfcnmaskvncascmaslmcsalkncsaclksajndcjmasmlfcaswfmsafcmaslcwcsacscscscscsdsefedfbsdvgdsvsdvmnsdkalvndjsnvdjksncsszcsafdsdfgdfdfdfvfacscs32532532sacscs";

    /**
     * 生成token
     * @return
     */
    public static String token (String id, String phone){

        String token = "";
        try {
            //过期时间
            Date date = new Date(System.currentTimeMillis()+EXPIRE_DATE);
            //秘钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            //设置头部信息
            Map<String,Object> header = new HashMap<>();
            header.put("typ","JWT");
            header.put("alg","HS256");
            //携带username，password信息，生成签名
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("id",id)
                    .withClaim("phone",phone)
                    .withExpiresAt(date)
                    .sign(algorithm);
        }catch (Exception e){
            log.error(e.getMessage());
            throw  new JavaWebException(200001,"jwt令牌创建失败");
        }
        return token;
    }

    /**
     * 校验
     * @param token
     * @return
     */
    public static boolean verify(String token){
        /**
         * @desc   验证token，通过返回true
         * @params [token]需要校验的串
         **/
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception e){
            log.error("校验失败:{}",e.getMessage());
            return  false;
        }
    }

    public static void main(String[] args) {

        String token=TokenUtils.token("1L","12");


        //eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwaG9uZSI6Inh4IiwiaW1hZ2VVcmwiOiJ4eHgiLCJuYW1lIjoieHh4IiwiaWQiOjEsImV4cCI6MTY3NzAzNTE1NSwiZW1haWwiOiJ4eCIsInVzZXJuYW1lIjoieHgifQ.sJNtrA_DKgq3KdrMxU_7stMZr_RDi45P_8tHUBJJ5vw
        System.out.println(token);


        System.out.println(verify("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJwaG9uZSI6Inh4IiwiaW1hZ2VVcmwiOiJ4eHgiLCJuYW1lIjoieHh4IiwiaWQiOjEsImV4cCI6MTY3NzAzNTE1NSwiZW1haWwiOiJ4eCIsInVzZXJuYW1lIjoieHgifQ.sJNtrA_DKgq3KdrMxU_7stMZr_RDi45P_8tHUBJJ5vw"));

    }

}