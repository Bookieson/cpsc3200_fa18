public class Tester1 {
    public static void main(String[] args) 
    {
        SinglyLinkedList<String> myList = new SinglyLinkedList<>();

        myList.add("a");
        myList.add("b");
        myList.add("c");

        
        System.out.println("loop 1 -- " + myList.size());
        for (String s : myList)
        {
            System.out.println(s);
        }

        System.out.println("loop 2 (in reverse) -- " + myList.size());
        for (String s : myList.inReverse())
        {
            System.out.println(s);
        }



        myList.add(0, "X");
        myList.remove();
        System.out.println("loop 3 (added X at the beginning, removed last) -- " + myList.size());
        for (String s : myList)
        {
            System.out.println(s);
        }

        System.out.println("loop 4 (in reverse) -- " + myList.size());
        for (String s : myList.inReverse())
        {
            System.out.println(s);
        }

        myList.add(myList.size(), "ZZZZZ");
        System.out.println("loop 5 (addition at end using add(X)) -- " + myList.size());
        for (String s : myList)
        {
            System.out.println(s);
        }

        myList.remove(0);
        System.out.println("loop 6 (removed 0  using remove(int)) -- " + myList.size());
        for (String s : myList)
        {
            System.out.println(s);
        }

        
        System.out.println("loop 7 (using get) -- " + myList.size() );
        for (int i=0; i<myList.size(); i++)
        {
            System.out.println(i + " -- " + myList.get(i));
        }

        System.out.println("iterator remove() test");

        SinglyLinkedList<Integer> intList=new SinglyLinkedList<>();
        for (int i=0; i<20; i++)
        {
            intList.add(i);
        }
        System.out.println("size before remove: " + intList.size());
        for (int i=0; i<intList.size(); i++)
        {
            System.out.println(i + " - " + intList.get(i));
        }

        java.util.Iterator<Integer> it = intList.iterator();
        while (it.hasNext())
        {
            int z = it.next();
            if (z % 5 != 0)     // remove non-multiples of 5
            {
                it.remove();
            }
        }

        System.out.println("loop 8 -- post iterator/remove in reverse -- " + intList.size());
        for (int i : intList.inReverse())
        {
            System.out.println(i);
        }

    }
}
