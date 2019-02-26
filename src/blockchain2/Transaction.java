package blockchain2;
import java.util.Date;
import java.io.Serializable;
public class Transaction implements Serializable {
	private String payer;
	private String receiver;
	private String content;
	private String timeStamp;
	private String sign;
	
	public Transaction(String payer, String receiver, String content)
	{
		this.payer = payer;
		this.receiver = receiver;
		this.content = content;
		this.timeStamp = new Date().toString();
		this.sign=signature();
	}
	
	public String getPayer()
	{
		return payer;
	}
	
	public String getReceiver()
	{
		return receiver;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public String getTimeStamp()
	{
		return timeStamp;
	}
    public String getSign()
    {
    	return sign;
    }
	public String signature()
	{
		SecretKey sk=new SecretKey();
		String toBeSign=payer+receiver+content+timeStamp;
		return RSASignature.sign(toBeSign,sk.privKeyStr,"UTF-8");
	}
	public Boolean validate()
	{
		
		String toBeCheck=this.payer
				+this.receiver
				+this.content
				+this.timeStamp;
		SecretKey sk=new SecretKey();
		if(!RSASignature.doCheck(toBeCheck, this.sign, sk.pubKeyStr, "UTF-8"))
		{
			System.out.println("交易签名有误\n");
		    return false;	
		}
		return true;
	}
}
