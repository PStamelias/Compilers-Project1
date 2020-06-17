import java.io.InputStream;
import java.io.IOException;

class Calculator {

    private int lookaheadToken;
    private InputStream in;

    public Calculator(InputStream in) throws IOException {
		this.in = in;
		lookaheadToken = in.read();
    }
    private void consume(int symbol) throws IOException, ParseError {
		if (lookaheadToken != symbol)
	    	throw new ParseError((char)symbol);
		lookaheadToken = in.read();
    }
    /*Calc --> expr*/

    private int calc() throws IOException, ParseError {  return expr(); }

    /*expr --> term expr2*/

    private int expr() throws IOException, ParseError {  return expr2(term()); }

    /* term --> factor term2*/

    private int term() throws IOException, ParseError {  return term2(factor()); }
    /*
    	term2 --> * factor term2
    	        | / factor term2
    	        | e
    */
    private int term2(int previous_val) throws IOException,ParseError {
    	char symbol=' ';
    	if(lookaheadToken=='*'||lookaheadToken=='/'){
    		symbol=(char)lookaheadToken;
    		consume(lookaheadToken);
    	}
    	else
    		return previous_val;
    	int value2=factor();
    	int return_val=0;
    	if(symbol=='*')
    		return_val=term2(value2*previous_val);
    	else if(symbol=='/')
    		return_val=term2(previous_val/value2);
    	return return_val;
    }
     /*
    expr2 --> + term expr2
            | - term expr2
            | e 
    */
    private int expr2(int previous_val) throws IOException,ParseError {
    	char symbol=' ';
    	if(lookaheadToken=='+'||lookaheadToken=='-'){
    		symbol=(char)lookaheadToken;
    	}
    	else if(lookaheadToken==-1||lookaheadToken=='\n'||lookaheadToken==')'){
    		return previous_val;
    	}
    	else 
    		throw new ParseError((char)lookaheadToken);
    	consume(lookaheadToken);
    	int value2=term();
    	int return_val=0;
    	if(symbol=='+')
    		return_val=expr2(value2+previous_val);
    	else if(symbol=='-')
    		return_val=expr2(previous_val-value2);
    	return return_val;

    }
    /*
    	factor --> number
    	         | (expr)
   	*/
    private int factor() throws IOException,ParseError {
    	if(lookaheadToken=='('){
    		consume(lookaheadToken);
    		int value_of_expr=expr();
    		if(lookaheadToken!=')')
    			throw new ParseError((char)lookaheadToken);
    		consume(lookaheadToken);
    		return value_of_expr;
    	}
    	else if(lookaheadToken>='0' && lookaheadToken<='9'){
    		int num=0;
    		while(lookaheadToken>='0' && lookaheadToken<='9'){
    			num=num*10+lookaheadToken-'0';
    			consume(lookaheadToken);
    		}
    		return num;
    	}
    	else
    		throw new ParseError((char)lookaheadToken);

    }
   public int eval() throws IOException, ParseError {
   	int rv = calc();
	if (lookaheadToken != '\n' && lookaheadToken != -1)
	    throw new ParseError((char)lookaheadToken);
	return rv;
   }

   public static void main(String args[]){
		try {
		    Calculator parser = new Calculator(System.in);
		    System.out.println(parser.eval());
		}
		catch (IOException e) {
		    System.err.println(e.getMessage());
		}
		catch(ParseError err){
		    System.err.println(err.GetMessage());
		}
	}
}

