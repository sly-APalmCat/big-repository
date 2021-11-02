package com.atguigu.digest;

import com.atguigu.rsa.RSAdemo;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.concurrent.TimeUnit;

/**
 * SignatureDemo
 *
 * @Author: 马伟奇
 * @CreateTime: 2020-05-09
 * @Description:
 */
public class SignatureDemo {


    @Test
    public void lkj() throws InterruptedException {
        new Thread(new Runnable() {
            public void run() {
                long l = System.currentTimeMillis();
                for (int i = 0; i < 1000000; i++) {
                    byte[] bytes = SecureRandom.getSeed(10);
                    String encode = com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(bytes);
                    if(encode.length() > 16 || encode.length() < 16){
                        System.out.println(encode);
                        System.out.println(encode.length());
                    }
                    System.out.print("|");
                }
                long l2 = System.currentTimeMillis();
                long s = l2 - l;
                System.out.println("1号完毕！"+ s);

            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                long l = System.currentTimeMillis();
                for (int i = 0; i < 1000000; i++) {
                    byte[] bytes = SecureRandom.getSeed(10);
                    String encode = com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(bytes);
                    if(encode.length() > 16 || encode.length() < 16){
                        System.out.println(encode);
                        System.out.println(encode.length());
                    }
                    System.out.print(".");
                }
                long l2 = System.currentTimeMillis();
                long s = l2 - l;
                System.out.println("2号完毕！"+ s);
            }

        }).start();

        System.out.println("我等等你俩！");

        TimeUnit.SECONDS.sleep
                (1000);
    }

    public static void main(String[] args) throws Exception{

        String a = "123";
        String a2 = "1232";

        PublicKey publicKey = RSAdemo.getPublicKey("a.pub","RSA");
        PrivateKey privateKey = RSAdemo.getPrivateKey("a.pri","RSA");
        // 获取数字签名
        String signaturedData = getSignature(a, "sha256withrsa", privateKey);
        System.out.println(signaturedData);
        // 校验签名
        boolean b = verifySignature(a, "sha256withrsa", publicKey, signaturedData);
        System.out.println(b);







    }

    /**
     * 校验签名
     * @param input 表示原文
     * @param algorithm 表示算法
     * @param publicKey 公钥key
     * @param signaturedData 签名密文
     * @return
     */
    private static boolean verifySignature(String input, String algorithm, PublicKey publicKey, String signaturedData) throws Exception{
        // 获取签名对象
        Signature signature = Signature.getInstance(algorithm);
        // 初始化校验
        signature.initVerify(publicKey);
        // 传入原文
        signature.update(input.getBytes());
        // 校验数据
        return signature.verify(Base64.decode(signaturedData));
    }

    /**
     * 生成数字签名
     * @param input 表示原文
     * @param algorithm  表示算法
     * @param privateKey 私钥key
     * @return
     */
    private static String getSignature(String input, String algorithm, PrivateKey privateKey) throws Exception{
        // 获取签名对象
        Signature signature = Signature.getInstance(algorithm);
        // 初始化签名
        signature.initSign(privateKey);
        // 传入原文
        signature.update(input.getBytes());
        // 开始签名
        byte[] sign = signature.sign();
        // 使用base64进行编码
        return Base64.encode(sign);
    }
}