package blockchain2;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
public class BlockChain implements Serializable {
     private ArrayList<Block> blockList=new ArrayList<Block>();
     private int blockCounter;
     public BlockChain()
     {	
        ; 
    	 
     }
     /*
      *              返回区块数计数器
      */
     public int getBlockCounter()
     {
    	 return this.blockCounter;
     }
     
     public void addBlock(Block block)
     {
    	 if (blockList.size()>0)
    	 {
    		Block lastBlock=blockList.get(blockList.size()-1);
    		block.link(lastBlock);
    	 }
    	 block.seal();
    	 try
    	 {
    		 block.validate();
        	 blockList.add(block);
        	 this.blockCounter++;
    	 }
    	 catch(MyException e)
    	 {
    		 System.out.println(e);
    	 }
    	
     }
     
     public void blockListAll()
 	{
 		for (Block block: blockList)
 		{
 			int index=block.getBlockIndex();
 			
 			System.out.printf("第%d个区块\n",index);
 			System.out.println(block);
 			
 		}
 		System.out.printf("一共有%d个区块\n", blockCounter);
 	}

     
     public ArrayList<Block> getBlockList()
     {
    	 return this.blockList;
     }
     public Boolean readFromFile(String fileName)
 	{
 		try
 		{
 			FileInputStream inFile = new FileInputStream(fileName);
 			ObjectInputStream ois = new ObjectInputStream(inFile);
 			BlockChain bc = (BlockChain)ois.readObject();
 			this.blockList = bc.blockList;
 			this.blockCounter=bc.blockCounter;
 			//this.index = bc.index;
 			ois.close();		
 		}catch (IOException e) {
 			e.printStackTrace();
 			return false;
 		}catch (ClassNotFoundException e) {
 			e.printStackTrace();
 			return false;
 		}
 		return true;
 	}	

     public Boolean saveToFile(String fileName)
 	{
 		try
 		{
 			FileOutputStream outFile = new FileOutputStream(fileName);
 			ObjectOutputStream oos = new ObjectOutputStream(outFile);
 			oos.writeObject(this);
 			oos.close();
 		}catch (IOException e) {
 			e.printStackTrace();
 			return false;
 		}
 		return true;
 	}

     
     public Boolean validate() throws MyException
     {
    	 for (int i=0;i<blockList.size();i++)
    	 {
    		 Block item=blockList.get(i);
    		 /*
    		 if(!item.validate())
    		 {
    			 System.out.printf("%d区块校验错误\n",i+1);
    			 return false;
    		 }
    		 */
    		 //item.CrackBlock();
    		 try
    		 {
    			 Boolean isValid=item.validate();
    		 }
    		 catch(MyException e)
    		 {
    			 
    		     System.out.println(i+"号"+e.getMessage());
    		     return false;
    		 }
    		 if(i>0)
    		 {
    			 Block prevItem=blockList.get(i-1);
    			 if(!item.getPrevHash().equals(prevItem.getHash()))
    			 {
    				 /*
    				 System.out.printf("%d号区块连接错误",i+1);
    				 return false;
    				 */
    				 throw new MyException((i+1)+"号区块连接错误");
    			 }
    		 }
    		 
    	 }
    	 
    	 return true;
     }
}
