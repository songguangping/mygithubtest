package blockchain2;

import java.io.File;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class SecretKey {
	String pubKeyStr;
	String privKeyStr;
	RSAPublicKey pk;
	RSAPrivateKey pv;
	
	public SecretKey()
	{
		String currentDir = System.getProperty("user.dir");
		File publicKeyFile = new File(currentDir + "\\publicKey.keystore");
		File privateKeyFile = new File(currentDir + "\\privateKey.keystore");
		
		if( !publicKeyFile.exists() || !privateKeyFile.exists())
		{
			System.out.println("公私钥文件不存在，创建");
			RSAEncrypt.genKeyPair(".");
			//RSAEncrypt.genKeyPair(curentDir);
		}
		try
		{
			System.out.println("公私钥文件存在");
			pubKeyStr = RSAEncrypt.loadPublicKeyByFile(".");
			privKeyStr = RSAEncrypt.loadPrivateKeyByFile(".");
			pk = RSAEncrypt.loadPublicKeyByStr(pubKeyStr);
			pv = RSAEncrypt.loadPrivateKeyByStr(privKeyStr);
				
		}catch (Exception e) {
			;
		}
	}

}
