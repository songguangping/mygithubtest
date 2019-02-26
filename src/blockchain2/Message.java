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
			//System.out.println("����ǩ������");
			throw new MyException("����ǩ������");
		}
		
		if (!payLoadHash.equals(genPayLoadHash())) 
		{
			/*
			System.out.println("���׵����ݺ�ʱ�䱻�޸�");
			//System.out.printf("�����е�%����Ϣ���۸�\n",i+1);
			System.out.printf("��Ϣpayer:%s\n",data.getPayer());
			System.out.printf("��Ϣreceiver:%s\n",data.getReceiver());
			System.out.printf("��Ϣ���:%sbits\n",data.getContent());
			System.out.printf("��Ϣtimestamp:%s\n",data.getTimeStamp());
			return false;
			*/
			throw new MyException("���׵����ݺ�ʱ�䱻�޸�");
		}
		if (!hash.equals(StringUtil.applySha256(prevHash + payLoadHash)))
		{
			/*
			System.out.println("���׵�hash���ӱ��޸�");
		    return false;
		    */
			throw new MyException("���׵�hash���ӱ��޸�");
		}
		
		return true;
	}

}
