/**
   A single token from the parsed expression.
*/
public class Token
{
    public static enum Type {
        /** an operator */
        OP,
        /** a variable */
        VAR,
        /** an opening delimiter */
        DELIM_OPEN,
        /** a closing delimiter */
        DELIM_CLOSE,
        /** a number */
        NUM,
        /** expression end */
        END
    };


    private Type type;
    private String content;
    private double value;
        
    public Token(Type type, String content)
    {
        this.type = type;
        this.content = content.trim();
    }

    public Token(double value)
    {
        this.type = Type.NUM;
        this.value = value;
    }

    /**
       @return token type
    */
    public Type getType()
    {
        return type;
    }

    /**
       @return raw String token contents for all types other than Type.NUM
    */
    public String getText()
    {
        return content;
    }

    /**
       @return numeric value of token with type Type.NUM
    */
    public double getNum()
    {
        return value;
    }

    public String toString()
    {
        String rval="Token[";

        if (type == Type.NUM)
        {
            rval += "NUM: " + value;
        }
        else if (type == Type.END)
        {
            rval +="END";
        }
        else
        {
            rval += type + ": '" + content + "'";
        }

        rval += "]";
        return rval;
    }
}
