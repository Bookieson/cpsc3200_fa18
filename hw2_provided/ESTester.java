import java.util.Scanner;

public class ESTester {
    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);
        ExpressionScanner es = new ExpressionScanner(input);


        while(es.hasNext())
        {
            Token t = es.next();
            System.out.println(t);
        }
        
        
    }
}
