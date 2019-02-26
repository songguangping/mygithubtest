package blockchain2;

import java.util.Date;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.Scanner;

public class IOProcess implements Consts {
	private BlockChain blockChain; //= new BlockChain();
	public IOProcess(BlockChain blockChain)
	{
		this.blockChain = blockChain;
	}
	/**
	 * �˻�����ҵ��
	 *          ��Ӷ�ʽ�����Ϣ��һ��Ʊ�ݣ���������˻�
	 */
	public void addNote()
	{
		System.out.println("���潫�����˻�����ҵ��");
		int quit=0;
		int count=1;
		//int newInBlock=0;
		Block b = new Block();
		System.out.printf("�������%d��Ʊ��\n", blockChain.getBlockCounter()+1);
		while(quit!=1)
		{
			System.out.printf("�������%d�ʽ���\n",count);
			Scanner sc = new Scanner(System.in);
			System.out.println("������payer:");
			String payer = sc.next();
			System.out.println("������receiver:");
			String receiver = sc.next();
			System.out.println("����������:");
			String content = sc.next();
			Transaction t = new Transaction(payer,receiver,content);
			Message m = new Message(t);
			b.addMessage(m);
			
			System.out.println("¼����һ��(Y/N):");
			String tag=sc.next();
			if(tag.equals("N") || tag.equals("n"))
                 quit=1;
            count++;
		}
		b.setBlockIndex(blockChain.getBlockCounter());
		blockChain.addBlock(b);
		System.out.printf("��%d��Ʊ��������\n", blockChain.getBlockCounter());
		//newInBlock++;
		
	}
	/*
	 * Ʊ�ݲ�ѯҵ��
	 *           ����Ʊ�ݱ�ţ����Ʊ���ڽ�����Ϣ
	 */
	public void queryNote() throws InterruptedException
	{   
		if(blockChain.getBlockCounter()>0)
		{   System.out.println("���潫����Ʊ�ݲ�ѯҵ��");
		    int quit=0;
		    while(quit!=1)
		    {
		    	System.out.println("һ����"+blockChain.getBlockCounter()+"��Ʊ��");
				System.out.println("������Ʊ�ݱ�ţ�");
				Scanner sc=new Scanner(System.in);
				String orderTemp=sc.next();
				while(orderTemp.compareTo("0")<0 || orderTemp.compareTo("9")>0)
				{
					System.out.println("����������");
					orderTemp=sc.next();
				}
				int orderBlock=Integer.parseInt(orderTemp);
				
				while(orderBlock<=0 || orderBlock>blockChain.getBlockCounter())
				{
					System.out.println("�Ҳ�������ѯ��Ʊ��\n����������Ʊ�ݱ�ţ�");
					String RepeatOrderTemp=sc.next();
					while(RepeatOrderTemp.compareTo("0")<0 || 
						  RepeatOrderTemp.compareTo("9")>0)
					{
						System.out.println("����������");
						RepeatOrderTemp=sc.next();
					}
					orderBlock=Integer.parseInt(RepeatOrderTemp);
				}
				
				
				Block block=blockChain.getBlockList().get(orderBlock-1);//get the block accord to orderBlock
				Date timeStamp=new Date(block.getTimeStamp());
				int blockIndex=block.getBlockIndex();
				System.out.printf("��%d��Ʊ��\n",orderBlock);
				System.out.printf("Ʊ�ݱ�ţ�%d\n",blockIndex);
				System.out.printf("Ʊ�ݽ�����Ŀ���ʣ���%d\n",block
						                          .getMessageList()
						                          .size());
				System.out.println("Ʊ�ݴ���ʱ�䣨��/��/��/ʱ��/���/����"+timeStamp);
				System.out.println("�����ǽ����б�");
				int counter=1;
				for (Message mesg: block.getMessageList())
				{
					System.out.printf("��%d�ʽ���\n",counter);
					Transaction t = mesg.getTransaction();
					Thread.sleep(Consts.timeGap);
					System.out.printf("Payer: %s\n",t.getPayer());
					Thread.sleep(Consts.timeGap);
					System.out.printf("Receiver: %s\n",t.getReceiver());
					Thread.sleep(Consts.timeGap);
					System.out.println("Transaction Date: "+t.getTimeStamp());
					Thread.sleep(Consts.timeGap);
					System.out.println("Transaction Amount: "+t.getContent()+" bits");
					System.out.println("------------------");
					
					counter++;
				}
				System.out.println("�Ƿ������ѯ(Y/N):");
				String continueQuery=sc.next();
				if(continueQuery.equals("N") || continueQuery.equals("n"))
				   quit=1;
		    }
			
		}
		else
		{
			System.out.println("Ʊ���˻�Ϊ��");
			return;
		}
			
			
		
		/*
		for(Block block: blockChain.getBlockList())
		{
			int counter=0;
			System.out.printf("��%d������\n",block.getBlockIndex());
			for (Message mesg: block.getMessageList())
			{
				Transaction t = mesg.getTransaction();
				System.out.println("payer:");
				System.out.println(t.getPayer());
				System.out.println("receiver:");
				System.out.println(t.getReceiver());
				System.out.println("Date:");
				System.out.println(t.getTimeStamp());
				System.out.println("------------------");
				counter++;
			}
			System.out.printf("��Ϣ������%d\n", counter);
		};
		*/
		
	}
	/*
	 * �˳�ϵͳ
	 *          ��ʾÿ��Ʊ����Ϣ
	 *          ������
	 *          �ϸ�Ʊ�ݼ�¼���ݿ�        
	 *          �˳�
	 */
	public void systemExit()
	{
		blockChain.blockListAll();
		try
		{
			Boolean isValid=blockChain.validate();
			if(isValid)
			{
			    blockChain.saveToFile("blockchain.dat");
			    System.out.printf("���ݿ⹲��%d��Ʊ��\n",blockChain.getBlockCounter()); 
			    //System.out.println(newInBlock);
			}
			else
				System.out.println("�ļ������Ʊ��д����");
		}
		catch(MyException e)
		{
			System.out.println(e);
		}
		System.exit(0);
	}

}
