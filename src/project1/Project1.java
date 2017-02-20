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
    
    public class ProcessNode implements Comparable
    {
        public int processID, priority, timeSlice;
        
        public ProcessNode(int processID, int priority, int timeSlice)
        {
            this.processID = processID;
            this.priority = priority;
            this.timeSlice = timeSlice;
        }

        @Override
        public int compareTo(Object o) 
        {
            return ((Integer)priority).compareTo(((ProcessNode)o).priority);
        }
    }
    
    public class ConsumerProcess implements Runnable
    {
        MinHeap heap;
        
        public ConsumerProcess(MinHeap heap) 
        {
            this.heap = heap;
        }        

        @Override
        public void run() 
        {
            try 
            {
                ProcessNode current = (ProcessNode)heap.popMin();
                if(current != null)
                {
                    Thread.sleep((current).timeSlice);
                    String curTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
                    System.out.print("Process with ID " + current.processID + " and priority " + current.priority + " completed at " + curTime + ".");
                }
                else
                {
                    System.out.println("Heap empty, resting...");
                    Thread.sleep(10000);
                }
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Project1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public class CreatorProcess implements Runnable
    {
        MinHeap heap;
        int nodeCount = 0;
        
        public CreatorProcess(MinHeap heap) 
        {
            this.heap = heap;
        }
        
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
                    num = rand.nextInt(1000) + 1;
                }
            }
            
            return num;
        }
        
        private int getRandomPriority()
        {
            Random rand = new Random();
            return rand.nextInt(4);
        }
        
        private int getRandomTimeSlice()
        {
            Random rand = new Random();
            return rand.nextInt(5000) + 1000;
        }
        
        private ArrayList<Integer> getIDs()
        {
            ArrayList<Integer> idList = new ArrayList<Integer>();
            for (Object node : heap.getItems())
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
                Random rand = new Random();
                Thread.sleep(rand.nextInt(2000));
                heap.insert(new ProcessNode(getNewProcessID(), getRandomPriority(), getRandomTimeSlice()));
                nodeCount++;
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Project1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
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
        Runnable consumer1 = new ConsumerProcess(heap);
        Runnable consumer2 = new ConsumerProcess(heap);
        
        executor.execute(creator);
        executor.execute(consumer1);
        executor.execute(consumer2);
        
        //check to end here
    }    
}
