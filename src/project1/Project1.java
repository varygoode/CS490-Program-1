package project1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Rob Vary
 */
public class Project1 
{
    private static final int MYTHREADS = 30;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        MinHeap heap = MinHeap.getInstance();
        ProcessNode[] heapArray = new ProcessNode[30];
        heap.init(heapArray, 30, 0);
        
        ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
        Runnable creator = new CreatorProcess(heap);
        executor.execute(creator);
        Runnable consumer1 = new ConsumerProcess(heap);
        executor.execute(consumer1);
        Runnable consumer2 = new ConsumerProcess(heap);
        executor.execute(consumer2);
        
        executor.shutdown();
        
        while(!executor.isTerminated())
        {
            
        }
        
        System.out.println("All processes completed.");
    }    
}
