/* JFlex example: part of Java language lexer specification */
import java_cup.runtime.*;
/**
%%
/* -----------------Options and Declarations Section----------------- */

/*
   The name of the class JFlex will create will be Lexer.
   Will write the code to the file Lexer.java.
*/
%class Scanner

/*
  The current line number can be accessed with the variable yyline
  and the current column number with the variable yycolumn.
*/
%line
%column

/*
   Will switch to a CUP compatibility mode to interface with a CUP
   generated parser.
*/
%cup

/*
  Declarations

  Code between %{ and %}, both of which must be at the beginning of a
  line, will be copied letter to letter into the lexer class source.
  Here you declare member variables and functions that are used inside
  scanner actions.
*/

%{
StringBuffer stringBuffer = new StringBuffer();
private Symbol symbol(int type) {
   return new Symbol(type, yyline, yycolumn);
}
private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
}
%}

/*
  Macro Declarations

  These declarations are regular expressions that will be used latter
  in the Lexical Rules Section.
*/


/* A line terminator is a \r (carriage return), \n (line feed), or
   \r\n. */
LineTerminator = \r|\n|\r\n

/* White space is a line terminator, space, tab, or line feed. */
WhiteSpace     = {LineTerminator} | [ \t\f]
Identifier = [:jletter:] [:jletterdigit:]*
String_word= \"(.[^\"]*)\"
%%
/* ------------------------Lexical Rules Section---------------------- */

<YYINITIAL> {
/* operators */
 "+"            { return symbol(sym.PLUS);}
 "if"           { return symbol(sym.IF);}
 "else"         { return symbol(sym.ELSE);}
 "{"            { return symbol(sym.LBRUCKET);}
 "reverse"      { return symbol(sym.REVERSE);}
 "prefix"       { return symbol(sym.PREFIX);}
 "}"            { return symbol(sym.RBRUCKET);}
 ","            { return symbol(sym.COMMA);}
 "("            { return symbol(sym.LPAREN);}
 ")"            { return symbol(sym.RPAREN);}
 {Identifier}   { return symbol(sym.ID,yytext());}
 {String_word}  { return symbol(sym.STRING_WORD,yytext());}
 {WhiteSpace}   { /*Do nothing*/}
}
[^]                    { throw new Error("Illegal character <"+yytext()+">"); }
