package com.atguigu.desaes;

import com.alibaba.fastjson.JSON;
import com.atguigu.ascii.JsonUtil;
import com.atguigu.rsa.RSAdemo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.HashMap;

/**
 * DesAesDemo
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-05-05
 * @Description:
 *         对称加密
 */
public class AesDemo {
    private static ObjectMapper INSTANCE = JsonUtil.getInstance();

    @Test
    public void klj() throws Exception {
        String sl = "LkjuOsWox2AO457cvAArzkdrTtDwJk9X";
//106
        HashMap<String, Long> body = new HashMap();
        body.put("brId",1442711623476527L);
        String s = JSON.toJSONString(body);
        byte[] encode = Pkcs7Encoder.encode(s.getBytes());


        String s2 = AesDemo.aes(encode, sl.getBytes(), Cipher.ENCRYPT_MODE);
        System.out.println(s2);

    }

    @Test
    public void lkjl() throws Exception {
        //brid 1442711623476527108
        String pub = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjhpDSo4LrbjluCcvkjet1quH6SBsbWB0U7yL/vQu1YyiCLpFJPUuZ2hUqz+pSm0svquiwNobgAYyriB4lihjd+pyYJKoBvDY9JniCadgLGDfINq2efbsmkQQtSr8WOEHbkdbgbYjR0c/VmqnxJkcyaCZuO7bNXV8dHsc8CkMEu7Ek631JAlppq1G1zXSE+wJAjkynYXjVUZxrqW+qFJM/PQOMBqxTvfsNNpo68h3GPCNMinRpMduOTYfY/jLT3/C5GDkRNo5vB9Eq7nNYJsSpf+npHcHM5cpJ5wc/7IofDULxsQZqabqn71OoE7SChcoN5O/L/ETb9FYn/09nlbC4wIDAQAB";
        String pri = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCOGkNKjgutuOW4Jy+SN63Wq4fpIGxtYHRTvIv+9C7VjKIIukUk9S5naFSrP6lKbSy+q6LA2huABjKuIHiWKGN36nJgkqgG8Nj0meIJp2AsYN8g2rZ59uyaRBC1KvxY4QduR1uBtiNHRz9WaqfEmRzJoJm47ts1dXx0exzwKQwS7sSTrfUkCWmmrUbXNdIT7AkCOTKdheNVRnGupb6oUkz89A4wGrFO9+w02mjryHcY8I0yKdGkx245Nh9j+MtPf8LkYORE2jm8H0Sruc1gmxKl/6ekdwczlyknnBz/sih8NQvGxBmppuqfvU6gTtIKFyg3k78v8RNv0Vif/T2eVsLjAgMBAAECggEADSBz8uEoRwX7goVsKY+nKVtzFKAAJCJy3IFgVU7tPKDfHvtGA+H6RWcn2yHoW422Qm7MfBAp1iu8ZsVtHkHg3mTz5+PkTa/V+tJ0RPwHMFJKilhp3t5mgOGkwXeVFAjFjPReiZ7vp1f3i94SXWDffeiuCp1V9pxBwOjWhYDlMqJkj08wcHunmHTRiW1jDo3e5AEjuGfRgcUjo2jJ18Sfn305obcp66N7yNgvxuUAWddgUaJdJhsb6sR+uhfiTFpc8qVMA8dr9NixSAWZuCIkeBtUy19WYqlywJdM0HfeVQZlZ4Acg3kkywjbT8EHEke9FLy6YqEx98m0viUF7gtWUQKBgQDJrlpGJToHF4Z8C/YurLNuilso1Yh+2SBz+TVWaDb/boJuIqPQD+3wpLBsUsCJYXBrTVAtK7UYCOAmumPxmUVJw0C849Q5JLiqA24HiiIS+JV0xXTnO+CCUPpfvPiUQ6dZDziFznFcv1vovbeUt7rEKTSxGlEbsK3KYA3GlfJL1wKBgQC0YA0+3dKuE9PpZg+qVzKfS0vou5PD+/6FVt6NRP37bpvdKJQSmjumo4hCJ6vcg1S9dsr6doYHy61wuuSV3/qmlinEYlDcISQ1peQDgI2b3ybXVZKrQg85Oy+XjR6b4RM4LR78fVk7fuIv0VLezJ43CypnVdU5FFEeHQRZ5RJ/1QKBgQCYckQmdYkxRyUOqvQBP+W5jrdtXEr6pyESyLL6h3fbCCtrGJ/+a6wNlE8O8lY7Af45/Yy3OCyA6GSWoZleS97F7OMIZKZMM2eqi0EmTdGHyYMfawn8p5QCrTc0GuFDIQhkF09ARVYHJ1ZPzOfxcBeqowMDQ8M/TQNwz75Z94nYrQKBgAr1oxhkxf5JuiB+UQlajvraylvzucC4uPhFFPSkcoIqcFH5uzV+JY20dfz/CBQ37pgsdZzOQsqKd5lX4bexijtA+Hy0NJGLefXAhKAp2XDVGDkRO/4n32Ds1bFxo6B5Bn/tTeqtexSAWyHoqA5dhGd3yDXLI096ios33CHSJ1OZAoGAByjGb0FXjTLFHJMJRqYaUjgjki3Vw3Ki/HYK8zv1zjAzwH14KFJ5l6gl4ckGQCLmZKvB6RFL39uPnzWu4IxxAhvWaj8MNXuXQm7Ls/kjcPnd2Zso/hCi3nG4AtOAutjJXI/ifIPeViMFUU7IeBdI/qkwuVWPsd47QEUfQQAlLs0=";
        String salt = "8EPTRGfS2p6QtA==";
        String sl = "LkjuOsWox2AO457cvAArzkdrTtDwJk9X";

        String pwd = "123456";

        String s = AesDemo.encryptDES(pwd, salt, "AES", "AES");

        System.out.println(s);
        PublicKey publicKey = RSAdemo.getPublicKey(pub, "RSA");
        PrivateKey privateKey = RSAdemo.getPrivateKey(pri, "RSA");

        String rsa = RSAdemo.encryptRSA("RSA", publicKey, s);

        System.out.println(rsa);
        String rsa1 = RSAdemo.decryptRSA("RSA", privateKey, rsa);
        System.out.println(rsa1);
        rsa1 = "W3wzEb3DxtrVtY02uYJgww==";
        String s1 = AesDemo.decryptDES(rsa1, salt, "AES", "AES");
        System.out.println(s1);
    }



    public static void main(String[] args) throws Exception{
        // 原文
        String input = "硅谷";
        // 定义key
        // 如果使用des进行加密，那么密钥必须是8个字节
        // 如果使用的是AES加密，那么密钥必须是16个字节
        String key = "123456789abcdefg";//"1234567812345678";
        // 算法
        String transformation = "AES";
        // 加密类型
        String algorithm = "AES";
        // 指定获取密钥的算法
        String encryptDES = encryptDES(input, key, transformation, algorithm);
        System.out.println("加密:" + encryptDES);

        String s = decryptDES(encryptDES, key, transformation, algorithm);
        System.out.println("解密:" + s);
    }

    /**
     * 解密
     * @param encryptDES  密文
     * @param key         密钥
     * @param transformation 加密算法
     * @param algorithm   加密类型
     * @return
     */
    private static String decryptDES(String encryptDES, String key, String transformation, String algorithm) throws Exception{
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(),algorithm);
        //Cipher.DECRYPT_MODE:表示解密
        // 解密规则
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
        // 解密，传入密文
        byte[] decode = Base64.decode(encryptDES);

        byte[] bytes = cipher.doFinal(decode);

        return new String(bytes);
    }

    /**
     * 使用DES加密数据
     *
     * @param input          : 原文
     * @param key            : 密钥(DES,密钥的长度必须是8个字节)
     * @param transformation : 获取Cipher对象的算法
     * @param algorithm      : 获取密钥的算法
     * @return : 密文
     * @throws Exception
     */
    private static String encryptDES(String input, String key, String transformation, String algorithm) throws Exception {
        // 获取加密对象
        Cipher cipher = Cipher.getInstance(transformation);
        // 创建加密规则
        // 第一个参数key的字节
        // 第二个参数表示加密算法
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), algorithm);
        // ENCRYPT_MODE：加密模式
        // DECRYPT_MODE: 解密模式
        // 初始化加密模式和算法
        cipher.init(Cipher.ENCRYPT_MODE,sks);

        // 加密
        byte[] bytes = cipher.doFinal(input.getBytes());
        // 输出加密后的数据
        String encode = Base64.encode(bytes);

        return encode;
    }

    private static String aes(byte[] encrypted, byte[] aesKey, int mode) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
            cipher.init(mode, keySpec, iv);
            byte[] bytes = cipher.doFinal(encrypted);
            return Base64.encode(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将二进制转换成16进制
     * @method parseByte2HexStr
     * @param buf
     * @return
     * @throws
     * @since v1.0
     */
    public static String parseByte2HexStr(byte buf[]){
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < buf.length; i++){
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     * @method parseHexStr2Byte
     * @param hexStr
     * @return
     * @throws
     * @since v1.0
     */
    public static byte[] parseHexStr2Byte(String hexStr){
        if(hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 提供基于PKCS7算法的加解密接口.
     */
    private static class Pkcs7Encoder {
        private static final int BLOCK_SIZE = 32;

        private static byte[] encode(byte[] src) {
            int count = src.length;
            // 计算需要填充的位数
            int amountToPad = BLOCK_SIZE - (count % BLOCK_SIZE);
            // 获得补位所用的字符
            byte pad = (byte) (amountToPad & 0xFF);
            byte[] pads = new byte[amountToPad];
            for (int index = 0; index < amountToPad; index++) {
                pads[index] = pad;
            }
            int length = count + amountToPad;
            byte[] dest = new byte[length];
            System.arraycopy(src, 0, dest, 0, count);
            System.arraycopy(pads, 0, dest, count, amountToPad);
            return dest;
        }

        private static byte[] decode(byte[] decrypted) {
            int pad = decrypted[decrypted.length - 1];
            if (pad < 1 || pad > BLOCK_SIZE) {
                pad = 0;
            }
            if (pad > 0) {
                return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
            }
            return decrypted;
        }
    }
}