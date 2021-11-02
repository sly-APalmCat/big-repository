import com.alibaba.fastjson.JSON;
import com.blade.finance.entity.WalletLogEntity;
import com.blade.finance.utils.wallet.AESUtil;
import com.blade.finance.utils.wallet.RsaKeyUtil;
import com.blade.finance.utils.wallet.SignatureUtil;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.junit.Test;
import org.springblade.core.api.crypto.bean.CryptoInfoBean;
import org.springblade.core.api.crypto.config.ApiCryptoProperties;
import org.springblade.core.api.crypto.enums.CryptoType;
import org.springblade.core.api.crypto.util.ApiCryptoUtil;
import org.springblade.core.tool.jackson.JsonUtil;
import org.springblade.core.tool.utils.RsaUtil;
import org.springframework.util.AntPathMatcher;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zl
 */
public class Abc {

	@Test
	public  void lkjl() throws Base64DecodingException {
		HashMap<String, Object> body = new HashMap<>();
		body.put("brId",1442711623476527108L);
//		body.put("brId","1442711623476527108");
//		body.put("withdrawVal","10.00");
//		body.put("pwd","NKMqzaUojspiw6EeQ9oiI0j1vCSpSTQI090L0x/SlJt7CFLaaUzufy16A/Phaxfc4gzs+ld7CxtIhd0Ym0e7q+e9yd4CQwNGu/z0VrvBl/xZnFlJdAyPPvMfKJ2zv03YCcEGuhTFDa4lGN2pi3xbwqUIOYAsRrryZbtkzrlvKhQtbsHP7Rt5081d/K2xWDVCkWDoPG6BQWkJeR4PMwFBHc/qnoBrWsKGfSVQ3gCo9XWn6Ae0Ay9uyGMD3Za5XUhLr7IyqPRuW3E46hSpC9m30ZBAb3PIK95GEMaxR8a0BHmpsS6bTzpDsTrWLOMOF91KcRF6Gq7eQ/z7z6hKwOEX2g==");
//		body.put("withdrawType","3");
//		HashMap<String, String> str = new HashMap<>();
//		str.put("platformRate","0.01");
//		str.put("procedureCharge","0.10");
//		body.put("rateDto",str);
//		body.put("brId","1442711623476527108");
//		body.put("id","1443181858980454402");
//		body.put("withrawRefuseState","1");
//		body.put("withdrawValNumber","sdfs");
//		body.put("picUrl","sdf");
//		body.put("picType","sdf");
//		body.put("remark","dfs");


		byte[] bodyJsonBytes = JsonUtil.toJsonAsBytes(body);
		ApiCryptoProperties properties = new ApiCryptoProperties();
		properties.setAesKey("AES");
		properties.setAesKey("LkjuOsWox2AO457cvAArzkdrTtDwJk9X");
		CryptoInfoBean cryptoInfoBean = new CryptoInfoBean(CryptoType.AES, "LkjuOsWox2AO457cvAArzkdrTtDwJk9X");
		String s = ApiCryptoUtil.encryptData(properties, bodyJsonBytes, cryptoInfoBean);
		System.out.println(" ------------------------------------   ");
		System.out.println(s);
		s = "+JJbbZsTEl6LwwrKRtLau2ku+ArfKBjVu27mDqjPQGhApNpj0a69S/zKhddlvJLVtcrghTnKvTvKd0gbs0hvM+xzUx0qdNKTo8PgJmasXcNsUDP2wXzMJOTSXl5YmxKF";
		byte[] bodyByteArray = s.getBytes(StandardCharsets.UTF_8);
		byte[] decryptedBody = ApiCryptoUtil.decryptData(properties, bodyByteArray, cryptoInfoBean);
		System.out.println(new String(decryptedBody));
		String str ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqyEVi6+eiUKiafoeDbAsLPyb/Tqz933fij7TT14X1I4Sn3NCCHNVmsz5rjFtYvWnx4muvazLr4PFjg7uT8QbHfBPq1QZeL1UzSrXHr2/3/rWzhxTrmv9k+ueC2ewFYSxQVGKmcSvTBDOz5E0daDICxL9vGemrHzLUNlSW5fLhteqoDpzuIAYyRXZCae26EYwNLWHvGav0S9jmV9NYuYmHxoj4Uziv3RJUKs/CmzUJNg63f25zRl4lGx3z2Ds3Cw86Vw58KQA72fNadKLSQ+EQqQBvAi1IDjvyZfTKYEEXWbd2PhaEuk+9nLJUsI1fHmy9viDN/rblvwV+AsSDHQHYwIDAQAB";

		String publicKeyToBase64 = RsaUtil.getPublicKeyToBase64(str);
		String  encrypted = "Ni35tz6ZcNuIwa5o+45OseOTg8Gy3pVMX0+C1xEssciruH0nfaaVH45GyyCX7Y/qzVYdj7SClmN7zKnlLV/N1Xw53OsYekcSzwQguW3k10anvRdF2vvgDf0apnPlaJKJS81G9wsh6UfBQy+RLZA4f/V3cThCP46WztAP94VFt1lldthdLxM75y2r5EMnsaA1nArpw0f8NxoxPi5mHZ91rbViK4LiDxBdn+WzQSoVlDCMyqflf6srwlInJ0PqrmH4L/38AaZGCTZLJKaVOzOW/XhnYSYWDEKoYU9BGF3aXjhbRPFzmN4E2x1a4RxN42/SVBOE9roCM9PzhzqL8U1Xxw==";
		byte[] decode = Base64.decode(encrypted);
		byte[] bytes = RsaUtil.decryptByPublicKey(publicKeyToBase64, decode);
		System.out.println(new String(bytes));

	}


	@Test
	public void lkjjlkll(){
		String s = AESUtil.decryptAES("OGTe6YJzZfZJbQ==", "ejI7mmK/zhOfGUJg7pOBGOacfbbdPMB/paY5m53l0zW8aJfqupjbolgk+Ky8G1F1", AESUtil.AES, AESUtil.AES);
		//s = java.util.Base64.getEncoder().encodeToString(s.getBytes());
		//s = "123456a";
		System.out.println(s);
		//s = "P1ZTr1wD1Bzb1wKVaaK8tp4EVG43puzFSC7LyxHYfkA9EMfIadgYYxZKc5b4ZIM7";
		String pri = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCHXuB7msVZNnhHmicajbxtiXOKcb/3E1D3ptKoaEMLZqmgWNwT1pnYX/Z34eyPlEYJyz3134pc5NW3DYCu6ZoaUNM4oC8dAurIAwvCcdUJIbTTSM+SLeW+vNWXMyzAr6WydGwSF2oxx9qUoWzvid9quNyl9fdkBfTe+KLjpJS5E61tpqyU7X97U+81v5vEqbbVIdB2NLud1LzfvGbGa/tu02PFwDowFCKJKHuQxUZCAiY7go0mUqb45nbww0ToZEY8ozJ+cPSjCwtSJB1tWyvjFb1jlTaBPFr2tnL89TkweMgMbssnOFPF9LyWfZmWcUtSyzfmaTc/mQgvvUNsgOrrAgMBAAECggEAIt5kclI2fTlTmBXz9poFrC1YjpOoP8tA8tmMr4ZTpq8mCEB7R7ziWeK2CMEv/vfRZppsdlXDPRRQnFslB2jyoc6p6Y8RM6bZtPVb5RUPK8/71OSPWvbpk8zuM4kiAOv9gU8Nu0c+ul3hRqGh/r2Dx+igJTuLIr+KCJkA1CRDjrxKD/25V+SQBQTFGZKj8Zyhi7TB4dfZYdFcCKOSgoQFek07rlf0BxsJkxZcI5lfPMXIBGTw47lE8xNQl23TBvNrUZlwO/SOLw504frw/azCk3sjznBNTayjiVYYGT2JykblCtMiVZyMO2wBVNL2zfemD/sCYhomN4af5lZGCMVo2QKBgQDil1r4Mon5QBJO2vOe1vINPLeHFOF7jhI+We5aJdxa/UgG15qddN3Cya1fcbVjA05TEIpHWDfSjUtna8I9eTId1FXm0ihU5DfKsJF4ZKPcIQIO5mbrW43QBhL/xnMQ8S8ezEHKpp0UUo7bERrSfserUvTlnpWTl18rBwkBKcB5VQKBgQCY8KfbbL90rUEzMSR/hoqW+5rOPyHav9gfKlIO5o31bFT/L9EberMYfGB6CeYvvCG2p6/ucdqUqVp+QhMdK0+LIZN/LrSoPmIfZea8ItleSFJS4j0VU7k7GMVGyiUenslQqUBR2eZNPHeBoq4bl703uwhYXCuH2XgYMmRVcZrTPwKBgDq6c++EWdJqUtRxV1dB6raXmBHAzbhSWFQkxuxCh9xlbTeoxgAtfBSyZOcp95NUFgbdcJmROdaunG6PtWZtdRnaIPGUYrOt+u/j17BWlUnGnFv0R5SUxCq4iFAWxVgMyuO2mQ7HJEtSIoiQ5FzOUz4KYELrWWVJhHcIecL9aU+JAoGAfPsM8S9pNbvvsBnUrbIQx1lNJIZK8CwePTmkXTDjiU/CFdfJlh8rBDN/07sLNK8VDeLyznSUHzBWFx9/5zJhil0h36kXUPXJ0cbAS7Ze08FeJQVkzWzhzf1sGXFP7DnuYdAuE7VxkmR7dKCwjzPtGQHqy+pmWFnK3lsvyvNPhpkCgYBRyKXJfFd5pAJSmpQX5XZxtU34aApji+YT49TU073mCZE0heH0XwdzWA2wWOZ2Lc9XnL19ackZjZtx1ZsTUvg+fPEl6i5zll7kvoq1cX7mSNGgXEO6LeBzvzyADniLEFUmZsHj20JPUCrt93j8l3nDFwjlqHENRDB7L2RQJwBCQg==";
		String pub = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh17ge5rFWTZ4R5onGo28bYlzinG/9xNQ96bSqGhDC2apoFjcE9aZ2F/2d+Hsj5RGCcs99d+KXOTVtw2ArumaGlDTOKAvHQLqyAMLwnHVCSG000jPki3lvrzVlzMswK+lsnRsEhdqMcfalKFs74nfarjcpfX3ZAX03vii46SUuROtbaaslO1/e1PvNb+bxKm21SHQdjS7ndS837xmxmv7btNjxcA6MBQiiSh7kMVGQgImO4KNJlKm+OZ28MNE6GRGPKMyfnD0owsLUiQdbVsr4xW9Y5U2gTxa9rZy/PU5MHjIDG7LJzhTxfS8ln2ZlnFLUss35mk3P5kIL71DbIDq6wIDAQAB";
		PrivateKey rsa = RsaKeyUtil.getPrivateKey(pri, "RSA");
		PublicKey publicKey = RsaKeyUtil.getPublicKey(pub, "RSA");
		String signatureMark = SignatureUtil.signatureMark(s,"SHA256withRSA", rsa);
		System.out.println(new String(signatureMark));
		//signatureMark  = "F3ovkT7Ju+O3g+EK7AWh1YUxzMgV7bsOf25WvRzXZDW7hd1rJSl382vWEZSO30HdVxPjcx/9u4kxcI4gtgHFMZ0QWGBjg0mIzBLJ9VSZNY36FWkqgJ/h9T+CpsuufhuJtftk4dtFTLQuQjI5kw8XQarK/8OkQY5JHhWIfFnum3NFfzdTf+EcAqeeYeH8ItEVT3ipEr+2ATitdNNntpg/H0b6Gksz1M7/IyhE0EEfL3llnVwLBccwEp2H3UP/sZRaNWP1wdTEYsvDMQ7MIBSv1WBX1rkrob9G26C6m8b4nyxuu+XEo4gzLUCJh85PnhwWtCwq/8amO27p1642uOBb0Q==";
		boolean rsa1 = SignatureUtil.verifyMark(s, "SHA256withRSA", publicKey, signatureMark);
		System.out.println(rsa1);
		//String ll = "G8qqMAl3Ux59D29pO6TQQUh950WNVhUqYBOd+oLKKUsDOZPgZmyrsFkHE2rQ8QTpuJn98wiqsGIVXmQnmIOMBnOsSxIYjJMMX6jfam4ZtNB/9ZFZojFC/Di8BHrt5nz2pYCOgjGB3Rpa7eCX0gjaGMy1MOvjVwVhB9Hk5YdlpgOEvAf99B8rxXlrLlqb7UJ5k12Eu16EA6+wP+7fTCZDJ6Vna0CDaJm4YrdaNaPtB8sVay9adBjgmiRSI9kNUc+KyxuOlAZX2Ft898WrOIepO7kAaoRUh0l+eze/b1SKuJ1xXs6dzIZ24JLUie6B9Tfg3Sly9acyABZiPPwKqcm9uQ==";
		//System.out.println(signatureMark.equals(ll));
	}

	@Test
	public void lkj(){
		byte[] bytes = {34, 52, 56};
		System.out.println(new String(bytes));


	}
	@Test
	public void lkjjlk(){

		List<WalletLogEntity> recordsLog = new ArrayList<>();

		Map<Long, List<WalletLogEntity>> recordMap = recordsLog.stream().collect(Collectors.groupingBy(WalletLogEntity::getBrid));
		List<WalletLogEntity> walletLogEntities = recordMap.get(1L);
		System.out.println(JSON.toJSONString(walletLogEntities));
	}


	@Test
	public void lkjk(){
		 AntPathMatcher antPathMatcher = new AntPathMatcher();
		boolean brIds = antPathMatcher.match("/brid/api/brIds", "/brid/api/brIds");
		System.out.println(brIds);
	}


}
