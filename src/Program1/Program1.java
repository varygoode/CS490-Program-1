package Program1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CS490 Program 1
 * Professor: Beth Allen
 * Note: Instead of "passing a reference" of the heap through all the threads,
 * I made it a singleton that is referenced as such in each thread.
 * @author Rob Vary
 */
public class Program1 
{
    private static final int MYTHREADS = 30;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        System.out.println("The creator process will create no more than 20 nodes.");
        System.out.println("The consumer processes will be notified to stop running when the heap is empty and the creator process is complete.");

        //get the singleton heap fired up
        MinHeap heap = MinHeap.getInstance();
        
        //prepare an empty array of process nodes so we have a max
        ProcessNode[] heapArray = new ProcessNode[30];
        
        //initialize the MinHeap singleton
        heap.init(heapArray, 30, 0);
        
        //set the idle time for consumer threads in milliseconds
        int idleTime = 1000;
        
        //this is to run the threads in parallel instead of sequentially
        ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
        Runnable consumer1 = new ConsumerProcess("Consumer Process 1", idleTime);
        executor.execute(consumer1);
        Runnable consumer2 = new ConsumerProcess("Consumer Process 2", idleTime);
        executor.execute(consumer2);
        Runnable creator = new CreatorProcess();
        executor.execute(creator);
        
        executor.shutdown();
        
        while(!executor.isTerminated())
        {
            //all the stuff to do is being done in the threads
            //this just ensures the program doesn't quit early
            if(((CreatorProcess)creator).stopped)
            {
                if(MinHeap.getInstance().heapSize() <= 0)
                {
                    //stop the consumer processes
                    ((ConsumerProcess)consumer1).stop();
                    ((ConsumerProcess)consumer2).stop();
                }
            }
        }
        
        MinHeap.getInstance().printHeap();
    }    
}