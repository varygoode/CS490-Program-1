/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

/**
 *
 * @author Rob
 */
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
    
    @Override
    public String toString()
    {
        return "Process #" + processID + ", Priority " + priority;
    }
}
