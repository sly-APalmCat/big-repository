package io.renren.tetst;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.portable.Delegate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPairGeneratorSpi;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

/**
 * @author zl
 */
public class SecretTest {


    @Test
    public void lkjkjk() throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String secret="2131231@#42";
        String message="我加密一下";
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes("utf-8"), "HmacSHA256");
        sha256_HMAC.init(secretKey);
        byte[] hash = sha256_HMAC.doFinal(message.getBytes("utf-8"));
        String encodeStr = Base64.encodeBase64String(hash);
        String encodeStr16=byte2Hex(hash);
        System.out.println(encodeStr);
        System.out.println(encodeStr16);

    }

    @Test
    public void lkj() throws Exception {
        System.out.println(HMACSHA256("anv1234", "1234sadf"));

    }
    public static String HMACSHA256(String data, String key) throws Exception {

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");

        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");

        sha256_HMAC.init(secret_key);

        byte[] array = sha256_HMAC.doFinal(data.getBytes("UTF-8"));

        StringBuilder sb = new StringBuilder();

        for (byte item : array) {

            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));

        }

        return sb.toString().toUpperCase();

    }

    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

}
