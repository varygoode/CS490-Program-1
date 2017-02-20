package project1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Object that acts as a thread consuming resources
 * @author Rob Vary
 */
public class ConsumerProcess implements Runnable
{
    private boolean stopped = false;
    
    //constructor
    public ConsumerProcess() 
    {
        
    }
    
    //method to give a cue to stop
    public void stop()
    {
        stopped = true;
    }
    
    @Override
    public void run() 
    {
        try 
        {
            while(!stopped)
            {
                //get the next node to process
                ProcessNode current = (ProcessNode)MinHeap.getInstance().popMin();
                
                //so long as we acquired a node, act on it
                //this sleeps for the timeslice amount of time
                //then it outputs all required information
                if(current != null)
                {
                    Thread.sleep((current).timeSlice);
                    String curTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
                    System.out.println("Process with ID " + current.processID + " and priority " + current.priority + " completed at " + curTime + ".");
                }
                else
                {
                    System.out.println("Heap empty, resting...");
                    Thread.sleep(1000);
                }
            }
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Project1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}