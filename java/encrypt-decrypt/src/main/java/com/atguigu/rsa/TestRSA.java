package com.atguigu.rsa;
 
import cn.hutool.core.bean.BeanUtil;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import cn.hutool.core.codec.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
 
 
 
public class TestRSA {
	
//	public static String privateKey = "MIICXAIBAAKBgQDKO8Ff8JRwJYk8imP/0ntKdYWEZqJDM8G4xLDEBTArMSMhIqwhdOXkq1vDKyG6awlpFiUKlGHA2AwZ7JZsZhqpQhYEqkxTyqXuPs5Akk+jfaQBKcU2TTPqQti5cZiTvERVKNk+5/PyFSqz2CFOj0+wAPr/SyQeYKz3/hukld78AQIDAQABAoGAA2aJ0+EpvdEDw77X+59Ab3oWaqmHJPYj1cWI62Li8nb7Dj2VA2MB0pyHCHsbMBVIrTmwGcNw+VsoqO7vgJce754D35/DbvobMVTxx6DluRz0KwDhbvtdRDiEg3+8YbGoPO29G4+k0uagaaJhbuXVPqvMBMytHVPhT9kPTRFBjzkCQQDlXHrwPDB3v3ZJhGXE6JuKrv9qAVXoT0w7xby0lMnYWWeXCI316qTyDqIw1EVfi3z/AEJiJodu0EhL5Po7XT/vAkEA4bixTZAyw0viUmq1eexfI3GIPgmZloxwFNeDas59r269uF6PUagSQmXhnUvPR+Sa1pxImKIPgyLn09z3WW6TDwJAA3Nivh6JtCODShPd3BqLzIhZe2TNwBMBWJze+/CJIOIndtRAHoULOkESG5319TwKA2Qjm4ps2zPzAoFNans6rQJADUYdNyB5u75kqtAXp/nchmsQxgUugQn1NWSPF+LYxU5P3d3Bs92cZUc0g1mH8QRijqhWJJOzMdUFqRHfgeTcxQJBAOACRgPyk4NhafIrxIFpmpXU2yB8SAyvHE9FkJhZsDhia0I+3D27An2M4yzhaA95H8GHfT7SCNhEwE1rzmHe/wU=";
//	public static String publicKey ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDKO8Ff8JRwJYk8imP/0ntKdYWEZqJDM8G4xLDEBTArMSMhIqwhdOXkq1vDKyG6awlpFiUKlGHA2AwZ7JZsZhqpQhYEqkxTyqXuPs5Akk+jfaQBKcU2TTPqQti5cZiTvERVKNk+5/PyFSqz2CFOj0+wAPr/SyQeYKz3/hukld78AQIDAQAB";
public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCCrtuN0zg5G33EK/E0pjGS3/KnTSURRMB/vrHTHSmaB8VcudFO6GDGpcKWe8vnbP/LgqPxAZImrIz8Qlj6V3wiUH8H0hEh3Qcae0/DFQYG8mtgX9jzOobNOoXWO2LUDrOJMlZ8ALtdrJSzBntK2jUygsueCeBnUgCrJOwhUwB4BwIDAQAB";
	public static String privateKey ="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIKu243TODkbfcQr8TSmMZLf8qdNJRFEwH++sdMdKZoHxVy50U7oYMalwpZ7y+ds/8uCo/EBkiasjPxCWPpXfCJQfwfSESHdBxp7T8MVBgbya2Bf2PM6hs06hdY7YtQOs4kyVnwAu12slLMGe0raNTKCy54J4GdSAKsk7CFTAHgHAgMBAAECgYEAgdPdYuS4MmPRahaNptSE43mAxByr+p/m7gIw/581OUUHGbuw6UeK/U6oJoAObnenGDNNU51P6QP7fJEdfHd1ZeTl9lR+YXHSScaDB6yi/+0ZLRO5i5HkQmvhdFaoJuLy4lJLxuLz3Hp3BnM+VPmfFBWfhyyvUufv420L0BxN/CECQQDHuDWneiYVU90h3hJiyiPuoHieFKlOlQq2G/brPOYDBHT470F9QHyw2bExGAmm4wzIJxw5bWC7PW/Uze+ZytBbAkEAp4JYdKYXGSu3LFFwl2RWFBECyg+IbQG03XLaG1UjbdVnBqhKGtElyG27ueiHzWe0D+dw6/6UUHLhnTfbeOQGxQJBAJpJ18FTHvqY+BtC2MdSy+F8PNI6ufUINOcswVtHh5XLKqZpBxnA6NMiyb1YiZRQkT5K4IYI1pKvyqYGldQkl0MCQGJZrrP2nNNDjo3v1yqcV8n6XufijNhryxTGjXbE20Nuq8oF31S1ffibLUCvXaTlgZxg6DU3yT8e5IbZt/vX7n0CQFkUV6l6mRWRTjFH5uxdEIuMtLuTgSpPzI0BdMwM5UgNDZseiFYN277vPR9bBSDRikICf/ZgLlvEeXaz0YFkPDA=";

	private static String algorithm = "RSA"; //$NON-NLS-1$
	private static final int MAX_ENCRYPT_BLOCK = 117;
	private static final int MAX_DECRYPT_BLOCK = 128;
	private static String data = "就看见sdfasdfasdfasdfsad{}sdfasdf"; //$NON-NLS-1$
	
	public static void main(String[] args) throws Exception {
//		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQgEoj3z9JrdPNI23DbMQkl3gkGuDke7iBr5yrYyqolkTyxuBLWFwHNuGv4VKOj9fXg61QxpaJ/fxDBvMvmkBSRowHBloGFceVTx8wV/8u0DcjvTCu0IZ1zp6wjG6xBn5j66Sg/q+9hvaY2p7fkKmsvcW6VoNPgQHU1Cf01DLZmQIDAQAB+oXcINOiE3AsuZ4VJmwNZg9Y/7fY+OFRS2JAh5YMsrv2qyoGP+Z9ksre26NYR+Lt91B2lhdwJHLpQpziaANZm/ONb31fj/lwIDAQAB";
//		String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANCASiPfP0mt080jbcNsxCSXeCQa4OR7uIGvnKtjKqiWRPLG4EtYXAc24a/hUo6P19eDrVDGlon9/EMG8y+aQFJGjAcGWgYVx5VPHzBX/y7QNyO9MK7QhnXOnrCMbrEGfmPrpKD+r72G9pjant+Qqay9xbpWg0+BAdTUJ/TUMtmZAgMBAAECgYBSozY/Z4FW+31h5fPgK+DFu/8TGFAgXuTvCaJnz2Md9IkZTDejxT6cYWUr53toI5zhvz/XLw6FXNQ54KxMJq/s9PiZYUgq/PMrnyU4gBSTm5BmiWjdaGicVEZ1lofHjpkAchPNW/CzwxD8AeKI7QaObE+EkWbLAi6sa+nRdHKgrQJBAOwYLD2DncU15XCKS0RNzTrNohdBQcisOPHdtQO0CGZlxx3xjuU4WL6/EpdmbjTeYbOSDKCmY5vyVbYZdOWfEs8CQQDiFIwWpvW2WLxLVw3i2P55WmMMXuecwEzg++ae3Ht7nW0zNcWSsyvHh40sM8XqEzmWOzMY6JOePbkuVfWTc4cXAkBRzf5mQhiEoKwjVofF3v9hhKbJT/8vPR1uENgLtHHEqTdZFL3ihqeZUDNs6jz9bKCFy/E8KOsSueEg+6kZdwjZAkEAj2RW4fstd2VasDJb5ViaNqAEmJENOBej60L6KCJR07qqy0M8t+oaR2iLOtDvo6Jj8QxFQXQqRMCDVodAxjANKwJAL3KuaqA6kdy9RxdV3uP8nRXLY7C/1ZIK6U0pyZqKXEwpD+7Ar3hwwhPz9TeuoqjB/cCknZjw70BQFQ0/VUHW2g==";

		String test = testEncrypt(publicKey,data,true);
		System.out.println(test);

//		//test = "bbX8bV2Ar5Nxbk8HPTD0qGB727tUqh5ygkE60Bn+z5l3j8R3qaWwRQD0EbHrt2CM+H2f3oyKrs4w\n" +
//				"i/1cIb3wgC0/1bVfdioQXnNDQTweVXWo8Lw7AJ7FlvB8bPdpZLbBOj6SNYtB/pmNGqL3slE3aEi1\n" +
//				"QX0gxqW1r8V0TTQbISA=";

		String testDecrypt = testDecrypt(privateKey, test,true);
		System.out.println(testDecrypt);
		
	}
	
 
	/**
	 * 加密
	 * @param key
	 * @param data
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeyException
	 * @throws IOException 
	 */
	public static String testEncrypt(String key,String data,Boolean flag) throws  Exception {
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(key));
		KeyFactory kf = KeyFactory.getInstance(algorithm);
		Key generatePrivate = kf.generatePrivate(pkcs8EncodedKeySpec);
		if(flag){
			generatePrivate = kf.generatePublic(pkcs8EncodedKeySpec);
		}

		Cipher ci = Cipher.getInstance(algorithm);
		ci.init(Cipher.ENCRYPT_MODE, generatePrivate);
		
		byte[] bytes = data.getBytes();
		int inputLen = bytes.length;
		int offLen = 0;//偏移量
		int i = 0;
		ByteArrayOutputStream bops = new ByteArrayOutputStream();
		while(inputLen - offLen > 0){
			byte [] cache;
			if(inputLen - offLen > 117){
				cache = ci.doFinal(bytes, offLen,117);
			}else{
				cache = ci.doFinal(bytes, offLen,inputLen - offLen);
			}
			bops.write(cache);
			i++;
			offLen = 117 * i;
		}
		bops.close();
		byte[] encryptedData = bops.toByteArray();

		return Base64.encode(encryptedData);
	}
	
 
 
	
	/**
	 * 解密
	 * @param key
	 * @param data
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeySpecException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws IOException 
	 */
	public static String testDecrypt(String key,String data,Boolean flag) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, IOException, Base64DecodingException {

//		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decode); //java底层 RSA公钥只支持X509EncodedKeySpec这种格式
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(key));
		KeyFactory kf = KeyFactory.getInstance(algorithm);
		Key generatePublic = kf.generatePublic(x509EncodedKeySpec);
		if(flag){
			generatePublic = kf.generatePrivate(x509EncodedKeySpec);
		}


		Cipher ci = Cipher.getInstance(algorithm);
		ci.init(Cipher.DECRYPT_MODE,generatePublic);
		
		byte[] bytes = Base64.decode(data);

		int inputLen = bytes.length;
		int offLen = 0;
		int i = 0;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		while(inputLen - offLen > 0){
			byte[] cache;
			if(inputLen - offLen > 128){
				cache = ci.doFinal(bytes,offLen,128); 
			}else{
				cache = ci.doFinal(bytes,offLen,inputLen - offLen);
			}
			byteArrayOutputStream.write(cache);
			i++;
			offLen = 128 * i;
			
		}
		byteArrayOutputStream.close();
		byte[] byteArray = byteArrayOutputStream.toByteArray();
		return new String(byteArray);
	}
	
	
	
}
