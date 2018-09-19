/**
   A generic SinglyLinkedList class for you to modify and analyze
*/
public class SinglyLinkedList<T> implements Iterable<T>{

    // since this is an inner class (not a static nested class), we can access
    // T
    private class Node
    {
        T value;
        Node next;

        Node(T value, Node next)
        {
            this.value=value;
            this.next=next;
        }

        Node() { }
    }

    private int count;          // number of items in the list
    private Node head;          // pointer to dummy node at head of list

    private Node tail;          // This member variable keeps track of the
                                // current end of the list.  Note: if
                                // tail==head, this list is empty...
    
    public SinglyLinkedList()
    {
        head = new Node();      // dummy node
        tail = head;
    }


    /**
     * Add a value to the end of the list
     * @param value the value to be added

     *     This performs in constant time with respect to the length of the list.
    */
    public void add(T value)
    {
        tail.next = new Node(value, null);
        tail=tail.next;
        count++;
    }
    
    /**
     * Add a value to the list at position index
     * @param index the desired location
     * @param value the value to be added
     *
     * Note: this throws an exception if index is invalid
     *
     * Food for thought:  How does the performance of this method depend on index?
     */
    public void add(int index, T value)
    {
        Node c=findNode(index);
        c.next=new Node(value, c.next);

        if (c == tail)          // handle the case where we just added to the end of the list
        {
            tail=tail.next;
        }

        count++;
    }


    /**
       @index the index of the desired list value
       @return the value in the list at the index'th position

       Throws exception if index is invalid
    */
    public T get(int index)
    {
        Node c=findNode(index);
        return c.next.value;
    }
    

    /**
     * @return the number of items in this list
     */
    public int size()
    {
        return count;
    }
    

    /**
     * This removes the last item in the list.
     */
    public void remove()
    {
        if (tail == head)
        {
            throw new RuntimeException("attempt to remove() from an empty list");
        }

        Node n = prevNode(tail);
        n.next=null;
        tail=n;
        
        count--;
    }

    /**
     * This removes the value at `index'.
     * It throws an exception if index is invalid.
     */
    public void remove(int index)
    {
        Node p=findNode(index);

        if (p.next == null)
        {
            throw new RuntimeException("invalid index in remove(index)");
        }
        else
        {
            p.next=p.next.next;

            if (p.next == null)
            {
                tail=p;
            }


            count--;
        }
    }



    /**
     * Returns a Node whose .next member is (or would be, in the case of
     * index==count) the index'th Node in the list
     *
     * Throws an exception if the index is not valid
     *
     * @param index the index of the desired Node
     * @return the Node whose next is the index'th
     */
    private Node findNode(int index)
    {
        // Notice how the use of a 'dummy' node at the head makes this logic
        // simpler

        Node curs=head;
        int p=0;

        if (index < 0)
        {
            throw new RuntimeException("invalid index: " + index);
        }

        while (p<index && curs != null)
        {
            curs=curs.next;
            p++;
        }

        if (curs == null)
        {
            throw new RuntimeException("invalid index: " + index);
        }

        return curs;
    }
        
    /**
     * @return the node whose .next is n, or null if n is not found
     */
    private Node prevNode(Node n)
    {
        Node curs=head;
        while (curs.next != null && curs.next != n)
        {
            curs=curs.next;
        }

        if (curs.next == null)
        {
            return null;
        }
        else
        {
            return curs;
        }
        
    }

    


    /**
       Implements java.lang.Iterable<T>
    */
    public java.util.Iterator<T> iterator()
    {
        return new ForwardIterator();
    }


    /**
       This class provides an iterator that goes through the contents in order.
    */
    private class ForwardIterator implements java.util.Iterator<T>
    {
        // This is legal since it's a proper inner class (not a static nested
        // class)
        Node curs=head;
        Node previous=null;

        public boolean hasNext()
        {
            return curs.next != null;
        }

        // Note: this method has undefined behavior if hasNext() return false
        public T next()
        {
            previous=curs;
            curs=curs.next;
            return curs.value;
        }

        public void remove()
        {
            if (previous==null)
            {
                throw new RuntimeException("invalid use of remove()");
            }

            previous.next=curs.next;

            // this is the new end of the list
            if (previous.next == null)
            {
                tail=previous;
            }

            // to prevent multiple remove's without an intermediary call to
            // next()
            curs=previous;
            previous=null;
            
            count--;
        }

    }

    
    private class ReverseIterator implements java.util.Iterator<T>
    {
        Node nextNode;

        public ReverseIterator()
        {
            nextNode=tail;
        }


        public boolean hasNext()
        {
            return nextNode != head;
        }

        public T next()
        {
            if (nextNode == head)
            {
                throw new RuntimeException("iterated too far");
            }

            T rval = nextNode.value;
            nextNode=prevNode(nextNode);
            return rval;

        }
    }

    public java.util.Iterator<T> reverseIterator()
    {
        return new ReverseIterator();
    }


    /** this was not required, but it's a neat way to take advantage of the reverse iterator.
        e.g.,

        for (String s : myList.inReverse())
        {
             System.out.println(s);
        }
    */
    public Iterable<T> inReverse()
    {
        return new Iterable<T>() {
            public java.util.Iterator<T> iterator() { return new ReverseIterator(); }
            
        };
    }

}
