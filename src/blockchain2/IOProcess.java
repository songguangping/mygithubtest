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
	 * 账户存入业务
	 *          添加多笔交易信息至一份票据，打包存入账户
	 */
	public void addNote()
	{
		System.out.println("下面将进行账户存入业务");
		int quit=0;
		int count=1;
		//int newInBlock=0;
		Block b = new Block();
		System.out.printf("将打包第%d份票据\n", blockChain.getBlockCounter()+1);
		while(quit!=1)
		{
			System.out.printf("将处理第%d笔交易\n",count);
			Scanner sc = new Scanner(System.in);
			System.out.println("请输入payer:");
			String payer = sc.next();
			System.out.println("请输入receiver:");
			String receiver = sc.next();
			System.out.println("请输入内容:");
			String content = sc.next();
			Transaction t = new Transaction(payer,receiver,content);
			Message m = new Message(t);
			b.addMessage(m);
			
			System.out.println("录入下一条(Y/N):");
			String tag=sc.next();
			if(tag.equals("N") || tag.equals("n"))
                 quit=1;
            count++;
		}
		b.setBlockIndex(blockChain.getBlockCounter());
		blockChain.addBlock(b);
		System.out.printf("第%d份票据已入账\n", blockChain.getBlockCounter());
		//newInBlock++;
		
	}
	/*
	 * 票据查询业务
	 *           输入票据编号，输出票据内交易信息
	 */
	public void queryNote() throws InterruptedException
	{   
		if(blockChain.getBlockCounter()>0)
		{   System.out.println("下面将进行票据查询业务");
		    int quit=0;
		    while(quit!=1)
		    {
		    	System.out.println("一共有"+blockChain.getBlockCounter()+"份票据");
				System.out.println("请输入票据编号：");
				Scanner sc=new Scanner(System.in);
				String orderTemp=sc.next();
				while(orderTemp.compareTo("0")<0 || orderTemp.compareTo("9")>0)
				{
					System.out.println("请输入数字");
					orderTemp=sc.next();
				}
				int orderBlock=Integer.parseInt(orderTemp);
				
				while(orderBlock<=0 || orderBlock>blockChain.getBlockCounter())
				{
					System.out.println("找不到待查询的票据\n请重新输入票据编号：");
					String RepeatOrderTemp=sc.next();
					while(RepeatOrderTemp.compareTo("0")<0 || 
						  RepeatOrderTemp.compareTo("9")>0)
					{
						System.out.println("请输入数字");
						RepeatOrderTemp=sc.next();
					}
					orderBlock=Integer.parseInt(RepeatOrderTemp);
				}
				
				
				Block block=blockChain.getBlockList().get(orderBlock-1);//get the block accord to orderBlock
				Date timeStamp=new Date(block.getTimeStamp());
				int blockIndex=block.getBlockIndex();
				System.out.printf("第%d份票据\n",orderBlock);
				System.out.printf("票据编号：%d\n",blockIndex);
				System.out.printf("票据交易数目（笔）：%d\n",block
						                          .getMessageList()
						                          .size());
				System.out.println("票据创建时间（日/月/天/时间/年份/）："+timeStamp);
				System.out.println("以下是交易列表");
				int counter=1;
				for (Message mesg: block.getMessageList())
				{
					System.out.printf("第%d笔交易\n",counter);
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
				System.out.println("是否继续查询(Y/N):");
				String continueQuery=sc.next();
				if(continueQuery.equals("N") || continueQuery.equals("n"))
				   quit=1;
		    }
			
		}
		else
		{
			System.out.println("票据账户为空");
			return;
		}
			
			
		
		/*
		for(Block block: blockChain.getBlockList())
		{
			int counter=0;
			System.out.printf("第%d个区块\n",block.getBlockIndex());
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
			System.out.printf("消息总数：%d\n", counter);
		};
		*/
		
	}
	/*
	 * 退出系统
	 *          显示每份票据信息
	 *          链检验
	 *          合格票据记录数据库        
	 *          退出
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
			    System.out.printf("数据库共有%d份票据\n",blockChain.getBlockCounter()); 
			    //System.out.println(newInBlock);
			}
			else
				System.out.println("文件因错误票据写不进");
		}
		catch(MyException e)
		{
			System.out.println(e);
		}
		System.exit(0);
	}

}
