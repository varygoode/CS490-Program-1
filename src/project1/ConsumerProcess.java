/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rob
 */
public class ConsumerProcess implements Runnable
{
    MinHeap heap;

    public ConsumerProcess(MinHeap heap) 
    {
        this.heap = heap;
    }
    
    private void updateHeap()
    {
        this.heap = MinHeap.getInstance();
    }
    
    @Override
    public void run() 
    {
        try 
        {
            updateHeap();
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
                Thread.sleep(500);
            }
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Project1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}