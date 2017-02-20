package project1;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rob Vary
 */
public class CreatorProcess implements Runnable
{
    //our list of consumer threads
    ArrayList<ConsumerProcess> consumers;
    
    //number of nodes added so far
    int nodeCount = 0;

    //constructor
    public CreatorProcess(ArrayList<ConsumerProcess> consumers) 
    {
        this.consumers = consumers;
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
            //note start time
            double startTime = System.currentTimeMillis();
            
            //run for up to 30 seconds
            while((System.currentTimeMillis() - startTime) < 30000)
            {
                //create and insert no more than 20 nodes
                if(nodeCount < 20)
                {
                    Random rand = new Random();
                    Thread.sleep(rand.nextInt(2000));
                    MinHeap.getInstance().insert(new ProcessNode(getNewProcessID(), getRandomPriority(), getRandomTimeSlice()));
                    nodeCount++;
                }
            }
            
            //once we exit the while loop, stop the consumer processes, too
            for(ConsumerProcess consumer : consumers)
            {
                consumer.stop();
            }
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Project1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}