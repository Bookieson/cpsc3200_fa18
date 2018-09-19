public class Tester2 {


    public static void main(String[] args) 
    {
        // linear time add
        for (long N=10000; N<1000000; N*=2)
        {
            long start = System.nanoTime();
            addN(N);
            long end = System.nanoTime();

            System.out.println("calling add() " + N + " times took " + (end-start)/1e6 + " ms");
        }


        // quadratric time reverse iterator
        for (long N=10000; N<100000; N*=2)
        {

            SinglyLinkedList<Long> lst = addN(N);
            long start = System.nanoTime();
            for (Long l : lst.inReverse())
            {
                // nop
            }
            long end = System.nanoTime();

            System.out.println("reverse iterator on  " + N + " took " + (end-start)/1e6 + " ms");
        }

    }


    public static SinglyLinkedList<Long> addN(long N)
    {
        SinglyLinkedList<Long> slist = new SinglyLinkedList<>();

        for (long i=0; i<N; i++)
        {
            slist.add(i);
        }

        return slist;
    }


}
