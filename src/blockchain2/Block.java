package blockchain2;
import java.util.*;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.Date;
public class Block implements Serializable {
     private ArrayList<Message> messageList=new ArrayList<Message>();
     private String hash;
     private String prevHash="";
     private String timeStamp;
     private int index;
     
     public Block(Message...mesgs)
     {
    	 this.timeStamp=new Date().toString();
    	 for (Message mesg:mesgs)
    		 addMessage(mesg);
    	 
     }
     public void setBlockIndex(int index)
     {
    	 this.index=index;
     }
     
     public void CrackBlock()
     {
    	 this.timeStamp=" ";
     }
     
     public ArrayList<Message> getMessageList()
     {
    	 return this.messageList;
     }
     
     public String getHash()
     {
    	 return this.hash;
     }
     
     public String getPrevHash()
     {
    	 return this.prevHash;
     }
     
     public String getTimeStamp()
     {
    	 return this.timeStamp;
     }
     
     public void addMessage(Message mesg)
     {
    	 
    	 if (messageList.size()>0)
    	 {
    		 Message mesgLast=messageList.get(messageList.size()-1);
    		 mesg.link(mesgLast);
    	 }
    	 mesg.seal();
    	 
    	 try 
    	 {
    		 Boolean isValid=mesg.validate();
    		 messageList.add(mesg);
    	 }
    	 catch(MyException e)
    	 {
    		 System.out.println(e);
    	 }
    	 
    	 
    	 
     }
     
     public int getBlockIndex()
     {
    	 return this.index;
     }
     
     
     public void link(Block prevBlock)
     {
    	 prevHash=prevBlock.hash;
     }
     
     public void seal()
     {
          hash=StringUtil.applySha256(prevHash
					        		  +timeStamp
					        		  +messageList.get(messageList.size()-1).getHash());
          
     }
     
     public String hashVerify()
     {
    	 return StringUtil.applySha256(prevHash
       		  +timeStamp
       		  +messageList.get(messageList.size()-1).getHash());
     }
     
     public Boolean validate() throws MyException
     {
    	 for (int i=0;i<messageList.size();i++)
    	 {
    		 
    		 Message item=messageList.get(i);
    		 //item.CrackMessage();测试消息异常
    		 /*
    		 if(!item.validate())
    		 {
    			 System.out.printf("区块中的%号消息被篡改\n",i+1);
    			 System.out.printf("消息payer:%s\n",item.getTransaction().getPayer());
    			 System.out.printf("消息receiver:%s\n",item.getTransaction().getReceiver());
    			 System.out.printf("消息金额:%sbits\n",item.getTransaction().getContent());
    			 System.out.printf("消息timestamp:%s\n",item.getTransaction().getTimeStamp());
    			 return false;
    		 }
    		 */
    		 try
    		 {
    			 Boolean isValid=item.validate();
    		 }
    		 catch(MyException e)
    		 {
    			 System.out.println(e);
    			 return false;
    		 }
    		 if(!hash.equals(hashVerify()))
    			 throw new MyException("区块被修改");
    		 if(i>0)
    		 {
    			 Message prevItem=messageList.get(i-1);
    			 if(!item.getPrevHash().equals(prevItem.getHash()))
    			 {
    				 /*
    				 System.out.printf("%d号消息链被接错误\n",i+1);
    				 return false;
    				 */
    				 throw new MyException((i+1)+"号消息链被接错误");
    			 }
    		 }
    		 
    	 }
    	 
    	 return true;
     }
     
}
