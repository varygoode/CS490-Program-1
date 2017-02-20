/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rob
 */
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
            while(nodeCount < 20)
            {
                Random rand = new Random();
                Thread.sleep(rand.nextInt(2000));
                MinHeap.getInstance().insert(new ProcessNode(getNewProcessID(), getRandomPriority(), getRandomTimeSlice()));
                nodeCount++;
            }
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Project1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}