package blockchain2;
import java.util.Scanner;

public class ConsoleMenu {
	public ConsoleMenu()
	{
		;
	}
	public void menuProcess(BlockChain blockChain)
	{
		
		Scanner sc= new Scanner(System.in);
		IOProcess oper = new IOProcess(blockChain);
		int choose =0;
		while(true)
		{
			System.out.println("---------票据防伪系统--------------------------");
			System.out.println("1. 票据新增  2.票据查询  3.退出");
			choose = sc.nextInt();
			
			switch(choose)
			{
			case 1:
				oper.addNote();
				break;
			case 2:
				try 
				{
					oper.queryNote();	
				}
				catch(InterruptedException e)
				{
					System.out.println(e);
				}
				
				break;
//			case 3:
//				oper.queryNote();
//				break;
				
			case 3:
				oper.systemExit();
			    break;
			}
			
		}
	}

}
