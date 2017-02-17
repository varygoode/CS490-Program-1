package project1;

/**
 * MinHeap Data Structure
 * @author Rob Vary
 * @param <E> specifies a particular Comparable type
 */
public class MinHeap<E extends Comparable<? super E>>
{
    public final E[] heap;       //heap array
    private final int max;    //max size
    private int numItems;    //number of items in heap
    
    //constructor
    public MinHeap(E[] heap, int max, int numItems)
    {
        this.heap = heap;
        this.max = max;
        this.numItems = numItems;
        
        //make it a minheap!
        heapify();
    }
    
    //get the position of the parent
    public int parentPos(int childPos)
    {
        return (childPos > 0) ? (childPos-1)/2 : 0;
    }
    
    //get the position for the left child
    public int leftChildPos(int pos)
    {
        return (pos > (numItems-1)/2) ? pos : 2*pos + 1;
    }
    
    //get the position for the right child
    public int rightChildPos(int pos)
    {
        return (pos > (numItems-1)/2) ? pos : 2*pos + 2;
    }
    
    //check to see if an item has a right child
    public boolean hasRightChild(int pos)
    {
        return rightChildPos(pos) < numItems;
    }
    
    //turn our array into a minheap!
    public void heapify()
    {
        for(int i = (numItems/2) - 1; i >= 0; i--)
        {
            correctPos(i);
        }
    }
    
    //insert into the min heap in appropriate position
    public void insert(E item)
    {
        //only insert if there's room
        if(numItems >= max)
        {
            System.out.println("The heap is already full!");
        }
        else
        {
            int curNumItems = numItems++;
            heap[curNumItems] = item;
            
            //as long as there are still items and the parent is bigger, bubble it up
            while((curNumItems > 0) && (heap[curNumItems].compareTo(heap[parentPos(curNumItems)]) < 0))
            {
                swapPositions(curNumItems, parentPos(curNumItems));
                item = heap[parentPos(curNumItems)];
            }
        }
    }
    
    //remove item at specified position
    public E remove(int pos)
    {
        if(pos < 0 || pos >= max)
        {
            System.out.println("The specified position isn't legal in this heap.");
        }
        else if(pos == numItems - 1) //final element, so there's nothing to do extra
        {
            numItems--;
        }
        else
        {
            swapPositions(pos, --numItems); //put it at the bottom and decrese number of items to "delete"
            
            //now fix it if we swapped it with a small value
            while((pos > 0) && (heap[pos].compareTo(heap[parentPos(pos)]) < 0))
            {
                swapPositions(pos, parentPos(pos));
                pos = parentPos(pos);
            }
            
            //just in case it's too large, we will correct its position
            if(numItems != 0)
            {
                correctPos(pos);
            }
        }
        return heap[numItems];
    }
    
    //push a larger item to its proper position
    private void correctPos(int pos)
    {
        if(pos < 0 || pos >= max)
        {
            System.out.println("The specified position isn't legal in this heap.");
        }
        else
        {
            while(!isLeaf(pos))
            {
                int childPos = leftChildPos(pos);
                if((childPos < (numItems - 1))
                    && (heap[childPos].compareTo(heap[childPos + 1]) > 0))
                {
                    childPos++; //childPos will be the position of the child with greatest value
                }
                //break loop if the item we're on is the same or smaller than next
                if(heap[pos].compareTo(heap[childPos]) <= 0)
                {
                    return;
                }
                
                swapPositions(pos, childPos);
                pos = childPos; //move with our item
            }
        }
    }
    
    //is the item at the specified position a leaf?
    public boolean isLeaf(int pos)
    {
        //in this structure, all the leaves are in the last half or the array
        return (pos >= numItems/2) && (pos < numItems);
    }
    
    //get the size
    public int heapSize()
    {
        return numItems;
    }
    
    //remove and return the minimum value
    public E popMin()
    {
        if(numItems <= 0)
        {
            System.out.println("This heap is empty!");
            return null;
        }
        else
        {
            swapPositions(0, --numItems); //swap min with our last value, then decrease numItems
            if(numItems != 0)
            {
                correctPos(0); //now correct the position of our swapped item
            }
        }
        return heap[numItems];
    }
    
    //swap heap items at given positions
    private void swapPositions(int pos1, int pos2)
    {
        E temp = heap[pos1];
        heap[pos1] = heap[pos2];
        heap[pos2] = temp;
    }
    
    public void printHeap()
    {
        for(int i=0; i < numItems; i++)
        {
            if(!isLeaf(i))
            {
                String rightChild = hasRightChild(i) ? "" + heap[rightChildPos(i)] : "None";
                System.out.println("Node: " + heap[i] + ". Left child: " + heap[leftChildPos(i)] + ". Right child: " + rightChild);
            }
            else
            {
                System.out.println("Leaf: " + heap[i] + ". Parent: " + heap[parentPos(i)]);
            }
        }
    }
    
    public void printArray()
    {
        for(int i=0; i < numItems; i++)
        {
            System.out.println(heap[i]);
        }
    }
}