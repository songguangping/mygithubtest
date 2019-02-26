package blockchain2;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import java.security.interfaces.RSAPrivateKey;  
import java.security.interfaces.RSAPublicKey;  
import java.security.spec.InvalidKeySpecException;  
import java.security.spec.PKCS8EncodedKeySpec;  
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import java.util.Base64;

public class RSAEncrypt 
{
    
	/**
	 * ���������Կ��
	 */
	public static void genKeyPair(String filePath)
	{
		
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//��ʼ����Կ����������Կ��С96-1024
		keyPairGen.initialize(1024, new SecureRandom());
		//����һ����Կ��������keyPair��
		KeyPair keyPair = keyPairGen.generateKeyPair();
		//�õ���Կ
		RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
		//�õ�˽Կ 
		RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
		
		
		try {
			Base64.Encoder base64Encoder = Base64.getEncoder();
			String publicKeyString = base64Encoder.encodeToString(publicKey.getEncoded());
			String privateKeyString = base64Encoder.encodeToString(privateKey.getEncoded());
			//just for testing 
			System.out.println("publickey");
			System.out.println(publicKeyString);
			System.out.println("privatekey");
			System.out.println(privateKeyString);
		    //
			FileWriter pubFw = new FileWriter(filePath + "/publicKey.keystore");
			FileWriter priFw = new FileWriter(filePath + "/privateKey.keystore");
			
			pubFw.write(publicKeyString);
			priFw.write(privateKeyString);
			pubFw.flush();
			pubFw.close();
			priFw.flush();
			priFw.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
    
	/**
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String loadPublicKeyByFile(String filePath) throws Exception
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath + "/publicKey.keystore"));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while((readLine = br.readLine()) !=null)
			{
				sb.append(readLine);
			}
			br.close();
			//for testing
			//System.out.println("the read from file");
			//System.out.println(sb.toString());
			return sb.toString();
		
	        }catch (IOException e) {
	        	throw new Exception("��Կ���ݶ�ȡ����");
	        }catch (NullPointerException e) {
	        	throw new Exception("��Կ������Ϊ��");
	        }
	}
	/**
	 * 
	 * @param publicKeyStr
	 * @return
	 * @throws Exception
	 */
	public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr) throws Exception {
		try {
			Base64.Decoder base64Decoder = Base64.getDecoder();
			byte[] buffer = base64Decoder.decode(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("�޴��㷨");
		} catch (InvalidKeySpecException e) {
			throw new Exception("��Կ�Ƿ�");
		} catch (NullPointerException e) {
			throw new Exception("��Կ����Ϊ��");
		}
	}
	
    /**
     * 
     * @param filePath
     * @return
     * @throws Exception
     */
	public static String loadPrivateKeyByFile(String filePath) throws Exception
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath + "/privateKey.keystore"));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while((readLine = br.readLine()) !=null)
			{
				sb.append(readLine);
			}
			br.close();
			//for testing
			//System.out.println("the read from file");
			//System.out.println(sb.toString());
			return sb.toString();
		
	        }catch (IOException e) {
	        	throw new Exception("˽Կ���ݶ�ȡ����");
	        }catch (NullPointerException e) {
	        	throw new Exception("˽Կ����������Ϊ��");
	        }
	}
	
	/**
	 * 
	 * @param privateKeyStr
	 * @return
	 * @throws Exception
	 */
	public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr) throws Exception {
		try {
			Base64.Decoder base64Decoder = Base64.getDecoder();
			byte[] buffer = base64Decoder.decode(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("�޴��㷨");
		} catch (InvalidKeySpecException e) {
			throw new Exception("��Կ�Ƿ�");
		} catch (NullPointerException e) {
			throw new Exception("��Կ����Ϊ��");
		}
	}
	
/**
 * 
 * @param privateKey
 * @param plainTextData
 * @return
 * @throws Exception
 */
	public static byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData) throws Exception {
		if(privateKey == null) {
			throw new Exception("����˽ԿΪ�գ�������");
		}
		byte[] output=null;
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			output = cipher.doFinal(plainTextData);
			return output;
			
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
		return output;
	}
	
	/**
	 * 
	 * @param publicKey
	 * @param plainTextData
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
		if(publicKey == null) {
			throw new Exception("���ܹ�ԿΪ�գ�������");
		}
		
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
			
		}catch (NoSuchAlgorithmException e) {
			throw new Exception("�޴˼����㷨");
		}catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		}catch (InvalidKeyException e) {
			throw new Exception("���ܹ�Կ�Ƿ�");
		}catch (IllegalBlockSizeException e) {
			throw new Exception("���ĳ��ȷǷ�");
		}catch (BadPaddingException e) {
			throw new Exception("������������");
		}
		
	}
	
	/**
	 * 
	 * @param privateKey
	 * @param cipherData
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception
	{
		if (privateKey == null)
		{
			throw new Exception("");
		}
		
		Cipher cipher = null;
		try
		{
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e)
		{
			throw new Exception("�޴˽����㷨");
		}
			
	}
	
	
	
	public static byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData) throws Exception
	{
		if (publicKey == null)
		{
			throw new Exception("");
		}
		
		
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		    } catch(NoSuchAlgorithmException e) {
		    	throw new Exception("�޴˽����㷨");
		    }
	}
	
	public static String byteToBase64(byte[] cipherData) {
		String cipherDataBase64 = Base64.getEncoder().encodeToString(cipherData);
		return cipherDataBase64;
	}
	
	public static byte[] base64ToByte(String cipherData) {
		byte[] cipher = Base64.getDecoder().decode(cipherData);
		return cipher;
	}
	
	
	
}
