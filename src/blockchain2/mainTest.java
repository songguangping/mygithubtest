package blockchain2;

import java.io.File;
import java.io.FileInputStream;

public class mainTest {
     public static void main(String[] args)
     {
    	 BlockChain bck=new BlockChain();
    	 
    	 /*
    	 Transaction t1=new Transaction("a1","a2","10bits");
    	 
    	 System.out.printf("payer:%s\n",t1.getPayer());
    	 System.out.printf("receiver:%s\n",t1.getReceiver());
    	 System.out.printf("content:%s\n",t1.getContent());
    	 System.out.printf("timestamp:%s\n",t1.getTimeStamp());
    	 
    	 Transaction t2=new Transaction("b1","b2","4bits");
    	 Transaction t3=new Transaction("c1","c2","13bits");
    	 Transaction t4=new Transaction("d1","d2","14bits");
    	 Transaction t5=new Transaction("e1","e2","11bits");
    	 
    	 Message m1=new Message(t1);
    	 Message m2=new Message(t2);
    	 Message m3=new Message(t3);
    	 Message m4=new Message(t4);
    	 Message m5=new Message(t5);
    	 
    	
    	 
    	 System.out.printf("m1 hash:%s\n", m1.getHash());
    	 System.out.printf("m1 prevhash:%s\n", m1.getPrevHash());
    	 System.out.printf("m2 hash:%s\n", m2.getHash());
    	 System.out.printf("m2 prevhash:%s\n", m2.getPrevHash());
    	 
    	 Block b1=new Block(m1,m2);
    	 b1.setBlockIndex(bck.getBlockCounter()+1);
    	 bck.addBlock(b1);
    	 
    	 Block b2=new Block(m3,m4);
    	 b2.setBlockIndex(bck.getBlockCounter()+1);
    	 bck.addBlock(b2);
    	 
    	 Block b3=new Block(m5);
    	 b3.setBlockIndex(bck.getBlockCounter()+1);
    	 bck.addBlock(b3);
    	 
    	 try
    	 {
    		 boolean isValid=bck.validate();
    	 }
    	 catch(MyException e)
    	 {
    		 System.out.println(e);
    	 }
    	 
    	
    	 //文件倒过头读出来blockchain 有问题
    	 /*
    	 bck.saveToFile("blockchain.dat");
    	 BlockChain bChain2=new BlockChain();
    	 bChain2.readFromFile("blockchain.dat");
    	 bChain2.validate();
    	 bChain2.blockListAll();
    	  */
    	 
    	 
    	 /*
    	  * 从数据库中读取票据记录，没有则自动创建文件
    	  * */
    	 File blockChainFile=new File(".\\blockchain.dat");
    	
    	 if (blockChainFile.exists())
    		 bck.readFromFile("blockchain.dat");
    	 
    	 ConsoleMenu menu=new ConsoleMenu();
    	 menu.menuProcess(bck);
    	 
    	 
     }
}
