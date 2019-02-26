package blockchain2;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.Signature;
import java.util.Base64;

public class RSASignature {
	/**
	 * 
	 * @param content
	 * @param privateKey
	 * @param encode
	 * @return
	 */
	public static String sign(String content, String privateKey, String encode)
	{
		try
		{
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec
					      (Base64.getDecoder().decode(privateKey));
			
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			
			Signature signature = Signature.getInstance("SHA1withRSA");
			signature.initSign(priKey);
			signature.update(content.getBytes(encode));
			
			byte[] signed = signature.sign();
			
			return Base64.getEncoder().encodeToString(signed);
		} 
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean doCheck(String content,String sign,String publicKey,String encode)
	{
		try
		{
			KeyFactory keyFactory=KeyFactory.getInstance("RSA");
			byte[] encodedKey=Base64.getDecoder().decode(publicKey);
			PublicKey pubKey=keyFactory.
					         generatePublic(new X509EncodedKeySpec(encodedKey));
			
			Signature signature=Signature.getInstance("SHA1withRSA");
			
			signature.initVerify(pubKey);
			signature.update(content.getBytes(encode));
			
			boolean bverify=signature.verify(Base64.getDecoder().decode(sign));
			return bverify;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return false;
	}

	
	
}
