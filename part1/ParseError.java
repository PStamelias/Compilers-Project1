public class ParseError extends Exception {
	private char character_Error;/*Character that causes the error*/
    public ParseError(char err_char){
    	this.character_Error=(char)err_char;
    }
    public String GetMessage(){
    	String mess;
    	mess="Parse Error-"+"ASCII Character: "+ this.character_Error;
    	return mess;
    }
}