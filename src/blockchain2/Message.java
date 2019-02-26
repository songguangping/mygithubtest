package blockchain2;
import java.util.Date;
import java.io.Serializable;
public class Message implements Serializable {
	private String hash;
	private String prevHash = "";
	private String timeStamp;
	private Transaction data;
	private String payLoadHash;
	
	public Message(Transaction data)
	{
		this.data = data;
		this.timeStamp = new Date().toString();
		this.payLoadHash = genPayLoadHash();
		
	}
	
	public void CrackMessage()
	{
		this.timeStamp=" ";
	}
	
	private String genPayLoadHash()
	{
		String temp;
		temp = timeStamp + 
				data.getPayer()+
				data.getReceiver()+
				data.getContent()+
				data.getTimeStamp()+
				data.getSign();

		return StringUtil.applySha256(temp);
	}
	
	public String getPayLoadHash()
	{
		return this.payLoadHash;
	}
	
	public void link(Message prevMessage)
	{
		prevHash = prevMessage.hash;
	}
	
	public void seal()
	{
		hash = StringUtil.applySha256(prevHash + payLoadHash);
	}
	
	public String getHash()
	{
		return hash;
	}
	
	public String getPrevHash()
	{
		return prevHash;
	}
	
	public String getTimeStamp()
	{
		return timeStamp;
	}
	
	public Transaction getTransaction()
	{
		return data;
	}
	
	public Boolean validate() throws MyException
	{
		
		if(!data.validate())
		{
			//System.out.println("交易签名有误");
			throw new MyException("交易签名有误");
		}
		
		if (!payLoadHash.equals(genPayLoadHash())) 
		{
			/*
			System.out.println("交易的数据和时间被修改");
			//System.out.printf("区块中的%号消息被篡改\n",i+1);
			System.out.printf("消息payer:%s\n",data.getPayer());
			System.out.printf("消息receiver:%s\n",data.getReceiver());
			System.out.printf("消息金额:%sbits\n",data.getContent());
			System.out.printf("消息timestamp:%s\n",data.getTimeStamp());
			return false;
			*/
			throw new MyException("交易的数据和时间被修改");
		}
		if (!hash.equals(StringUtil.applySha256(prevHash + payLoadHash)))
		{
			/*
			System.out.println("交易的hash链接被修改");
		    return false;
		    */
			throw new MyException("交易的hash链接被修改");
		}
		
		return true;
	}

}
