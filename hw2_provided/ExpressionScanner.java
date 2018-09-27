// ExpressionScanner.java
// Craig Tanis
//
// useful code for evaluating simple mathematical expressions

import java.util.Scanner;
import java.util.NoSuchElementException;

public class ExpressionScanner 
{

    private Scanner in;
    private Scanner lineScanner;
    private boolean doEnd=false;;
    private boolean opAllowed=false;


    /**
       Create an ExpressionScanner with a preconfigured Scanner
       @param in Scanner to use for expression
    */
    public ExpressionScanner(Scanner in)
    {
        this.in = in;
    }

    /**
       Create an ExpressionScanner for a single String
       @param expStr String to scan for expression
    */
    public ExpressionScanner(String expStr)
    {
        this.in = new Scanner(expStr);
    }


    /**
       @return true if a token will be returned by next()
    */
    public boolean hasNext()
    {
        if (lineScanner == null)
        {
            if (! in.hasNextLine())
            {
                if (doEnd == false)
                {
                    doEnd=true;
                    return true;
                }

                return false;
            }

            String line = in.nextLine();

            if (line.equals("."))
            {
                return false;
            }

            lineScanner = new Scanner(line);
            opAllowed = false;
        }

        if (lineScanner.hasNext())
        {
            return true;
        }
        else
        {
            if (doEnd == false)
            {
                doEnd=true;
                return true;
            }

            doEnd=false;
            if (in.hasNextLine())
            {
                String line = in.nextLine();

                if (line.equals("."))
                {
                    return false;
                }

                lineScanner = new Scanner(line);
                opAllowed = false;
                return hasNext();
            }
            else
            {
                return false;
            }
        }
    }

    
    /**
       @return the next Token
    */
    public Token next()
    {
        
        String match = null;
        int multiplier=1;

        if (doEnd)
        {
            return new Token(Token.Type.END, "");
        }
        

        if (opAllowed)          // special 'minus' handling
        {
            // see if there's a negative number (leading space before -, but
            // none after, with a digit there... )
            match = lineScanner.findInLine("\\G\\s+\\-(?=\\d)");

            // skip whitespace
            lineScanner.skip("\\s*");
            

            if (match != null)
            {
                // it's a negative number -- read the rest of it down below
                multiplier=-1;
            }
            else
            {
                // if a -  is there, after the previous test, then it's an operator
                match = lineScanner.findInLine("\\G\\-");
                
                if (match != null)
                {
                    switch (match.charAt(0))
                    {
                     case '-':
                         return new Token(Token.Type.OP, match);

                     default:
                         throw new RuntimeException("impossible state: " + match);
                    }

                }
            }
            
        }
        else
        {
            lineScanner.skip("\\s*");
        }

        // don't do special check for minuses next time...
        opAllowed = true;

        // check for delimiters
        match = lineScanner.findInLine("\\G[({\\[\\]})]");

        if (match != null)
        {
            switch (match.charAt(0))
            {
             case '(':
             case '{':
             case '[':
                 return new Token(Token.Type.DELIM_OPEN, match);
             case ')':
             case '}':
             case ']':
                 return new Token(Token.Type.DELIM_CLOSE, match);
             default:
                 throw new RuntimeException("impossible state: "+match);
            }
        }


        // look for number
        match = lineScanner.findInLine("\\G-?\\d+\\.?\\d*");

        if (match != null)
        {
            // apply multiplier if necessary
            return new Token(multiplier * Double.parseDouble(match));
        }


        // look for operator
        match = lineScanner.findInLine("\\G[+*\\-/=]");
        if (match != null)
        {
            opAllowed = false;

            switch (match.charAt(0))
            {
             case '+':
             case '-':
             case '*':
             case '/':
             case '=':
                 return new Token(Token.Type.OP, match);

             default:
                 throw new RuntimeException("impossible state: "+match);
            }
        }



        // look for variable
        match = lineScanner.findInLine("\\G[a-zA-Z]+");

        if (match != null)
        {
            return new Token(Token.Type.VAR, match);
        }

        
        match = lineScanner.next();
        throw new RuntimeException("expression parse error: '" + match + "'");
    }

}
