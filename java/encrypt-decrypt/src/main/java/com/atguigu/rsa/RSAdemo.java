package com.atguigu.rsa;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.commons.io.FileUtils;

import javax.crypto.Cipher;
import java.io.File;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSAdemo
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-05-05
 * @Description:
 */
public class RSAdemo {
    public static void main(String[] args) throws Exception{
        generateKeyToFile("RSA","a.pub","a.pri");



        String input = "就看见sdfs";
        // 创建密钥对
        // KeyPairGenerator:密钥对生成器对象
        String algorithm = "RSA";
        // 读取私钥
       // PrivateKey privateKey = getPrivateKey("a.pri", algorithm);
        PrivateKey privateKey = getPrivateKey("MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIFwK3LEakuRZAdlsY4ckYsu+IHr\n" +
                "6SIoo+0bU97H9pP6NlMGOZR343K0AiTktyaLU6oeLhEgV6JiD3dKozm/KmXnD71Xdaee6x/K+OG4\n" +
                "TG5+f3LVvY84GwF6mNPGKJjNUyd11fzaVLWkHsvYScAQSpz7ZGD5TTbmeKhmRkGuGMPnAgMBAAEC\n" +
                "gYBwwkrXXAxNcIb3ervG1VRlL7IFXIJn44eKkvfpZ8GC/8I7aXzXvelCj1Ye1bBHv7BOzqPK/6XY\n" +
                "LBzVCwxOzh4cB2lH3VjXGinz4hnD4NyQfIPDEK+32bEOzFIicQ8uTtAXrvPveL2futeMFoRV6LlD\n" +
                "Vt23tm8CXk7Y3dgQCxnwQQJBAO07uPazEuN9WXL+3bu8bz0fbS2c0c/0jYsFudWZgiW9ESG3R11h\n" +
                "JYOa5n8e+psU+uDazbdG7wx/0RfIgFVGUnkCQQCLrXVBUK+D1yzdidE3TQAOwjkvTHu4EZvtRk4Z\n" +
                "4EmVdc8puJawYwhr4qgIr78jv4G18qHtyPjR13KuyQYnLzFfAkAmFZVe/WaF4MSwYlwEXH7bKCVl\n" +
                "0iwYn2DttZCcytUK6+xJJlwtkH/uPbrNI5MFvHt+B4JI7vlHX2mY0NZRWkW5AkB1u+LvCBy5a4E0\n" +
                "x3sjOvNGfW4IMVbDY4xkJvOkGgrMQX46M45I5WDu1yE+GS1q63xhKkBVlyYscocHTwixtLTPAkEA\n" +
                "h25vcIB+VFtqgs+4TFFTeOHrC3VzMZtSoxZqBwLkWYiN/14CtlUSLd8ej3Xs3ZIDnOdDxhC6+I/3\n" +
                "7aCipqfCsQ==", algorithm);
        System.out.println(privateKey);
        // 读取公钥key
       // PublicKey publicKey = getPublicKey("a.pub", algorithm);
        PublicKey publicKey = getPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCBcCtyxGpLkWQHZbGOHJGLLviB6+kiKKPtG1Pe\n" +
                "x/aT+jZTBjmUd+NytAIk5Lcmi1OqHi4RIFeiYg93SqM5vypl5w+9V3WnnusfyvjhuExufn9y1b2P\n" +
                "OBsBepjTxiiYzVMnddX82lS1pB7L2EnAEEqc+2Rg+U025nioZkZBrhjD5wIDAQAB", algorithm);
        System.out.println(publicKey);


//        //生成密钥对并保存在本地文件中
//        generateKeyToFile(algorithm, "a.pub", "a.pri");
//
//        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
//        // 生成密钥对
//        KeyPair keyPair = keyPairGenerator.generateKeyPair();
//        // 生成私钥
//        PrivateKey privateKey = keyPair.getPrivate();
//        // 生成公钥
//        PublicKey publicKey = keyPair.getPublic();
//        // 获取私钥的字节数组
//        byte[] privateKeyEncoded = privateKey.getEncoded();
//        // 获取公钥字节数组
//        byte[] publicKeyEncoded = publicKey.getEncoded();
//        // 使用base64进行编码
//        String privateEncodeString = Base64.encode(privateKeyEncoded);
//        String publicEncodeString = Base64.encode(publicKeyEncoded);
//        // 打印公钥和私钥
////        System.out.println(privateEncodeString);
////        System.out.println(publicEncodeString);
//
//
        System.out.println("-=====================================-------------------------------========================");
        String s = encryptRSA(algorithm,privateKey, input);
        String s2 = encryptRSA(algorithm,publicKey, input);
        System.out.println(s.trim());
        System.out.println(s2.trim());
        s = "POIuAe7FgUXJf995jrweTiJT7zVmyb+7k3GFSQHNivD1zt44b+CytQjjy97YYwNlFd+bX4IDI6mE0zA5PWAmLXuXL6t/3PnIsnELDvk1Tf3nm3SdJT8b75gjezCf/lAtneRlz3JD5rLByNEoJLKtqtBbY9vM6JlwskGHqo53qkg=";
        s2 = "ZbzIjrRmdSZ6cO1EDvJ+o9DXPXRpvpbzkdyfkn4vpbH6DwBsAYierACvcg13NQYuJr1nCfFkzpgfHcEGyyzeX3/oSBnlGtknHj//X5NTKQHiN64JhndbtQ0MqaAaWaZCxl5m0dmQrBekUXt2DZMioZZOWiP6xSnSxo/qTXuVvac=";
       // String s1 = decryptRSA(algorithm, publicKey , s);
        String s12 = decryptRSA(algorithm, privateKey , s2);
        //System.out.println(s1);
        System.out.println(s12);

    }

    /**
     * 读取公钥
     * @param publicPath 公钥路径
     * @param algorithm  算法
     * @return
     */
    public static PublicKey getPublicKey(String publicPath, String algorithm) throws Exception{
        String publicKeyString = publicPath;//FileUtils.readFileToString(new File(publicPath), Charset.defaultCharset());
        // 创建key的工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        // 创建公钥规则
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicKeyString));
        return keyFactory.generatePublic(keySpec);
    }

    /**
     *  读取私钥
     * @param priPath 私钥的路径
     * @param algorithm 算法
     * @return 返回私钥的key对象
     */
    public static PrivateKey getPrivateKey(String priPath, String algorithm) throws Exception{
        String privateKeyString = priPath;//FileUtils.readFileToString(new File(priPath), Charset.defaultCharset());
        // 创建key的工厂
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        // 创建私钥key的规则
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyString));
        // 返回私钥对象
        return keyFactory.generatePrivate(keySpec);
    }


    /**
     * 解密数据
     *
     * @param algorithm      : 算法
     * @param encrypted      : 密文
     * @param publicKey            : 密钥
     * @return : 原文
     * @throws Exception
     */
    public static String decryptRSA(String algorithm,Key publicKey,String encrypted) throws Exception{
        // 创建加密对象
        Cipher cipher = Cipher.getInstance(algorithm);
        // 私钥解密
        cipher.init(Cipher.DECRYPT_MODE,publicKey);
        // 使用base64进行转码
        byte[] decode = Base64.decode(encrypted);


        // 使用私钥进行解密
        byte[] bytes1 = cipher.doFinal(decode);
        return new String(bytes1);
    }


    /**
     * 使用密钥加密数据
     *
     * @param algorithm      : 算法
     * @param input          : 原文
     * @param privateKey            : 密钥
     * @return : 密文
     * @throws Exception
     */
    public static String encryptRSA(String algorithm, Key privateKey, String input) throws Exception{
        // 创建加密对象
        Cipher cipher = Cipher.getInstance(algorithm);
        // 对加密进行初始化
        // 第一个参数：加密的模式
        // 第二个参数：你想使用公钥加密还是私钥加密
        // 我想使用私钥进行加密
        cipher.init(Cipher.ENCRYPT_MODE,privateKey);
        // 使用私钥进行加密

        byte[] bytes = cipher.doFinal(input.getBytes());
        return Base64.encode(bytes);
    }




    /**
     * 保存公钥和私钥，把公钥和私钥保存到根目录
     * @param algorithm 算法
     * @param pubPath 公钥路径
     * @param priPath 私钥路径
     */
    private static void generateKeyToFile(String algorithm, String pubPath, String priPath) throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(1024);
        // 生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 生成私钥
        PrivateKey privateKey = keyPair.getPrivate();
        // 生成公钥
        PublicKey publicKey = keyPair.getPublic();
        // 获取私钥的字节数组
        byte[] privateKeyEncoded = privateKey.getEncoded();
        // 获取公钥字节数组
        byte[] publicKeyEncoded = publicKey.getEncoded();
        // 使用base64进行编码
        String privateEncodeString = Base64.encode(privateKeyEncoded);
        String publicEncodeString = Base64.encode(publicKeyEncoded);
        System.out.println(privateEncodeString);
        System.out.println("-------------------             --------------------------");
        System.out.println(publicEncodeString);
        System.out.println("---------------------                      ------------------------");
        System.out.println(privateKey.getFormat());
        String format = publicKey.getFormat();
        System.out.println(format);
        System.out.println("-------------- ---------------                            -------------");

        // 把公钥和私钥保存到根目录
        FileUtils.writeStringToFile(new File(pubPath),publicEncodeString, Charset.forName("UTF-8"));
        FileUtils.writeStringToFile(new File(priPath),privateEncodeString, Charset.forName("UTF-8"));

    }
}