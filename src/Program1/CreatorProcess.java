package Program1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rob Vary
 */
public class CreatorProcess implements Runnable
{
    //number of nodes added so far
    int nodeCount = 0;
    
    //stop condition
    public boolean stopped = false;

    //constructor
    public CreatorProcess() 
    {
        
    }

    //randomly acquire an unused process ID between 100 and 1000
    private int getNewProcessID()
    {
        Random rand = new Random();

        int num = rand.nextInt(1000) + 1;

        if(getIDs().isEmpty())
        {
            return num;
        }
        else
        {
            while(getIDs().contains(num))
            {
                num = rand.nextInt(1000) + 100;
            }
        }

        return num;
    }

    //randomly acquire a priority between 0 and 3
    private int getRandomPriority()
    {
        Random rand = new Random();
        return rand.nextInt(4);
    }

    //randomly acquire a time slice between 1000 and 5000 milliseconds
    private int getRandomTimeSlice()
    {
        Random rand = new Random();
        return rand.nextInt(5000) + 1000;
    }

    //return a list of all the already-used process IDs
    private ArrayList<Integer> getIDs()
    {
        ArrayList<Integer> idList = new ArrayList<Integer>();
        for (Object node : MinHeap.getInstance().getItems())
        {
            int curID = ((ProcessNode)node).processID;
            idList.add(curID);
        }

        return idList;
    }

    @Override
    public void run() 
    {
        try 
        {
            while(!stopped)
            {
                //create the first node very fast
                if(nodeCount == 0)
                {
                    ProcessNode newOne = new ProcessNode(getNewProcessID(), getRandomPriority(), getRandomTimeSlice());
                    MinHeap.getInstance().insert(newOne);
                    nodeCount++;                    
                    
                    String curTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
                    System.out.println("Creator process added process with ID " + newOne.processID + " and priority " + newOne.priority + " and time slice " + newOne.timeSlice + " at " + curTime + ".");
                }
                //create and insert no more than 20 nodes
                else if(nodeCount < 20)
                {
                    Thread.sleep(2000);
                    ProcessNode newOne = new ProcessNode(getNewProcessID(), getRandomPriority(), getRandomTimeSlice());
                    MinHeap.getInstance().insert(newOne);
                    nodeCount++;                   
                    
                    String curTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
                    System.out.println("Creator process added process with ID " + newOne.processID + " and priority " + newOne.priority + " and time slice " + newOne.timeSlice + " at " + curTime + ".");
                }
                else
                {
                    stopped = true;
                }
            }
            System.out.println("Creator loop finished!!!");
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Program1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}