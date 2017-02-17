/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rob Vary
 */
public class Project1 
{
    public class ProcessNode
    {
        public int processID, priority, timeSlice;
        
        public ProcessNode(int processID, int priority, int timeSlice)
        {
            this.processID = processID;
            this.priority = priority;
            this.timeSlice = timeSlice;
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
                Thread.sleep(1000);
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Project1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public class CreatorProcess implements Runnable
    {
        public CreatorProcess(int processID, int priority, int timeSlice) 
        {
            
        }        

        @Override
        public void run() 
        {
            try 
            {
                Thread.sleep(1000);
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
        
    }
    
}
